package modid.imsm.worldgeneration;

import java.util.ArrayList;
import java.util.List;

import modid.imsm.core.IMSM;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class LiveCommand implements ICommand
{
  private List aliases;
  public LiveCommand()
  {
    this.aliases = new ArrayList();
    this.aliases.add("removelivestructures");
    this.aliases.add("liveremove");
    this.aliases.add("livestructuresremove");
  }

  @Override
  public String getCommandName()
  {
    return "removelive";
  }

  @Override
  public String getCommandUsage(ICommandSender icommandsender)
  {
    return "removelive";
  }
  
  public String getCommandUsage()
  {
    return "removelive";
  }

  @Override
  public List getCommandAliases()
  {
    return this.aliases;
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) 
  {
	  int j = 0;
	  for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
		  IMSM.eventHandler.liveCreators.get(i).removeThisLiveStructure(false);
		  i--;
		  j++;
	  }
	  Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString("Removed "+j+" Live Structures."));
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

@Override
public int compareTo(ICommand arg0) {
	// TODO Auto-generated method stub
	return 0;
}
}
