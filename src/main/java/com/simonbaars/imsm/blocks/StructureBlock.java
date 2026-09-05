package com.simonbaars.imsm.blocks;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class StructureBlock extends Block {
	private final String structureName;
	private final int modX;
	private final int modY;
	private final int modZ;

	public StructureBlock(Properties settings, String structureName, 
		int modX, int modY, int modZ) {
		super(settings);
		this.structureName = structureName;
		this.modX = modX;
		this.modY = modY;
		this.modZ = modZ;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos,
		Player player, BlockHitResult hit) {
		
		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		}

		ItemStack heldItem = player.getMainHandItem();
		
		if (heldItem.is(Items.REDSTONE)) {
			player.displayClientMessage(Component.literal("Structure outline preview not yet implemented"), false);
			return InteractionResult.SUCCESS;
		}
		
		if (heldItem.is(Items.BOOK)) {
			player.displayClientMessage(Component.literal("Air replacement mode toggle not yet implemented"), false);
			return InteractionResult.SUCCESS;
		}

		if (heldItem.is(Items.FIRE_CHARGE)) {
			player.displayClientMessage(Component.literal("Structure removal not yet implemented"), false);
			return InteractionResult.SUCCESS;
		}

		ServerLevel serverWorld = (ServerLevel) world;
		BlockPos spawnPos = pos.offset(modX, modY, modZ);
		
		try {
			SchematicStructure structure = new SchematicStructure(structureName);
			structure.readFromFile();
			structure.process(serverWorld, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
			
			world.removeBlock(pos, false);
			
			player.displayClientMessage(Component.literal("Structure '" + structureName + 
				"' spawned successfully!"), false);
			
			InstantMassiveStructures.LOGGER.info("Player {} spawned structure {} at {}", 
				player.getName().getString(), structureName, spawnPos);
			
		} catch (Exception e) {
			InstantMassiveStructures.LOGGER.error("Failed to spawn structure {}", 
				structureName, e);
			player.displayClientMessage(Component.literal("Error spawning structure: " + 
				e.getMessage()), false);
		}

		return InteractionResult.SUCCESS;
	}
}
