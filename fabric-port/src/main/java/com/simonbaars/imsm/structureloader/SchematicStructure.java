package com.simonbaars.imsm.structureloader;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class SchematicStructure {
	private final String fileName;
	private Block[][][] blocks;
	private int[][][] blockData;
	private int length;
	private int height;
	private int width;

	public SchematicStructure(String fileName) {
		this.fileName = "/assets/imsm/structs/" + fileName + ".structure";
	}

	public void readFromFile() throws Exception {
		InputStream fileStream = SchematicStructure.class.getResourceAsStream(fileName);
		if (fileStream == null) {
			throw new Exception("Structure file not found: " + fileName);
		}

		CompoundTag nbt;
		try (DataInputStream dataStream = new DataInputStream(new GZIPInputStream(fileStream))) {
			nbt = NbtIo.read(dataStream);
		}

		this.length = nbt.getShort("Width").orElse((short)0);
		this.width = nbt.getShort("Length").orElse((short)0);
		this.height = nbt.getShort("Height").orElse((short)0);

		this.blocks = new Block[height][width][length];
		this.blockData = new int[height][width][length];

		byte[] blockIds = nbt.getByteArray("Blocks").orElse(new byte[0]);
		byte[] blockDataBytes = nbt.getByteArray("Data").orElse(new byte[0]);

		int x = 1, y = 1, z = 1;
		for (int i = 0; i < blockIds.length; i++) {
			int blockId = (short) (blockIds[i] & 0xFF);
			
			Block block = getBlockFromLegacyId(blockId);
			if (block != null) {
				this.blocks[y - 1][z - 1][x - 1] = block;
				this.blockData[y - 1][z - 1][x - 1] = blockDataBytes[i] & 0xFF;
			}

			x++;
			if (x > length) {
				x = 1;
				z++;
			}
			if (z > width) {
				z = 1;
				y++;
			}
		}

		InstantMassiveStructures.LOGGER.info("Loaded structure {} with dimensions {}x{}x{}", 
			fileName, length, height, width);
	}

	public void process(ServerLevel world, int posX, int posY, int posZ) {
		posX -= length / 2 - 1;
		posZ -= width / 2 - 1;

		int blocksPlaced = 0;

		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					Block block = blocks[y][z][x];
					if (block == null) continue;

					BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);
					
					try {
						BlockState state = block.defaultBlockState();
						world.setBlock(pos, state, Block.UPDATE_ALL);
						blocksPlaced++;
					} catch (Exception e) {
						InstantMassiveStructures.LOGGER.warn("Failed to place block at {}: {}", 
							pos, e.getMessage());
					}
				}
			}
		}

		InstantMassiveStructures.LOGGER.info("Placed {} blocks for structure {}", 
			blocksPlaced, fileName);
	}

	private Block getBlockFromLegacyId(int legacyId) {
		String[] legacyMappings = {
			"air", "stone", "grass_block", "dirt", "cobblestone", "oak_planks",
			"oak_sapling", "bedrock", "water", "water", "lava", "lava", "sand",
			"gravel", "gold_ore", "iron_ore", "coal_ore", "oak_log", "oak_leaves",
			"sponge", "glass", "lapis_ore", "lapis_block", "dispenser", "sandstone",
			"note_block", "red_bed", "powered_rail", "detector_rail", "sticky_piston",
			"cobweb", "grass", "dead_bush", "piston", "white_wool", "dandelion",
			"poppy", "brown_mushroom", "red_mushroom", "gold_block", "iron_block",
			"smooth_stone_slab", "bricks", "tnt", "bookshelf", "mossy_cobblestone",
			"obsidian", "torch", "fire", "spawner", "oak_stairs", "chest", "redstone_wire",
			"diamond_ore", "diamond_block", "crafting_table", "wheat", "farmland",
			"furnace", "oak_sign", "oak_door", "ladder", "rail", "cobblestone_stairs",
			"oak_wall_sign", "lever", "stone_pressure_plate", "iron_door", "oak_pressure_plate",
			"redstone_ore", "redstone_torch", "stone_button", "snow", "ice", "snow_block",
			"cactus", "clay", "sugar_cane", "jukebox", "oak_fence", "pumpkin",
			"netherrack", "soul_sand", "glowstone", "nether_portal", "jack_o_lantern",
			"cake", "repeater", "white_stained_glass", "oak_trapdoor", "stone_bricks",
			"brown_mushroom_block", "red_mushroom_block", "iron_bars", "glass_pane",
			"melon", "pumpkin_stem", "melon_stem", "vine", "oak_fence_gate",
			"brick_stairs", "stone_brick_stairs", "mycelium", "lily_pad", "nether_bricks",
			"nether_brick_fence", "nether_brick_stairs", "nether_wart", "enchanting_table",
			"brewing_stand", "cauldron", "end_portal", "end_portal_frame", "end_stone",
			"dragon_egg", "redstone_lamp", "oak_slab", "sandstone_stairs", "emerald_ore",
			"ender_chest", "tripwire_hook", "tripwire", "emerald_block", "spruce_stairs",
			"birch_stairs", "jungle_stairs", "command_block", "beacon", "cobblestone_wall",
			"flower_pot", "carrots", "potatoes", "oak_button", "skeleton_skull",
			"anvil", "trapped_chest", "light_weighted_pressure_plate", "heavy_weighted_pressure_plate",
			"comparator", "daylight_detector", "redstone_block", "nether_quartz_ore", "hopper",
			"quartz_block", "quartz_stairs", "activator_rail", "dropper", "white_terracotta",
			"white_stained_glass_pane", "acacia_leaves", "acacia_log", "acacia_stairs", "dark_oak_stairs",
			"slime_block", "barrier", "iron_trapdoor", "prismarine", "sea_lantern",
			"hay_block", "white_carpet", "terracotta", "coal_block", "packed_ice",
			"sunflower", "standing_banner", "wall_banner", "daylight_detector", "red_sandstone",
			"red_sandstone_stairs", "red_sandstone_slab", "spruce_fence_gate", "birch_fence_gate",
			"jungle_fence_gate", "dark_oak_fence_gate", "acacia_fence_gate", "spruce_fence",
			"birch_fence", "jungle_fence", "dark_oak_fence", "acacia_fence", "spruce_door",
			"birch_door", "jungle_door", "acacia_door", "dark_oak_door", "end_rod",
			"chorus_plant", "chorus_flower", "purpur_block", "purpur_pillar", "purpur_stairs",
			"purpur_slab", "end_stone_bricks", "beetroots", "grass_path", "end_gateway",
			"repeating_command_block", "chain_command_block", "frosted_ice", "magma_block",
			"nether_wart_block", "red_nether_bricks", "bone_block", "structure_void",
			"observer", "white_shulker_box", "orange_shulker_box"
		};

		if (legacyId >= 0 && legacyId < legacyMappings.length) {
			String blockName = legacyMappings[legacyId];
			Identifier targetId = Identifier.fromNamespaceAndPath("minecraft", blockName);
			return BuiltInRegistries.BLOCK.get(targetId).orElse(null).value();
		}

		return null;
	}

	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	/** Clear the same centered bounding box that {@link #process} would occupy. */
	public static void clearBounds(ServerLevel world, int posX, int posY, int posZ,
			int length, int height, int width) {
		posX -= length / 2 - 1;
		posZ -= width / 2 - 1;
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);
					world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}
	}
}
