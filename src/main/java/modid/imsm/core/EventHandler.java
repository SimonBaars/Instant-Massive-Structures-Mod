package modid.imsm.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import modid.imsm.livestructures.YSync;
import modid.imsm.structureloader.LightUpdateCheck;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
	public ArrayList<SchematicStructure> postProcessors = new ArrayList<SchematicStructure>();
	public ArrayList<ICreatorBlock> creators = new ArrayList<ICreatorBlock>();
	public ArrayList<ICreatorBlock> serverCreators = new ArrayList<ICreatorBlock>();
	public ArrayList<LiveStructureServer> liveCreators = new ArrayList<LiveStructureServer>();
	public ArrayList<Integer> scheduledExplosions = new ArrayList<Integer>();
	public ArrayList<String> delayedPrints = new ArrayList<String>();
	public LightUpdateCheck lightUpdate;
	//long lastUpdateTime=0;
	public long previousTick = 0;
	public LiveStructure isRiding;
	public YSync ySync;
	public boolean isLoaded=false;
	//public ArrayList<LiveStructureServer> liveServerCreators = new ArrayList<LiveStructureServer>();
	
	public EventHandler(){
	}
	/*@SubscribeEvent
	public void update(TickEvent.WorldTickEvent event){
		//System.out.println("world");
		if(postProcessors.size()>0){
			postProcessors.get(0).postProcess();
			//System.out.println("Post processing done");
			postProcessors.remove(0);
		}
		if(event.phase==Phase.END){
		/*IMSM.canWrite=false;
		} else {
			IMSM.canWrite=true;
			System.out.println("Thread may place a block");
		}
		//System.out.println(IMSM.canWrite);
	}*/
	
		
	public void load(){
		if(!IMSM.updateChecked){
			IMSM.updateChecked=true;
			UpdateThread updateThread = new UpdateThread();
			updateThread.start();
			loadLanguageFile();
		}
		
			loadLiveCreators();
			loadStructures();
			lightUpdate= new LightUpdateCheck(Minecraft.getInstance().world, Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension));
			isLoaded=true;
	}
	
	private void loadLanguageFile() {
		try {
			File file = new File("structures/en_US.lang");
			if(file.exists()){
			InputStream languageFile = new FileInputStream(file);
			LanguageMap.inject(languageFile);
			languageFile.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	@SubscribeEvent
	public void update(TickEvent.ClientTickEvent event){
		
		/*for(int i = 0; i<liveCreators.size(); i++){
			if(System.currentTimeMillis()>liveCreators.get(i).lastTickTime+liveCreators.get(i).waitTime){
				if(liveCreators.get(i).run() && liveCreators.get(i).boundTo.run()){
					i--;
					continue;
				}
				if(i<liveCreators.size() && liveCreators.get(i)!=null){
				liveCreators.get(i).lastTickTime=System.currentTimeMillis();
				}
			}
		}*/
		try{
		//if(isRiding!=null){
			if(ySync!=null){
			//if(isRiding.ride.progress>=0 && isRiding.ride.progress<isRiding.ride.animation[0].length){
				if(Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).playerEntities==null){
			for(Object playerObject : Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).playerEntities){
				EntityPlayerMP player = (EntityPlayerMP)playerObject;
				if(ySync.isVehicle==null || (/*Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).getBlockState(new BlockPos(player.posX,player.posY-1,player.posZ)).getBlock()!=Blocks.air ||*/ Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).getBlockState(new BlockPos(player.posX,player.posY,player.posZ)).getBlock()!=Blocks.AIR)){
				player.posY=/*isRiding.y+isRiding.ride.getHeight()+isRiding.ride.animation[0][isRiding.ride.progress]*/ySync.getY();
				if(player.getName().equals(Minecraft.getInstance().player.getName())){
					Minecraft.getInstance().player.posY=/*isRiding.y+isRiding.ride.getHeight()+isRiding.ride.animation[0][isRiding.ride.progress]*/ySync.getY();
				}
				} else {
					//System.out.println(MathHelper.floor_double(player.posY));
					IMSM.eventHandler.ySync.setY(player.posY);
				}
			}
			} else {
				EntityPlayerMP player = (EntityPlayerMP) Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).getPlayerEntityByName(Minecraft.getInstance().player.getName());
				if(ySync.isVehicle==null || (/*Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).getBlockState(new BlockPos(player.posX,player.posY-1,player.posZ)).getBlock()!=Blocks.air ||*/ Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).getBlockState(new BlockPos(player.posX,player.posY,player.posZ)).getBlock()!=Blocks.AIR)){
				player.posY=/*isRiding.y+isRiding.ride.getHeight()+isRiding.ride.animation[0][isRiding.ride.progress]*/ySync.getY();
				//if(player.getName().equals(Minecraft.getInstance().player.getName())){
					Minecraft.getInstance().player.posY=/*isRiding.y+isRiding.ride.getHeight()+isRiding.ride.animation[0][isRiding.ride.progress]*/ySync.getY();
				//}
				} else {
					//System.out.println(MathHelper.floor_double(player.posY));
					IMSM.eventHandler.ySync.setY(player.posY);
				}
			}
			}
						
			if(creators.size()>0){
				creators.clear();
			}
			
			/*for(int i = 0; i<creators.size(); i++){
				if((!(creators.get(i) instanceof StructureCreator) || 
						((StructureCreator)creators.get(i)).boundTo.generatedBlocks>((StructureCreator)creators.get(i)).generatedBlocks) && 
						creators.get(i).run()){
					creators.remove(i);
					i--;
				}
			}*/
		//}
	//}
			if(isLoaded){
				//lightUpdate.runClient();
				lightUpdate.clientProcesses.clear();
				}
		} catch (Exception e){
			e.printStackTrace();
		}
		}
	
	
	@SubscribeEvent
	public void update(TickEvent.ServerTickEvent event){
		if(!IMSM.updateChecked && Minecraft.getInstance().world!=null){
			load();
			IMSM.updateChecked=true;
		}
		
		for(int i = 0; i<liveCreators.size(); i++){
			if(System.currentTimeMillis()>liveCreators.get(i).lastTickTime+liveCreators.get(i).waitTime){
				if(liveCreators.get(i).run()/* && liveCreators.get(i).boundTo.run()*/){
					i--;
					continue;
				}
				if(i<liveCreators.size() && liveCreators.get(i)!=null){
					liveCreators.get(i).lastTickTime=System.currentTimeMillis();
				}
			}
		}
		
		for(int i = 0; i<serverCreators.size(); i++){
			if(serverCreators.get(i).run()){
				serverCreators.remove(i);
				i--;
			}
		}
				
		//System.out.println("render"+System.currentTimeMillis());
	//long tickTime = System.currentTimeMillis();
		for(int i = 0; i<scheduledExplosions.size(); i+=3){
			Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).newExplosion((Entity)null, scheduledExplosions.get(0), scheduledExplosions.get(1), scheduledExplosions.get(2), 25.0F, true, true);
		}
		scheduledExplosions.clear();
		if(postProcessors.size()>0){
			postProcessors.get(0).postProcess();
			//System.out.println("Post processing done");
			postProcessors.remove(0);
		}
			
			
			
			while(delayedPrints.size()>0 && Minecraft.getInstance().getIntegratedServer().isCallingFromMinecraftThread()){
					Minecraft.getInstance().player.sendChatMessage(delayedPrints.get(0)));
					
				delayedPrints.remove(0);
			}
			if(isLoaded){
				lightUpdate.runServer();
	}
		
		//lastUpdateTime=System.currentTimeMillis();
			//System.out.println("IMSM took "+(System.currentTimeMillis()-tickTime));
	}
	
	private void loadLiveCreators() {
		liveCreators.clear();
		int i = 0;
		while(fileExists(i+".txt")){
			//System.out.println("Loop 3");
			String[] array = new String[10];
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+i+".txt"));
			

			for(int j = 0; j<array.length; j++){
				//System.out.println("Loop 4");
				try {
					array[j]=in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//liveCreators.add(new LiveStructureClient(array[0], Integer.parseInt(array[1]),Integer.parseInt(array[2]),Integer.parseInt(array[3]), array[4].equals("true"), Integer.parseInt(array[8]), Integer.parseInt(array[9]),  i, getAnimationFor(array[0]),array[7].equals("true")));
			liveCreators.add(new LiveStructureServer(array[0], Integer.parseInt(array[1]),Integer.parseInt(array[2]),Integer.parseInt(array[3]), array[4].equals("true"), Integer.parseInt(array[8]), Integer.parseInt(array[9]),  i, getAnimationFor(array[0]),array[7].equals("true")));
			if(liveCreators.get(liveCreators.size()-1).animation!=null/*array[0].equals("LivePlane") || array[0].equals("LiveFlyingShip") || array[0].equals("LiveFlyingShip2") || array[0].equals("LiveAirplane") || array[0].equals("LiveAirBalloon") || array[0].equals("LiveBoat")*/){
			  	byte some = 3;
				if(array[0].equals("LiveBoat") || array[0].equals("Live_Bus") || array[0].equals("Live_Bus2")){
			  		some=2;
			  	}
				try {
					liveCreators.get(liveCreators.size()-1).animation[some][0]=Integer.parseInt(in.readLine());
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				in.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			liveCreators.get(liveCreators.size()-1).animationPhase = Integer.parseInt(array[5]);
			liveCreators.get(liveCreators.size()-1).animationTimes = Integer.parseInt(array[6]);
			if(liveCreators.get(liveCreators.size()-1).animation!=null && liveCreators.get(liveCreators.size()-1).waitTime==2000000000){
				liveCreators.get(liveCreators.size()-1).removeThisLiveStructure(true);
				i--;
			}
			}catch (FileNotFoundException e1) {
				e1.printStackTrace();
			
			}
			i++;
		}
		
	}
	
	public int[][] getAnimationFor(String name){
		if(name.equals("LivePlane")){
		  	int[][] animation = {
					{
						1,2000000000,0,0,0
					},
					{
						1,10000,0,0,0
					},
					{
						30,100,-1,1,0
					},
					{
						100,100,-1,0,0
					},
					{
						31,100,-1,-1,0
					},
					{
						1,100,0,0,0
					}
			};
			return animation;
		} else if (name.equals("LiveFlyingShip2")){
			int[][] animation = {
					{
						1,2000000000,0,0,0
					},
					{
						1,10000,0,0,0
					},
					{
						30,500,1,1,0
					},
					{
						100,500,1,0,0
					},
					{
						31,500,1,-1,0
					},
					{
						1,500,0,0,0
					}
			};
			return animation;
		} else if(name.equals("LiveFlyingShip1") || name.equals("LiveAirBalloon")){
			int[][] animation = {
					{
						1,2000000000,0,0,0
					},
					{
						1,10000,0,0,0
					},
					{
						30,500,0,1,-1
					},
					{
						100,500,0,0,-1
					},
					{
						31,500,0,-1,-1
					},
					{
						1,500,0,0,0
					}
			};
			return animation;
		} else if(name.equals("Live_Flying_Helicopter")){
			int[][] animation = {
					{
						1,2000000000,0,0,0
					},
					{
						1,10000,0,0,0
					},
					{
						30,200,0,1,-1
					},
					{
						100,200,0,0,-1
					},
					{
						31,200,0,-1,-1
					},
					{
						1,200,0,0,0
					}
			};
			return animation;
		}else if (name.equals("LiveAirplane")){
			int[][] animation = {
					{
						1,2000000000,0,0,0
					},
					{
						1,10000,0,0,0
					},
					{
						30,300,0,1,1
					},
					{
						100,300,0,0,1
					},
					{
						31,300,0,-1,1
					},
					{
						1,300,0,0,0
					}
			};
			return animation;
		}else if (name.equals("LiveBoat") || name.equals("Live_Bus") || name.equals("Live_Bus2")){
			int[][] animation = {
					{
						1,2000000000,0,0,0
					},
					{
						1,10000,0,0,0
					},
					{
						100,300,0,0,1
					},
					{
						1,300,0,0,0
					}
			};
			return animation;
		}
		//System.out.println(name+" not found");
		return null;
	}
	
	private void loadStructures() {
		creators.clear();
		int i = 0;
		while(fileExists2(i+".txt")){
			//System.out.println("Loop 5");
			String[] array = new String[9];
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/Structures/"+i+".txt"));
			

			for(int j = 0; j<array.length; j++){
				//System.out.println("Loop 6");
				try {
					array[j]=in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				in.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			creators.add(new StructureCreatorClient(array[0], Integer.parseInt(array[2]),Integer.parseInt(array[3]),Integer.parseInt(array[4]), array[1].equals("true"),i));
			((StructureCreator)creators.get(creators.size()-1)).i=Integer.parseInt(array[5]);
			((StructureCreator)creators.get(creators.size()-1)).j=Integer.parseInt(array[6]);
			((StructureCreator)creators.get(creators.size()-1)).k=Integer.parseInt(array[7]);
			((StructureCreator)creators.get(creators.size()-1)).speedUp=Integer.parseInt(array[8]);
			}catch (FileNotFoundException e1) {
				e1.printStackTrace();
			
			}
			i++;
		}
		
	}
	boolean fileExists(String path){
		File f = new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/LiveStructures/"+path);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		return false;
	}
	
	boolean fileExists2(String path){
		File f = new File("saves/"+Minecraft.getInstance().getIntegratedServer().getFolderName()+"/Structures/"+path);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		return false;
	}


	public void scheduleExplosion(int i, int j, int k) {
		scheduledExplosions.add(i);
		scheduledExplosions.add(j);
		scheduledExplosions.add(k);
	}
}
