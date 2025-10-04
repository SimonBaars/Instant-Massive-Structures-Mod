package modid.imsm.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLiveStructureFabric extends Block {
    private final String structureName;
    private final boolean doReplaceAir;
    private final int modifierX, modifierY, modifierZ;
    private final int spawnPosModifierX, spawnPosModifierY, spawnPosModifierZ;
    private final int amountOfSlides;
    private final int waitTime;
    private final boolean doLoop;

    public BlockLiveStructureFabric(Settings settings, String name, boolean doReplaceAir, 
                                   int amountOfSlides, int waitTime,
                                   int modifierX, int modifierY, int modifierZ,
                                   int spawnPosModifierX, int spawnPosModifierY, int spawnPosModifierZ,
                                   boolean doLoop) {
        super(settings);
        this.structureName = name;
        this.doReplaceAir = doReplaceAir;
        this.modifierX = modifierX;
        this.modifierY = modifierY;
        this.modifierZ = modifierZ;
        this.spawnPosModifierX = spawnPosModifierX;
        this.spawnPosModifierY = spawnPosModifierY;
        this.spawnPosModifierZ = spawnPosModifierZ;
        this.amountOfSlides = amountOfSlides;
        this.waitTime = waitTime;
        this.doLoop = doLoop;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (world instanceof ServerWorld serverWorld) {
            BlockPos spawnPos = pos.add(
                modifierX - spawnPosModifierX,
                modifierY - spawnPosModifierY,
                modifierZ - spawnPosModifierZ
            );
            
            // Add live structure creator
            IMSMNew.eventHandler.addLiveStructureCreator(
                structureName, spawnPos, doReplaceAir, amountOfSlides, waitTime, doLoop, serverWorld
            );
            
            world.removeBlock(pos, false);
            
            // Info messages based on structure type
            if (structureName.contains("Plane") || structureName.contains("Ship") || 
                structureName.contains("Airplane") || structureName.contains("Balloon") || 
                structureName.contains("Helicopter")) {
                player.sendMessage(Text.literal("Thanks for choosing SimJoo's Aviation Solutions."), false);
                player.sendMessage(Text.literal("The structure will animate automatically!"), false);
            } else if (structureName.contains("Boat")) {
                player.sendMessage(Text.literal("Thanks for choosing SimJoo's Maritime Solutions."), false);
                player.sendMessage(Text.literal("The structure will animate automatically!"), false);
            } else if (structureName.contains("Bus")) {
                player.sendMessage(Text.literal("Thanks for choosing SimJoo's Bus Depot."), false);
                player.sendMessage(Text.literal("The structure will animate automatically!"), false);
            } else if (structureName.contains("FerrisWheel") || structureName.contains("FreeFall")) {
                player.sendMessage(Text.literal("Enjoy the ride!"), false);
            }
        }

        return ActionResult.SUCCESS;
    }

    public String getStructureName() {
        return structureName;
    }
}
