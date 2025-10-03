package modid.imsm.structureloader;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class SchematicStructureFabric {
    public int length;
    public int width;
    public int height;
    private Block[][][] blocks;
    private int[][][] blockData;
    private final String fileName;
    private final boolean doReplaceAir;
    
    public SchematicStructureFabric(String fileName, boolean doReplaceAir) {
        this.fileName = fileName;
        this.doReplaceAir = doReplaceAir;
    }
    
    public boolean readFromFile() {
        try {
            // Try multiple possible locations for structure files
            File file = findStructureFile();
            if (file == null || !file.exists()) {
                return false;
            }
            
            InputStream is = new FileInputStream(file);
            if (fileName.endsWith(".schematic")) {
                is = new GZIPInputStream(is);
            }
            
            NbtCompound nbt = NbtIo.readCompressed(is);
            is.close();
            
            // Read dimensions
            width = nbt.getShort("Width");
            height = nbt.getShort("Height");
            length = nbt.getShort("Length");
            
            // Read blocks
            byte[] blockIds = nbt.getByteArray("Blocks");
            byte[] blockMeta = nbt.getByteArray("Data");
            
            blocks = new Block[height][width][length];
            blockData = new int[height][width][length];
            
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int index = (y * width + z) * length + x;
                        int blockId = blockIds[index] & 0xFF;
                        int meta = blockMeta[index] & 0xFF;
                        
                        // Convert old block IDs to modern blocks (simplified)
                        blocks[y][z][x] = getBlockFromId(blockId);
                        blockData[y][z][x] = meta;
                    }
                }
            }
            
            return true;
        } catch (Exception e) {
            System.err.println("Error reading structure file: " + fileName);
            e.printStackTrace();
            return false;
        }
    }
    
    private File findStructureFile() {
        String[] possiblePaths = {
            "structures/" + fileName,
            "run/structures/" + fileName,
            "../structures/" + fileName,
            "resources/structures/" + fileName
        };
        
        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
    
    private Block getBlockFromId(int id) {
        // Simplified block ID conversion (would need full mapping for production)
        return switch (id) {
            case 0 -> Blocks.AIR;
            case 1 -> Blocks.STONE;
            case 2 -> Blocks.GRASS_BLOCK;
            case 3 -> Blocks.DIRT;
            case 4 -> Blocks.COBBLESTONE;
            case 5 -> Blocks.OAK_PLANKS;
            case 7 -> Blocks.BEDROCK;
            case 8, 9 -> Blocks.WATER;
            case 10, 11 -> Blocks.LAVA;
            case 12 -> Blocks.SAND;
            case 13 -> Blocks.GRAVEL;
            case 17 -> Blocks.OAK_LOG;
            case 18 -> Blocks.OAK_LEAVES;
            case 20 -> Blocks.GLASS;
            case 24 -> Blocks.SANDSTONE;
            case 35 -> Blocks.WHITE_WOOL;
            case 43 -> Blocks.STONE_SLAB;
            case 44 -> Blocks.STONE_SLAB;
            case 45 -> Blocks.BRICKS;
            case 50 -> Blocks.TORCH;
            case 53 -> Blocks.OAK_STAIRS;
            case 54 -> Blocks.CHEST;
            case 61 -> Blocks.FURNACE;
            case 64 -> Blocks.OAK_DOOR;
            case 65 -> Blocks.LADDER;
            case 67 -> Blocks.COBBLESTONE_STAIRS;
            case 85 -> Blocks.OAK_FENCE;
            case 98 -> Blocks.STONE_BRICKS;
            default -> Blocks.STONE; // Fallback
        };
    }
    
    public void placeBlock(ServerWorld world, int worldX, int worldY, int worldZ, int structX, int structY, int structZ) {
        if (structY >= height || structZ >= width || structX >= length) return;
        
        Block block = blocks[structY][structZ][structX];
        if (block == null) block = Blocks.AIR;
        
        if (!doReplaceAir && block == Blocks.AIR) {
            return; // Skip air if not replacing
        }
        
        BlockPos pos = new BlockPos(worldX, worldY, worldZ);
        BlockState state = block.getDefaultState();
        
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
    }
    
    public boolean isValid() {
        return blocks != null && length > 0 && width > 0 && height > 0;
    }
}
