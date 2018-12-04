package modid.imsm.livestructures;

import modid.imsm.core.BlockLiveStructure;

public class Live_WaterMill extends BlockLiveStructure
{
  public Live_WaterMill(int i)
    {
	  super("Live_WaterMill", true/*doreplaceair*/, 3/*nslides*/, 750/*slidespeed*/,14/*modifierx*/, -3/*modifiery*/, 9/*modifierz*/, 2/*livemodx*/,-2/*livemody*/,3/*livemodz*/, false /*doLoop*/);
    }
}
