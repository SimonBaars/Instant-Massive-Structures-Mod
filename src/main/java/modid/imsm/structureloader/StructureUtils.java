package modid.imsm.structureloader;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class StructureUtils
{
	public static BlockPos getWorldPos(BlockPos structPos, BlockPos structCenter, BlockPos harvestPos)
	{
		return harvestPos.add(structPos).subtract(structCenter);
	}

	public static BlockPos getWorldPos(BlockPos structPos, Vector3d structCenter, Vector3d harvestPos)
	{
		return new BlockPos(getWorldPos(new Vector3d(structPos.getX() + 0.5, structPos.getY(), structPos.getZ() + 0.5), structCenter, harvestPos));
	}

	public static Vector3d getWorldPos(Vector3d structPos, Vector3d structCenter, Vector3d harvestPos)
	{
		return harvestPos.add(structPos).subtract(structCenter);
	}

	public static boolean setBlock(BlockPlacer blockPlacer, BlockState blockState, BlockPos structPos, Vector3d structCenter, Vector3d harvestPos)
	{
		return blockPlacer.add(blockState, StructureUtils.getWorldPos(structPos, structCenter, harvestPos));
	}

	public static void setTileEntity(World world, CompoundNBT tileEntity, BlockPos structPos, Vector3d structCenter, Vector3d harvestPos)
	{
		BlockPos pos = getWorldPos(structPos, structCenter, harvestPos);
		BlockState blockState = world.getBlockState(pos);

		world.removeTileEntity(pos);
		TileEntity blockTileEntity = TileEntity.readTileEntity(blockState, tileEntity);
		if (blockTileEntity == null) {
			return;
		}
		blockTileEntity.setWorldAndPos(world, pos);

		world.setTileEntity(pos, blockTileEntity);
		blockTileEntity.updateContainingBlockInfo();
	}

	public static void setTileEntity(World world, TileEntity tileEntity, Vector3d structCenter, Vector3d harvestPos)
	{
		try{
		BlockPos pos = getWorldPos(tileEntity.getPos(), structCenter, harvestPos);
		world.removeTileEntity(pos);
		tileEntity.setWorldAndPos(world, pos);
		world.setTileEntity(pos, tileEntity);
		tileEntity.updateContainingBlockInfo();
		} catch (ArrayIndexOutOfBoundsException e){
		}
	}

	public static void setEntity(World world, Entity entity, Vector3d structCenter, Vector3d harvestPos)
	{
		Vector3d pos = getWorldPos(entity.getPositionVec(), structCenter, harvestPos);
		entity.setPosition(pos.x, pos.y, pos.z);
		world.addEntity(entity);
	}
}
