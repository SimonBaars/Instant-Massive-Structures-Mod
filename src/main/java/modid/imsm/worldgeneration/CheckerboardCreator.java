package modid.imsm.worldgeneration;

import modid.imsm.core.CreatorBlocks;
import modid.imsm.core.ICreatorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumLightType;

public class CheckerboardCreator extends CreatorBlocks implements ICreatorBlock {
	int i,j,k;
	private int nCheckers;
	final int checkerSize = 16;
	int direction;
	boolean color;
	
    public CheckerboardCreator(int x, int y, int z, int nCheckers){
    	this.world= Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension);
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
				color=false;
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
					createChecker();
					color=!color;
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

	private void createChecker() {
		for(int i = 0; i<checkerSize; i++){
			for(int j = 0; j<checkerSize; j++){
				for(int k = 0; k<2; k++){
					Block blk= Blocks.WHITE_WOOL;
					if(color){
						blk=Blocks.BLACK_WOOL;
					}
				createBlock(blk,i,j,k);
				}
			}
		}
	}
	
	public void createBlock(Block blk, int i, int j, int k){
		// Make a position.
		   BlockPos pos0 = new BlockPos(x+i, y , z+j);
		   // Get the default state(basically metadata 0)
		   IBlockState state0=blk.getDefaultState();
		   // set the block
		   world.setBlockState(pos0, state0);
		   
		   world.checkLightFor(EnumLightType.SKY, pos0);
	}
}
