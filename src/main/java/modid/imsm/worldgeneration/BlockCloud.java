package modid.imsm.worldgeneration;

import com.google.common.collect.ImmutableMap;

import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockCloud extends Block
{
	
	private final String name;
	private World serverWorld;
	private int nCheckers = 8;

  public BlockCloud(int i)
    {
	  super(Block.Properties.create(Material.ROCK));
        this.name="BlockCloud";
    }
  
  public Block setCreativeTab(ItemGroup g) {
		//Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
  
  @Override
  public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(player.getHeldItemMainhand()!=null && player.getHeldItemMainhand().getItem() ==  Items.REDSTONE){
			if(worldIn.isRemote){
			nCheckers*=2;
			if(nCheckers>2000){
				nCheckers=1;
			}
			Minecraft.getInstance().player.sendChatMessage("This block will now create "+nCheckers+" rows of checkers");
			
			}
  	} else {
  if (worldIn.isRemote) {
    this.serverWorld = worldIn;
  } else {
  	//AtlantisThread loadThread = new AtlantisThread(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld);
	  BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
	  IMSM.eventHandler.creators.add(new CloudCreator(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld));
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
