package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.structureloader.LegacyEntities;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.ItemStack;

import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

/** Real WorldEdit entity fixtures: world-to-local translation, hanging anchors and nested items. */
public class LegacyEntitiesTest {
	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		HolderLookup.Provider registries = VanillaRegistries.createLookup();
		BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(registries).forEach(initializer -> initializer.apply());
		Path directory = Path.of("src/main/resources/assets/imsm/structs");
		if (!Files.isDirectory(directory)) directory = Path.of("fabric-port").resolve(directory);
		BlockPos worldOrigin = new BlockPos(-57, 91, 128);
		int entities = 0, pictures = 0, frames = 0, populatedFrames = 0, entityFiles = 0;
		try (var files = Files.list(directory)) {
			for (Path file : files.filter(path -> path.toString().endsWith(".structure")).toList()) {
				CompoundTag schematic = read(file);
				ListTag sources = schematic.getList("Entities").orElse(new ListTag());
				if (sources.isEmpty()) continue;
				entityFiles++;
				BlockPos schematicOrigin = new BlockPos(schematic.getInt("WEOriginX").orElse(0),
					schematic.getInt("WEOriginY").orElse(0), schematic.getInt("WEOriginZ").orElse(0));
				BlockPos offset = worldOrigin.subtract(schematicOrigin);
				for (var entry : sources) {
					CompoundTag source = (CompoundTag) entry;
					CompoundTag unchanged = source.copy();
					CompoundTag modern = LegacyEntities.toWorldTag(source, worldOrigin, schematicOrigin, 99);
					require(source.equals(unchanged), "source mutated: " + file);
					require(!modern.contains("UUID") && !modern.contains("UUIDMost") && !modern.contains("UUIDLeast"),
						"original UUID retained: " + file);
					Identifier id = Identifier.tryParse(modern.getString("id").orElse(""));
					require(id != null && BuiltInRegistries.ENTITY_TYPE.get(id).isPresent(), "unknown entity: " + modern);
					ListTag before = source.getList("Pos").orElseThrow();
					ListTag after = modern.getList("Pos").orElseThrow();
					int[] delta = {offset.getX(), offset.getY(), offset.getZ()};
					for (int axis = 0; axis < 3; axis++) {
						double expected = before.getDouble(axis).orElseThrow() + delta[axis];
						require(Math.abs(after.getDouble(axis).orElseThrow() - expected) < 0.000001,
							"fractional position changed: " + file);
					}
					if (source.contains("TileX")) {
						int[] anchor = modern.getIntArray("block_pos").orElseThrow();
						require(anchor.length == 3 && anchor[0] == source.getInt("TileX").orElseThrow() + offset.getX()
							&& anchor[1] == source.getInt("TileY").orElseThrow() + offset.getY()
							&& anchor[2] == source.getInt("TileZ").orElseThrow() + offset.getZ(),
							"hanging anchor changed: " + file + " " + modern);
					}
					if (source.getString("id").orElse("").equals("Painting")) {
						pictures++;
						require(modern.contains("variant"), "painting variant lost: " + file);
					}
					if (source.getString("id").orElse("").equals("ItemFrame")) {
						frames++;
						if (source.contains("Item")) {
							CompoundTag item = modern.getCompound("Item").orElseThrow();
							ItemStack stack = ItemStack.CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), item)
								.getOrThrow();
							require(!stack.isEmpty(), "framed item lost: " + file);
							populatedFrames++;
						}
					}
					entities++;
				}
			}
		}
		require(entityFiles == 23 && pictures == 2586 && frames == 566 && populatedFrames == 560,
			"entity corpus incomplete");
		System.out.println("Legacy entities passed: " + entities + " entities in " + entityFiles
			+ " structures, " + pictures + " paintings, " + frames + " item frames ("
			+ populatedFrames + " populated).");
	}

	private static CompoundTag read(Path file) throws Exception {
		try (DataInputStream in = new DataInputStream(new GZIPInputStream(Files.newInputStream(file)))) {
			return NbtIo.read(in);
		}
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}
}
