package modid.imsm.livestructures;

import modid.imsm.core.BlockLiveStructure;

public class BlockFerrisWheel extends BlockLiveStructure
{
  public BlockFerrisWheel(int i)
    {
	  super("Live_FerrisWheel", true/*doreplaceair*/, 3/*nslides*/, 500/*slidespeed*/, 1/*modifierx*/, -1/*modifiery*/, 36/*modifierz*/, 4/*livemodx*/,-2/*livemody*/,0/*livemodz*/, false /*doLoop*/);
		
    }
}
