package modid.imsm.livestructures;

import modid.imsm.core.BlockLiveStructure;

public class LiveAirBalloon extends BlockLiveStructure
{
  public LiveAirBalloon(int i)
    {
        super("LiveAirBalloon", true/*doreplaceair*/, 1/*nslides*/, 500/*slidespeed*/, 0/*modifierx*/, 0/*modifiery*/, 0/*modifierz*/, 0/*livemodx*/,0/*livemody*/,0/*livemodz*/, false /*doLoop*/);
    }
}
