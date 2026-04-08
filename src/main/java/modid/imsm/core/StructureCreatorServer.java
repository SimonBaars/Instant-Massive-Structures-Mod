package modid.imsm.core;

import net.minecraft.client.Minecraft;

public class StructureCreatorServer extends StructureCreator {

	public StructureCreatorServer(String name, int i, int j, int k, boolean doReplaceAir, int id) {
		super(name, Minecraft.getInstance().getIntegratedServer().getWorld(Minecraft.getInstance().player.dimension), i, j, k, doReplaceAir, id);
		// TODO Auto-generated constructor stub
	}

}
