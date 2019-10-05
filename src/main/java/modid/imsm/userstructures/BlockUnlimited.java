package modid.imsm.userstructures;

import modid.imsm.core.ForgeEventHandler;
import modid.imsm.core.IMSM;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockUnlimited extends Block
{
  public BlockUnlimited(int i)
    {
	  super(Block.Properties.create(Material.ROCK));
    }
  
  public Block setCreativeTab(ItemGroup g) {
		Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
  
  @Override
  public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

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
	  worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
	  return true;
  }
}
