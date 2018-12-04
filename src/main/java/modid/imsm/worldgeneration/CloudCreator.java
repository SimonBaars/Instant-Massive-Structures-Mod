package modid.imsm.worldgeneration;

import modid.imsm.core.CreatorBlocks;
import modid.imsm.core.ICreatorBlock;
import modid.imsm.core.IMSM;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.world.World;

public class CloudCreator extends CreatorBlocks implements ICreatorBlock {
	int i,j,k;
	private int nCheckers;
	final int checkerSize = 64;
	int direction;
	World[] worldIn = new World[2];
	
    public CloudCreator(int x, int y, int z, int nCheckers, World worldIn, World serverWorld){
    	this.worldIn[1]=worldIn;
    	this.worldIn[0]=serverWorld;
    	this.x=x;
    	this.y=y;
    	this.z=z;
    	this.nCheckers=nCheckers;
    	init();
    }
	
	private void init(){
		direction=0;
		i=1;
			j=0;
				k=0;
				y+=30;
				//System.out.println("Engine Starting...");
	}
    
	public boolean run() {
		//for(int i = 1; i<nCheckers+1; i++){
			//for(int j= 0; j<3; j++){
				//for(int k = 0; k<i; k++){
					switch(direction){
						case 0: z+=checkerSize; break;
						case 1: x+=checkerSize; break;
						case 2: z-=checkerSize; break;
						case 3: x-=checkerSize; break;
					}
					//System.out.println("Direction: "+direction);
					createCloud();
					//System.out.println("Generating a checker...");
				k++;
				if(k>=i){
					direction++;
					if(direction==4){
						direction=0;
					}
					j++;
					k=0;
					if(j>=3){
						i++;
						j=0;
						if(i>=nCheckers+1){
							return true;
						}
					}
				}
				//}
			//}
		//}
		
		return false;
	}

	private void createCloud() {
		SchematicStructure struct = new SchematicStructure("cloud"+((int)((Math.random()*6)+1))+".structure", false);
	      struct.readFromFile();
	      struct.doNotReplaceAir();
	      struct.process(worldIn[0], x+(int)(Math.random()*(2*checkerSize))-checkerSize,y+((int)((Math.random()*30)-15)),z+(int)(Math.random()*(2*checkerSize))-checkerSize);
	      struct.process(worldIn[1], x+(int)(Math.random()*(2*checkerSize))-checkerSize,y+((int)((Math.random()*30)-15)),z+(int)(Math.random()*(2*checkerSize))-checkerSize);
		     IMSM.eventHandler.postProcessors.add(struct);
	}
}
