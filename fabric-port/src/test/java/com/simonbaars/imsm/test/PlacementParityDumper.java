package com.simonbaars.imsm.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonbaars.imsm.structureloader.LegacyBlockStates;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.world.level.block.state.BlockState;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Dumps placement snapshots for all structures in fabric-port/src/main/resources/structures/
 * for parity verification against original mod behavior.
 * <p>
 * Output: fabric-port/parity-dumps/fabric-placement.ndjson
 * Format: One JSON object per line per block: {structure, x, y, z, blockId, properties, tileEntity}
 */
public class PlacementParityDumper {
	private static final Gson GSON = new GsonBuilder().create();
	
	public static void main(String[] args) throws IOException {
		Path structuresDir = Paths.get("fabric-port/src/main/resources/structures");
		Path outputDir = Paths.get("fabric-port/parity-dumps");
		Files.createDirectories(outputDir);
		
		Path outputFile = outputDir.resolve("fabric-placement.ndjson");
		
		List<Path> structureFiles;
		try (Stream<Path> paths = Files.walk(structuresDir)) {
			structureFiles = paths
				.filter(Files::isRegularFile)
				.filter(p -> p.toString().endsWith(".structure"))
				.sorted()
				.toList();
		}
		
		System.out.println("Found " + structureFiles.size() + " structure files");
		System.out.println("Writing placement snapshots to: " + outputFile);
		
		int processedCount = 0;
		int totalBlocks = 0;
		
		try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
			for (Path structurePath : structureFiles) {
				String structureName = structuresDir.relativize(structurePath).toString()
					.replace('\\', '/')
					.replace(".structure", "");
				
				try {
					List<Map<String, Object>> blocks = dumpStructure(structurePath, structureName);
					for (Map<String, Object> block : blocks) {
						writer.write(GSON.toJson(block));
						writer.newLine();
						totalBlocks++;
					}
					processedCount++;
					
					if (processedCount % 100 == 0) {
						System.out.println("Processed " + processedCount + "/" + structureFiles.size() + " structures...");
					}
				} catch (Exception e) {
					System.err.println("Error processing " + structureName + ": " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("\nDump complete!");
		System.out.println("Structures processed: " + processedCount);
		System.out.println("Total blocks dumped: " + totalBlocks);
		System.out.println("Output: " + outputFile.toAbsolutePath());
	}
	
	private static List<Map<String, Object>> dumpStructure(Path structurePath, String structureName) throws IOException {
		List<Map<String, Object>> result = new ArrayList<>();
		
		// Read NBT
		CompoundTag nbt;
		try (var input = Files.newInputStream(structurePath)) {
			nbt = NbtIo.readCompressed(input, NbtAccounter.unlimitedHeap());
		}
		
		// Extract dimensions
		ListTag size = nbt.getList("size").orElse(new ListTag());
		int width = size.getInt(0);
		int height = size.getInt(1);
		int length = size.getInt(2);
		
		// Extract block arrays
		byte[] blocks = nbt.getByteArray("blocks").orElse(new byte[0]);
		byte[] data = nbt.getByteArray("data").orElse(new byte[0]);
		
		// Extract TileEntities
		ListTag tileEntities = nbt.getList("TileEntities").orElse(new ListTag());
		Map<BlockPos, CompoundTag> tileEntityMap = new HashMap<>();
		for (Tag tag : tileEntities) {
			CompoundTag te = (CompoundTag) tag;
			int x = te.getInt("x").orElse(0);
			int y = te.getInt("y").orElse(0);
			int z = te.getInt("z").orElse(0);
			tileEntityMap.put(new BlockPos(x, y, z), te);
		}
		
		// Process each block
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < length; z++) {
				for (int x = 0; x < width; x++) {
					int index = x + (y * length + z) * width;
					int legacyId = blocks[index] & 0xFF;
					int legacyMeta = data[index] & 0xFF;
					
					// Skip air
					if (legacyId == 0) {
						continue;
					}
					
					// Convert to modern BlockState
					BlockState state = LegacyBlockStates.fromLegacy(legacyId, legacyMeta);
					
					Map<String, Object> blockRecord = new LinkedHashMap<>();
					blockRecord.put("structure", structureName);
					blockRecord.put("x", x);
					blockRecord.put("y", y);
					blockRecord.put("z", z);
					blockRecord.put("legacyId", legacyId);
					blockRecord.put("legacyMeta", legacyMeta);
					
					if (state == null) {
						blockRecord.put("blockId", "UNMAPPED");
						blockRecord.put("properties", Map.of());
					} else {
						blockRecord.put("blockId", state.getBlock().builtInRegistryHolder().key().location().toString());
						blockRecord.put("properties", extractProperties(state));
					}
					
					// Add TileEntity data if present
					BlockPos pos = new BlockPos(x, y, z);
					if (tileEntityMap.containsKey(pos)) {
						CompoundTag te = tileEntityMap.get(pos);
						blockRecord.put("tileEntity", extractTileEntitySummary(te));
					}
					
					result.add(blockRecord);
				}
			}
		}
		
		return result;
	}
	
	private static Map<String, String> extractProperties(BlockState state) {
		Map<String, String> props = new LinkedHashMap<>();
		state.getValues().forEach((property, value) -> {
			props.put(property.getName(), value.toString());
		});
		return props;
	}
	
	private static Map<String, Object> extractTileEntitySummary(CompoundTag te) {
		Map<String, Object> summary = new LinkedHashMap<>();
		summary.put("id", te.getString("id").orElse(""));
		
		// Include Items list if present (chests, furnaces)
		if (te.contains("Items")) {
			ListTag items = te.getList("Items").orElse(new ListTag());
			List<Map<String, Object>> itemList = new ArrayList<>();
			for (Tag tag : items) {
				CompoundTag item = (CompoundTag) tag;
				Map<String, Object> itemSummary = new LinkedHashMap<>();
				itemSummary.put("Slot", item.getByte("Slot").orElse((byte)0));
				itemSummary.put("id", item.getShort("id").orElse((short)0));
				itemSummary.put("Count", item.getByte("Count").orElse((byte)0));
				if (item.contains("Damage")) {
					itemSummary.put("Damage", item.getShort("Damage").orElse((short)0));
				}
				itemList.add(itemSummary);
			}
			summary.put("Items", itemList);
		}
		
		return summary;
	}
}
