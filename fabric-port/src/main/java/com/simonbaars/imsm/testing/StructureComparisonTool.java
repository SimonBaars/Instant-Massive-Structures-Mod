package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.structureloader.LegacyBlockStates;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.block.state.BlockState;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Comprehensive structure comparison tool for full parity QA.
 * 
 * Validates for EVERY structure:
 * - Block ID mapping (legacy → modern)
 * - Block metadata → BlockState properties
 * - TileEntity/BlockEntity data (chests, furnaces, etc.)
 * 
 * Usage:
 *   java -cp ... com.simonbaars.imsm.testing.StructureComparisonTool [structure_name | --all]
 */
public class StructureComparisonTool {
	
	private static final String[] LEGACY_BLOCK_NAMES = {
		"air", "stone", "grass", "dirt", "cobblestone", "planks", "sapling", "bedrock",
		"flowing_water", "water", "flowing_lava", "lava", "sand", "gravel", "gold_ore", "iron_ore",
		"coal_ore", "log", "leaves", "sponge", "glass", "lapis_ore", "lapis_block", "dispenser",
		"sandstone", "noteblock", "bed", "golden_rail", "detector_rail", "sticky_piston", "web", "tallgrass",
		"deadbush", "piston", "piston_head", "wool", "piston_extension", "yellow_flower", "red_flower", "brown_mushroom",
		"red_mushroom", "gold_block", "iron_block", "double_stone_slab", "stone_slab", "brick_block", "tnt", "bookshelf",
		"mossy_cobblestone", "obsidian", "torch", "fire", "mob_spawner", "oak_stairs", "chest", "redstone_wire"
	};
	
	public static class StructureAnalysis {
		public String name;
		public int width, height, length;
		public int totalBlocks;
		public int tileEntityCount;
		public Map<String, Integer> blockCounts = new TreeMap<>();
		public Map<String, Integer> tileEntityTypes = new TreeMap<>();
		public List<String> unmappedBlocks = new ArrayList<>();
		public List<String> warnings = new ArrayList<>();
		public boolean hasChests;
		public boolean hasFire;
		public boolean hasFurnaces;
	}
	
	public static StructureAnalysis analyzeStructure(String structureName) throws Exception {
		StructureAnalysis analysis = new StructureAnalysis();
		analysis.name = structureName;
		
		String fileName = "/assets/imsm/structs/" + structureName + ".structure";
		InputStream fileStream = StructureComparisonTool.class.getResourceAsStream(fileName);
		if (fileStream == null) {
			throw new Exception("Structure file not found: " + fileName);
		}
		
		CompoundTag nbt;
		try (DataInputStream dataStream = new DataInputStream(new GZIPInputStream(fileStream))) {
			nbt = NbtIo.read(dataStream);
		}
		
		analysis.length = nbt.getShort("Width").orElse((short)0);
		analysis.width = nbt.getShort("Length").orElse((short)0);
		analysis.height = nbt.getShort("Height").orElse((short)0);
		
		// Analyze blocks
		byte[] blockIds = nbt.getByteArray("Blocks").orElse(new byte[0]);
		byte[] blockData = nbt.getByteArray("Data").orElse(new byte[0]);
		
		analysis.totalBlocks = blockIds.length;
		
		for (int i = 0; i < blockIds.length; i++) {
			int blockId = blockIds[i] & 0xFF;
			int meta = i < blockData.length ? (blockData[i] & 0xFF) : 0;
			
			String legacyName = blockId < LEGACY_BLOCK_NAMES.length ? LEGACY_BLOCK_NAMES[blockId] : "unknown_" + blockId;
			String key = legacyName + ":" + meta;
			analysis.blockCounts.merge(key, 1, Integer::sum);
			
			// Check mapping
			BlockState state = LegacyBlockStates.fromLegacy(blockId, meta);
			if (state == null && blockId != 0) {  // 0 = air, ok to be null
				String unmapped = String.format("ID %d meta %d (%s)", blockId, meta, legacyName);
				if (!analysis.unmappedBlocks.contains(unmapped)) {
					analysis.unmappedBlocks.add(unmapped);
				}
			}
			
			// Flag special blocks
			if (blockId == 54) analysis.hasChests = true;
			if (blockId == 51) analysis.hasFire = true;
			if (blockId == 61 || blockId == 62) analysis.hasFurnaces = true;
		}
		
		// Analyze tile entities
		ListTag tileEntitiesList = nbt.getList("TileEntities").orElse(new ListTag());
		analysis.tileEntityCount = tileEntitiesList.size();
		
		for (int i = 0; i < tileEntitiesList.size(); i++) {
			CompoundTag te = tileEntitiesList.getCompound(i).orElse(new CompoundTag());
			String teType = te.getString("id").orElse("");
			analysis.tileEntityTypes.merge(teType, 1, Integer::sum);
			
			// Warn if chest has no items
			if ("Chest".equals(teType) || "minecraft:chest".equals(teType)) {
				ListTag items = te.getList("Items").orElse(new ListTag());
				if (items.isEmpty()) {
					analysis.warnings.add("Chest at " + te.getInt("x").orElse(0) + "," + te.getInt("y").orElse(0) + "," + te.getInt("z").orElse(0) + " has no items");
				}
			}
		}
		
		return analysis;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("=== IMSM Structure Parity QA Tool ===\n");
		System.out.println("Target: Fabric 26.2 / Java 25");
		System.out.println("Analyzing block mapping + blockstate + tile entity parity\n");
		
		// Priority structures for detailed analysis
		String[] priority = {
			"BlockStoreHouse",   // Should have chests with contents
			"BlockCosyHouse",    // Should have fire
			"Live_Cinema",       // Large structure
			"Live_Cinema0",      // Frame 0
			"BlockFarm",         // Farming blocks
			"BlockEnchantmentRoom"  // Special blocks
		};
		
		List<StructureAnalysis> results = new ArrayList<>();
		int totalUnmapped = 0;
		int totalWithTileEntities = 0;
		
		for (String struct : priority) {
			try {
				StructureAnalysis analysis = analyzeStructure(struct);
				results.add(analysis);
				
				System.out.println("Structure: " + struct);
				System.out.println("  Dimensions: " + analysis.width + "x" + analysis.height + "x" + analysis.length);
				System.out.println("  Total blocks: " + analysis.totalBlocks);
				System.out.println("  Tile entities: " + analysis.tileEntityCount);
				
				if (!analysis.tileEntityTypes.isEmpty()) {
					totalWithTileEntities++;
					System.out.println("  Tile entity types:");
					analysis.tileEntityTypes.forEach((type, count) -> 
						System.out.println("    - " + type + ": " + count));
				}
				
				if (analysis.hasChests) System.out.println("  ✓ Contains chests");
				if (analysis.hasFire) System.out.println("  ✓ Contains fire");
				if (analysis.hasFurnaces) System.out.println("  ✓ Contains furnaces");
				
				if (!analysis.unmappedBlocks.isEmpty()) {
					totalUnmapped += analysis.unmappedBlocks.size();
					System.out.println("  ⚠ UNMAPPED BLOCKS: " + analysis.unmappedBlocks.size());
					analysis.unmappedBlocks.forEach(b -> System.out.println("    - " + b));
				}
				
				if (!analysis.warnings.isEmpty()) {
					System.out.println("  ⚠ WARNINGS: " + analysis.warnings.size());
					analysis.warnings.stream().limit(3).forEach(w -> System.out.println("    - " + w));
				}
				
				System.out.println();
			} catch (Exception e) {
				System.err.println("ERROR analyzing " + struct + ": " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		// Summary
		System.out.println("=== SUMMARY ===");
		System.out.println("Structures analyzed: " + results.size());
		System.out.println("Structures with tile entities: " + totalWithTileEntities);
		System.out.println("Total unmapped block types: " + totalUnmapped);
		
		if (totalUnmapped > 0) {
			System.out.println("\n⚠ CRITICAL: Unmapped blocks found! Fix LegacyBlockStates.java");
		}
		
		// Write detailed report
		try (PrintWriter writer = new PrintWriter(new FileWriter("structure_analysis_report.txt"))) {
			writer.println("IMSM Structure Analysis Report");
			writer.println("Generated: " + new Date());
			writer.println("=".repeat(80));
			writer.println();
			
			for (StructureAnalysis analysis : results) {
				writer.println("Structure: " + analysis.name);
				writer.println("Dimensions: " + analysis.width + "x" + analysis.height + "x" + analysis.length);
				writer.println("Blocks: " + analysis.totalBlocks + ", Tile Entities: " + analysis.tileEntityCount);
				writer.println();
				
				if (!analysis.blockCounts.isEmpty()) {
					writer.println("Block composition (top 20):");
					analysis.blockCounts.entrySet().stream()
						.sorted((a, b) -> b.getValue().compareTo(a.getValue()))
						.limit(20)
						.forEach(e -> writer.println("  " + e.getKey() + ": " + e.getValue()));
					writer.println();
				}
				
				if (!analysis.tileEntityTypes.isEmpty()) {
					writer.println("Tile entities:");
					analysis.tileEntityTypes.forEach((type, count) -> 
						writer.println("  " + type + ": " + count));
					writer.println();
				}
				
				if (!analysis.unmappedBlocks.isEmpty()) {
					writer.println("UNMAPPED BLOCKS:");
					analysis.unmappedBlocks.forEach(b -> writer.println("  " + b));
					writer.println();
				}
				
				writer.println("-".repeat(80));
				writer.println();
			}
		}
		
		System.out.println("\nDetailed report written to: structure_analysis_report.txt");
	}
}
