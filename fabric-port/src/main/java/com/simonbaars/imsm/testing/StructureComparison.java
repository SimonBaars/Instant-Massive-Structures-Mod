package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.structureloader.LegacyBlockStates;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.block.state.BlockState;

import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Comprehensive structure comparison tool for QA.
 * Validates block mapping, metadata, and tile entity conversion.
 */
public class StructureComparison {
	
	public static class StructureSnapshot {
		public String name;
		public int width, height, length;
		public Map<String, BlockInfo> blocks = new LinkedHashMap<>();
		public Map<String, TileEntityInfo> tileEntities = new LinkedHashMap<>();
		public List<String> warnings = new ArrayList<>();
		
		public static class BlockInfo {
			public int legacyId, metadata;
			public String legacyName;
			public String modernBlock;
			public String modernState;
			
			@Override
			public String toString() {
				return String.format("%s:%d -> %s%s", legacyName, metadata, modernBlock,
					modernState != null && !modernState.isEmpty() ? "[" + modernState + "]" : "");
			}
		}
		
		public static class TileEntityInfo {
			public String type;
			public CompoundTag nbt;
			public Map<String, Object> summary = new LinkedHashMap<>();
			
			@Override
			public String toString() {
				return type + (summary.isEmpty() ? "" : " " + summary);
			}
		}
	}
	
	public static StructureSnapshot analyzeStructure(String structureName) throws Exception {
		StructureSnapshot snapshot = new StructureSnapshot();
		snapshot.name = structureName;
		
		String fileName = "/assets/imsm/structs/" + structureName + ".structure";
		InputStream fileStream = StructureComparison.class.getResourceAsStream(fileName);
		if (fileStream == null) {
			throw new Exception("Structure file not found: " + fileName);
		}
		
		CompoundTag nbt;
		try (DataInputStream dataStream = new DataInputStream(new GZIPInputStream(fileStream))) {
			nbt = NbtIo.read(dataStream);
		}
		
		snapshot.length = nbt.getShort("Width").orElse((short)0);
		snapshot.width = nbt.getShort("Length").orElse((short)0);
		snapshot.height = nbt.getShort("Height").orElse((short)0);
		
		// Read blocks
		byte[] blockIds = nbt.getByteArray("Blocks").orElse(new byte[0]);
		byte[] blockData = nbt.getByteArray("Data").orElse(new byte[0]);
		
		int x = 1, y = 1, z = 1;
		for (int i = 0; i < blockIds.length; i++) {
			int blockId = blockIds[i] & 0xFF;
			int meta = i < blockData.length ? (blockData[i] & 0xFF) : 0;
			
			String pos = String.format("%d,%d,%d", x - 1, y - 1, z - 1);
			StructureSnapshot.BlockInfo info = new StructureSnapshot.BlockInfo();
			info.legacyId = blockId;
			info.metadata = meta;
			info.legacyName = getLegacyBlockName(blockId);
			
			// Convert using LegacyBlockStates
			BlockState state = LegacyBlockStates.fromLegacy(blockId, meta);
			if (state != null) {
				info.modernBlock = state.getBlock().toString();
				info.modernState = state.toString().replaceFirst("^Block\\{[^}]+\\}\\[", "").replaceFirst("\\]$", "");
			} else {
				info.modernBlock = "NULL";
				info.modernState = "";
				snapshot.warnings.add(String.format("Unmapped block at %s: ID %d meta %d", pos, blockId, meta));
			}
			
			snapshot.blocks.put(pos, info);
			
			x++;
			if (x > snapshot.length) {
				x = 1;
				z++;
			}
			if (z > snapshot.width) {
				z = 1;
				y++;
			}
		}
		
		// Read tile entities
		ListTag tileEntitiesList = nbt.getList("TileEntities").orElse(new ListTag());
		for (int i = 0; i < tileEntitiesList.size(); i++) {
			CompoundTag te = tileEntitiesList.getCompound(i).orElse(new CompoundTag());
			int teX = te.getInt("x").orElse(0);
			int teY = te.getInt("y").orElse(0);
			int teZ = te.getInt("z").orElse(0);
			String pos = String.format("%d,%d,%d", teX, teY, teZ);
			
			StructureSnapshot.TileEntityInfo info = new StructureSnapshot.TileEntityInfo();
			info.type = te.getString("id").orElse("");
			info.nbt = te;
			
			// Summarize important data
			if ("Chest".equals(info.type) || "minecraft:chest".equals(info.type)) {
				ListTag items = te.getList("Items").orElse(new ListTag());
				info.summary.put("items", items.size());
				if (items.size() > 0) {
					List<String> itemSummary = new ArrayList<>();
					for (int j = 0; j < Math.min(3, items.size()); j++) {
						CompoundTag item = items.getCompound(j).orElse(new CompoundTag());
						String itemId = item.getString("id").orElse("");
						int count = item.getByte("Count").orElse((byte)0);
						itemSummary.add(String.format("%s x%d", itemId, count));
					}
					info.summary.put("sample", itemSummary);
				}
			} else if ("Furnace".equals(info.type) || "minecraft:furnace".equals(info.type)) {
				ListTag items = te.getList("Items").orElse(new ListTag());
				info.summary.put("items", items.size());
				info.summary.put("burnTime", te.getShort("BurnTime").orElse((short)0));
				info.summary.put("cookTime", te.getShort("CookTime").orElse((short)0));
			}
			
			snapshot.tileEntities.put(pos, info);
		}
		
		return snapshot;
	}
	
	public static Map<String, Integer> countBlockTypes(StructureSnapshot snapshot) {
		Map<String, Integer> counts = new LinkedHashMap<>();
		for (StructureSnapshot.BlockInfo block : snapshot.blocks.values()) {
			String key = block.legacyName + ":" + block.metadata;
			counts.merge(key, 1, Integer::sum);
		}
		return counts;
	}
	
	public static Map<String, Integer> countTileEntityTypes(StructureSnapshot snapshot) {
		Map<String, Integer> counts = new LinkedHashMap<>();
		for (StructureSnapshot.TileEntityInfo te : snapshot.tileEntities.values()) {
			counts.merge(te.type, 1, Integer::sum);
		}
		return counts;
	}
	
	private static String getLegacyBlockName(int id) {
		String[] names = {
			"air", "stone", "grass", "dirt", "cobblestone", "planks", "sapling", "bedrock",
			"flowing_water", "water", "flowing_lava", "lava", "sand", "gravel", "gold_ore", "iron_ore",
			"coal_ore", "log", "leaves", "sponge", "glass", "lapis_ore", "lapis_block", "dispenser",
			"sandstone", "noteblock", "bed", "golden_rail", "detector_rail", "sticky_piston", "web", "tallgrass",
			"deadbush", "piston", "piston_head", "wool", "piston_extension", "yellow_flower", "red_flower", "brown_mushroom",
			"red_mushroom", "gold_block", "iron_block", "double_stone_slab", "stone_slab", "brick_block", "tnt", "bookshelf",
			"mossy_cobblestone", "obsidian", "torch", "fire", "mob_spawner", "oak_stairs", "chest", "redstone_wire"
		};
		return id < names.length ? names[id] : "unknown_" + id;
	}
	
	public static void main(String[] args) throws Exception {
		String[] criticalStructures = {
			"BlockStoreHouse",  // Should have chests with contents
			"BlockCosyHouse",   // Should have fire
			"Live_Cinema"       // Large structure with multiple frames
		};
		
		System.out.println("=== IMSM Structure Comparison Report ===\n");
		
		for (String structName : criticalStructures) {
			try {
				System.out.println("Analyzing: " + structName);
				StructureSnapshot snapshot = analyzeStructure(structName);
				
				System.out.println("  Dimensions: " + snapshot.width + "x" + snapshot.height + "x" + snapshot.length);
				System.out.println("  Blocks: " + snapshot.blocks.size());
				System.out.println("  Tile Entities: " + snapshot.tileEntities.size());
				
				if (!snapshot.warnings.isEmpty()) {
					System.out.println("  ⚠ WARNINGS: " + snapshot.warnings.size());
					for (String warning : snapshot.warnings.subList(0, Math.min(5, snapshot.warnings.size()))) {
						System.out.println("    - " + warning);
					}
				}
				
				Map<String, Integer> blockCounts = countBlockTypes(snapshot);
				System.out.println("  Block types (top 10):");
				blockCounts.entrySet().stream()
					.sorted((a, b) -> b.getValue().compareTo(a.getValue()))
					.limit(10)
					.forEach(e -> System.out.println("    " + e.getKey() + ": " + e.getValue()));
				
				if (!snapshot.tileEntities.isEmpty()) {
					System.out.println("  Tile Entities:");
					Map<String, Integer> teCounts = countTileEntityTypes(snapshot);
					teCounts.forEach((type, count) -> System.out.println("    " + type + ": " + count));
					
					// Show sample TEs
					snapshot.tileEntities.values().stream().limit(3).forEach(te -> {
						System.out.println("      Sample: " + te);
					});
				}
				
				System.out.println();
			} catch (Exception e) {
				System.err.println("  ❌ ERROR: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
