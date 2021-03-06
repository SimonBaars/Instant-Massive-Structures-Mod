package modid.imsm.userstructures;

import modid.imsm.core.StructureCreator;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.client.Minecraft;

public class StructureCreatorUserServer extends StructureCreator{

	public StructureCreatorUserServer(String name, int i, int j, int k, boolean doReplaceAir, int id) {
		super(name, Minecraft.getMinecraft().getIntegratedServer().getEntityWorld(), i,j,k, doReplaceAir, id);
	}
	
	@Override
	protected void init(String structureName,boolean doPlaceAir, boolean server){
		this.structureName=structureName;
		SchematicStructure struct = new SchematicStructure(structureName+".structure");
	      struct.readFromFile();
	      if(!doPlaceAir){
	    	  struct.doNotReplaceAir();
	      }
	      struct.initSingleBlockPlacer(world, this.x,this.y,this.z);
	      this.struct=struct;
	      speedUp=100;
	      if(server){
	      registerStructure(id);
	      }
	}

	@Override
	protected void registerStructure(int at){
		
	}
	
	@Override
	protected void removeThisStructure(){
		
	}

}
