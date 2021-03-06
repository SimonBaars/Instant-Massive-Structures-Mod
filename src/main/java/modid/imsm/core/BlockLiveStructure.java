package modid.imsm.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
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
		super(Material.ROCK);
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
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
	if(worldIn.isRemote) {   
		return true;
	}
      BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
      int[][] animation = IMSM.eventHandler.getAnimationFor(name);
      if(animation==null){
  	IMSM.eventHandler.creators.add(new StructureCreatorClient(name, pos.getX()+modifierx, pos.getY()+modifiery, pos.getZ()+modifierz, doReplaceAir,IMSM.eventHandler.creators.size()));
      }
                      //liveCreators.add(new LiveStructure(array[0], Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().getIntegratedServer().worldServerForDimension(0), Integer.parseInt(array[1]),Integer.parseInt(array[2]),Integer.parseInt(array[3]), array[4].equals("true"), Integer.parseInt(array[8]), Integer.parseInt(array[9]),  i, getAnimationFor(array[0]),array[7].equals("true")));
  	IMSM.eventHandler.liveCreators.add(new LiveStructureServer(name, pos.getX()+modifierx-spawnPosModifierX, pos.getY()+modifiery-spawnPosModifierY, pos.getZ()+modifierz-spawnPosModifierZ, doReplaceAir, amountOfSlides, waitTime, IMSM.eventHandler.liveCreators.size(), animation, doLoop));
  	IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1).registerLiveCreator(IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1),IMSM.eventHandler.liveCreators.size()-1);
  	worldIn.setBlockToAir(newPos);
  	if(IMSM.eventHandler.liveCreators.size()>4){
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("You have now placed more than 5 Live Structures, which may cause some lag."));
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Do the command /removelive to remove all live structures"));
  	}
  	if(name.equals("LivePlane") || name.equals("LiveFlyingShip1") || name.equals("LiveFlyingShip2") || name.equals("LiveAirplane") || name.equals("LiveAirBalloon") || name.equals("Live_Flying_Helicopter")){
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Thanks for choosing SimJoo's Aviation Solutions."));
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Please type in the distance (in blocks) you'd like to fly."));
  		IMSM.dialoge=1;
  		IMSM.currentInput = IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1);
  	} else if (name.equals("LiveBoat")){
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Thanks for choosing SimJoo's Maritime Solutions."));
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Please type in the distance (in blocks) you'd like to sail."));
  		IMSM.dialoge=2;
  		IMSM.currentInput = IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1);
  	} else if(name.equals("Live_Bus") || name.equals("Live_Bus2")){
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Thanks for choosing SimJoo's Bus Depot."));
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Please type in the distance (in blocks) you'd like to ride."));
  		IMSM.dialoge=3;
  		IMSM.currentInput = IMSM.eventHandler.liveCreators.get(IMSM.eventHandler.liveCreators.size()-1);
  	} else if (name.equals("Live_FerrisWheel") || name.equals("Live_Fair_FreeFall")){
  		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Use /ride to ride this structure!."));
  	}
        return true;
    }
  

public String getName()
  {
    return name;
  }
}
