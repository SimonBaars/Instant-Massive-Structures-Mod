package modid.imsm.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class StructureCreatorClient extends StructureCreator {

	public StructureCreatorClient(String name, int i, int j, int k, boolean doReplaceAir, int id) {
		super(name, Minecraft.getMinecraft().theWorld, i, j, k, doReplaceAir, id);
		// TODO Auto-generated constructor stub
	}

}
