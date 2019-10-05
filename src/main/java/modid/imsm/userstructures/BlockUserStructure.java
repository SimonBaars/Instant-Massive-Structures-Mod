package modid.imsm.userstructures;

import modid.imsm.core.IMSM;
import modid.imsm.core.StructureCreator;
import modid.imsm.core.StructureCreatorServer;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BlockUserStructure extends Block {
	private final String name;
	private boolean hasOutline=false;
	private boolean doReplaceAir = true;
	private int modifierx=0;
	private int modifiery=0;
	private int modifierz=0;

	public BlockUserStructure(String name){
		super(Block.Properties.create(Material.ROCK));
		this.name=name;
	}
	
	public Block setCreativeTab(ItemGroup g) {
		//Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
	
	@Override
	 public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {


		if(worldIn.isRemote){
			return true;
		}
		if(player.getHeldItemMainhand()!=null && player.getHeldItemMainhand().getItem() == Items.REDSTONE){
			
        	IMSM.eventHandler.serverCreators.add(new OutlineCreator(name,pos,modifierx,modifiery,modifierz));
        	hasOutline=true;
    	} else if(player.getHeldItemMainhand()!=null && player.getHeldItemMainhand().getItem() == Items.BOOK) {
    			doReplaceAir=!doReplaceAir;
    			if(doReplaceAir){
    				Minecraft.getInstance().player.sendChatMessage("I will replace all existing blocks in the part I'm gonna spawn in with air now");
    			} else {
    				Minecraft.getInstance().player.sendChatMessage("I won't replace any existing blocks with air");
    			}
    	} /*else if(playerIn.getCurrentEquippedItem()!=null && playerIn.getCurrentEquippedItem().getItem() instanceof ItemAppleGold) {
    		if(worldIn.isRemote){
    			if(IMSM.spawnSpeed<10){
    			IMSM.spawnSpeed++;
    				Minecraft.getInstance().player.sendChatMessage("The speed in which structures will be created has been increased to "+IMSM.spawnSpeed+" (default is 4).");
    			}}
    	}*/ else if(player.getHeldItemMainhand()!=null && player.getHeldItemMainhand().getItem() == Items.FIRE_CHARGE) {
    			remove(new StructureCreatorServer(name, pos.getX()+modifierx, pos.getY()+modifiery, pos.getZ()+modifierz, doReplaceAir,getSize(IMSM.eventHandler.creators.size())));
    	} else {
    	/*if(hasOutline){
    		SchematicStructure struct = new SchematicStructure(name+".structure");
        	struct.readFromFile();
        	struct.removeOutline(pos.getX(),modifierx, pos.getY(),modifiery, pos.getZ(),modifierz, worldIn);
        	hasOutline=false;
    	}*/
    	BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    	IMSM.eventHandler.creators.add(new StructureCreatorUser(name, pos.getX()+modifierx, pos.getY()+modifiery, pos.getZ()+modifierz, doReplaceAir,getSize(IMSM.eventHandler.creators.size())));
    	worldIn.setBlockState(newPos, Blocks.AIR.getDefaultState());

    	}
        return true;
    }
	
	void remove(StructureCreator structure){
		if(structure!=null){
		SchematicStructure struct = new SchematicStructure(structure.structureName+".structure");
	    struct.readFromFile();
		for(int x = 0; x<struct.length; x++){
			for(int y =0; y<struct.height; y++){
				for(int z =0; z<struct.width; z++){
					Block blk = Blocks.AIR;
					   // Make a position.
					   BlockPos pos0 = new BlockPos(structure.x-x, structure.y+y ,structure.z-z);
					   // Get the default state(basically metadata 0)
					   BlockState state0=blk.getDefaultState();
					   // set the block
					   Minecraft.getInstance().world.setBlockState(pos0, state0);
					   Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).setBlockState(pos0, state0);
				}
			}
		}
		Minecraft.getInstance().player.sendChatMessage("The last placed structure has been removed.");
		//IMSM.lastPlaced=null;
	}else {
		Minecraft.getInstance().player.sendChatMessage("You didn't place a structure to undo.");
	}
	}

  
  public String getName()
  {
    return name;
  }
	
	@Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)   {
    	if(hasOutline && !worldIn.isRemote){
    		if(Minecraft.getInstance().world!=null){
    	SchematicStructure struct = new SchematicStructure(name+".structure");
    	struct.readFromFile();
    	//for(int i = 0; i<IMSM.worlds.length; i++){
    	struct.removeOutline(pos.getX(),modifierx, pos.getY(),modifiery, pos.getZ(),modifierz);
    	//}
    	hasOutline=false;
    		}
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
