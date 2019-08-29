/*
*** MADE BY SIMJOO AND CREATORPAUL FROM PLANETMINECRAFT.COM! ALL RIGHTS RESERVED!
*/

package modid.imsm.structures;

import modid.imsm.worldgeneration.UndoCommand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class RemoverLast extends Block
{
	public RemoverLast(int i)
    {
		super(Block.Properties.create(Material.ROCK));
		//remove(IMSM.lastPlaced);
    }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
		
		UndoCommand.runCommand();
		return this.getStateFromMeta(meta);
    }
	

	/*void remove(StructureCreator structure){
		if(structure!=null){
		SchematicStructure struct = new SchematicStructure(structure.structureName+".structure", false);
	    struct.readFromFile();
		for(int x = 0; x<struct.length; x++){
			for(int y =0; y<struct.height; y++){
				for(int z =0; z<struct.width; z++){
					Block blk = Blocks.air;
					   // Make a position.
					   BlockPos pos0 = new BlockPos(structure.x-x, structure.y+y ,structure.z-z);
					   // Get the default state(basically metadata 0)
					   IBlockState state0=blk.getDefaultState();
					   // set the block
					   Minecraft.getInstance().world.setBlockState(pos0, state0);
					   Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).setBlockState(pos0, state0);
				}
			}
		}
		Minecraft.getInstance().player.sendChatMessage("The last placed structure has been removed."));
		IMSM.lastPlaced=null;
	}else {
		Minecraft.getInstance().player.sendChatMessage("You didn't place a structure to undo."));
	}
	}*/

}