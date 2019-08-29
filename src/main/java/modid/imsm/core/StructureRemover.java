package modid.imsm.core;

import modid.imsm.worldgeneration.UndoCommand;
import net.minecraft.client.Minecraft;

public class StructureRemover implements ICreatorBlock{
//StructureCreator structure;
//SchematicStructure struct;
//int x=0,y=0,z=0;
	@Override
	public boolean run() {
		if(UndoCommand.removedPositions.size()>0 && UndoCommand.removedStates.size()>0){
		for(int i = 0; i<100; i++){
		Minecraft.getInstance().getIntegratedServer().getEntityWorld().setBlockState(UndoCommand.removedPositions.get(UndoCommand.removedPositions.size()-1), UndoCommand.removedStates.get(UndoCommand.removedStates.size()-1));
		UndoCommand.removedPositions.remove(UndoCommand.removedPositions.size()-1);
		UndoCommand.removedStates.remove(UndoCommand.removedStates.size()-1);
		if(UndoCommand.removedPositions.size()==0 || UndoCommand.removedStates.size()==0){
			return true;
		}
		}
		} else {
		return true;
	}
		return false;
	}

	/*private boolean remove() {
		for(int i = 0; i<100; i++){
					Block blk = Blocks.air;
					   // Make a position.
					   BlockPos pos0 = new BlockPos(structure.x-x, structure.y+y ,structure.z-z);
					   // Get the default state(basically metadata 0)
					   IBlockState state0=blk.getDefaultState();
					   // set the block
					   //Minecraft.getInstance().theWorld.setBlockState(pos0, state0);
					   Minecraft.getInstance().getIntegratedServer().getEntityWorld().setBlockState(pos0, state0);
		z++;
		if(z>struct.width){
			z=0;
			//System.out.println("WUTTT "+daddy.i);
			y++;
			if(y>struct.height){
				y=0;
				x++;
				if(x>struct.length){
		Minecraft.getInstance().player.sendChatMessage("The last placed structure has been removed."));
		return true;
				}
			}
		}
		
}
		return false;
	}
*/
	/*public StructureRemover(StructureCreator structure) {
		if(structure instanceof StructureCreatorUserServer){
			struct = new SchematicStructure(structure.structureName+".structure");
		}else {
		struct = new SchematicStructure(structure.structureName+".structure", false);
		}
	    struct.readFromFile();
		this.structure=structure;
	}*/

}
