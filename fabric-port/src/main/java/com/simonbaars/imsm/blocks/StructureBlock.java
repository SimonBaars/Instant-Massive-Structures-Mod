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

import java.util.ArrayList;
import java.util.List;

/**
 * Structure spawn block. Held-item interactions match legacy {@code BlockStructure}
 * (static schematics only — legacy {@code BlockLiveStructure} ignores held items):
 * <ul>
 *   <li>Redstone — glass AABB outline preview</li>
 *   <li>Book — toggle replace-air / overlay mode</li>
 *   <li>Fire charge — undo last placed static structure</li>
 * </ul>
 */
public class StructureBlock extends Block {
	/** Last static schematic spawn for fire-charge undo (legacy StructureCreatorServer remove). */
	private static LastPlaced lastPlaced;

	private final String structureName;
	private final int modX;
	private final int modY;
	private final int modZ;
	/** Legacy BlockStructure.doReplaceAir (default true). */
	private boolean doReplaceAir = true;
	private boolean hasOutline = false;
	private List<BlockPos> outlinePositions = new ArrayList<>();

	private record LastPlaced(String name, BlockPos origin, int length, int height, int width) {}

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

		ServerLevel serverWorld = (ServerLevel) world;
		LiveStructureTicker.LiveDef liveDef = LiveStructureTicker.findDefinition(structureName);

		// Legacy BlockLiveStructure: held items do not gate live start.
		if (liveDef != null) {
			return startLive(serverWorld, world, pos, player, liveDef);
		}

		if (heldItem.is(Items.REDSTONE)) {
			return handleRedstoneOutline(serverWorld, pos, player);
		}

		if (heldItem.is(Items.BOOK)) {
			doReplaceAir = !doReplaceAir;
			if (doReplaceAir) {
				player.sendSystemMessage(Component.literal(
					"I will replace all existing blocks in the part I'm gonna spawn in with air now"));
			} else {
				player.sendSystemMessage(Component.literal(
					"I won't replace any existing blocks with air"));
			}
			return InteractionResult.SUCCESS;
		}

		if (heldItem.is(Items.FIRE_CHARGE)) {
			return handleFireChargeUndo(serverWorld, player);
		}

		BlockPos spawnPos = pos.offset(modX, modY, modZ);
		try {
			world.removeBlock(pos, false);
			clearOutline(serverWorld);

			SchematicStructure structure = new SchematicStructure(structureName);
			structure.readFromFile();
			structure.process(serverWorld, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), doReplaceAir);
			lastPlaced = new LastPlaced(structureName, spawnPos.immutable(),
				structure.getLength(), structure.getHeight(), structure.getWidth());
			player.sendSystemMessage(Component.literal("Structure '" + structureName +
				"' spawned successfully!"));
			InstantMassiveStructures.LOGGER.info("Player {} spawned structure {} at {} (replaceAir={})",
				player.getName().getString(), structureName, spawnPos, doReplaceAir);
		} catch (Exception e) {
			InstantMassiveStructures.LOGGER.error("Failed to spawn structure {}",
				structureName, e);
			player.sendSystemMessage(Component.literal("Error spawning structure: " +
				e.getMessage()));
		}

		return InteractionResult.SUCCESS;
	}

	private InteractionResult startLive(ServerLevel serverWorld, Level world, BlockPos pos,
			Player player, LiveStructureTicker.LiveDef liveDef) {
		BlockPos spawnPos = pos.offset(modX, modY, modZ);
		try {
			world.removeBlock(pos, false);
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
							+ " (default fly " + d + ", legacy one-shot). Use /imsm live airplane|plane|balloon|ship1|ship2|flyingheli <n> [loop]."));
				} else {
					player.sendSystemMessage(Component.literal(
						"Live '" + started + "' path: " + pm.defaultDistance()
							+ " blocks +Z (legacy one-shot). Use /imsm live boat|bus|bus2 <n> [loop]."));
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
		} catch (Exception e) {
			InstantMassiveStructures.LOGGER.error("Failed to spawn structure {}",
				structureName, e);
			player.sendSystemMessage(Component.literal("Error spawning structure: " +
				e.getMessage()));
		}
		return InteractionResult.SUCCESS;
	}

	private InteractionResult handleRedstoneOutline(ServerLevel world, BlockPos pos, Player player) {
		try {
			if (hasOutline) {
				clearOutline(world);
				player.sendSystemMessage(Component.literal("Structure outline cleared."));
				return InteractionResult.SUCCESS;
			}
			SchematicStructure structure = new SchematicStructure(structureName);
			structure.readFromFile();
			outlinePositions = structure.showOutline(world, pos.getX(), pos.getY(), pos.getZ(),
				modX, modY, modZ);
			hasOutline = true;
			player.sendSystemMessage(Component.literal(
				"Structure outline preview (" + outlinePositions.size() + " glass). Right-click with redstone again to clear."));
		} catch (Exception e) {
			InstantMassiveStructures.LOGGER.error("Outline failed for {}", structureName, e);
			player.sendSystemMessage(Component.literal("Outline failed: " + e.getMessage()));
		}
		return InteractionResult.SUCCESS;
	}

	private void clearOutline(ServerLevel world) {
		if (!hasOutline && outlinePositions.isEmpty()) {
			return;
		}
		try {
			SchematicStructure structure = new SchematicStructure(structureName);
			structure.removeOutline(world, outlinePositions);
		} catch (Exception ignored) {
			for (BlockPos p : outlinePositions) {
				if (world.getBlockState(p).is(net.minecraft.world.level.block.Blocks.GLASS)) {
					world.setBlock(p, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(),
						Block.UPDATE_ALL);
				}
			}
		}
		outlinePositions = new ArrayList<>();
		hasOutline = false;
	}

	private InteractionResult handleFireChargeUndo(ServerLevel world, Player player) {
		if (lastPlaced == null) {
			player.sendSystemMessage(Component.literal("You didn't place a structure to undo."));
			return InteractionResult.SUCCESS;
		}
		SchematicStructure.clearBounds(world,
			lastPlaced.origin().getX(), lastPlaced.origin().getY(), lastPlaced.origin().getZ(),
			lastPlaced.length(), lastPlaced.height(), lastPlaced.width());
		player.sendSystemMessage(Component.literal("The last placed structure has been removed."));
		InstantMassiveStructures.LOGGER.info("Fire-charge undo cleared '{}' at {}",
			lastPlaced.name(), lastPlaced.origin());
		lastPlaced = null;
		return InteractionResult.SUCCESS;
	}
}
