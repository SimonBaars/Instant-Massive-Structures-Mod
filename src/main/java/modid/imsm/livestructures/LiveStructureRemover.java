package modid.imsm.livestructures;

import modid.imsm.core.IMSM;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class LiveStructureRemover extends Block {
	public LiveStructureRemover(){
		super(Material.rock);
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		if(worldIn.isRemote){
			for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
				SchematicStructure struct = new SchematicStructure(IMSM.eventHandler.liveCreators.get(i).structureName+".structure", true);
				struct.readFromFile();
				if(IMSM.eventHandler.liveCreators.get(i).closeTo(2, pos.getX(), pos.getY(), pos.getZ(), struct.width, struct.height, struct.length)){
					IMSM.eventHandler.liveCreators.get(i).removeThisLiveStructure(false);
					Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockToAir(pos);
					Minecraft.getMinecraft().theWorld.setBlockToAir(pos);
					Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("You succesfully removed a structure's movements"));
					break;
				}
			}
		}
		return this.getStateFromMeta(meta);
    }
	
}
