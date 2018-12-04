package modid.imsm.livestructures;

import modid.imsm.core.BlockLiveStructure;

public class Live_Mill extends BlockLiveStructure
{
  public Live_Mill(int i)
    {
	  super("Live_Mill", true/*doreplaceair*/, 6/*nslides*/, 750/*slidespeed*/,15/*modifierx*/, -1/*modifiery*/, 15/*modifierz*/, 14/*livemodx*/,-13/*livemody*/,0/*livemodz*/, false /*doLoop*/);
    }
}
