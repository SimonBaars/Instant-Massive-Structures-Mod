package com.simonbaars.imsm.structureloader;

/**
 * World position of a schematic cell.
 *
 * <p>Forge {@code SchematicStructure} does two X/Z shifts before
 * {@code StructureUtils.getWorldPos}:
 * <ol>
 *   <li>{@code pos -= size/2 - 1} on the click (already including {@code BlockStructure}'s modifier)</li>
 *   <li>subtract {@code center}, which is {@code size/2 + 0.5} on X/Z and {@code 0} on Y</li>
 * </ol>
 * Integer block coordinates of local {@code (x, y, z)} are therefore
 * {@code click + modifier + local - 2*(size/2) + 1} on X/Z, and
 * {@code clickY + modifierY + y} on Y. Y is not centered.
 *
 * <p>Fabric {@link SchematicStructure#process} keeps the single half-size used by live
 * shells and frames: {@code anchor - size/2 + 1 + local}. Live frame alignment (the
 * separate shell/frame fix) assumes that formula for both the shell and the frame.
 * Static and instant spawns pass {@link #staticAnchorX} so the same process method
 * lands on the Forge cell without moving live origins.
 *
 * <p>Schematic {@code WEOffsetX/Y/Z} is not applied. Forge never reads those tags.
 */
public final class SpawnOrigin {
	private SpawnOrigin() {}

	/** Local {@code (0, *, 0)} X under Fabric {@code process} / {@code clearBounds} / outline. */
	public static int fabricOriginX(int anchorX, int length) {
		return anchorX - (length / 2) + 1;
	}

	/** Local {@code (0, *, 0)} Z under Fabric {@code process}. {@code width} is schematic Length. */
	public static int fabricOriginZ(int anchorZ, int width) {
		return anchorZ - (width / 2) + 1;
	}

	public static int fabricWorldX(int anchorX, int length, int localX) {
		return fabricOriginX(anchorX, length) + localX;
	}

	public static int fabricWorldZ(int anchorZ, int width, int localZ) {
		return fabricOriginZ(anchorZ, width) + localZ;
	}

	public static int fabricWorldY(int anchorY, int localY) {
		return anchorY + localY;
	}

	/**
	 * Forge world X of schematic local {@code localX}.
	 * {@code length} is schematic Width (the X axis).
	 */
	public static int forgeWorldX(int clickX, int modX, int length, int localX) {
		return clickX + modX + localX - 2 * (length / 2) + 1;
	}

	/** Forge world Z of schematic local {@code localZ}. {@code width} is schematic Length. */
	public static int forgeWorldZ(int clickZ, int modZ, int width, int localZ) {
		return clickZ + modZ + localZ - 2 * (width / 2) + 1;
	}

	public static int forgeWorldY(int clickY, int modY, int localY) {
		return clickY + modY + localY;
	}

	/**
	 * X anchor to feed {@link SchematicStructure#process} so Fabric's single half-size
	 * equals {@link #forgeWorldX} for every local X.
	 */
	public static int staticAnchorX(int clickX, int modX, int length) {
		return clickX + modX - (length / 2);
	}

	public static int staticAnchorZ(int clickZ, int modZ, int width) {
		return clickZ + modZ - (width / 2);
	}

	public static int staticAnchorY(int clickY, int modY) {
		return clickY + modY;
	}
}
