package com.simonbaars.imsm.blocks;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.core.LiveStructureTicker;
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
			player.sendSystemMessage(Component.literal("Structure outline preview not yet implemented"));
			return InteractionResult.SUCCESS;
		}
		
		if (heldItem.is(Items.BOOK)) {
			player.sendSystemMessage(Component.literal("Air replacement mode toggle not yet implemented"));
			return InteractionResult.SUCCESS;
		}

		if (heldItem.is(Items.FIRE_CHARGE)) {
			player.sendSystemMessage(Component.literal("Structure removal not yet implemented"));
			return InteractionResult.SUCCESS;
		}

		ServerLevel serverWorld = (ServerLevel) world;
		BlockPos spawnPos = pos.offset(modX, modY, modZ);
		
		try {
			world.removeBlock(pos, false);

			if (LiveStructureTicker.isFerrisWheel(structureName)) {
				LiveStructureTicker.startFerrisWheel(serverWorld, spawnPos);
				player.sendSystemMessage(Component.literal(
					"Live Ferris Wheel started (cycling frames every 10 ticks)!"));
				InstantMassiveStructures.LOGGER.info("Player {} started live Ferris Wheel at {}",
					player.getName().getString(), spawnPos);
			} else {
				SchematicStructure structure = new SchematicStructure(structureName);
				structure.readFromFile();
				structure.process(serverWorld, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
				player.sendSystemMessage(Component.literal("Structure '" + structureName +
					"' spawned successfully!"));
				InstantMassiveStructures.LOGGER.info("Player {} spawned structure {} at {}",
					player.getName().getString(), structureName, spawnPos);
			}
		} catch (Exception e) {
			InstantMassiveStructures.LOGGER.error("Failed to spawn structure {}", 
				structureName, e);
			player.sendSystemMessage(Component.literal("Error spawning structure: " + 
				e.getMessage()));
		}

		return InteractionResult.SUCCESS;
	}
}
