package com.simonbaars.imsm.blocks;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.core.LiveStructureTicker;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
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
		return trySpawn(world, pos, player, ItemStack.EMPTY);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos,
		Player player, InteractionHand hand, BlockHitResult hit) {
		return trySpawn(world, pos, player, stack);
	}

	private InteractionResult trySpawn(Level world, BlockPos pos, Player player, ItemStack heldItem) {
		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		}

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

			LiveStructureTicker.LiveDef liveDef = LiveStructureTicker.findDefinition(structureName);
			if (liveDef != null) {
				String started = LiveStructureTicker.startLive(serverWorld, spawnPos, liveDef);
				if (liveDef.isPathMover()) {
					LiveStructureTicker.PathMotion pm = liveDef.path();
					String brand = pm.aviation() ? "Aviation"
						: ("LiveBoat".equals(started) ? "Maritime" : "Bus Depot");
					player.sendSystemMessage(Component.literal(
						"Thanks for choosing SimJoo's " + brand + " Solutions."));
					if (pm.aviation()) {
						int d = pm.defaultDistance();
						player.sendSystemMessage(Component.literal(
							"Live '" + started + "' aviation path: climb "
								+ pm.climbCount() + " → level " + pm.levelStepsForDistance(d)
								+ " → descend " + pm.descendCount()
								+ " (default fly " + d + ", short loop). Use /imsm live airplane|flyingheli <n>."));
					} else {
						player.sendSystemMessage(Component.literal(
							"Live '" + started + "' path: " + pm.defaultDistance()
								+ " blocks +Z (short loop). Use /imsm live boat <n> for custom distance."));
					}
				} else {
					String timing = liveDef.hasVariableWaits()
						? liveDef.frames().length + " frames, variable waits"
						: liveDef.frames().length + " frames every " + liveDef.ticksPerFrame() + " ticks";
					player.sendSystemMessage(Component.literal(
						"Live '" + started + "' started (cycling " + timing + ")!"));
					if ("Live_Fair_FreeFall".equals(started) || "Live_FerrisWheel".equals(started)) {
						player.sendSystemMessage(Component.literal("Use /ride to ride this structure!"));
					}
				}
				InstantMassiveStructures.LOGGER.info("Player {} started live {} at {}",
					player.getName().getString(), started, spawnPos);
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
