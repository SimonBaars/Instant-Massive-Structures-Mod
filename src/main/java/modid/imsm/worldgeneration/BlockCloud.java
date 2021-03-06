package modid.imsm.worldgeneration;

import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockCloud extends Block
{
	
	private final String name;
	private World serverWorld;
	private int nCheckers = 8;

  public BlockCloud(int i)
    {
        super(Material.ROCK);
        this.name="BlockCloud";
    }
  
  @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
  {
		if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemRedstone){
			if(worldIn.isRemote){
			nCheckers*=2;
			if(nCheckers>2000){
				nCheckers=1;
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("This block will now create "+nCheckers+" rows of checkers"));
			
			}
  	} else {
  if (worldIn.isRemote) {
    this.serverWorld = worldIn;
  } else {
  	//AtlantisThread loadThread = new AtlantisThread(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld);
	  BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
	  IMSM.eventHandler.creators.add(new CloudCreator(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld));
  	worldIn.setBlockToAir(newPos);
  }
  	}
      return true;
  }

public String getName()
{
  return name;
}
}
