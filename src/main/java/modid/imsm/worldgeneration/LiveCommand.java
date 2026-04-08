package modid.imsm.worldgeneration;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import modid.imsm.core.IMSM;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class LiveCommand 
{
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("undo").executes(LiveCommand::ride));
	}

	private static int ride(CommandContext<CommandSource> context) {
		runCommand();
		return 1;
	}

  public static void runCommand() 
  {
	  int j = 0;
	  for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
		  IMSM.eventHandler.liveCreators.get(i).removeThisLiveStructure(false);
		  i--;
		  j++;
	  }
	  Minecraft.getInstance().player.sendChatMessage("Removed "+j+" Live Structures.");
  }


}
