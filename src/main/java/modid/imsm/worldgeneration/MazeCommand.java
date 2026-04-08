package modid.imsm.worldgeneration;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import modid.imsm.core.IMSM;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class MazeCommand
{
	
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("undo").executes(MazeCommand::ride));
	}

	private static int ride(CommandContext<CommandSource> context) {
		runCommand();
		return 1;
	}
 
  public static void runCommand() 
  {
	  Minecraft.getInstance().player.sendChatMessage("-- MAZE RUNNER - MAZE GENERATOR --");
	  Minecraft.getInstance().player.sendChatMessage("Do you want the default settings {1} or want to setup the maze yourself {2}?");
	  Minecraft.getInstance().player.sendChatMessage("Please type the number of your choice in the chat");
	  IMSM.dialoge=4;
    
  }
}
