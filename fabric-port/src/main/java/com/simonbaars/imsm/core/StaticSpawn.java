package com.simonbaars.imsm.core;

import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.minecraft.core.BlockPos;

/**
 * Click-relative anchor for non-live schematics.
 * Live shells and frames keep the raw click: {@code process} applies one half-size,
 * and the live frame shift is calculated against that formula.
 */
public final class StaticSpawn {
	private StaticSpawn() {}

	public static BlockPos placeAnchor(String structureName, SchematicStructure structure, BlockPos click) {
		if (LiveStructureTicker.isAnimatedLive(structureName)) {
			return click;
		}
		int[] mod = StructureRegistry.modifiersFor(structureName);
		return structure.staticProcessAnchor(click.getX(), click.getY(), click.getZ(),
			mod[0], mod[1], mod[2]);
	}
}
