package com.simonbaars.imsm.core;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Live-structure animation: cycles schematic frames in place for stationary rides
 * (Ferris, Mill, Water Mill, Power Windmill, Helicopter, Cinema). Legacy also had
 * moving vehicles / free-fall with path animation and player input — those remain unwired.
 */
public final class LiveStructureTicker {
	/** One animated live type: entry base name + frame schematic names + tick interval. */
	public record LiveDef(String baseName, int ticksPerFrame, String... frames) {
		boolean matches(String structureName) {
			if (structureName == null) {
				return false;
			}
			if (structureName.equals(baseName)) {
				return true;
			}
			// Match numbered frames belonging to this sequence (Live_Mill0..5, etc.)
			if (!structureName.startsWith(baseName)) {
				return false;
			}
			String suffix = structureName.substring(baseName.length());
			if (suffix.isEmpty()) {
				return true;
			}
			for (int i = 0; i < suffix.length(); i++) {
				if (!Character.isDigit(suffix.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Stationary frame-cyclers from legacy BlockLiveStructure nslides.
	 * Aviation/boat/bus/free-fall still need movement paths / ride — not included.
	 */
	private static final LiveDef[] DEFINITIONS = {
		new LiveDef("Live_FerrisWheel", 40,
			"Live_FerrisWheel", "Live_FerrisWheel0", "Live_FerrisWheel1", "Live_FerrisWheel2"),
		// Legacy nslides=6 → Live_Mill0..5 (750ms ≈ 15 ticks)
		new LiveDef("Live_Mill", 15,
			"Live_Mill0", "Live_Mill1", "Live_Mill2", "Live_Mill3", "Live_Mill4", "Live_Mill5"),
		// Legacy nslides=3 → Live_WaterMill0..2
		new LiveDef("Live_WaterMill", 15,
			"Live_WaterMill0", "Live_WaterMill1", "Live_WaterMill2"),
		// Legacy nslides=3 → Live_Power_Windmill_East0..2
		new LiveDef("Live_Power_Windmill_East", 15,
			"Live_Power_Windmill_East0", "Live_Power_Windmill_East1", "Live_Power_Windmill_East2"),
		// Legacy nslides=4 → Live_Helicopter0..3 (200ms ≈ 4 ticks; use 10 for visibility)
		new LiveDef("Live_Helicopter", 10,
			"Live_Helicopter0", "Live_Helicopter1", "Live_Helicopter2", "Live_Helicopter3"),
		// Legacy nslides=43 → Live_Cinema0..42, slidespeed 300ms ≈ 6 ticks; use 20 (slower) for llvmpipe
		// Frames are thin 1×20×30 screen slabs (~600 blocks), not the full 51×39×50 building.
		new LiveDef("Live_Cinema", 20,
			"Live_Cinema0", "Live_Cinema1", "Live_Cinema2", "Live_Cinema3", "Live_Cinema4", "Live_Cinema5",
			"Live_Cinema6", "Live_Cinema7", "Live_Cinema8", "Live_Cinema9", "Live_Cinema10", "Live_Cinema11",
			"Live_Cinema12", "Live_Cinema13", "Live_Cinema14", "Live_Cinema15", "Live_Cinema16", "Live_Cinema17",
			"Live_Cinema18", "Live_Cinema19", "Live_Cinema20", "Live_Cinema21", "Live_Cinema22", "Live_Cinema23",
			"Live_Cinema24", "Live_Cinema25", "Live_Cinema26", "Live_Cinema27", "Live_Cinema28", "Live_Cinema29",
			"Live_Cinema30", "Live_Cinema31", "Live_Cinema32", "Live_Cinema33", "Live_Cinema34", "Live_Cinema35",
			"Live_Cinema36", "Live_Cinema37", "Live_Cinema38", "Live_Cinema39", "Live_Cinema40", "Live_Cinema41",
			"Live_Cinema42"),
	};

	private static final Map<String, LiveDef> BY_BASE = new LinkedHashMap<>();
	static {
		for (LiveDef def : DEFINITIONS) {
			BY_BASE.put(def.baseName(), def);
		}
	}

	private static final List<LiveInstance> ACTIVE = new CopyOnWriteArrayList<>();
	private static boolean registered;

	private LiveStructureTicker() {}

	public static void init() {
		if (registered) {
			return;
		}
		registered = true;
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (ACTIVE.isEmpty()) {
				return;
			}
			for (LiveInstance inst : ACTIVE) {
				inst.ticksSinceFrame++;
				if (inst.ticksSinceFrame < inst.ticksPerFrame) {
					continue;
				}
				inst.ticksSinceFrame = 0;
				try {
					inst.advanceFrame();
				} catch (Exception e) {
					InstantMassiveStructures.LOGGER.error("Live structure animation failed for {}",
						inst.frames[inst.frameIndex], e);
					ACTIVE.remove(inst);
				}
			}
		});
		InstantMassiveStructures.LOGGER.info(
			"LiveStructureTicker registered ({} live types: {})",
			DEFINITIONS.length, String.join(", ", BY_BASE.keySet()));
	}

	public static LiveDef findDefinition(String structureName) {
		if (structureName == null) {
			return null;
		}
		for (LiveDef def : DEFINITIONS) {
			if (def.matches(structureName)) {
				return def;
			}
		}
		return null;
	}

	public static boolean isAnimatedLive(String structureName) {
		return findDefinition(structureName) != null;
	}

	/** @deprecated use {@link #isAnimatedLive(String)} */
	@Deprecated
	public static boolean isFerrisWheel(String structureName) {
		LiveDef def = findDefinition(structureName);
		return def != null && def.baseName().equals("Live_FerrisWheel");
	}

	/**
	 * Place the first frame at origin and start cycling.
	 * @return display name of the live type started
	 */
	public static String startLive(ServerLevel world, BlockPos origin, LiveDef def) {
		LiveInstance inst = new LiveInstance(world, origin, def.frames(), def.ticksPerFrame());
		try {
			inst.placeCurrentFrame(true);
			ACTIVE.add(inst);
			InstantMassiveStructures.LOGGER.info("Started {} live animation at {} ({} frames, every {} ticks)",
				def.baseName(), origin, def.frames().length, def.ticksPerFrame());
			return def.baseName();
		} catch (Exception e) {
			InstantMassiveStructures.LOGGER.error("Failed to start {} animation", def.baseName(), e);
			throw new RuntimeException(e);
		}
	}

	/** @deprecated use {@link #startLive(ServerLevel, BlockPos, LiveDef)} */
	@Deprecated
	public static void startFerrisWheel(ServerLevel world, BlockPos origin) {
		startLive(world, origin, BY_BASE.get("Live_FerrisWheel"));
	}

	private static final class LiveInstance {
		final ServerLevel world;
		final BlockPos origin;
		final String[] frames;
		final int ticksPerFrame;
		int frameIndex;
		int ticksSinceFrame;
		int lastLength;
		int lastHeight;
		int lastWidth;

		LiveInstance(ServerLevel world, BlockPos origin, String[] frames, int ticksPerFrame) {
			this.world = world;
			this.origin = origin.immutable();
			this.frames = frames;
			this.ticksPerFrame = ticksPerFrame;
			this.frameIndex = 0;
			this.ticksSinceFrame = 0;
		}

		void advanceFrame() throws Exception {
			clearLastBounds();
			frameIndex = (frameIndex + 1) % frames.length;
			placeCurrentFrame(false);
		}

		void placeCurrentFrame(boolean first) throws Exception {
			SchematicStructure structure = new SchematicStructure(frames[frameIndex]);
			structure.readFromFile();
			structure.process(world, origin.getX(), origin.getY(), origin.getZ());
			lastLength = structure.getLength();
			lastHeight = structure.getHeight();
			lastWidth = structure.getWidth();
			if (!first) {
				InstantMassiveStructures.LOGGER.debug("Live frame {} at {}", frames[frameIndex], origin);
			}
		}

		void clearLastBounds() {
			if (lastLength <= 0 || lastHeight <= 0 || lastWidth <= 0) {
				return;
			}
			SchematicStructure.clearBounds(world, origin.getX(), origin.getY(), origin.getZ(),
				lastLength, lastHeight, lastWidth);
		}
	}
}
