package modid.imsm.core;

import modid.imsm.structureloader.SchematicStructureFabric;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStructureFabric extends Block {
    private final String structureName;
    private boolean hasOutline = false;
    private boolean doReplaceAir;
    private final int modifierX;
    private final int modifierY;
    private final int modifierZ;

    public BlockStructureFabric(Settings settings, String name, boolean doReplaceAir, int modifierX, int modifierY, int modifierZ) {
        super(settings);
        this.structureName = name;
        this.doReplaceAir = doReplaceAir;
        this.modifierX = modifierX;
        this.modifierY = modifierY;
        this.modifierZ = modifierZ;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.getItem() == Items.REDSTONE) {
            // Show outline
            if (world instanceof ServerWorld serverWorld) {
                IMSMNew.eventHandler.addOutlineCreator(structureName, pos, modifierX, modifierY, modifierZ, serverWorld);
                hasOutline = true;
            }
        } else if (heldItem.getItem() == Items.BOOK) {
            // Toggle air replacement
            doReplaceAir = !doReplaceAir;
            if (doReplaceAir) {
                player.sendMessage(Text.literal("I will replace all existing blocks in the part I'm gonna spawn in with air now"), false);
            } else {
                player.sendMessage(Text.literal("I won't replace any existing blocks with air"), false);
            }
        } else if (heldItem.getItem() == Items.FIRE_CHARGE) {
            // Remove structure
            if (world instanceof ServerWorld serverWorld) {
                removeStructure(pos, serverWorld, player);
            }
        } else {
            // Place structure
            if (world instanceof ServerWorld serverWorld) {
                BlockPos newPos = pos.add(modifierX, modifierY, modifierZ);
                IMSMNew.eventHandler.addStructureCreator(structureName, newPos.getX(), newPos.getY(), newPos.getZ(), doReplaceAir, serverWorld);
                world.removeBlock(pos, false);
            }
        }

        return ActionResult.SUCCESS;
    }

    private void removeStructure(BlockPos pos, ServerWorld world, PlayerEntity player) {
        SchematicStructureFabric struct = new SchematicStructureFabric(structureName + ".structure", false);
        if (struct.readFromFile()) {
            BlockPos startPos = pos.add(modifierX, modifierY, modifierZ);
            for (int x = 0; x < struct.length; x++) {
                for (int y = 0; y < struct.height; y++) {
                    for (int z = 0; z < struct.width; z++) {
                        BlockPos blockPos = new BlockPos(startPos.getX() - x, startPos.getY() + y, startPos.getZ() - z);
                        world.removeBlock(blockPos, false);
                    }
                }
            }
            player.sendMessage(Text.literal("The last placed structure has been removed."), false);
        } else {
            player.sendMessage(Text.literal("You didn't place a structure to undo."), false);
        }
    }

    public String getStructureName() {
        return structureName;
    }
}
