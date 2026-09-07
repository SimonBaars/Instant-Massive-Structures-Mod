package com.simonbaars.imsm.structureloader;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;

/**
 * Converts legacy Minecraft 1.7-1.12 block metadata (id + data value)
 * to modern BlockState properties for Fabric 1.21+.
 */
public class LegacyBlockMetadataConverter {

	/**
	 * Apply legacy metadata to a block's default state.
	 * 
	 * @param block The block instance
	 * @param metadata Legacy metadata value (0-15)
	 * @return BlockState with properties set according to legacy metadata
	 */
	public static BlockState applyMetadata(Block block, int metadata) {
		BlockState state = block.defaultBlockState();
		
		// Stairs (facing + half + shape)
		if (block instanceof StairBlock) {
			return applyStairsMetadata(state, metadata);
		}
		
		// Slabs (type: bottom/top/double)
		if (block instanceof SlabBlock) {
			return applySlabMetadata(state, metadata);
		}
		
		// Logs (axis)
		if (block instanceof RotatedPillarBlock) {
			return applyLogMetadata(state, metadata);
		}
		
		// Pistons (check by properties since class names may vary)
		if (state.hasProperty(DirectionalBlock.FACING)) {
			String blockName = block.toString().toLowerCase();
			if (blockName.contains("piston")) {
				return applyPistonMetadata(state, metadata);
			}
		}
		
		// Doors (half + hinge + facing + open)
		if (block instanceof DoorBlock) {
			return applyDoorMetadata(state, metadata);
		}
		
		// Trapdoors (facing + half + open)
		if (block instanceof TrapDoorBlock) {
			return applyTrapdoorMetadata(state, metadata);
		}
		
		// Fence gates (facing + open)
		if (block instanceof FenceGateBlock) {
			return applyFenceGateMetadata(state, metadata);
		}
		
		// Buttons (facing + powered)
		if (block instanceof ButtonBlock) {
			return applyButtonMetadata(state, metadata);
		}
		
		// Levers (facing + powered)
		if (block instanceof LeverBlock) {
			return applyLeverMetadata(state, metadata);
		}
		
		// Torches and redstone torches (facing)
		if (block instanceof TorchBlock || block instanceof WallTorchBlock) {
			return applyTorchMetadata(state, metadata);
		}
		
		// Rails (shape)
		if (block instanceof BaseRailBlock) {
			return applyRailMetadata(state, metadata);
		}
		
		// Dispensers, droppers, observers (facing)
		if (block instanceof DispenserBlock || block instanceof DropperBlock || block instanceof ObserverBlock) {
			return applyDirectionalMetadata(state, metadata);
		}
		
		// Furnaces (facing)
		if (block instanceof FurnaceBlock) {
			return applyHorizontalFacingMetadata(state, metadata);
		}
		
		// Chests, trapped chests (facing)
		if (block instanceof ChestBlock) {
			return applyHorizontalFacingMetadata(state, metadata);
		}
		
		// Ladders (facing)
		if (block instanceof LadderBlock) {
			return applyHorizontalFacingMetadata(state, metadata);
		}
		
		// Beds (facing + occupied + part)
		if (block instanceof BedBlock) {
			return applyBedMetadata(state, metadata);
		}
		
		// Anvils (facing + damage)
		if (block instanceof AnvilBlock) {
			return applyAnvilMetadata(state, metadata);
		}
		
		return state;
	}
	
	private static BlockState applyStairsMetadata(BlockState state, int metadata) {
		// Bits 0-1: facing (0=E, 1=W, 2=S, 3=N)
		Direction facing = switch (metadata & 0x3) {
			case 0 -> Direction.EAST;
			case 1 -> Direction.WEST;
			case 2 -> Direction.SOUTH;
			default -> Direction.NORTH;
		};
		
		// Bit 2: half (0=bottom, 1=top)
		Half half = ((metadata & 0x4) == 0) ? Half.BOTTOM : Half.TOP;
		
		state = state.setValue(StairBlock.FACING, facing);
		state = state.setValue(StairBlock.HALF, half);
		
		// Shape is computed automatically based on neighbors
		return state;
	}
	
	private static BlockState applySlabMetadata(BlockState state, int metadata) {
		// Bit 3: type (0=bottom, 1=top)
		// In legacy, metadata 8+ meant top slab
		SlabType type = (metadata >= 8) ? SlabType.TOP : SlabType.BOTTOM;
		
		if (state.hasProperty(SlabBlock.TYPE)) {
			state = state.setValue(SlabBlock.TYPE, type);
		}
		
		return state;
	}
	
	private static BlockState applyLogMetadata(BlockState state, int metadata) {
		// Bits 2-3: axis (0=y, 1=x, 2=z, 3=all bark)
		Direction.Axis axis = switch ((metadata >> 2) & 0x3) {
			case 1 -> Direction.Axis.X;
			case 2 -> Direction.Axis.Z;
			default -> Direction.Axis.Y; // 0 or 3
		};
		
		if (state.hasProperty(RotatedPillarBlock.AXIS)) {
			state = state.setValue(RotatedPillarBlock.AXIS, axis);
		}
		
		return state;
	}
	
	private static BlockState applyPistonMetadata(BlockState state, int metadata) {
		// Bits 0-2: facing (0=down, 1=up, 2=north, 3=south, 4=west, 5=east)
		Direction facing = switch (metadata & 0x7) {
			case 0 -> Direction.DOWN;
			case 1 -> Direction.UP;
			case 2 -> Direction.NORTH;
			case 3 -> Direction.SOUTH;
			case 4 -> Direction.WEST;
			case 5 -> Direction.EAST;
			default -> Direction.NORTH;
		};
		
		if (state.hasProperty(DirectionalBlock.FACING)) {
			state = state.setValue(DirectionalBlock.FACING, facing);
		}
		
		return state;
	}
	
	private static BlockState applyDoorMetadata(BlockState state, int metadata) {
		// Bit 3: upper/lower half
		boolean isUpper = (metadata & 0x8) != 0;
		
		if (isUpper) {
			// Upper half: bit 0 = hinge side, bit 1 = powered
			DoorHingeSide hinge = ((metadata & 0x1) == 0) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
			if (state.hasProperty(DoorBlock.HINGE)) {
				state = state.setValue(DoorBlock.HINGE, hinge);
			}
			if (state.hasProperty(DoorBlock.HALF)) {
				state = state.setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER);
			}
		} else {
			// Lower half: bits 0-1 = facing, bit 2 = open
			Direction facing = switch (metadata & 0x3) {
				case 0 -> Direction.SOUTH;
				case 1 -> Direction.WEST;
				case 2 -> Direction.NORTH;
				default -> Direction.EAST;
			};
			boolean open = (metadata & 0x4) != 0;
			
			if (state.hasProperty(DoorBlock.FACING)) {
				state = state.setValue(DoorBlock.FACING, facing);
			}
			if (state.hasProperty(DoorBlock.OPEN)) {
				state = state.setValue(DoorBlock.OPEN, open);
			}
			if (state.hasProperty(DoorBlock.HALF)) {
				state = state.setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER);
			}
		}
		
		return state;
	}
	
	private static BlockState applyTrapdoorMetadata(BlockState state, int metadata) {
		// Bits 0-1: facing
		Direction facing = switch (metadata & 0x3) {
			case 0 -> Direction.SOUTH;
			case 1 -> Direction.NORTH;
			case 2 -> Direction.EAST;
			default -> Direction.WEST;
		};
		
		// Bit 2: open
		boolean open = (metadata & 0x4) != 0;
		
		// Bit 3: top half
		Half half = ((metadata & 0x8) == 0) ? Half.BOTTOM : Half.TOP;
		
		if (state.hasProperty(TrapDoorBlock.FACING)) {
			state = state.setValue(TrapDoorBlock.FACING, facing);
		}
		if (state.hasProperty(TrapDoorBlock.OPEN)) {
			state = state.setValue(TrapDoorBlock.OPEN, open);
		}
		if (state.hasProperty(TrapDoorBlock.HALF)) {
			state = state.setValue(TrapDoorBlock.HALF, half);
		}
		
		return state;
	}
	
	private static BlockState applyFenceGateMetadata(BlockState state, int metadata) {
		// Bits 0-1: facing
		Direction facing = switch (metadata & 0x3) {
			case 0 -> Direction.SOUTH;
			case 1 -> Direction.WEST;
			case 2 -> Direction.NORTH;
			default -> Direction.EAST;
		};
		
		// Bit 2: open
		boolean open = (metadata & 0x4) != 0;
		
		if (state.hasProperty(FenceGateBlock.FACING)) {
			state = state.setValue(FenceGateBlock.FACING, facing);
		}
		if (state.hasProperty(FenceGateBlock.OPEN)) {
			state = state.setValue(FenceGateBlock.OPEN, open);
		}
		
		return state;
	}
	
	private static BlockState applyButtonMetadata(BlockState state, int metadata) {
		// Bits 0-2: facing attachment
		AttachFace face;
		Direction facing;
		
		int facingValue = metadata & 0x7;
		switch (facingValue) {
			case 0: // Floor, facing south
				face = AttachFace.FLOOR;
				facing = Direction.SOUTH;
				break;
			case 1: // East wall
				face = AttachFace.WALL;
				facing = Direction.EAST;
				break;
			case 2: // West wall
				face = AttachFace.WALL;
				facing = Direction.WEST;
				break;
			case 3: // South wall
				face = AttachFace.WALL;
				facing = Direction.SOUTH;
				break;
			case 4: // North wall
				face = AttachFace.WALL;
				facing = Direction.NORTH;
				break;
			case 5: // Ceiling, facing south
				face = AttachFace.CEILING;
				facing = Direction.SOUTH;
				break;
			default:
				face = AttachFace.WALL;
				facing = Direction.NORTH;
		}
		
		if (state.hasProperty(ButtonBlock.FACING)) {
			state = state.setValue(ButtonBlock.FACING, facing);
		}
		if (state.hasProperty(ButtonBlock.FACE)) {
			state = state.setValue(ButtonBlock.FACE, face);
		}
		
		// Bit 3: powered
		boolean powered = (metadata & 0x8) != 0;
		if (state.hasProperty(ButtonBlock.POWERED)) {
			state = state.setValue(ButtonBlock.POWERED, powered);
		}
		
		return state;
	}
	
	private static BlockState applyLeverMetadata(BlockState state, int metadata) {
		// Similar to button
		AttachFace face;
		Direction facing;
		
		int facingValue = metadata & 0x7;
		switch (facingValue) {
			case 0: // Ceiling, Z-axis
			case 7: // Ceiling, X-axis
				face = AttachFace.CEILING;
				facing = (facingValue == 7) ? Direction.EAST : Direction.SOUTH;
				break;
			case 1: // East wall
				face = AttachFace.WALL;
				facing = Direction.EAST;
				break;
			case 2: // West wall
				face = AttachFace.WALL;
				facing = Direction.WEST;
				break;
			case 3: // South wall
				face = AttachFace.WALL;
				facing = Direction.SOUTH;
				break;
			case 4: // North wall
				face = AttachFace.WALL;
				facing = Direction.NORTH;
				break;
			case 5: // Floor, Z-axis
			case 6: // Floor, X-axis
				face = AttachFace.FLOOR;
				facing = (facingValue == 6) ? Direction.EAST : Direction.SOUTH;
				break;
			default:
				face = AttachFace.WALL;
				facing = Direction.NORTH;
		}
		
		if (state.hasProperty(LeverBlock.FACING)) {
			state = state.setValue(LeverBlock.FACING, facing);
		}
		if (state.hasProperty(LeverBlock.FACE)) {
			state = state.setValue(LeverBlock.FACE, face);
		}
		
		// Bit 3: powered
		boolean powered = (metadata & 0x8) != 0;
		if (state.hasProperty(LeverBlock.POWERED)) {
			state = state.setValue(LeverBlock.POWERED, powered);
		}
		
		return state;
	}
	
	private static BlockState applyTorchMetadata(BlockState state, int metadata) {
		// 1=east, 2=west, 3=south, 4=north, 5=standing
		if (metadata >= 1 && metadata <= 4) {
			Direction facing = switch (metadata) {
				case 1 -> Direction.EAST;
				case 2 -> Direction.WEST;
				case 3 -> Direction.SOUTH;
				default -> Direction.NORTH;
			};
			
			if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
				state = state.setValue(HorizontalDirectionalBlock.FACING, facing);
			}
		}
		
		return state;
	}
	
	private static BlockState applyRailMetadata(BlockState state, int metadata) {
		// Rail shapes
		if (state.hasProperty(RailBlock.SHAPE)) {
			RailShape shape = switch (metadata & 0xF) {
				case 0 -> RailShape.NORTH_SOUTH;
				case 1 -> RailShape.EAST_WEST;
				case 2 -> RailShape.ASCENDING_EAST;
				case 3 -> RailShape.ASCENDING_WEST;
				case 4 -> RailShape.ASCENDING_NORTH;
				case 5 -> RailShape.ASCENDING_SOUTH;
				case 6 -> RailShape.SOUTH_EAST;
				case 7 -> RailShape.SOUTH_WEST;
				case 8 -> RailShape.NORTH_WEST;
				case 9 -> RailShape.NORTH_EAST;
				default -> RailShape.NORTH_SOUTH;
			};
			state = state.setValue(RailBlock.SHAPE, shape);
		} else if (state.hasProperty(PoweredRailBlock.SHAPE)) {
			// Powered rails have limited shapes
			RailShape shape = switch (metadata & 0x7) {
				case 0 -> RailShape.NORTH_SOUTH;
				case 1 -> RailShape.EAST_WEST;
				case 2 -> RailShape.ASCENDING_EAST;
				case 3 -> RailShape.ASCENDING_WEST;
				case 4 -> RailShape.ASCENDING_NORTH;
				case 5 -> RailShape.ASCENDING_SOUTH;
				default -> RailShape.NORTH_SOUTH;
			};
			state = state.setValue(PoweredRailBlock.SHAPE, shape);
			
			// Bit 3: powered
			boolean powered = (metadata & 0x8) != 0;
			if (state.hasProperty(PoweredRailBlock.POWERED)) {
				state = state.setValue(PoweredRailBlock.POWERED, powered);
			}
		}
		
		return state;
	}
	
	private static BlockState applyDirectionalMetadata(BlockState state, int metadata) {
		// Full 6-direction facing (dispensers, droppers, observers)
		Direction facing = switch (metadata & 0x7) {
			case 0 -> Direction.DOWN;
			case 1 -> Direction.UP;
			case 2 -> Direction.NORTH;
			case 3 -> Direction.SOUTH;
			case 4 -> Direction.WEST;
			case 5 -> Direction.EAST;
			default -> Direction.NORTH;
		};
		
		if (state.hasProperty(DirectionalBlock.FACING)) {
			state = state.setValue(DirectionalBlock.FACING, facing);
		}
		
		return state;
	}
	
	private static BlockState applyHorizontalFacingMetadata(BlockState state, int metadata) {
		// Horizontal only (furnaces, chests, ladders)
		Direction facing = switch (metadata & 0x7) {
			case 2 -> Direction.NORTH;
			case 3 -> Direction.SOUTH;
			case 4 -> Direction.WEST;
			case 5 -> Direction.EAST;
			default -> Direction.NORTH;
		};
		
		if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
			state = state.setValue(HorizontalDirectionalBlock.FACING, facing);
		}
		
		return state;
	}
	
	private static BlockState applyBedMetadata(BlockState state, int metadata) {
		// Bit 3: head/foot part
		boolean isHead = (metadata & 0x8) != 0;
		
		// Bits 0-1: facing
		Direction facing = switch (metadata & 0x3) {
			case 0 -> Direction.SOUTH;
			case 1 -> Direction.WEST;
			case 2 -> Direction.NORTH;
			default -> Direction.EAST;
		};
		
		if (state.hasProperty(BedBlock.FACING)) {
			state = state.setValue(BedBlock.FACING, facing);
		}
		if (state.hasProperty(BedBlock.PART)) {
			state = state.setValue(BedBlock.PART, isHead ? BedPart.HEAD : BedPart.FOOT);
		}
		
		return state;
	}
	
	private static BlockState applyAnvilMetadata(BlockState state, int metadata) {
		// Bits 0-1: facing
		Direction facing = switch (metadata & 0x3) {
			case 0 -> Direction.SOUTH;
			case 1 -> Direction.WEST;
			case 2 -> Direction.NORTH;
			default -> Direction.EAST;
		};
		
		if (state.hasProperty(AnvilBlock.FACING)) {
			state = state.setValue(AnvilBlock.FACING, facing);
		}
		
		return state;
	}
}
