package modid.imsm.core;

import modid.imsm.structureloader.SchematicStructureFabric;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class EventHandlerFabric {
    public final List<StructureCreatorFabric> creators = new ArrayList<>();
    public final List<OutlineCreatorFabric> outlineCreators = new ArrayList<>();
    
    public EventHandlerFabric() {
        // Register server tick event
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // Process structure creators
            List<StructureCreatorFabric> toRemove = new ArrayList<>();
            for (StructureCreatorFabric creator : creators) {
                if (creator.tick()) {
                    toRemove.add(creator);
                }
            }
            creators.removeAll(toRemove);
            
            // Process outline creators
            List<OutlineCreatorFabric> outlinesToRemove = new ArrayList<>();
            for (OutlineCreatorFabric outline : outlineCreators) {
                if (outline.tick()) {
                    outlinesToRemove.add(outline);
                }
            }
            outlineCreators.removeAll(outlinesToRemove);
        });
    }
    
    public void addStructureCreator(String structureName, int x, int y, int z, boolean doReplaceAir, ServerWorld world) {
        creators.add(new StructureCreatorFabric(structureName, x, y, z, doReplaceAir, world));
    }
    
    public void addOutlineCreator(String structureName, BlockPos pos, int modifierX, int modifierY, int modifierZ, ServerWorld world) {
        outlineCreators.add(new OutlineCreatorFabric(structureName, pos, modifierX, modifierY, modifierZ, world));
    }
    
    public void addLiveStructureCreator(String structureName, BlockPos pos, boolean doReplaceAir, 
                                       int amountOfSlides, int waitTime, boolean doLoop, ServerWorld world) {
        // For now, just place the structure - animations would need more complex implementation
        creators.add(new StructureCreatorFabric(structureName, pos.getX(), pos.getY(), pos.getZ(), doReplaceAir, world));
    }
}

class StructureCreatorFabric {
    final String structureName;
    final int x, y, z;
    final boolean doReplaceAir;
    final ServerWorld world;
    private int progress = 0;
    private SchematicStructureFabric structure;
    
    public StructureCreatorFabric(String structureName, int x, int y, int z, boolean doReplaceAir, ServerWorld world) {
        this.structureName = structureName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.doReplaceAir = doReplaceAir;
        this.world = world;
        
        // Load structure
        this.structure = new SchematicStructureFabric(structureName + ".structure", doReplaceAir);
        this.structure.readFromFile();
    }
    
    public boolean tick() {
        if (structure == null || !structure.isValid()) {
            return true; // Remove if invalid
        }
        
        // Place blocks progressively (place multiple blocks per tick for better performance)
        int blocksPerTick = 100;
        for (int i = 0; i < blocksPerTick && progress < getTotalBlocks(); i++) {
            placeNextBlock();
            progress++;
        }
        
        return progress >= getTotalBlocks();
    }
    
    private int getTotalBlocks() {
        return structure.length * structure.height * structure.width;
    }
    
    private void placeNextBlock() {
        int blockIndex = progress;
        int blocksPerLayer = structure.length * structure.width;
        
        int y = blockIndex / blocksPerLayer;
        int remainder = blockIndex % blocksPerLayer;
        int x = remainder / structure.width;
        int z = remainder % structure.width;
        
        structure.placeBlock(world, this.x - x, this.y + y, this.z - z, x, y, z);
    }
}

class OutlineCreatorFabric {
    final String structureName;
    final BlockPos pos;
    final int modifierX, modifierY, modifierZ;
    final ServerWorld world;
    private boolean created = false;
    
    public OutlineCreatorFabric(String structureName, BlockPos pos, int modifierX, int modifierY, int modifierZ, ServerWorld world) {
        this.structureName = structureName;
        this.pos = pos;
        this.modifierX = modifierX;
        this.modifierY = modifierY;
        this.modifierZ = modifierZ;
        this.world = world;
    }
    
    public boolean tick() {
        if (!created) {
            SchematicStructureFabric struct = new SchematicStructureFabric(structureName + ".structure", false);
            if (struct.readFromFile()) {
                // Create outline using glass or similar blocks
                // For now, simplified version
                created = true;
            }
        }
        return false; // Outlines persist
    }
}
