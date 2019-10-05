package modid.imsm.worldgeneration;

import com.google.common.collect.ImmutableMap;

import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockAtlantis extends Block
{
	
	private final String name;
	private World serverWorld;
	private int nCheckers = 64;

  public BlockAtlantis(int i)
    {
	  super(Block.Properties.create(Material.ROCK));
        this.name="BlockAtlantis";
    }
  
  public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(player.getHeldItemMainhand()!=null && player.getHeldItemMainhand().getItem() == Items.REDSTONE){
			if(worldIn.isRemote){
			nCheckers*=2;
			if(nCheckers>20000){
				nCheckers=1;
			}
			Minecraft.getInstance().player.sendChatMessage("This block will now edit "+nCheckers+" rows of landscape");
			
			}
  	} else {
  if (worldIn.isRemote) {
    this.serverWorld = worldIn;
  } else {
	  //System.out.println("Processing new block");
  	BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
  	//AtlantisThread loadThread = new AtlantisThread(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld);
  	IMSM.eventHandler.creators.add(new AtlantisCreator(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld));
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
