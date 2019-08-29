package modid.imsm.worldgeneration;

import java.util.ArrayList;
import java.util.List;

import modid.imsm.core.IMSM;
import modid.imsm.livestructures.RideStructure;
import modid.imsm.structureloader.SchematicStructure;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class RideCommand implements ICommand
{
  private List aliases;
  public RideCommand()
  {
    this.aliases = new ArrayList();
    this.aliases.add("ridestructure");
    this.aliases.add("ridethis");
  }

  @Override
  public String getCommandName()
  {
    return "ride";
  }

  @Override
  public String getCommandUsage(ICommandSender icommandsender)
  {
    return "ride";
  }
  
  public String getCommandUsage()
  {
    return "ride";
  }

  @Override
  public List getCommandAliases()
  {
    return this.aliases;
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) 
  {  
	 // Minecraft.getInstance().thePlayer.addChatMessage(new TextComponentString("Structure riding went broken in the 1.9 upgrade... Will fix asap!"));
	  if(IMSM.eventHandler.isRiding==null){
	  for(int i = 0; i<IMSM.eventHandler.liveCreators.size(); i++){
			SchematicStructure struct = new SchematicStructure(IMSM.eventHandler.liveCreators.get(i).structureName+".structure", true);
			struct.readFromFile();
			if(IMSM.eventHandler.liveCreators.get(i).structureName.equals("Live_FerrisWheel") && IMSM.eventHandler.liveCreators.get(i).closeTo(5, Minecraft.getInstance().thePlayer.posX, Minecraft.getInstance().thePlayer.posY, Minecraft.getInstance().thePlayer.posZ, struct.width, struct.height, struct.length) && IMSM.eventHandler.liveCreators.get(i).ride==null){
				IMSM.eventHandler.liveCreators.get(i).ride=new RideStructure(0);
				IMSM.eventHandler.isRiding=IMSM.eventHandler.liveCreators.get(i);
				Minecraft.getInstance().thePlayer.addChatMessage(new TextComponentString("Please hop aboard in the bottommost cart."));
				return;
			} else if(IMSM.eventHandler.liveCreators.get(i).structureName.equals("Live_Fair_FreeFall") && IMSM.eventHandler.liveCreators.get(i).closeTo(5, Minecraft.getInstance().thePlayer.posX, Minecraft.getInstance().thePlayer.posY, Minecraft.getInstance().thePlayer.posZ, struct.width, struct.height, struct.length) && IMSM.eventHandler.liveCreators.get(i).ride==null){
				IMSM.eventHandler.liveCreators.get(i).ride=new RideStructure(1);
				IMSM.eventHandler.isRiding=IMSM.eventHandler.liveCreators.get(i);
				Minecraft.getInstance().thePlayer.addChatMessage(new TextComponentString("We'll pick you up on our next ride! Please hop aboard then."));
				return;
			}
		}
	  Minecraft.getInstance().thePlayer.addChatMessage(new TextComponentString("There is no freefall or ferris wheel is sight. Please step closer."));
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
