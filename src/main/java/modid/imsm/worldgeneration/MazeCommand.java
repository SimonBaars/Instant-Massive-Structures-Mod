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

public class MazeCommand implements ICommand
{
  private List aliases;
  public MazeCommand()
  {
    this.aliases = new ArrayList();
    this.aliases.add("mazegen");
    this.aliases.add("generatemaze");
  }

  @Override
  public String getCommandName()
  {
    return "maze";
  }

  @Override
  public String getCommandUsage(ICommandSender icommandsender)
  {
    return "maze";
  }
  
  public String getCommandUsage()
  {
    return "maze";
  }

  @Override
  public List getCommandAliases()
  {
    return this.aliases;
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) 
  {
	  Minecraft.getInstance().thePlayer.addChatMessage(new TextComponentString("-- MAZE RUNNER - MAZE GENERATOR --"));
	  Minecraft.getInstance().thePlayer.addChatMessage(new TextComponentString("Do you want the default settings {1} or want to setup the maze yourself {2}?"));
	  Minecraft.getInstance().thePlayer.addChatMessage(new TextComponentString("Please type the number of your choice in the chat"));
	  IMSM.dialoge=4;
    
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
public int compareTo(ICommand o) {
	// TODO Auto-generated method stub
	return 0;
}
}
