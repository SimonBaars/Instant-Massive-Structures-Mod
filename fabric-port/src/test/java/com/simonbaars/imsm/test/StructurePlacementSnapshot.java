package com.simonbaars.imsm.test;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import java.util.*;

/**
 * Captures a complete placement snapshot for dump/diff comparison.
 * Records every non-air block with full state and tile entity data.
 */
public class StructurePlacementSnapshot {
    public static class BlockRecord {
        public final int x, y, z;
        public final String blockId;
        public final Map<String, String> blockStateProperties;
        public final CompoundTag tileEntityData; // null if none
        public final int legacyId;
        public final int legacyMeta;
        
        public BlockRecord(int x, int y, int z, String blockId, 
                          Map<String, String> properties,
                          CompoundTag teData,
                          int legacyId, int legacyMeta) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.blockId = blockId;
            this.blockStateProperties = properties != null ? new TreeMap<>(properties) : new TreeMap<>();
            this.tileEntityData = teData;
            this.legacyId = legacyId;
            this.legacyMeta = legacyMeta;
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BlockRecord other)) return false;
            return x == other.x && y == other.y && z == other.z &&
                   blockId.equals(other.blockId) &&
                   blockStateProperties.equals(other.blockStateProperties) &&
                   Objects.equals(tileEntityData, other.tileEntityData);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, blockId, blockStateProperties);
        }
        
        public String toCompactString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("(%d,%d,%d) %s", x, y, z, blockId));
            if (!blockStateProperties.isEmpty()) {
                sb.append("[");
                blockStateProperties.forEach((k, v) -> sb.append(k).append("=").append(v).append(","));
                sb.setLength(sb.length() - 1);
                sb.append("]");
            }
            if (tileEntityData != null) {
                sb.append(" +TE");
            }
            return sb.toString();
        }
    }
    
    private final String structureName;
    private final List<BlockRecord> blocks;
    private final int width, height, length;
    
    public StructurePlacementSnapshot(String name, int width, int height, int length) {
        this.structureName = name;
        this.width = width;
        this.height = height;
        this.length = length;
        this.blocks = new ArrayList<>();
    }
    
    public void addBlock(BlockRecord record) {
        blocks.add(record);
    }
    
    public List<BlockRecord> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }
    
    public String getName() {
        return structureName;
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getLength() { return length; }
    
    public int getTotalBlocks() {
        return blocks.size();
    }
    
    public long getAirBlocks() {
        return width * height * length - blocks.size();
    }
}
