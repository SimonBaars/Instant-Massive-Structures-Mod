package modid.imsm.worldgeneration;

import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRedstone;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
  
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
  {
		if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemRedstone){
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
