package com.simonbaars.imsm.structureloader;

import com.mojang.serialization.Dynamic;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

/**
 * Converts schematic numeric block IDs and metadata with Minecraft's own flattening table.
 * The table preserves variants and encoded properties; the adapters below handle names and
 * properties that changed after flattening. Neighbor connections and properties shared between
 * door/plant halves are reconstructed by {@link SchematicStructure} from the complete schematic.
 */
public final class LegacyBlockStates {
	private static final BlockState[] STATES = new BlockState[256 * 16];

	private LegacyBlockStates() {}

	public static BlockState fromLegacy(int id, int meta) {
		if (id < 0 || id > 255) return null;
		int key = id << 4 | (meta & 15);
		BlockState state = STATES[key];
		if (state == null) {
			state = convert(id, meta & 15, key);
			STATES[key] = state;
		}
		return state;
	}

	private static BlockState convert(int id, int meta, int key) {
		// These flattening entries are placeholders: their type/contents lived in tile entities.
		if (id == 144) return skull(meta);
		if (id == 140) return flowerPot(meta);

		Dynamic<?> tag = BlockStateData.getTag(key);
		String name = modernName(tag.get("Name").asString("minecraft:air"));
		if (id == 118 && !tag.get("Properties").get("level").asString("0").equals("0")) {
			name = "minecraft:water_cauldron";
		}
		Block block = BuiltInRegistries.BLOCK.get(Identifier.parse(name))
			.map(holder -> holder.value())
			.orElseThrow(() -> new IllegalArgumentException("Unknown flattened block for " + id + ":" + meta));
		BlockState state = block.defaultBlockState();
		var properties = tag.get("Properties").asMapOpt().result();
		if (properties.isPresent()) {
			for (var entry : properties.get().toList()) {
				String propertyName = entry.getFirst().asString("");
				String value = entry.getSecond().asString("");
				if (block instanceof LeavesBlock) {
					// Legacy check_decay was a pending decay check, not persistence.
					if (propertyName.equals("check_decay")) continue;
					if (propertyName.equals("decayable")) {
						propertyName = "persistent";
						value = Boolean.toString(!Boolean.parseBoolean(value));
					}
				}
				if (block instanceof WallBlock && !propertyName.equals("up")) {
					value = Boolean.parseBoolean(value) ? "low" : "none";
				}
				if (id == 118 && block == Blocks.CAULDRON && propertyName.equals("level")) continue;
				Property<?> property = block.getStateDefinition().getProperty(propertyName);
				if (property == null) {
					throw new IllegalArgumentException("Unknown flattened property " + name + "[" + propertyName + "]");
				}
				state = setProperty(state, property, value);
			}
		}
		return state;
	}

	private static String modernName(String name) {
		return switch (name) {
			case "minecraft:grass" -> "minecraft:short_grass";
			case "minecraft:grass_path" -> "minecraft:dirt_path";
			case "minecraft:melon_block" -> "minecraft:melon";
			case "minecraft:mob_spawner" -> "minecraft:spawner";
			case "minecraft:portal" -> "minecraft:nether_portal";
			case "minecraft:sign" -> "minecraft:oak_sign";
			case "minecraft:wall_sign" -> "minecraft:oak_wall_sign";
			// The old smooth stone slab predates the current ordinary stone slab.
			case "minecraft:stone_slab" -> "minecraft:smooth_stone_slab";
			default -> name.endsWith("_bark") ? name.substring(0, name.length() - 5) + "_wood" : name;
		};
	}

	private static <T extends Comparable<T>> BlockState setProperty(BlockState state, Property<T> property, String value) {
		return state.setValue(property, property.getValue(value).orElseThrow(() ->
			new IllegalArgumentException("Invalid flattened property " + property.getName() + "=" + value)));
	}

	private static BlockState skull(int meta) {
		Direction facing = switch (meta & 7) {
			case 2 -> Direction.NORTH;
			case 3 -> Direction.SOUTH;
			case 4 -> Direction.WEST;
			case 5 -> Direction.EAST;
			default -> null;
		};
		return facing == null ? Blocks.SKELETON_SKULL.defaultBlockState()
			: Blocks.SKELETON_WALL_SKULL.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
	}

	private static BlockState flowerPot(int meta) {
		// Pre-1.8 pots could encode contents in metadata. Later schematics use FlowerPot NBT.
		String name = switch (meta) {
			case 1 -> "potted_poppy";
			case 2 -> "potted_dandelion";
			case 3 -> "potted_oak_sapling";
			case 4 -> "potted_spruce_sapling";
			case 5 -> "potted_birch_sapling";
			case 6 -> "potted_jungle_sapling";
			case 7 -> "potted_red_mushroom";
			case 8 -> "potted_brown_mushroom";
			case 9 -> "potted_cactus";
			case 10 -> "potted_dead_bush";
			case 11 -> "potted_fern";
			case 12 -> "potted_acacia_sapling";
			case 13 -> "potted_dark_oak_sapling";
			default -> "flower_pot";
		};
		return BuiltInRegistries.BLOCK.get(Identifier.withDefaultNamespace(name)).orElseThrow().value().defaultBlockState();
	}
}
