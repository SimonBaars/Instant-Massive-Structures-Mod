package modid.imsm.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import modid.imsm.structureloader.SchematicStructure;
import modid.imsm.userstructures.StructureCreatorUser;
import modid.imsm.userstructures.StructureCreatorUserServer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class StructureCreator extends CreatorBlocks implements ICreatorBlock {

	boolean doReplaceAir;
	public int i=0;
	public int j=0;
	public int k=0;
	protected SchematicStructure struct;
	protected int speedUp=100;
	public String structureName;
	protected int id;
	public int generatedBlocks=0;
	public StructureCreator boundTo;

	protected StructureCreator(String name, World world, int i, int j, int k, boolean doReplaceAir, int id) {
		if(this instanceof StructureCreatorClient){
			IMSM.eventHandler.serverCreators.add(new StructureCreatorServer(name,i,j,k,doReplaceAir,id));
			((StructureCreator)IMSM.eventHandler.serverCreators.get(IMSM.eventHandler.serverCreators.size()-1)).boundTo=this;
			boundTo=((StructureCreator)IMSM.eventHandler.serverCreators.get(IMSM.eventHandler.serverCreators.size()-1));
		} else if(this instanceof StructureCreatorUser){
			IMSM.eventHandler.serverCreators.add(new StructureCreatorUserServer(name,i,j,k,doReplaceAir,id));
			((StructureCreator)IMSM.eventHandler.serverCreators.get(IMSM.eventHandler.serverCreators.size()-1)).boundTo=this;
			boundTo=((StructureCreator)IMSM.eventHandler.serverCreators.get(IMSM.eventHandler.serverCreators.size()-1));
		} else {
		this.world=world;
		this.x=i;
		this.y=j;
		this.z=k;
		this.doReplaceAir=doReplaceAir;
		this.id=id;
		init(name, doReplaceAir, !world.isRemote);
	//	IMSM.lastPlaced=this;
		}
	}
	
	protected void init(String structureName,boolean doPlaceAir, boolean server){
		this.structureName=structureName;
		SchematicStructure struct = new SchematicStructure(structureName+".structure", false);
	      struct.readFromFile();
	      if(!doPlaceAir){
	    	  struct.doNotReplaceAir();
	      }
	      struct.initSingleBlockPlacer(world, this.x,this.y,this.z);
	      this.struct=struct;
	      //speedUp=100;
	      if(server){
	      registerStructure(id);
	      }
	}

	@Override
	public boolean run() {
		for(int i = 0; i<speedUp; i++){
			//System.out.println(i+", "+j+", "+k);
		if(struct.placeBlock(this)){
			if(!world.isRemote){
			IMSM.eventHandler.postProcessors.add(struct);
			removeThisStructure();
			}
			return true;
	}
		generatedBlocks++;
		}
		registerStructure(id);
		return false;
	}
	
	protected void registerStructure(int at){
		//System.out.println("Registered "+at);
		if(!world.isRemote){
		PrintWriter writer;
		try {
			(new File("saves/"+Minecraft.getMinecraft().getIntegratedServer().getFolderName()+"/Structures")).mkdirs();
			writer = new PrintWriter("saves/"+Minecraft.getMinecraft().getIntegratedServer().getFolderName()+"/Structures/"+at+".txt", "UTF-8");
		
			writer.println(structureName);
			writer.println(doReplaceAir);
			writer.println(x);
			writer.println(y);
			writer.println(z);
			writer.println(i);
			writer.println(j);
			writer.println(k);
			writer.println(speedUp);
		
		writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	protected void removeThisStructure(){
		if(!world.isRemote){
		for(int i = 0; i<IMSM.eventHandler.creators.size(); i++){
			if(IMSM.eventHandler.creators.get(i) instanceof StructureCreator){
			if(((StructureCreator)IMSM.eventHandler.creators.get(i)).id>id){
				((StructureCreator)IMSM.eventHandler.creators.get(i)).id--;
			}}
		}
		new File("saves/"+Minecraft.getMinecraft().getIntegratedServer().getFolderName()+"/Structures/"+id+".txt").delete();
		System.out.println("Removed "+id);
		for(int i = id+1; i<getSize(IMSM.eventHandler.liveCreators.size()); i++){
			new File("saves/"+Minecraft.getMinecraft().getIntegratedServer().getFolderName()+"/Structures/"+i+".txt").renameTo(new File("saves/"+Minecraft.getMinecraft().getIntegratedServer().getFolderName()+"/Structures/"+(i-1)+".txt"));
			System.out.println("Removed "+i+" then added "+(i-1));
		}
		}
	}
	
	private int getSize(int size) {
		  int totalsize=0;
		  for(int i = 0; i<IMSM.eventHandler.creators.size(); i++){
				if(IMSM.eventHandler.creators.get(i) instanceof StructureCreator){
				totalsize++;
				}
				
				}
			return totalsize;
		}


}
