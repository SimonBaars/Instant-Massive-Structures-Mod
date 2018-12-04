package modid.imsm.worldgeneration;

import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockBigWorld extends Block
{
	
	private final String name;
	private World serverWorld;
	private int nCheckers = 64;
	private int checkerSize = 2;

  public BlockBigWorld(int i)
    {
        super(Material.ROCK);
        this.name="BlockBigWorld";
    }
  
  @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
  {
		if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemRedstone){
			if(worldIn.isRemote){
			nCheckers*=2;
			if(nCheckers>20000){
				nCheckers=1;
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("This block will now edit "+nCheckers+" rows of landscape"));
			
			}
  	} else if(playerIn.getActiveItemStack()!=null && playerIn.getActiveItemStack().getItem() instanceof ItemDye){
		if(worldIn.isRemote){
		checkerSize++;
		if(checkerSize>64){
			checkerSize=2;
		}
		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("This block will now increase the blocksize by "+checkerSize+""));
		
		}
	} else {
  if (worldIn.isRemote) {
    this.serverWorld = worldIn;
  } else {
	  //System.out.println("Processing new block");
  	BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
  	//AtlantisThread loadThread = new AtlantisThread(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld);
  	IMSM.eventHandler.creators.add(new BigWorldCreator(pos.getX(), pos.getY(), pos.getZ(), nCheckers, worldIn,serverWorld, checkerSize));
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
