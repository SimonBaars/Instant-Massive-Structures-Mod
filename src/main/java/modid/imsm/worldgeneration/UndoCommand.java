package modid.imsm.worldgeneration;

import java.util.ArrayList;
import java.util.List;

import modid.imsm.core.IMSM;
import modid.imsm.core.StructureRemover;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class UndoCommand implements ICommand
{
  private List aliases;
  public static ArrayList<IBlockState> removedStates = new ArrayList<IBlockState>();
  public static ArrayList<BlockPos> removedPositions = new ArrayList<BlockPos>();
public static int numBlocksUndoable = 0;
  public UndoCommand()
  {
    this.aliases = new ArrayList();
    this.aliases.add("undo");
  }

  @Override
  public String getCommandName()
  {
    return "undo";
  }

  @Override
  public String getCommandUsage(ICommandSender icommandsender)
  {
    return "undo";
  }
  
  public String getCommandUsage()
  {
    return "undo";
  }

  @Override
  public List getCommandAliases()
  {
    return this.aliases;
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) 
  {  
	 runCommand();
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
@Override
  public List getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
  {
    return null;
  }

  @Override
  public boolean isUsernameIndex(String[] astring, int i)
  {
    return false;
  }

@Override
public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
	// TODO Auto-generated method stub
	return true;
}

/*void remove(StructureCreator structure){
	if(structure!=null){
		
	IMSM.eventHandler.serverCreators.add(new StructureRemover(structure));
	IMSM.lastPlaced=null;
}else {
	Minecraft.getInstance().player.sendChatMessage("You didn't place a structure to undo."));
}
}*/

@Override
public int compareTo(ICommand o) {
	// TODO Auto-generated method stub
	return 0;
}

}
