package modid.imsm.core;

import net.minecraft.client.Minecraft;

public class LiveStructureClient extends LiveStructure {

	private LiveStructureClient(String structureName, int x, int y, int z, boolean doPlaceAir, int amountOfSlides,
			int waitTime, int id, int[][] animation, boolean doLoop) {
		super(structureName, Minecraft.getInstance().world, x, y, z, doPlaceAir, amountOfSlides, waitTime, id, animation, doLoop);
		// TODO Auto-generated constructor stub
	}

}
