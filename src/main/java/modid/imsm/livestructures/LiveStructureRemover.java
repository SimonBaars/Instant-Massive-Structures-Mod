package modid.imsm.livestructures;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import modid.imsm.core.IMSM;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LiveStructureRemover extends Block {
	public LiveStructureRemover(){
		super(Block.Properties.create(Material.ROCK));
	}
	
	public Block setCreativeTab(ItemGroup g) {
		Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
		if(worldIn.isRemote){
			for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
				SchematicStructure struct = new SchematicStructure(IMSM.eventHandler.liveCreators.get(i).structureName+".structure", true);
				struct.readFromFile();
				if(IMSM.eventHandler.liveCreators.get(i).closeTo(2, pos.getX(), pos.getY(), pos.getZ(), struct.width, struct.height, struct.length)){
					IMSM.eventHandler.liveCreators.get(i).removeThisLiveStructure(false);
					Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).setBlockState(pos, new BlockState(Blocks.AIR, ImmutableMap.of()));
					Minecraft.getInstance().world.setBlockState(pos, new BlockState(Blocks.AIR, ImmutableMap.of()));
					Minecraft.getInstance().player.sendChatMessage("You succesfully removed a structure's movements");
					break;
				}
			}
		}
    }
	
}
