package modid.imsm.worldgeneration;

import modid.imsm.core.CreatorBlocks;
import modid.imsm.core.ICreatorBlock;
import modid.imsm.core.IMSM;
import modid.imsm.core.StructureCreator;
import modid.imsm.core.StructureCreatorClient;
import modid.imsm.structureloader.BlockPlaceHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.IScoreCriteria.EnumRenderType;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class MazeGenerator extends CreatorBlocks implements ICreatorBlock { 
	public MazeRunner maze;
	int i=0,j=0;
	public int blockSize = 8;
	public int blockHeight=32;
	int direction = 0;
	int k=0,l=0,m=0, error=0, generated=0;
	int n=0;
	int size;
	int phase;
	boolean isDone=false;
	public Block wallMaterial = Blocks.MOSSY_COBBLESTONE;
	
	Score displayProgress;
	ScoreObjective scoreBoard;
	private boolean initComplete = false;
	
	public MazeGenerator(int x, int y, int z){
		this.x=x;
    	this.y=y;
    	this.z=z;
		this.maze = new MazeRunner();
		//init();
	}
	
	public void startGeneration(){
		//System.out.println("Running Start Generation");
		//Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("A huge random maze will now be generated at your location!"));
		IMSM.eventHandler.delayedPrints.add("A huge random maze will now be generated at your location!");
		maze.initCollisions(true);
		init();
		initComplete=true;
	}
	
	void placeAt(int posX, int posY){
		if(maze.nodeRegister[posX][posY]==0){
            BlockPlaceHandler.placeBlocks(wallMaterial, x+(posX*blockSize), y, z+(posY*blockSize), blockSize, blockHeight, blockSize);
        } else if(maze.nodeRegister[posX][posY]==1) {
        	BlockPlaceHandler.placeBlocks(Blocks.AIR, x+(posX*blockSize), y, z+(posY*blockSize), blockSize, blockHeight, blockSize);
      	  }else if(maze.nodeRegister[posX][posY]==3) {
      		  int xPos =  x+(posX*blockSize)+((int)(Math.random()*(blockSize-1)));
      		int zPos =  z+(posY*blockSize)+((int)(Math.random()*(blockSize-1)));
      		BlockPlaceHandler.placeBlock(Blocks.CHEST, xPos, y, zPos);
      		TileEntityChest chest = (TileEntityChest) Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getTileEntity(new BlockPos(xPos, y, zPos));
      		if(chest!=null){
      			generateChestContents(chest);
      		}
      	  }else if(maze.nodeRegister[posX][posY]==10 && blockSize>=8){
        		BlockPos newPos = new BlockPos(x+(posX*blockSize), y, z+(posY*blockSize));
            	IMSM.eventHandler.creators.add(new StructureCreatorClient("FoodWheatNorthEastSouthWest", x+(posX*blockSize)+16, y-2, z+(posY*blockSize)+16, true,getSize(IMSM.eventHandler.creators.size())));
          	  }else if(maze.nodeRegister[posX][posY]==9 && blockSize>=8){
          		BlockPos newPos = new BlockPos(x+(posX*blockSize), y, z+(posY*blockSize));
            	IMSM.eventHandler.creators.add(new StructureCreatorClient("FoodFarmEast",  x+(posX*blockSize)+16, y-2, z+(posY*blockSize)+16, true,getSize(IMSM.eventHandler.creators.size())));
          	  }else if(maze.nodeRegister[posX][posY]==11 && blockSize>=8){
            		BlockPos newPos = new BlockPos(x+(posX*blockSize), y, z+(posY*blockSize));
                	IMSM.eventHandler.creators.add(new StructureCreatorClient("ResidentalMedium_DensityRoofWest",  x+(posX*blockSize)+16, y-1, z+(posY*blockSize)+16, true,getSize(IMSM.eventHandler.creators.size())));
              	  }else if(maze.nodeRegister[posX][posY]==12 && blockSize>=8){
                		BlockPos newPos = new BlockPos(x+(posX*blockSize), y, z+(posY*blockSize));
                    	IMSM.eventHandler.creators.add(new StructureCreatorClient("ResidentalHigh_DensityStoneWest",  x+(posX*blockSize)+16, y-1, z+(posY*blockSize)+16, true,getSize(IMSM.eventHandler.creators.size())));
                  	  }else if(maze.nodeRegister[posX][posY]==20) {
      		generated--;
      	  }else if(maze.nodeRegister[posX][posY]==21){
      		  
      	  } else {
      		BlockPlaceHandler.placeBlocks(Blocks.AIR, x+(posX*blockSize), y, z+(posY*blockSize), blockSize, blockHeight, blockSize);
      	  }
		generated++;
		maze.nodeRegister[posX][posY]=20;
	}
	
	private int getSize(int size) {
	  	  int totalsize=0;
	  	  for(int i = 0; i<IMSM.eventHandler.serverCreators.size(); i++){
	  			if(IMSM.eventHandler.serverCreators.get(i) instanceof StructureCreator){
	  			totalsize++;
	  			}
	  			
	  			}
	  		return totalsize;
	  	}


	private void generateChestContents(TileEntityChest chest) {
		// TODO Auto-generated method stub
		for(int i = 0; i<27; i++){
			if(Math.random()<0.2){
				chest.setInventorySlotContents(i, new ItemStack(getRandomItem(), 1));
			}
		}
	}

	private Item getRandomItem() {
		int randomItem = (int)(Math.random()*81);
		switch(randomItem){
		case 0: return Items.FLINT_AND_STEEL;
		case 1: return Items.ARROW;
		case 2: return Items.BOW;
		case 3: return Items.APPLE;
		case 4: return Items.COAL;
		case 5: return Items.IRON_SWORD;
		case 6: return Items.WOODEN_SWORD;
		case 7: return Items.STONE_SWORD;
		case 8: return Items.DIAMOND_SWORD;
		case 9: return Items.GOLDEN_SWORD;
		case 10: return Items.MUSHROOM_STEW;
		case 11: return Items.IRON_HOE;
		case 12: return Items.WHEAT;
		case 13: return Items.BREAD;
		case 14: return Items.LEATHER_BOOTS;
		case 15: return Items.LEATHER_CHESTPLATE;
		case 16: return Items.LEATHER_HELMET;
		case 17: return Items.LEATHER_LEGGINGS;
		case 18: return Items.CHAINMAIL_BOOTS;
		case 19: return Items.CHAINMAIL_CHESTPLATE;
		case 20: return Items.CHAINMAIL_HELMET;
		case 21: return Items.CHAINMAIL_LEGGINGS;
		case 22: return Items.IRON_HELMET;
		case 23: return Items.IRON_CHESTPLATE;
		case 24: return Items.IRON_LEGGINGS;
		case 25: return Items.IRON_BOOTS;
		case 26: return Items.DIAMOND_HELMET;
		case 27: return Items.DIAMOND_CHESTPLATE;
		case 28: return Items.DIAMOND_LEGGINGS;
		case 29: return Items.DIAMOND_BOOTS;
		case 30: return Items.GOLDEN_HELMET;
		case 31: return Items.GOLDEN_CHESTPLATE;
		case 32: return Items.GOLDEN_LEGGINGS;
		case 33: return Items.GOLDEN_BOOTS;
		case 34: return Items.PORKCHOP;
		case 35: return Items.COOKED_PORKCHOP;
		case 36: return Items.GOLDEN_APPLE;
		case 37: return Items.REEDS;
		case 38: return Items.SADDLE;
		case 39: return Items.SNOWBALL;
		case 40: return Items.PAPER;
		case 41: return Items.COMPASS;
		case 42: return Items.FISHING_ROD;
		case 43: return Items.CLOCK;
		case 44: return Items.FISH;
		case 45: return Items.COOKED_FISH;
		case 46: return Items.CAKE;
		case 47: return Items.BED;
		case 48: return Items.COOKIE;
		case 49: return Items.MAP;
		case 50: return Items.BEEF;
		case 51: return Items.COOKED_BEEF;
		case 52: return Items.CHICKEN;
		case 53: return Items.COOKED_CHICKEN;
		case 54: return Items.ROTTEN_FLESH;
		case 55: return Items.ENDER_PEARL;
		case 56: return Items.ARROW;
		case 57: return Items.CARROT;
		case 58: return Items.POTATO;
		case 59: return Items.BAKED_POTATO;
		case 60: return Items.POISONOUS_POTATO;
		case 61: return Items.GOLDEN_CARROT;
		case 62: return Items.SKULL;
		case 63: return Items.PUMPKIN_PIE;
		case 64: return Items.RABBIT_STEW;
		case 65: return Items.RABBIT;
		case 66: return Items.LEAD;
		case 67: return Items.MUTTON;
		case 68: return Items.COOKED_MUTTON;
		case 69: return Items.ARROW;
		case 70: return Items.ARROW;
		case 71: return Items.ARROW;
		case 72: return Items.ARROW;
		case 73: return Items.ARROW;
		case 74: return Items.ARROW;
		case 75: return Items.MAP;
		case 76: return Items.MAP;
		case 77: return Items.MAP;
		case 78: return Items.MAP;
		case 79: return Items.MAP;
		case 80: return Items.MAP;
		}
		return null;
	}

	private void init(){
		//Init scoreboard
		scoreBoard = Minecraft.getMinecraft().theWorld.getScoreboard().addScoreObjective("Score", IScoreCriteria.DUMMY);
		scoreBoard.setRenderType(EnumRenderType.INTEGER);
		scoreBoard.getScoreboard().setObjectiveInDisplaySlot(Scoreboard.getObjectiveDisplaySlotNumber("sidebar"), scoreBoard);
		displayProgress = scoreBoard.getScoreboard().getOrCreateScore("Maze Building Progress (%)", scoreBoard);
		displayProgress.setScorePoints(0);

		//Init map
		this.size = maze.nodeRegister[0].length/2;
		this.x-=(blockSize*maze.nodeRegister[0].length)/2;
		this.z-=(blockSize*maze.nodeRegister[1].length)/2;
		World worldIn = Minecraft.getMinecraft().theWorld;
		World serverWorld = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
		BlockPos pos;
		Block block;
		for(int x = 0; x<blockSize*maze.nodeRegister[0].length; x++){
			for(int y =0; y<1; y++){
				for(int z =0; z<blockSize*maze.nodeRegister[1].length; z++){
					pos= new BlockPos(this.x+i+x,this.y-1+y,this.z+j+z);
					if(maze.isCloseTo(x/blockSize, maze.gladeSize-1, maze.numOfColumns/2) && maze.isCloseTo(z/blockSize, maze.gladeSize-1, maze.numOfColumns/2)){
						block = Blocks.GRASS;
						/*if(Math.random()<0.05){
							BlockPlaceHandler.setBlock(worldIn,new BlockPos(this.x+i+x,this.y+y,this.z+j+z), Blocks.tallgrass.getDefaultState());
							BlockPlaceHandler.setBlock(serverWorld, new BlockPos(this.x+i+x,this.y+y,this.z+j+z), Blocks.tallgrass.getDefaultState());
						}*/
					} else {
						block = Blocks.STONEBRICK;
					}
					
					BlockPlaceHandler.setBlock(worldIn,pos, block.getDefaultState());
					BlockPlaceHandler.setBlock(serverWorld, pos, block.getDefaultState());
				}
			}
		}

	}
	
	/*public void doStuff(int x, int y, int N){
        x+=N/2; y+=N/2;
        if(x>=0 && y>=0 && x<N && y<N){
        }
	}*/
@Override
public boolean run() {
	//System.out.println("Running MazeGenerator");
	if(initComplete){
	//for(int i = 0; i<8; i++){
              
            	/*if (nodeRegister[i][j]==2){
            }
            	System.out.print("O");
            } else if (nodeRegister[i][j]==3){
            	System.out.print("C");
            } else if (nodeRegister[i][j]==4){
            	System.out.print("N");
            } else {
            	System.out.print((char)(nodeRegister[i][j]-9+'0'));
            }(*/
	
	if(!isDone){
              switch(direction){
				case 0: j++; break;
				case 1: i++; break;
				case 2: j--; break;
				case 3: i--; break;
			}
			//System.out.println("Direction: "+direction);
              //System.out.println("Setting block at "+(i+size)+", "+(j+size)+", "+(x+((i+size)*blockSize))+", "+(z+((j+size)*blockSize)));
              
              if(i+size>=0 && j+size>=0 && i+size<maze.nodeRegister.length && j+size<maze.nodeRegister[0].length){
              placeAt(i+size,j+size);
              
              } else {
            	  isDone=true;
            	  i=0;
            	  j=0;
              }
              
			//System.out.println("Generating a checker...");
              k++;
				if(k>=l){
					direction++;
					if(direction==4){
						direction=0;
					}
					m++;
					k=0;
					if(m>=2){
						l++;
						m=0;
					}
				}
		
	} else {
			placeAt(i,j);
			i++;
			if(i>=maze.nodeRegister.length){
				j++;
				i=0;
				if(j>=maze.nodeRegister[0].length){
					scoreBoard.getScoreboard().removeObjective(scoreBoard);
					Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("A random maze has been generated!"));
					return true;
				}
			}
    }
				
	displayProgress.setScorePoints((int)(generated/(float)(maze.nodeRegister.length*maze.nodeRegister[0].length)*100.00));
				if(generated>maze.nodeRegister.length*maze.nodeRegister[0].length){
					scoreBoard.getScoreboard().removeObjective(scoreBoard);
					Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("A random maze has been generated!"));
					return true;
				}
				
	}
	
	/*if(phase==0){
    for(n=0; n<=k; n++){
      x=-k+n; y=-n;
      doStuff(x,y);
      phase++;}  
	} else if (phase==1){
    for(n=1; n<=k; n++){
      x=n; y=-k+n;
      doStuff(x,y);
      phase++;} 
	} else if(phase==2){
    for(n=1; n<=k; n++){
      x=k-n; y=n;
      doStuff(x,y,N);
      phase++;}  
	} else if(phase==3){
    for(n=1; n<=k-1; n++){
      x=-n; y=k-n;
        doStuff(x,y);
        phase++;
    }
	}
    //if (x>0 && y>0 && x<N && y<N){ 
    
    K++;
    if(K>maze.nodeRegister.length+1) break;*/
//}
        /*j++;
        if(j>=maze.nodeRegister[0].length){
        	j=0;
        	i++;
        	if(i>=maze.nodeRegister.length){
        		return true;
        	}
        }*/
	//}
	// TODO Auto-generated method stub
	return false;
}}