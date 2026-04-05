package modid.imsm.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRemover extends Block {
	int removeX;
	int removeY;
	int removeZ;
	
	public BlockRemover(int removeX, int removeY, int removeZ){
		super(Material.ROCK);
		this.removeX=removeX;
		this.removeY=removeY;
		this.removeZ=removeZ;
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		BlockPos pos0;
		IBlockState state0;
				for(int x = 0; x<removeX; x++){
					for(int y =0; y<removeY; y++){
						for(int z =0; z<removeZ; z++){
							Block blk = Blocks.AIR;
							   pos0 = new BlockPos(pos.getX()-x, pos.getY()+y , pos.getZ()-z);
							   state0=blk.getDefaultState();
							   worldIn.setBlockState(pos0, state0);
						}
					}
				}

		return this.getStateFromMeta(meta);
	}
}
