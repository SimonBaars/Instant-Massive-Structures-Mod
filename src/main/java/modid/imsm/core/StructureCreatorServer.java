package modid.imsm.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class StructureCreatorServer extends StructureCreator {

	public StructureCreatorServer(String name, int i, int j, int k, boolean doReplaceAir, int id) {
		super(name, Minecraft.getMinecraft().getIntegratedServer().getEntityWorld(), i, j, k, doReplaceAir, id);
		// TODO Auto-generated constructor stub
	}

}
