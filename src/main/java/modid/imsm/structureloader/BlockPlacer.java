package modid.imsm.structureloader;

import java.util.ArrayList;

import net.minecraft.block.BannerBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockPlacer
{
	private World world;
	//private ArrayList<BlockPos> updatePos;
	//private ArrayList<IBlockState> updateState;
	boolean isLive;
	private ArrayList<BlockState> specialBlocks = new ArrayList<>();
	private ArrayList<BlockPos> specialBlockPos = new ArrayList<>();
	private ArrayList<BlockPos> disabledPos = new ArrayList<>();
	
	public BlockPlacer(World world, boolean isLive)
	{
		this.world = world;
		this.isLive=isLive;
		//this.updatePos = new ArrayList<BlockPos>();
		//this.updateState = new ArrayList<IBlockState>();
	}
	
	public void processSpecialBlocks(){
		for (int i = 0; i < this.specialBlocks.size(); i++)
		{
			add(specialBlocks.get(i), specialBlockPos.get(i), false);
			}
	}

	public boolean add(BlockState blockState, BlockPos blockPos)
	{
		return add(blockState, blockPos, true);
	}
	
	public boolean add(BlockState blockState, BlockPos blockPos, boolean doCheckSpecialBlock)
	{
		/*Field field;
		try {
			field = World.class.getDeclaredField("processingLoadedTiles");
			field.setAccessible(true);
	        Object value = field.get(world);
	        while((Boolean)value){
	        	value = field.get(world);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*world.processingLoadedTiles.setAccessible(true);
		while(world.processingLoadedTiles){
			
		}*/
		for(BlockPos pos : disabledPos){
			if(pos.getX()==blockPos.getX() && pos.getY()==blockPos.getY() && pos.getZ()==blockPos.getZ()){
				disabledPos.remove(pos);
				return true;
			}
		}
		Block block = blockState.getBlock();
		if(doCheckSpecialBlock && isSpecialBlock(block)){
			if(isLive){
				return false;
			}
			if(block instanceof DoorBlock){
				BlockPos pos = new BlockPos(blockPos.getX(),blockPos.getY()+1,blockPos.getZ());
				//world.setBlockState(blockPos, blockState..with(BlockDoor.HALF, 0), 2); TODO
		        //world.setBlockState(pos, blockState.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
		        disabledPos.add(pos);
		        return true;
			} else if(block instanceof BedBlock){
                //world.setBlockState(blockPos, blockState.withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT), 11);
                return false;
			}
			//specialBlocks.add(blockState);
			//specialBlockPos.add(blockPos);
			//return false;
		}
		if(isLive && blockState.getMaterial()==Material.LAVA){
			return false;
		}
		boolean blockAdded = DropFuncBlock.setBlock(this.world, blockState, blockPos, false, isLive);
		//this.updatePos.add(blockPos);
		//this.updateState.add(blockState);
		this.world.markAndNotifyBlock(blockPos, (Chunk) this.world.getChunk(blockPos), this.world.getBlockState(blockPos), blockState, 3);
		return blockAdded;
	}

	private boolean isSpecialBlock(Block block) {
		return (block instanceof BannerBlock || 
				block instanceof PressurePlateBlock ||
				block instanceof BedBlock ||
				block instanceof BrewingStandBlock ||
				block == Blocks.STONE_BUTTON ||
				block instanceof CactusBlock ||
				block instanceof CakeBlock ||
				block instanceof DoorBlock ||
				block instanceof FlowerPotBlock ||
				block instanceof LadderBlock ||
				block == Blocks.RAIL ||
				block == Blocks.COMPARATOR ||
				block instanceof RedstoneTorchBlock ||
				block instanceof RedstoneWireBlock ||
				block instanceof StandingSignBlock ||
				block instanceof BushBlock ||
				block instanceof TorchBlock ||
				block instanceof LeverBlock ||
				block instanceof TripWireHookBlock);
	}

	/*public void update()
	{
		for (int i = 0; i < this.updatePos.size(); i++)
		{
			this.world.markAndNotifyBlock(this.updatePos.get(i), this.world.getChunkFromBlockCoords(this.updatePos.get(i)), this.world.getBlockState(this.updatePos.get(i)), this.updateState.get(i), 3);
		}
	}*/
}
