package com.simonbaars.imsm.core;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Minimal live-structure animation: cycles Ferris Wheel schematic frames in place.
 */
public final class LiveStructureTicker {
	private static final int TICKS_PER_FRAME = 10;
	private static final String[] FERRIS_FRAMES = {
		"Live_FerrisWheel",
		"Live_FerrisWheel0",
		"Live_FerrisWheel1",
		"Live_FerrisWheel2"
	};

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
				if (inst.ticksSinceFrame < TICKS_PER_FRAME) {
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
		InstantMassiveStructures.LOGGER.info("LiveStructureTicker registered");
	}

	public static boolean isFerrisWheel(String structureName) {
		return structureName != null && structureName.startsWith("Live_FerrisWheel");
	}

	/**
	 * Place the first Ferris frame at origin and start cycling frames every ~10 ticks.
	 */
	public static void startFerrisWheel(ServerLevel world, BlockPos origin) {
		LiveInstance inst = new LiveInstance(world, origin, FERRIS_FRAMES);
		try {
			inst.placeCurrentFrame(true);
			ACTIVE.add(inst);
			InstantMassiveStructures.LOGGER.info("Started Ferris Wheel live animation at {}", origin);
		} catch (Exception e) {
			InstantMassiveStructures.LOGGER.error("Failed to start Ferris Wheel animation", e);
			throw new RuntimeException(e);
		}
	}

	private static final class LiveInstance {
		final ServerLevel world;
		final BlockPos origin;
		final String[] frames;
		int frameIndex;
		int ticksSinceFrame;
		int lastLength;
		int lastHeight;
		int lastWidth;

		LiveInstance(ServerLevel world, BlockPos origin, String[] frames) {
			this.world = world;
			this.origin = origin.immutable();
			this.frames = frames;
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
				InstantMassiveStructures.LOGGER.debug("Ferris frame {} at {}", frames[frameIndex], origin);
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
