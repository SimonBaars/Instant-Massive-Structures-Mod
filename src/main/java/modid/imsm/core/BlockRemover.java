package modid.imsm.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRemover extends Block {
	int removeX;
	int removeY;
	int removeZ;
	
	public BlockRemover(int removeX, int removeY, int removeZ){
		super(Block.Properties.create(Material.ROCK));
		this.removeX=removeX;
		this.removeY=removeY;
		this.removeZ=removeZ;
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		BlockPos pos0;
		IBlockState state0;
				for(int x = 0; x<removeX; x++){
					for(int y =0; y<removeY; y++){
						for(int z =0; z<removeZ; z++){
							Block blk = Blocks.AIR;
							   // Make a position.
							   pos0 = new BlockPos(pos.getX()-x, pos.getY()+y , pos.getZ()-z);
							   // Get the default state(basically metadata 0)
							   state0=blk.getDefaultState();
							   // set the block
							   worldIn.setBlockState(pos0, state0);
						}
					}
				}

		return true;
	}
}
