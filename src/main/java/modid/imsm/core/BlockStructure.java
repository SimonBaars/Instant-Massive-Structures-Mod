package modid.imsm.core;

import modid.imsm.structureloader.SchematicStructure;
import modid.imsm.userstructures.OutlineCreator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockStructure extends Block {
	private final String name;
	private boolean hasOutline=false;
	private boolean doReplaceAir;
	private int modifierx;
	private int modifiery;
	private int modifierz;

	public BlockStructure(String name, boolean doReplaceAir, int modifierx, int modifiery, int modifierz){
		super(Material.ROCK);
		this.name=name;
		this.doReplaceAir=doReplaceAir;
		this.modifierx=modifierx;
		this.modifiery=modifiery;
		this.modifierz=modifierz;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		/*if(IMSM.worlds[0]==null || IMSM.worlds[1]==null){
		if (worldIn.isRemote) {
		      IMSM.worlds[0] = worldIn;
		    } else {
		    	IMSM.worlds[1] = worldIn;
		    }
		}*/
		if(worldIn.isRemote){
			return true;
		}
		if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemRedstone){
			
        	//SchematicStructure struct = new SchematicStructure(name+".structure", false);
        	//struct.readFromFile();
IMSM.eventHandler.serverCreators.add(new OutlineCreator(name, pos ,modifierx, modifiery, modifierz));
        	hasOutline=true;
    	} else if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemBook) {
    			doReplaceAir=!doReplaceAir;
    			if(doReplaceAir){
    				Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("I will replace all existing blocks in the part I'm gonna spawn in with air now"));
    			} else {
    				Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("I won't replace any existing blocks with air"));
    			}
    	} /*else if(playerIn.getCurrentEquippedItem()!=null && playerIn.getCurrentEquippedItem().getItem() instanceof ItemAppleGold) {
    		if(worldIn.isRemote){
    			if(IMSM.spawnSpeed<10){
    			IMSM.spawnSpeed++;
    				Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("The speed in which structures will be created has been increased to "+IMSM.spawnSpeed+" (default is 4).");
    			}}
    	}*/ else if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemFireball) {
    			remove(new StructureCreatorServer(name, pos.getX()+modifierx, pos.getY()+modifiery, pos.getZ()+modifierz, doReplaceAir,getSize(IMSM.eventHandler.creators.size())));
    	} else {
    	/*if(hasOutline){
    		SchematicStructure struct = new SchematicStructure(name+".structure");
        	struct.readFromFile();
        	struct.removeOutline(pos.getX(),modifierx, pos.getY(),modifiery, pos.getZ(),modifierz, worldIn);
        	hasOutline=false;
    	}*/
    	BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    	IMSM.eventHandler.creators.add(new StructureCreatorClient(name, pos.getX()+modifierx, pos.getY()+modifiery, pos.getZ()+modifierz, doReplaceAir,getSize(IMSM.eventHandler.creators.size())));
    	worldIn.setBlockToAir(newPos);

    	}
        return true;
    }
	
	void remove(StructureCreator structure){
		if(structure!=null){
		SchematicStructure struct = new SchematicStructure(structure.structureName+".structure", false);
	    struct.readFromFile();
		for(int x = 0; x<struct.length; x++){
			for(int y =0; y<struct.height; y++){
				for(int z =0; z<struct.width; z++){
					Block blk = Blocks.AIR;
					   // Make a position.
					   BlockPos pos0 = new BlockPos(structure.x-x, structure.y+y ,structure.z-z);
					   // Get the default state(basically metadata 0)
					   IBlockState state0=blk.getDefaultState();
					   // set the block
					   Minecraft.getMinecraft().theWorld.setBlockState(pos0, state0);
					   Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().setBlockState(pos0, state0);
				}
			}
		}
		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("The last placed structure has been removed."));
		//IMSM.lastPlaced=null;
	}else {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("You didn't place a structure to undo."));
	}
	}

  
  public String getName()
  {
    return name;
  }
	
	@Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
    	if(hasOutline && !worldIn.isRemote){
    		if(Minecraft.getMinecraft().theWorld!=null){
    	SchematicStructure struct = new SchematicStructure(name+".structure", false);
    	struct.readFromFile();
    	//for(int i = 0; i<IMSM.worlds.length; i++){
    	struct.removeOutline(pos.getX(),modifierx, pos.getY(),modifiery, pos.getZ(),modifierz);
    	//struct.removeOutline(pos.getX(),modifierx, pos.getY(),modifiery, pos.getZ(),modifierz, Minecraft.getMinecraft().theWorld);
    	//}
    	hasOutline=false;
    		}
    	}
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
    	if(hasOutline && !worldIn.isRemote){
    	SchematicStructure struct = new SchematicStructure(name+".structure", false);
    	struct.readFromFile();
    	struct.removeOutline(pos.getX(),modifierx, pos.getY(),modifiery, pos.getZ(),modifierz);
    	hasOutline=false;
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
