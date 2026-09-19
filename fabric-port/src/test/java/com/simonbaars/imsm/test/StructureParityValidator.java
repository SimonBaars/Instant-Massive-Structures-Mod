package com.simonbaars.imsm.test;

import com.simonbaars.imsm.structureloader.LegacyBlockStates;
import com.simonbaars.imsm.structureloader.LegacyItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * Comprehensive structure parity validator.
 * Loads all structures, processes through LegacyBlockStates, reports unmapped blocks,
 * metadata preservation issues, and tile entity problems.
 */
public class StructureParityValidator {
    
    private static class LegacyIdMetaPair {
        final int id;
        final int meta;
        
        LegacyIdMetaPair(int id, int meta) {
            this.id = id;
            this.meta = meta;
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof LegacyIdMetaPair p)) return false;
            return id == p.id && meta == p.meta;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(id, meta);
        }
        
        @Override
        public String toString() {
            return String.format("ID:%d Meta:%d", id, meta);
        }
    }
    
    private static class ValidationResult {
        String structureName;
        int totalBlocks;
        int airBlocks;
        int unmappedBlocks;
        int tileEntities;
        Map<LegacyIdMetaPair, Integer> unmappedIdMetas = new HashMap<>();
        Map<Integer, Integer> unmappedLegacyItems = new HashMap<>();
        Map<String, Integer> blockStateCounts = new HashMap<>();
        List<String> warnings = new ArrayList<>();
        
        boolean hasIssues() {
            return unmappedBlocks > 0 || !warnings.isEmpty();
        }
    }
    
    public static void main(String[] args) {
        try {
            System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
            System.out.println("║     IMSM Structure Parity Validator - Comprehensive Analysis     ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
            System.out.println();
            
            Path structDir = Paths.get("src/main/resources/assets/imsm/structs");
            if (!Files.exists(structDir)) {
                System.err.println("ERROR: Structure directory not found: " + structDir);
                System.exit(1);
            }
            
            List<Path> structFiles = Files.list(structDir)
                .filter(p -> p.toString().endsWith(".structure"))
                .sorted()
                .collect(Collectors.toList());
            
            System.out.println("Found " + structFiles.size() + " structure files");
            System.out.println();
            
            List<ValidationResult> results = new ArrayList<>();
            Map<LegacyIdMetaPair, Integer> globalUnmappedIdMetas = new HashMap<>();
            Map<Integer, Integer> globalUnmappedItems = new HashMap<>();
            Set<String> allBlockStates = new TreeSet<>();
            
            int structuresProcessed = 0;
            int structuresWithIssues = 0;
            
            for (Path structFile : structFiles) {
                ValidationResult result = validateStructure(structFile);
                results.add(result);
                structuresProcessed++;
                
                if (result.hasIssues()) {
                    structuresWithIssues++;
                }
                
                result.unmappedIdMetas.forEach((pair, count) -> 
                    globalUnmappedIdMetas.merge(pair, count, Integer::sum));
                
                result.unmappedLegacyItems.forEach((itemId, count) ->
                    globalUnmappedItems.merge(itemId, count, Integer::sum));
                
                allBlockStates.addAll(result.blockStateCounts.keySet());
                
                if (structuresProcessed % 100 == 0) {
                    System.out.println("Processed " + structuresProcessed + " / " + structFiles.size() + " structures...");
                }
            }
            
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println("SUMMARY REPORT");
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println();
            System.out.println("Total structures:            " + structuresProcessed);
            System.out.println("Structures with issues:      " + structuresWithIssues);
            System.out.println("Clean structures:            " + (structuresProcessed - structuresWithIssues));
            System.out.println();
            
            if (!globalUnmappedIdMetas.isEmpty()) {
                System.out.println("═══════════════════════════════════════════════════════════════════");
                System.out.println("UNMAPPED LEGACY BLOCK IDS (CRITICAL)");
                System.out.println("═══════════════════════════════════════════════════════════════════");
                
                List<Map.Entry<LegacyIdMetaPair, Integer>> sortedUnmapped = 
                    globalUnmappedIdMetas.entrySet().stream()
                        .sorted(Map.Entry.<LegacyIdMetaPair, Integer>comparingByValue().reversed())
                        .collect(Collectors.toList());
                
                for (Map.Entry<LegacyIdMetaPair, Integer> entry : sortedUnmapped) {
                    LegacyIdMetaPair pair = entry.getKey();
                    System.out.printf("  Legacy ID:%3d Meta:%2d  ->  NULL (occurrences: %,6d)%n",
                        pair.id, pair.meta, entry.getValue());
                }
                System.out.println();
            }
            
            if (!globalUnmappedItems.isEmpty()) {
                System.out.println("═══════════════════════════════════════════════════════════════════");
                System.out.println("UNMAPPED LEGACY ITEM IDS");
                System.out.println("═══════════════════════════════════════════════════════════════════");
                
                List<Map.Entry<Integer, Integer>> sortedItems =
                    globalUnmappedItems.entrySet().stream()
                        .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                        .limit(20)
                        .collect(Collectors.toList());
                
                for (Map.Entry<Integer, Integer> entry : sortedItems) {
                    System.out.printf("  Legacy Item ID:%3d  ->  AIR (occurrences: %,6d)%n",
                        entry.getKey(), entry.getValue());
                }
                System.out.println();
            }
            
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println("BLOCK STATES IN USE");
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println("Total unique block states: " + allBlockStates.size());
            System.out.println("Sample:");
            allBlockStates.stream().limit(20).forEach(bs -> System.out.println("  " + bs));
            System.out.println();
            
            if (structuresWithIssues > 0) {
                System.out.println("═══════════════════════════════════════════════════════════════════");
                System.out.println("STRUCTURES WITH ISSUES (Top 30)");
                System.out.println("═══════════════════════════════════════════════════════════════════");
                
                results.stream()
                    .filter(ValidationResult::hasIssues)
                    .sorted(Comparator.comparingInt(r -> -r.unmappedBlocks))
                    .limit(30)
                    .forEach(r -> {
                        System.out.printf("  %s: %d unmapped blocks%n", r.structureName, r.unmappedBlocks);
                        r.warnings.stream().limit(3).forEach(w -> System.out.println("    - " + w));
                    });
                System.out.println();
            }
            
            // Write detailed report
            Path reportPath = Paths.get("PARITY_VALIDATION_REPORT.md");
            writeDetailedReport(reportPath, results, globalUnmappedIdMetas, globalUnmappedItems, allBlockStates);
            
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println("Detailed report written to: " + reportPath.toAbsolutePath());
            System.out.println("═══════════════════════════════════════════════════════════════════");
            
            if (structuresWithIssues > 0) {
                System.exit(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static ValidationResult validateStructure(Path structFile) {
        ValidationResult result = new ValidationResult();
        result.structureName = structFile.getFileName().toString().replace(".structure", "");
        
        try (InputStream fis = Files.newInputStream(structFile);
             DataInputStream dis = new DataInputStream(new GZIPInputStream(fis))) {
            
            CompoundTag nbt = NbtIo.read(dis);
            
            int width = nbt.getShort("Width").orElse((short)0);
            int height = nbt.getShort("Height").orElse((short)0);
            int length = nbt.getShort("Length").orElse((short)0);
            
            byte[] blockIds = nbt.getByteArray("Blocks").orElse(new byte[0]);
            byte[] blockData = nbt.getByteArray("Data").orElse(new byte[0]);
            
            result.totalBlocks = blockIds.length;
            result.airBlocks = 0;
            result.unmappedBlocks = 0;
            
            // Validate block mappings
            for (int i = 0; i < blockIds.length; i++) {
                int legacyId = blockIds[i] & 0xFF;
                int meta = i < blockData.length ? (blockData[i] & 0xFF) : 0;
                
                if (legacyId == 0) {
                    result.airBlocks++;
                    continue;
                }
                
                BlockState state = LegacyBlockStates.fromLegacy(legacyId, meta);
                
                if (state == null) {
                    result.unmappedBlocks++;
                    LegacyIdMetaPair pair = new LegacyIdMetaPair(legacyId, meta);
                    result.unmappedIdMetas.merge(pair, 1, Integer::sum);
                } else {
                    String blockStateStr = getBlockStateString(state);
                    result.blockStateCounts.merge(blockStateStr, 1, Integer::sum);
                }
            }
            
            // Validate tile entities
            ListTag tileEntitiesList = nbt.getListOrEmpty("TileEntities", 10);
            result.tileEntities = tileEntitiesList.size();
            
            for (int i = 0; i < tileEntitiesList.size(); i++) {
                CompoundTag te = tileEntitiesList.getCompound(i);
                
                // Check for Items (chest/furnace contents)
                if (te.contains("Items", 9)) {
                    ListTag items = te.getList("Items", 10);
                    for (int j = 0; j < items.size(); j++) {
                        CompoundTag itemTag = items.getCompound(j);
                        if (itemTag.contains("id")) {
                            short legacyItemId = itemTag.getShort("id");
                            var modernItem = LegacyItems.fromLegacyId(legacyItemId);
                            if (modernItem == null || modernItem.equals(net.minecraft.world.item.Items.AIR)) {
                                result.unmappedLegacyItems.merge((int)legacyItemId, 1, Integer::sum);
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            result.warnings.add("Failed to read structure: " + e.getMessage());
        }
        
        return result;
    }
    
    private static String getBlockStateString(BlockState state) {
        StringBuilder sb = new StringBuilder();
        sb.append(BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());
        
        if (!state.getProperties().isEmpty()) {
            sb.append("[");
            boolean first = true;
            for (Property<?> prop : state.getProperties()) {
                if (!first) sb.append(",");
                sb.append(prop.getName()).append("=").append(state.getValue(prop));
                first = false;
            }
            sb.append("]");
        }
        
        return sb.toString();
    }
    
    private static void writeDetailedReport(Path reportPath, List<ValidationResult> results,
                                           Map<LegacyIdMetaPair, Integer> globalUnmapped,
                                           Map<Integer, Integer> unmappedItems,
                                           Set<String> allBlockStates) throws IOException {
        
        try (BufferedWriter writer = Files.newBufferedWriter(reportPath)) {
            writer.write("# IMSM Structure Parity Validation Report\n\n");
            writer.write("**Environment:** Fabric 26.2 / Java 25 / Loader 0.19.5\n");
            writer.write("**Date:** " + new java.util.Date() + "\n\n");
            writer.write("---\n\n");
            
            writer.write("## Executive Summary\n\n");
            int totalStructures = results.size();
            long structuresWithIssues = results.stream().filter(ValidationResult::hasIssues).count();
            long totalBlocks = results.stream().mapToLong(r -> r.totalBlocks).sum();
            long unmappedBlocks = results.stream().mapToLong(r -> r.unmappedBlocks).sum();
            
            writer.write(String.format("- **Total structures analyzed:** %,d\n", totalStructures));
            writer.write(String.format("- **Structures with issues:** %,d\n", structuresWithIssues));
            writer.write(String.format("- **Clean structures:** %,d\n", totalStructures - structuresWithIssues));
            writer.write(String.format("- **Total blocks processed:** %,d\n", totalBlocks));
            writer.write(String.format("- **Unmapped blocks:** %,d\n", unmappedBlocks));
            writer.write(String.format("- **Unique block states in use:** %,d\n\n", allBlockStates.size()));
            
            if (!globalUnmapped.isEmpty()) {
                writer.write("## ❌ CRITICAL: Unmapped Legacy Block IDs\n\n");
                writer.write("These legacy ID+Meta combinations have NO mapping in LegacyBlockStates:\n\n");
                writer.write("| Legacy ID | Meta | Occurrences | Impact |\n");
                writer.write("|-----------|------|-------------|--------|\n");
                
                globalUnmapped.entrySet().stream()
                    .sorted(Map.Entry.<LegacyIdMetaPair, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        try {
                            LegacyIdMetaPair pair = entry.getKey();
                            writer.write(String.format("| %d | %d | %,d | BLOCKS MISSING |\n",
                                pair.id, pair.meta, entry.getValue()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                writer.write("\n");
            } else {
                writer.write("## ✅ Block Mappings: All Legacy IDs Mapped\n\n");
                writer.write("No unmapped legacy block IDs found.\n\n");
            }
            
            if (!unmappedItems.isEmpty()) {
                writer.write("## ⚠️ Unmapped Legacy Item IDs\n\n");
                writer.write("These legacy item IDs in container TEs have no mapping:\n\n");
                writer.write("| Legacy Item ID | Occurrences |\n");
                writer.write("|----------------|-------------|\n");
                
                unmappedItems.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                    .limit(30)
                    .forEach(entry -> {
                        try {
                            writer.write(String.format("| %d | %,d |\n", entry.getKey(), entry.getValue()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                writer.write("\n");
            }
            
            writer.write("## Structures with Issues\n\n");
            if (structuresWithIssues > 0) {
                writer.write("| Structure | Unmapped Blocks | Total Blocks | Tile Entities |\n");
                writer.write("|-----------|-----------------|--------------|---------------|\n");
                
                results.stream()
                    .filter(ValidationResult::hasIssues)
                    .sorted(Comparator.comparingInt(r -> -r.unmappedBlocks))
                    .forEach(r -> {
                        try {
                            writer.write(String.format("| %s | %,d | %,d | %d |\n",
                                r.structureName, r.unmappedBlocks, r.totalBlocks, r.tileEntities));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                writer.write("\n");
            } else {
                writer.write("**None** - All structures validated successfully.\n\n");
            }
            
            writer.write("## Next Steps\n\n");
            if (!globalUnmapped.isEmpty()) {
                writer.write("1. ❌ **CRITICAL**: Add missing legacy ID mappings to LegacyBlockStates\n");
                writer.write("2. Re-run validation after fixes\n");
                writer.write("3. Verify in-game placement matches expectations\n\n");
            } else {
                writer.write("1. ✅ All block IDs mapped\n");
                writer.write("2. ✅ Ready for in-game parity testing\n\n");
            }
        }
    }
}
