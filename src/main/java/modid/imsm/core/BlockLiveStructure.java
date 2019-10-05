package modid.imsm.core;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockLiveStructure extends Block {
	private final String name;
	//private World serverWorld;
	private boolean hasOutline=false;
	private boolean doReplaceAir;
	private int modifierx;
	private int modifiery;
	private int modifierz;
	private int spawnPosModifierX,spawnPosModifierY,spawnPosModifierZ;
	private int amountOfSlides;
	private int waitTime;
	private boolean doLoop;

	public BlockLiveStructure(String name, boolean doReplaceAir, int amountOfSlides, int waitTime, int modifierx, int modifiery, int modifierz,int spawnPosModifierX,int spawnPosModifierY,int spawnPosModifierZ, boolean doLoop){
		super(Block.Properties.create(Material.ROCK));
		this.name=name;
		this.doReplaceAir=doReplaceAir;
		this.modifierx=modifierx;
		this.modifiery=modifiery;
		this.modifierz=modifierz;
		this.spawnPosModifierX=spawnPosModifierX;this.spawnPosModifierY=spawnPosModifierY;this.spawnPosModifierZ=spawnPosModifierZ; 
		this.amountOfSlides=amountOfSlides;
		this.waitTime=waitTime;
		this.doLoop=doLoop;
	}
	
	public Block setCreativeTab(ItemGroup g) {
		Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	if(worldIn.isRemote) {   
		return true;
	}
      BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
      int[][] animation = IMSM.eventHandler.getAnimationFor(name);
      if(animation==null){
  	IMSM.eventHandler.creators.add(new StructureCreatorClient(name, pos.getX()+modifierx, pos.getY()+modifiery, pos.getZ()+modifierz, doReplaceAir,IMSM.eventHandler.creators.size()));
      }
                      //liveCreators.add(new LiveStructure(array[0], Minecraft.getInstance().world, Minecraft.getInstance().getIntegratedServer().worldServerForDimension(0), Integer.parseInt(array[1]),Integer.parseInt(array[2]),Integer.parseInt(array[3]), array[4].equals("true"), Integer.parseInt(array[8]), Integer.parseInt(array[9]),  i, getAnimationFor(array[0]),array[7].equals("true")));
  	IMSM.eventHandler.liveCreators.add(new LiveStructureServer(name, pos.getX()+modifierx-spawnPosModifierX, pos.getY()+modifiery-spawnPosModifierY, pos.getZ()+modifierz-spawnPosModifierZ, doReplaceAir, amountOfSlides, waitTime, IMSM.eventHandler.liveCreators.size(), animation, doLoop));
  	IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1).registerLiveCreator(IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1),IMSM.eventHandler.liveCreators.size()-1);
  	worldIn.setBlockState(newPos, new BlockState(Blocks.AIR, ImmutableMap.of()));
  	if(IMSM.eventHandler.liveCreators.size()>4){
  		Minecraft.getInstance().player.sendChatMessage("You have now placed more than 5 Live Structures, which may cause some lag.");
  		Minecraft.getInstance().player.sendChatMessage("Do the command /removelive to remove all live structures");
  	}
  	if(name.equals("LivePlane") || name.equals("LiveFlyingShip1") || name.equals("LiveFlyingShip2") || name.equals("LiveAirplane") || name.equals("LiveAirBalloon") || name.equals("Live_Flying_Helicopter")){
  		Minecraft.getInstance().player.sendChatMessage("Thanks for choosing SimJoo's Aviation Solutions.");
  		Minecraft.getInstance().player.sendChatMessage("Please type in the distance (in blocks) you'd like to fly.");
  		IMSM.dialoge=1;
  		IMSM.currentInput = IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1);
  	} else if (name.equals("LiveBoat")){
  		Minecraft.getInstance().player.sendChatMessage("Thanks for choosing SimJoo's Maritime Solutions.");
  		Minecraft.getInstance().player.sendChatMessage("Please type in the distance (in blocks) you'd like to sail.");
  		IMSM.dialoge=2;
  		IMSM.currentInput = IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1);
  	} else if(name.equals("Live_Bus") || name.equals("Live_Bus2")){
  		Minecraft.getInstance().player.sendChatMessage("Thanks for choosing SimJoo's Bus Depot.");
  		Minecraft.getInstance().player.sendChatMessage("Please type in the distance (in blocks) you'd like to ride.");
  		IMSM.dialoge=3;
  		IMSM.currentInput = IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1);
  	} else if (name.equals("Live_FerrisWheel") || name.equals("Live_Fair_FreeFall")){
  		Minecraft.getInstance().player.sendChatMessage("Use /ride to ride this structure!.");
  	}
        return true;
    }
  

public String getName()
  {
    return name;
  }
}
