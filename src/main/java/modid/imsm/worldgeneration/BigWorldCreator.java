package modid.imsm.worldgeneration;

import modid.imsm.core.CreatorBlocks;
import modid.imsm.core.ICreatorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BigWorldCreator extends CreatorBlocks implements ICreatorBlock {
	int i,j,k;
	private int nCheckers;
	int checkerSize = 2;
	int direction;
	World[] worldIn = new World[2];
	
    public BigWorldCreator(int x, int y, int z, int nCheckers, World worldIn, World serverWorld, int checkerSize){
    	this.worldIn[1]=worldIn;
    	this.worldIn[0]=serverWorld;
    	this.x=x;
    	this.y=y;
    	this.z=z;
    	this.nCheckers=nCheckers;
    	this.checkerSize=checkerSize;
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
				for(int l = 0; l<256; l+=checkerSize){
					for(int k = 0; k<2; k++){
						Block replace = worldIn[k].getBlockState(new BlockPos(x,l,z)).getBlock();
						for(int a = 0; a<checkerSize; a++){
							for(int b = 0; b<checkerSize; b++){
								for(int c = 0; c<checkerSize; c++){
									createBlock(new BlockPos(x+a,l+c,z+b), replace.getDefaultState());
								}
							}
						}
					}
				}
	}
	
	public  void createBlock(Block blk, int i, int j, int k, int l){
		// Make a position.
		   BlockPos pos0 = new BlockPos(i,l,j);
		   // Get the default state(basically metadata 0)
		   IBlockState state0=blk.getDefaultState();
		   // set the block
		   worldIn[k].setBlockState(pos0, state0);
	}
	
	public void createBlock(BlockPos pos, IBlockState state){
		/*try{
			Chunk chunk = worldIn[k].getChunkFromBlockCoords(pos);
			ExtendedBlockStorage storageArray = chunk.getBlockStorageArray()[pos.getY() >> 4];
			if (storageArray == null) storageArray = chunk.getBlockStorageArray()[pos.getY() >> 4] = new ExtendedBlockStorage(pos.getY() >> 4 << 4, !worldIn[k].provider.getHasNoSky());

			if (storageArray.get(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15).getBlock() != state.getBlock())
			{
				IBlockState oldState = worldIn[k].getBlockState(pos);
				storageArray.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, state);
				worldIn[k].markBlockRangeForRenderUpdate(pos, pos);//TODO: Still a hack
			}
			worldIn[k].checkLightFor(EnumSkyBlock.SKY, pos);
		} catch (Exception e){
			
		}*/
		worldIn[k].setBlockState(pos, state);
	}
}
