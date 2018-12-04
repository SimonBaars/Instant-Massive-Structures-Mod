package modid.imsm.livestructures;

import modid.imsm.core.BlockLiveStructure;

public class LiveBoat extends BlockLiveStructure
{
  public LiveBoat(int i)
    {
	 	super("LiveBoat", true/*doreplaceair*/, 4/*nslides*/, 400/*slidespeed*/, 0/*modifierx*/, -2/*modifiery*/, 0/*modifierz*/, 0/*livemodx*/,0/*livemody*/,0/*livemodz*/, false /*doLoop*/);
	}
}
