package modid.imsm.worldgeneration;

import java.util.ArrayList;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import modid.imsm.core.IMSM;
import modid.imsm.core.StructureRemover;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;

public final class UndoCommand {
	
	  public static ArrayList<IBlockState> removedStates = new ArrayList<IBlockState>();
	  public static ArrayList<BlockPos> removedPositions = new ArrayList<BlockPos>();
	public static int numBlocksUndoable = 0;

    private UndoCommand() {}

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("undo").executes(UndoCommand::undo));
    }

	private static int undo(CommandContext<CommandSource> context) {
		runCommand();
		return 1;
	}
	
	public static void runCommand(){
		  if(removedStates.size()>0 && removedPositions.size()>0){
				 for(int i = 0; i<IMSM.eventHandler.serverCreators.size(); i++){
					 if(IMSM.eventHandler.serverCreators.get(i) instanceof StructureRemover){
						 return;
					 }
				 }
				 IMSM.eventHandler.serverCreators.add(0,new StructureRemover());
			 }
	  }
}