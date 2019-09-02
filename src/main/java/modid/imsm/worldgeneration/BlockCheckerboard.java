package modid.imsm.worldgeneration;

import com.google.common.collect.ImmutableMap;

import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCheckerboard extends Block
{
	
	private final String name;
	private int nCheckers = 32;

  public BlockCheckerboard(int i)
    {
	  super(Block.Properties.create(Material.ROCK));
        this.name="BlockCheckerboard";
    }
  
  public Block setCreativeTab(ItemGroup g) {
		Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
  
  @Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
  {
		if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() == Items.REDSTONE){
			if(worldIn.isRemote){
			nCheckers*=2;
			if(nCheckers>2000){
				nCheckers=1;
			}
			Minecraft.getInstance().player.sendChatMessage("This block will now create "+nCheckers+" rows of checkers");
			
			}
  	} else {
  if (!worldIn.isRemote) {
  	//AtlantisThread loadThread = new AtlantisThread(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld);
	  BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
	  IMSM.eventHandler.serverCreators.add(new CheckerboardCreator(pos.getX(), pos.getY(), pos.getZ(), nCheckers));
  	worldIn.setBlockState(newPos, new BlockState(Blocks.AIR, ImmutableMap.of()));
  }
  	}
      return true;
  }

public String getName()
{
  return name;
}
}
