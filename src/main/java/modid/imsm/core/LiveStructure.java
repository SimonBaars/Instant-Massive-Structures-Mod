package modid.imsm.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import modid.imsm.livestructures.RideStructure;
import modid.imsm.livestructures.YSync;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class LiveStructure {
	public World world;
	public int x;
	public int y;
	public int z;
	public boolean doPlaceAir;
	public String structureName;
	public int amountOfSlides;
	public int waitTime;
	public int currentSlide = 0;
	int[][] animation; //times, speed, relx, rely, relz
	public long lastTickTime = 0;
	protected int id;
	private int relativeSpawnPointX;
	private int relativeSpawnPointY;
	private int relativeSpawnPointZ;
	private int prevrelativeSpawnPointX=0;
	private int prevrelativeSpawnPointY=0;
	private int prevrelativeSpawnPointZ=0;
	//double flightAtY;
	private boolean doLoop;
	public int animationPhase = 0;
	public int animationTimes = 0;
	//private int heightChangeAmount=0;
	public RideStructure ride;
	public LiveStructure boundTo;
	
	
    protected LiveStructure(String structureName, World world, int x, int y, int z, boolean doPlaceAir, int amountOfSlides, int waitTime, int id, int[][] animation, boolean doLoop){
    	this.structureName=structureName;
    	this.world=world;
    	this.x=x;
    	this.y=y;
    	this.z=z;
    	this.doPlaceAir=doPlaceAir;
    	this.amountOfSlides=amountOfSlides;
    	this.waitTime=waitTime;
    	this.id=id;
    	this.doLoop=doLoop;
    	this.animation=animation;
    	/*if(world.isRemote){
    		IMSM.eventHandler.liveServerCreators.add(new LiveStructureServer(structureName, x, y, z, doPlaceAir, amountOfSlides, waitTime, id, animation, doLoop));
    		IMSM.eventHandler.liveServerCreators.get(IMSM.eventHandler.liveServerCreators.size()-1).boundTo=this;
			boundTo=IMSM.eventHandler.liveServerCreators.get(IMSM.eventHandler.liveServerCreators.size()-1);
    	}*/
    	System.out.println("NEW LIVE "+x+", "+y+", "+z);
    }
    
	public boolean run() {
		if(Minecraft.getInstance().player==null || distanceToPlayer(x,y,z)>100){
			return false;
		}
		if(animation!=null){
			waitTime=animation[animationPhase][1];
			relativeSpawnPointX=animation[animationPhase][2];
			relativeSpawnPointY=animation[animationPhase][3];
			relativeSpawnPointZ=animation[animationPhase][4];
			animationTimes++;
			if(animationTimes>=animation[animationPhase][0]){
				animationPhase++;
				animationTimes=0;
				if(animationPhase>=animation.length){
					if(doLoop){
						animationPhase=0;
					} else {
						removeThisLiveStructure(false);
						return true;
					}
				}
			}
		}
		
		SchematicStructure struct = new SchematicStructure(structureName+currentSlide+".structure", true);
	      struct.readFromFile();
	      if(!(ride!=null && (ride.progress==-1 || ride.progress==ride.animation[0].length-1))){
	      struct.process(world, x,y,z);
	      IMSM.eventHandler.postProcessors.add(struct);
			x+=relativeSpawnPointX;
			y+=relativeSpawnPointY;
			z+=relativeSpawnPointZ;
			currentSlide++;
			if(currentSlide>=amountOfSlides){
				currentSlide=0;
			}
		}
			if(structureName.equals("Live_Fair_FreeFall")){
				if(currentSlide==1){
					waitTime=500;
				} else if(currentSlide==2){
					waitTime=3000;
				} else if(currentSlide==3){
					waitTime=800;
				} else if(currentSlide==11){
					waitTime=(int)(15000*Math.random());
				} else if(currentSlide==12){
					waitTime=250;
				}  
			}
			if(animation!=null){
				for(Object playerObject : Minecraft.getInstance().getIntegratedServer().getEntityWorld().playerEntities){
					EntityPlayerMP player = (EntityPlayerMP)playerObject;
				if(checkWithinBounds(player,struct.width, struct.height+5, struct.length)){
					//Minecraft.getInstance().player.motionX+=relativeSpawnPointX/2.25;
					
					
						double thisY = player.posY;
					if(relativeSpawnPointY>0){
						if(IMSM.eventHandler.ySync!=null){
							if(Minecraft.getInstance().getIntegratedServer().getEntityWorld().getBlockState(new BlockPos(player.posX,player.posY-1,player.posZ)).getBlock()!=Blocks.AIR || Minecraft.getInstance().getIntegratedServer().getEntityWorld().getBlockState(new BlockPos(player.posX,player.posY,player.posZ)).getBlock()!=Blocks.AIR){
						thisY=IMSM.eventHandler.ySync.getY()+1;
							} 
							IMSM.eventHandler.ySync.setY(thisY);	
						} else {
							IMSM.eventHandler.ySync=new YSync(Minecraft.getInstance().player.posY);
							//setYSync=true;
							IMSM.eventHandler.ySync.isVehicle=this;
						}
					} else if(IMSM.eventHandler.ySync!=null){
						if(IMSM.eventHandler.ySync.tickFinal){
							IMSM.eventHandler.ySync=null;
							//setYSync=false;
						} else {
							IMSM.eventHandler.ySync.setY(IMSM.eventHandler.ySync.getY()+1);
							IMSM.eventHandler.ySync.tickFinal=true;
						}
					}
					
					setPlayerToPosition(player,player.posX+relativeSpawnPointX,thisY,player.posZ+relativeSpawnPointZ);
					//System.out.println("Moved "+player.getName()+" to "+player.posX+relativeSpawnPointX+", "+thisY+", "+player.posZ+relativeSpawnPointZ);
					//setPlayerToPosition(Minecraft.getInstance().player.posX+relativeSpawnPointX,thisY,Minecraft.getInstance().player.posZ+relativeSpawnPointZ);
					} else if(IMSM.eventHandler.ySync!=null && IMSM.eventHandler.ySync.isVehicle==this){
						IMSM.eventHandler.ySync=null;
						//setYSync=false;
					}
					}
				//flightAtY=Minecraft.getInstance().player.posY;	
					//worldIn.setBlockState(new BlockPos(x-struct.width-1, y+(struct.height/2), z-(struct.length/2)), Blocks.diamond_block.getDefaultState());
					//System.out.println(worldIn.getBlockState(new BlockPos(x+struct.width, y+(struct.height/2), z+(struct.length/2))).getBlock());
				if(relativeSpawnPointX!=0 || relativeSpawnPointZ!=0)	{
				for(int i = -2; i<=2; i++){
						int checkx=0;
						int checkz=0;
						if(relativeSpawnPointX!=0){
							if(relativeSpawnPointX>0){
								checkx=x+1;
								checkz= z-(struct.length/2)+i;
							} else {
								checkx=x-struct.width-1;
								checkz= z-(struct.length/2)+i;
							}
						} else if(relativeSpawnPointZ!=0){
							if(relativeSpawnPointZ>0){
								checkx=x-(struct.length/2)+i+1;
								checkz= z+1;
							} else {
								checkx=x-(struct.length/2)+i;
								checkz= z-struct.width-1;
							}
						}
						
						//worldIn.setBlockState(new BlockPos(checkx, y+(struct.height/2), checkz), Blocks.redstone_block.getDefaultState());
						//serverWorld.setBlockState(new BlockPos(checkx, y+(struct.height/2), checkz), Blocks.redstone_block.getDefaultState());
						
					if(!(world.getBlockState(new BlockPos(checkx, y+(struct.height/2), checkz)).getBlock() instanceof BlockAir)){
						//System.out.println("Exploded due to "+worldIn.getBlockState(new BlockPos(checkx, y+(struct.height/2), checkz)).getBlock()+" at "+checkx+", "+(y+(struct.height/2))+", "+checkz);
						IMSM.eventHandler.scheduleExplosion(x-(struct.length/2), y+(struct.height/2), z-(struct.width/2));
						removeThisLiveStructure(false);
						return true;
					}
					}
				} 
				
				
					/*if(animationTimes==1){
						if(animationPhase==3 && (structureName.equals("LivePlane") || structureName.equals("LiveFlyingShip") || structureName.equals("LiveFlyingShip2") || structureName.equals("LiveAirplane"))){
							removeStuff(x+relativeSpawnPointX,y-relativeSpawnPointY-1,z+relativeSpawnPointZ,struct.width,1,struct.length);
						}
					} else { */
					if(relativeSpawnPointX!=0 || prevrelativeSpawnPointX!=0){
						if(relativeSpawnPointX>0 || prevrelativeSpawnPointX>0){
							removeStuff(x+relativeSpawnPointX-struct.length-1,y-relativeSpawnPointY,z+relativeSpawnPointZ,1,struct.height,struct.width);
						} else {
							removeStuff(x+relativeSpawnPointX+3,y-relativeSpawnPointY,z+relativeSpawnPointZ,1,struct.height,struct.width);
						}
					}
					if(relativeSpawnPointY!=0 || prevrelativeSpawnPointY!=0){
						if(relativeSpawnPointY>0 || prevrelativeSpawnPointY>0){
							removeStuff(x+relativeSpawnPointX,y-relativeSpawnPointY-1,z+relativeSpawnPointZ,struct.length,1,struct.width);
						} else {
							removeStuff(x+relativeSpawnPointX,y-relativeSpawnPointY+struct.height,z+relativeSpawnPointZ,struct.length,1,struct.width);
						}
					}
					if(relativeSpawnPointZ!=0 || prevrelativeSpawnPointZ!=0){
						if(relativeSpawnPointZ>0 || prevrelativeSpawnPointZ>0){
							if(structureName.equals("LiveBoat") || structureName.equals("LiveAirplane")){
								removeStuff(x+relativeSpawnPointX,y-relativeSpawnPointY+2,z+relativeSpawnPointZ-struct.width-1,struct.length,struct.height,1);
							}else {
								removeStuff(x+relativeSpawnPointX+1,y-relativeSpawnPointY,z+relativeSpawnPointZ-struct.width-2,struct.length,struct.height,1);
							}
						} else {
							removeStuff(x+relativeSpawnPointX,y-relativeSpawnPointY,z+relativeSpawnPointZ+4,struct.width,struct.height,1);
						}
					}
					prevrelativeSpawnPointX=relativeSpawnPointX;
					prevrelativeSpawnPointY=relativeSpawnPointY;
					prevrelativeSpawnPointZ=relativeSpawnPointZ;
				//}
				
			registerLiveCreator(this, id);
	}
			
			if(ride!=null){
				if(ride.number==0){
				if(ride.progress==-2 && currentSlide==1){ //Search for a mountpoint
					ride.progress++;
					//setAllPlayersToPosition(x-4,y+1,z-36);
				} else if (ride.progress==-1 && distanceToPlayer(x-4,y+1,z-36)==0){ //The ride is over bro
					ride.progress++;
					IMSM.eventHandler.ySync=new YSync(Minecraft.getInstance().player.posY);
				} else if ((ride.progress>=0 && ride.progress<ride.animation[0].length && distanceToPlayer(x-4-0.5,y+1+ride.animation[0][ride.progress],z-36-ride.animation[1][ride.progress]+0.5)>1)){ //The ride is over bro
					Minecraft.getInstance().player.sendChatMessage("Thanks for your visit. We hope to see you again soon!"));
					ride=null;
					IMSM.eventHandler.isRiding=null;
					IMSM.eventHandler.ySync=null;
					//System.out.println("BUT HAS POS "+Minecraft.getInstance().player.getPosition().getX()+", "+Minecraft.getInstance().player.getPosition().getY()+", "+Minecraft.getInstance().player.getPosition().getZ());
				}else if(ride.progress>=0 && ride.progress<ride.animation[0].length-1){ // The ride itself
					ride.progress++;
					//System.out.println(Minecraft.getInstance().getIntegratedServer().getEntityWorld().getBlockState(new BlockPos(Minecraft.getInstance().player.posX,y+1+ride.animation[0][ride.progress],Minecraft.getInstance().player.posZ-(ride.animation[1][ride.progress]-ride.animation[1][ride.progress-1]))).getBlock());
					//if(Minecraft.getInstance().getIntegratedServer().getEntityWorld().getBlockState(new BlockPos(Minecraft.getInstance().player.posX,y+1+ride.animation[0][ride.progress],Minecraft.getInstance().player.posZ-(ride.animation[1][ride.progress]-ride.animation[1][ride.progress-1]))).getBlock() instanceof BlockAir){
					//	setAllPlayersToPosition(Minecraft.getInstance().player.posX,y+1+ride.animation[0][ride.progress],Minecraft.getInstance().player.posZ-(ride.animation[1][ride.progress]-ride.animation[1][ride.progress-1]));
					//} else {
						setAllPlayersToPosition(x-4-0.5,y+1+ride.animation[0][ride.progress],z-36-ride.animation[1][ride.progress]+0.5);
						
					//}
					
					IMSM.eventHandler.ySync.setY(y+1+ride.animation[0][ride.progress]);
					//
				}
				} else if (ride.number==1){
					if(ride.progress==-2 && currentSlide==2){ //Search for a mountpoint
						ride.progress++;
						waitTime=500;
						//setAllPlayersToPosition(x-4,y+1,z-36);
					} else if (ride.progress==-1 && 
							((closeTo(x+0.5, Minecraft.getInstance().player.posX, 0.9) && closeTo(y+2.75, Minecraft.getInstance().player.posY, 1.0) && closeTo(z-3.0, Minecraft.getInstance().player.posZ, 2.9)) || 
							(closeTo(x-3.0, Minecraft.getInstance().player.posX, 2.9) && closeTo(y+2.75, Minecraft.getInstance().player.posY, 1.0) && closeTo(z-6.5, Minecraft.getInstance().player.posZ, 0.9)) || 
							(closeTo(x-6.5, Minecraft.getInstance().player.posX, 0.9) && closeTo(y+2.75, Minecraft.getInstance().player.posY, 1.0) && closeTo(z-3.0, Minecraft.getInstance().player.posZ, 2.9)) || 
							(closeTo(x-3.0, Minecraft.getInstance().player.posX, 2.9) && closeTo(y+2.75, Minecraft.getInstance().player.posY, 1.0) && closeTo(z-0.5, Minecraft.getInstance().player.posZ, 0.9)))){ //The ride is over bro
						ride.progress++;
						IMSM.eventHandler.ySync=new YSync(y+2.5+ride.animation[0][ride.progress]);
					} else if (ride.progress>=0 && ride.progress<ride.animation[0].length && !(
							(closeTo(x+0.5, Minecraft.getInstance().player.posX, 0.9) /*&& closeTo(y+2.75+ride.animation[0][ride.progress-1], Minecraft.getInstance().player.posY, 1.0)*/ && closeTo(z-3.0, Minecraft.getInstance().player.posZ, 2.9)) || 
							(closeTo(x-3.0, Minecraft.getInstance().player.posX, 2.9) /*&& closeTo(y+2.75+ride.animation[0][ride.progress-1], Minecraft.getInstance().player.posY, 1.0)*/ && closeTo(z-6.5, Minecraft.getInstance().player.posZ, 0.9)) || 
							(closeTo(x-6.5, Minecraft.getInstance().player.posX, 0.9) /*&& closeTo(y+2.75+ride.animation[0][ride.progress-1], Minecraft.getInstance().player.posY, 1.0)*/ && closeTo(z-3.0, Minecraft.getInstance().player.posZ, 2.9)) || 
							(closeTo(x-3.0, Minecraft.getInstance().player.posX, 2.9) /*&& closeTo(y+2.75+ride.animation[0][ride.progress-1], Minecraft.getInstance().player.posY, 1.0)*/ && closeTo(z-0.5, Minecraft.getInstance().player.posZ, 0.9)))){ //The ride is over bro
						Minecraft.getInstance().player.sendChatMessage("Thanks for your visit. We hope to see you again soon!"));
						ride=null;
						IMSM.eventHandler.isRiding=null;
						IMSM.eventHandler.ySync=null;
					}else if(ride.progress>=0 && ride.progress<ride.animation[0].length-1){ // The ride itself
						ride.progress++;
						setAllPlayersToPosition(Minecraft.getInstance().player.posX,y+2.5+ride.animation[0][ride.progress],Minecraft.getInstance().player.posZ);
						IMSM.eventHandler.ySync.setY(y+2.5+ride.animation[0][ride.progress]);
						//setAllPlayersToPosition(y+1+ride.animation[0][ride.progress],ride.animation[1][ride.progress]-ride.animation[1][ride.progress-1]);
					}
				}
			}
			
			return false;
	}
	
	private boolean closeTo(double number1, double number2, double howClose) {
		
		return number2>=number1-howClose && number2<=number1+howClose;
	}

	public void setAllPlayersToPosition( double x, double y, double z) {
		//System.out.println("SET ALL TO POS "+x+", "+y+", "+z);
		if(Minecraft.getInstance().getIntegratedServer().getEntityWorld().playerEntities!=null){
		for(Object playerObject : Minecraft.getInstance().getIntegratedServer().getEntityWorld().playerEntities){
			EntityPlayerMP player = (EntityPlayerMP)playerObject;
			setPlayerToPosition(player,x,y,z);
		}
		} else {
			EntityPlayerMP player = (EntityPlayerMP)Minecraft.getInstance().getIntegratedServer().getEntityWorld().getPlayerEntityByName(Minecraft.getInstance().player.getName());
			setPlayerToPosition(player,x,y,z);
		}
	}
	/*public void setAllPlayersToPosition( double y, double z) {
		System.out.println(z);
		// TODO Auto-generated method stub
		for(Object playerObject : Minecraft.getInstance().getIntegratedServer().getEntityWorld().playerEntities){
			EntityPlayerMP player = (EntityPlayerMP)playerObject;
			setPlayerToPosition(player,y,z);
		}
	}*/

	void setPlayerToPosition(EntityPlayerMP player, double x, double y, double z){
		player.setPosition(x,y,z);
		if(player.getName().equals(Minecraft.getInstance().player.getName())){
			Minecraft.getInstance().player.setPosition(x,y,z);
		}
	}
	
	/*void setPlayerToPosition(EntityPlayerMP player, double y, double z){
		player.posY=y;
		player.posZ+=z;
		if(player.getName().equals(Minecraft.getInstance().player.getName())){
			Minecraft.getInstance().player.posY=y;
			Minecraft.getInstance().player.posZ+=z;
		}
	}
	*/
	private int distanceToPlayer(double x1, double y1, double z1) {
		return (int) Math.sqrt(Math.pow(Minecraft.getInstance().player.posX-x1,2)+Math.pow(Minecraft.getInstance().player.posY-y1,2)+Math.pow(Minecraft.getInstance().player.posZ-z1,2));
	}

	private boolean checkWithinBounds(EntityPlayerMP player, int sizex, int sizey, int sizez){
		int playerX = player.getPosition().getX();
		int playerY = player.getPosition().getY();
		int playerZ = player.getPosition().getZ();
		//System.out.println(playerX+", "+x+", "+playerY+", "+y+", "+ playerZ+", "+z +", "+ playerX+", "+(x-sizex) +", "+playerY+", "+(y-sizey )+", "+ playerZ+", "+(z-sizez)+(playerX<=x && playerY>=y && playerZ<=z && playerX>=x-sizex && playerY<=y+sizey && playerZ>=z-sizez));
		if(playerX<=x+1 && playerY>=y-2 && playerZ<=z && playerX>=x+1-sizez && playerY<=y+sizey && playerZ>=z-sizex){
			return true;
		}
		return false;
	}
	
	public boolean closeTo(int howClose, int x, int y, int z, int sizex, int sizey, int sizez){
		//System.out.println(structureName+(x+howClose<=this.x && y-howClose>=this.y && z+howClose<=this.z && x-howClose>=this.x-sizex && y+howClose<=this.y+sizey && z-howClose>=this.z-sizez)+", "+x+"+"+howClose+"<="+this.x +"&&"+ y+"-"+howClose+">="+this.y +"&&"+ z+"+"+howClose+"<="+this.z +"&&"+ x+"-"+howClose+">="+this.x+"-"+sizex +"&&"+ y+"+"+howClose+"<="+this.y+"+"+sizey +"&&"+ z+"-"+howClose+">="+this.z+"-"+sizez);
		if(x-howClose<=this.x && y+howClose>=this.y && z-howClose<=this.z && x+howClose>=this.x-sizez && y-howClose<=this.y+sizey && z+howClose>=this.z-sizex){
			return true;
		} 
		return false;
	}
	
	public boolean closeTo(int howClose, double x, double y, double z, int sizex, int sizey, int sizez){
		//System.out.println(structureName+(x+howClose<=this.x && y-howClose>=this.y && z+howClose<=this.z && x-howClose>=this.x-sizex && y+howClose<=this.y+sizey && z-howClose>=this.z-sizez)+", "+x+"+"+howClose+"<="+this.x +"&&"+ y+"-"+howClose+">="+this.y +"&&"+ z+"+"+howClose+"<="+this.z +"&&"+ x+"-"+howClose+">="+this.x+"-"+sizex +"&&"+ y+"+"+howClose+"<="+this.y+"+"+sizey +"&&"+ z+"-"+howClose+">="+this.z+"-"+sizez);
		if(x-howClose<=this.x && y+howClose>=this.y && z-howClose<=this.z && x+howClose>=this.x-sizez && y-howClose<=this.y+sizey && z+howClose>=this.z-sizex){
			return true;
		} 
		return false;
	}
	
	void registerLiveCreator(LiveStructure liveStructure, int at){
		//System.out.println("Registered "+at);
		if(!world.isRemote){
		PrintWriter writer;
		try {
			(new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures")).mkdirs();
			writer = new PrintWriter("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+at+".txt", "UTF-8");
		
			writer.println(liveStructure.structureName);
		writer.println(liveStructure.x);
		writer.println(liveStructure.y);
		writer.println(liveStructure.z);
		writer.println(liveStructure.doPlaceAir);
		writer.println(liveStructure.animationPhase);
		writer.println(liveStructure.animationTimes);
		writer.println(liveStructure.doLoop);
		writer.println(liveStructure.amountOfSlides);
		writer.println(liveStructure.waitTime);
		if(structureName.equals("LivePlane") || structureName.equals("LiveFlyingShip1") || structureName.equals("LiveFlyingShip2") || structureName.equals("LiveAirplane") || structureName.equals("LiveAirBalloon") || structureName.equals("Live_Flying_Helicopter")){
		  		writer.println(animation[3][0]);
		} else if (structureName.equals("LiveBoat") || structureName.equals("Live_Bus") || structureName.equals("Live_Bus2")){
			writer.println(animation[2][0]);
		}
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
	
	public void removeThisLiveStructure(boolean renameFiles){
		//boolean windows=false;
		for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
			//System.out.println("Loop 1");
			if(IMSM.eventHandler.liveCreators.get(i).id>id){
				IMSM.eventHandler.liveCreators.get(i).id--;
			}
		}
		//System.out.println(new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+id+".txt").getAbsolutePath());
		new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+id+".txt").delete();
		//System.out.println("Removed "+id);
		//if(!windows){
		for(int i = id+1; i<IMSM.eventHandler.liveCreators.size(); i++){
			//System.out.println("Loop 2");
			new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+i+".txt").renameTo(new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+(i-1)+".txt"));
			//System.out.println("Removed "+i+" then added "+(i-1));
		}
		if(renameFiles){
			int i = id+1;
			while(fileExists(i+".txt")){
				new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+i+".txt").renameTo(new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+(i-1)+".txt"));
				i++;
			}
		}
		//} 
		//IMSM.eventHandler.liveServerCreators.remove(id);
		//else {
		IMSM.eventHandler.liveCreators.remove(id);
		//}
		
	}
		
		private int getGarbageId() {
			int i = 0;
			while(fileExists(i+".txt")){
				i++;
			}
			return i;
	}

		boolean fileExists(String path){
			File f = new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+path);
			if(f.exists() && !f.isDirectory()) { 
			    return true;
			}
			return false;
		}

	
	public void removeStuff(int posx, int posy, int posz, int removeX, int removeY, int removeZ){
	for(int x = 0; x<removeX; x++){
		for(int y =0; y<removeY; y++){
			for(int z =0; z<removeZ; z++){
				setBlock(Minecraft.getInstance().theWorld, new BlockPos(posx-x,posy+y,posz-z), Blocks.AIR.getDefaultState());
				setBlock(world, new BlockPos(posx-x,posy+y,posz-z), Blocks.AIR.getDefaultState());
				
			}
		}
	}
	Minecraft.getInstance().theWorld.markBlockRangeForRenderUpdate(new BlockPos(posx,posy,posz), new BlockPos(posx-removeX,posy+removeY,posz-removeZ));//TODO: This is a hack, and has to be changed in the future
	}

	public void setBlock(World world, BlockPos pos, IBlockState state){
		try{
			Chunk chunk = world.getChunkFromBlockCoords(pos);
			ExtendedBlockStorage storageArray = chunk.getBlockStorageArray()[pos.getY() >> 4];
			if (storageArray == null) storageArray = chunk.getBlockStorageArray()[pos.getY() >> 4] = new ExtendedBlockStorage(pos.getY() >> 4 << 4, !world.provider.getHasNoSky());

			if (storageArray.get(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15).getBlock() != state.getBlock())
			{
				IBlockState oldState = world.getBlockState(pos);
				storageArray.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, state);
			}
		} catch (Exception e){
			
		}
	}
	
	
}
