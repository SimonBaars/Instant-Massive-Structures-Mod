package modid.imsm.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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
	
	public Block setCreativeTab(ItemGroup g) {
		Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		BlockPos pos0;
		BlockState state0;
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
