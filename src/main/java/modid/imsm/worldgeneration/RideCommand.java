package modid.imsm.worldgeneration;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import modid.imsm.core.IMSM;
import modid.imsm.livestructures.RideStructure;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class RideCommand{

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("undo").executes(RideCommand::ride));
	}

	private static int ride(CommandContext<CommandSource> context) {
		runCommand();
		return 1;
	}

	private static void runCommand() 
	{  
		// Minecraft.getInstance().player.sendChatMessage("Structure riding went broken in the 1.9 upgrade... Will fix asap!"));
		if(IMSM.eventHandler.isRiding==null){
			for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
				SchematicStructure struct = new SchematicStructure(IMSM.eventHandler.liveCreators.get(i).structureName+".structure", true);
				struct.readFromFile();
				if(IMSM.eventHandler.liveCreators.get(i).structureName.equals("Live_FerrisWheel") && IMSM.eventHandler.liveCreators.get(i).closeTo(5, Minecraft.getInstance().player.posX, Minecraft.getInstance().player.posY, Minecraft.getInstance().player.posZ, struct.width, struct.height, struct.length) && IMSM.eventHandler.liveCreators.get(i).ride==null){
					IMSM.eventHandler.liveCreators.get(i).ride=new RideStructure(0);
					IMSM.eventHandler.isRiding=IMSM.eventHandler.liveCreators.get(i);
					Minecraft.getInstance().player.sendChatMessage("Please hop aboard in the bottommost cart.");
					return;
				} else if(IMSM.eventHandler.liveCreators.get(i).structureName.equals("Live_Fair_FreeFall") && IMSM.eventHandler.liveCreators.get(i).closeTo(5, Minecraft.getInstance().player.posX, Minecraft.getInstance().player.posY, Minecraft.getInstance().player.posZ, struct.width, struct.height, struct.length) && IMSM.eventHandler.liveCreators.get(i).ride==null){
					IMSM.eventHandler.liveCreators.get(i).ride=new RideStructure(1);
					IMSM.eventHandler.isRiding=IMSM.eventHandler.liveCreators.get(i);
					Minecraft.getInstance().player.sendChatMessage("We'll pick you up on our next ride! Please hop aboard then.");
					return;
				}
			}
			Minecraft.getInstance().player.sendChatMessage("There is no freefall or ferris wheel is sight. Please step closer.");
		} else {
			for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
				IMSM.eventHandler.liveCreators.get(i).ride=null;
			}
			IMSM.eventHandler.isRiding=null;
		}
		/*IMSM.eventHandler.liveCreators.get(0).currentSlide++;
	  SchematicStructure struct = new SchematicStructure(IMSM.eventHandler.liveCreators.get(0).structureName+IMSM.eventHandler.liveCreators.get(0).currentSlide+".structure", true);
      struct.readFromFile();
      struct.process(IMSM.eventHandler.liveCreators.get(0).serverWorld,IMSM.eventHandler.liveCreators.get(0).worldIn, IMSM.eventHandler.liveCreators.get(0).x,IMSM.eventHandler.liveCreators.get(0).y,IMSM.eventHandler.liveCreators.get(0).z);*/

	}
}
