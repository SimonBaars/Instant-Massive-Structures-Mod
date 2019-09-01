package modid.imsm.userstructures;

import modid.imsm.core.IMSM;
import modid.imsm.core.StructureCreator;
import modid.imsm.core.StructureCreatorServer;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
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
		Item.BLOCK_TO_ITEM.get(this).getCreativeTabs().add(g);
		return this;
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if(worldIn.isRemote){
			return true;
		}
		if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemRedstone){
			
        	IMSM.eventHandler.serverCreators.add(new OutlineCreator(name,pos,modifierx,modifiery,modifierz));
        	hasOutline=true;
    	} else if(playerIn.getHeldItemMainhand()!=null && playerIn.getHeldItemMainhand().getItem() instanceof ItemBook) {
    			doReplaceAir=!doReplaceAir;
    			if(doReplaceAir){
    				Minecraft.getInstance().player.sendChatMessage("I will replace all existing blocks in the part I'm gonna spawn in with air now"));
    			} else {
    				Minecraft.getInstance().player.sendChatMessage("I won't replace any existing blocks with air"));
    			}
    	} /*else if(playerIn.getCurrentEquippedItem()!=null && playerIn.getCurrentEquippedItem().getItem() instanceof ItemAppleGold) {
    		if(worldIn.isRemote){
    			if(IMSM.spawnSpeed<10){
    			IMSM.spawnSpeed++;
    				Minecraft.getInstance().player.sendChatMessage("The speed in which structures will be created has been increased to "+IMSM.spawnSpeed+" (default is 4).");
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
    	IMSM.eventHandler.creators.add(new StructureCreatorUser(name, pos.getX()+modifierx, pos.getY()+modifiery, pos.getZ()+modifierz, doReplaceAir,getSize(IMSM.eventHandler.creators.size())));
    	worldIn.setBlockState(newPos, new BlockState(Blocks.AIR, ImmutableMap.of()));

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
					   IBlockState state0=blk.getDefaultState();
					   // set the block
					   Minecraft.getInstance().world.setBlockState(pos0, state0);
					   Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension).setBlockState(pos0, state0);
				}
			}
		}
		Minecraft.getInstance().player.sendChatMessage("The last placed structure has been removed."));
		//IMSM.lastPlaced=null;
	}else {
		Minecraft.getInstance().player.sendChatMessage("You didn't place a structure to undo."));
	}
	}

  
  public String getName()
  {
    return name;
  }
	
	@Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)   {
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
    
    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
    	if(hasOutline && !worldIn.isRemote){
    	SchematicStructure struct = new SchematicStructure(name+".structure");
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
