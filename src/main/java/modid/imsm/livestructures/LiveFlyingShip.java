package modid.imsm.livestructures;

import modid.imsm.core.BlockLiveStructure;

public class LiveFlyingShip extends BlockLiveStructure
{
  public LiveFlyingShip(int i)
    {
	  super("LiveFlyingShip1", true/*doreplaceair*/, 3/*nslides*/, 300/*slidespeed*/, 15/*modifierx*/, -10/*modifiery*/, 24/*modifierz*/, 0/*livemodx*/,0/*livemody*/,0/*livemodz*/, false /*doLoop*/);
    }
}
