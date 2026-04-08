package modid.imsm.core;

import net.minecraft.client.Minecraft;

public class LiveStructureServer extends LiveStructure {

	public LiveStructureServer(String structureName, int x, int y, int z, boolean doPlaceAir, int amountOfSlides,
			int waitTime, int id, int[][] animation, boolean doLoop) {
		super(structureName, Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension), x, y, z, doPlaceAir, amountOfSlides, waitTime, id, animation, doLoop);
		// TODO Auto-generated constructor stub
	}

}
