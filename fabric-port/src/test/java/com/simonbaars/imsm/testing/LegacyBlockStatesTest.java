package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.structureloader.LegacyBlockStates;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.BitSet;

/** Real Minecraft registry assertions; run with {@code ./gradlew runLegacyBlockStatesTest}. */
public final class LegacyBlockStatesTest {
	private static int assertions;

	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		testRepresentativeStates();

		// Every numeric state exercises the explicit modern name/property adapters. Conversion
		// rejects unknown properties rather than silently substituting default block states.
		for (int id = 0; id < 256; id++) {
			for (int meta = 0; meta < 16; meta++) {
				BlockState state = LegacyBlockStates.fromLegacy(id, meta);
				check(state != null, "unresolved state " + id + ":" + meta);
				check(state == LegacyBlockStates.fromLegacy(id, meta | 0xF0), "metadata nibble/cache " + id + ":" + meta);
			}
		}
		check(LegacyBlockStates.fromLegacy(-1, 0) == null, "negative ID is unset");
		check(LegacyBlockStates.fromLegacy(256, 0) == null, "unsupported modded ID");
		testBundledSchematics();
		System.out.println("Legacy block state checks passed: " + assertions + " assertions, 4096 numeric states");
	}

	private static void testRepresentativeStates() {
		expect(1, 2, "polished_granite");
		expect(5, 4, "acacia_planks");
		expect(6, 9, "spruce_sapling", "stage=1");
		expect(12, 1, "red_sand");
		expect(17, 5, "spruce_log", "axis=x");
		expect(17, 13, "spruce_wood");
		expect(18, 4, "oak_leaves", "persistent=true");
		expect(18, 8, "oak_leaves", "persistent=false");
		expect(23, 13, "dispenser", "facing=east", "triggered=true");
		expect(26, 11, "red_bed", "facing=east", "part=head");
		expect(27, 9, "powered_rail", "shape=east_west", "powered=true");
		expect(31, 1, "short_grass");
		expect(35, 14, "red_wool");
		expect(43, 0, "smooth_stone_slab", "type=double");
		expect(43, 8, "smooth_stone");
		expect(43, 9, "smooth_sandstone");
		expect(44, 8, "smooth_stone_slab", "type=top");
		expect(50, 1, "wall_torch", "facing=east");
		expect(53, 5, "oak_stairs", "facing=west", "half=top");
		expect(54, 5, "chest", "facing=east", "type=single");
		expect(55, 11, "redstone_wire", "power=11");
		expect(59, 7, "wheat", "age=7");
		expect(60, 7, "farmland", "moisture=7");
		expect(62, 5, "furnace", "facing=east", "lit=true");
		expect(63, 13, "oak_sign", "rotation=13");
		expect(64, 5, "oak_door", "facing=south", "open=true", "half=lower");
		expect(64, 11, "oak_door", "half=upper", "hinge=right", "powered=true");
		expect(68, 4, "oak_wall_sign", "facing=west");
		expect(69, 6, "lever", "face=floor", "facing=west");
		expect(69, 8, "lever", "face=ceiling", "facing=west", "powered=true");
		expect(75, 4, "redstone_wall_torch", "facing=north", "lit=false");
		expect(77, 5, "stone_button", "face=floor");
		expect(78, 7, "snow", "layers=8");
		expect(86, 1, "carved_pumpkin", "facing=west");
		expect(91, 0, "jack_o_lantern", "facing=south");
		expect(94, 13, "repeater", "facing=west", "delay=4", "powered=true");
		expect(99, 10, "mushroom_stem", "up=false", "down=false", "east=true");
		expect(100, 5, "red_mushroom_block", "up=true", "north=false", "east=false");
		expect(118, 0, "cauldron");
		expect(118, 3, "water_cauldron", "level=3");
		expect(120, 7, "end_portal_frame", "facing=east", "eye=true");
		expect(132, 13, "tripwire", "powered=true", "attached=true", "disarmed=true");
		expect(139, 1, "mossy_cobblestone_wall", "north=none");
		expect(140, 0, "flower_pot");
		expect(140, 11, "potted_fern");
		expect(144, 5, "skeleton_wall_skull", "facing=east");
		expect(145, 8, "damaged_anvil");
		expect(149, 13, "comparator", "facing=west", "mode=subtract", "powered=true");
		expect(154, 12, "hopper", "facing=west", "enabled=false");
		expect(158, 13, "dropper", "facing=east", "triggered=true");
		expect(160, 14, "red_stained_glass_pane");
		expect(176, 11, "white_banner", "rotation=11");
		expect(181, 0, "red_sandstone_slab", "type=double");
		expect(204, 0, "purpur_slab", "type=double");
		expect(205, 8, "purpur_slab", "type=top");
		expect(208, 0, "dirt_path");
		expect(223, 5, "yellow_shulker_box", "facing=east");
		expect(250, 3, "black_glazed_terracotta", "facing=east");
		expect(251, 3, "light_blue_concrete");
		expect(252, 14, "red_concrete_powder");
	}

	private static void testBundledSchematics() throws Exception {
		Path directory = Path.of("src/main/resources/assets/imsm/structs");
		if (!Files.isDirectory(directory)) directory = Path.of("fabric-port").resolve(directory);
		BitSet states = new BitSet(4096);
		int files = 0;
		try (var paths = Files.list(directory)) {
			for (Path file : paths.filter(p -> p.toString().endsWith(".structure")).toList()) {
				try (var input = Files.newInputStream(file)) {
					var nbt = NbtIo.readCompressed(input, NbtAccounter.unlimitedHeap());
					byte[] blocks = nbt.getByteArray("Blocks").orElseThrow();
					byte[] data = nbt.getByteArray("Data").orElseThrow();
					check(blocks.length == data.length, "mismatched block/metadata arrays: " + file);
					for (int i = 0; i < blocks.length; i++) states.set((blocks[i] & 255) << 4 | (data[i] & 15));
					files++;
				}
			}
		}
		check(files >= 952, "complete bundled catalog: " + files);
		for (int key = states.nextSetBit(0); key >= 0; key = states.nextSetBit(key + 1)) {
			BlockState state = LegacyBlockStates.fromLegacy(key >> 4, key & 15);
			check(state != null, "unresolved bundled state " + key);
			check(key >> 4 == 0 || !state.isAir(), "bundled block silently became air: " + (key >> 4) + ":" + (key & 15));
		}
		System.out.println("Bundled schematic coverage: " + files + " structures, " + states.cardinality() + " distinct ID/metadata pairs");
	}

	private static void expect(int id, int meta, String block, String... values) {
		BlockState state = LegacyBlockStates.fromLegacy(id, meta);
		check(BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString().equals("minecraft:" + block),
			id + ":" + meta + " expected " + block + ", got " + state);
		for (String pair : values) {
			String[] parts = pair.split("=", 2);
			Property<?> property = state.getBlock().getStateDefinition().getProperty(parts[0]);
			check(property != null, "missing property " + pair + " in " + state);
			check(valueName(state, property).equals(parts[1]), id + ":" + meta + " expected " + pair + ", got " + state);
		}
	}

	private static <T extends Comparable<T>> String valueName(BlockState state, Property<T> property) {
		return property.getName(state.getValue(property));
	}

	private static void check(boolean success, String message) {
		assertions++;
		if (!success) throw new AssertionError(message);
	}
}
