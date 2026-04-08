package modid.imsm.worldgeneration;

import modid.imsm.core.CreatorBlocks;
import modid.imsm.core.ICreatorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AtlantisCreator extends CreatorBlocks implements ICreatorBlock {
	int i,j,k;
	private int nCheckers;
	final int checkerSize = 1;
	int direction;
	World[] worldIn = new World[2];
	
    public AtlantisCreator(int x, int y, int z, int nCheckers, World worldIn, World serverWorld){
    	this.worldIn[1]=worldIn;
    	this.worldIn[0]=serverWorld;
    	this.x=x;
    	this.y=y;
    	this.z=z;
    	this.nCheckers=nCheckers;
    }
	
	public void init(){
		direction=0;
		i=1;
			j=0;
				k=0;
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
				for(int l = 0; l<256; l++){
					for(int k = 0; k<2; k++){
						Block replace = worldIn[k].getBlockState(new BlockPos(x,l,z)).getBlock();
						if(replace == Blocks.STONE || replace == net.minecraft.block.Blocks.DIRT || replace instanceof GrassBlock){
							createBlock(Blocks.WATER,x,z,k,l);
						}
					}
				}
	}
	
	public  void createBlock(Block blk, int i, int j, int k, int l){
		// Make a position.
		   BlockPos pos0 = new BlockPos(i,l,j);
		   // Get the default state(basically metadata 0)
		   BlockState state0=blk.getDefaultState();
		   // set the block
		   worldIn[k].setBlockState(pos0, state0);
	}
}
