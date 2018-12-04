package modid.imsm.userstructures;

import modid.imsm.core.ForgeEventHandler;
import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockUnlimited extends Block
{
  public BlockUnlimited(int i)
    {
        super(Material.ROCK);
    }
  
  @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
  {
	  if(!worldIn.isRemote){
		  if(IMSM.pmcParser != null && IMSM.pmcParser.isAlive()){
			  IMSM.pmcParser.stopThread();
		  }
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD+"Add a structure from the internet to the Instant Massive Structures Mod!");
		  IMSM.eventHandler.delayedPrints.add("How would you like to search for structures?");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[1]"+TextFormatting.RESET+" - Show all by popularity");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[2]"+TextFormatting.RESET+" - Show all by recently updated");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[3]"+TextFormatting.RESET+" - Show all by what's hot");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[4]"+TextFormatting.RESET+" - Show all by latest");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[5]"+TextFormatting.RESET+" - Show all by number of downloads");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[6]"+TextFormatting.RESET+" - Show all by number of views");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[7]"+TextFormatting.RESET+" - Search by title");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[8]"+TextFormatting.RESET+" - Search by username");
		  IMSM.eventHandler.delayedPrints.add(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"[9]"+TextFormatting.RESET+" - Search a random title");
		  IMSM.eventHandler.delayedPrints.add("Please type "+TextFormatting.RED+"[a number]"+TextFormatting.RESET+"...");
		  IMSM.dialoge = 11;
		  ForgeEventHandler.searchingPage=1;
	  }
	  worldIn.setBlockToAir(pos);
	  return true;
  }
}
