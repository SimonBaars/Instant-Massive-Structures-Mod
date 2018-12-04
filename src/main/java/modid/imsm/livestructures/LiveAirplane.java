package modid.imsm.livestructures;

import modid.imsm.core.BlockLiveStructure;

public class LiveAirplane extends BlockLiveStructure
{
  public LiveAirplane(int i)
    {
	  	super("LiveAirplane", true/*doreplaceair*/, 1/*nslides*/, 300/*slidespeed*/, 0/*modifierx*/, 0/*modifiery*/, 0/*modifierz*/, 0/*livemodx*/,0/*livemody*/,0/*livemodz*/, false /*doLoop*/);
	}
}
