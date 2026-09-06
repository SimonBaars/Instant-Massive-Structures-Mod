package com.simonbaars.imsm.core;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Live-structure animation: cycles schematic frames for stationary rides
 * (Ferris, Mill, Water Mill, Power Windmill, Helicopter, Cinema, FreeFall).
 * FreeFall also supports a simplified {@code /ride} Y-curve teleporter.
 * Aviation/boat/bus path animation + dialog remain unwired.
 */
public final class LiveStructureTicker {
	/** One animated live type: entry base name + frame schematic names + tick interval. */
	public record LiveDef(
		String baseName,
		int ticksPerFrame,
		/** Optional per-frame waits: after placing frames[i], wait waitTicksAfterFrame[i] before next.
		 *  Null → always use ticksPerFrame. Length must equal frames.length when non-null. */
		int[] waitTicksAfterFrame,
		String... frames
	) {
		public LiveDef(String baseName, int ticksPerFrame, String... frames) {
			this(baseName, ticksPerFrame, null, frames);
		}

		public boolean matches(String structureName) {
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

		public boolean hasVariableWaits() {
			return waitTicksAfterFrame != null;
		}
	}

	/**
	 * Legacy FreeFall waitTimes (ms) after placing a slide, converted ≈ ms/50 → ticks,
	 * with sticky intervals mapped explicitly (legacy did not reset to 200 after customs).
	 * Index = frame just placed (0..20). After frame 10 (→ slide 11) legacy used
	 * {@code (int)(15000*Math.random())}; we roll 5..200 ticks at runtime instead of a fixed slot.
	 */
	private static final int[] FREEFALL_WAIT_AFTER = {
		10, // after 0 → slide 1: 500ms
		60, // after 1 → slide 2: 3000ms
		16, // after 2 → slide 3: 800ms
		16, 16, 16, 16, 16, 16, 16, // after 3..9 sticky 800ms
		-1, // after 10 → slide 11: RANDOM (sentinel)
		5,  // after 11 → slide 12: 250ms
		5, 5, 5, 5, 5, 5, 5, 5, 5 // after 12..20 sticky 250ms
	};

	/** Legacy RideStructure(1) FreeFall Y offsets (relative to originY + 2.5). */
	private static final int[] FREEFALL_RIDE_Y = {
		0, 4, 11, 20, 32, 47, 65, 79, 87, 92, 87, 80, 63, 47, 30, 11, 5, 3, 1, 0
	};

	/**
	 * Stationary frame-cyclers from legacy BlockLiveStructure nslides.
	 * Aviation/boat/bus still need movement paths — not included.
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
		// Legacy nslides=21 → Live_Fair_FreeFall0..20, slidespeed 200ms + custom waitTimes; tiny schematics
		new LiveDef("Live_Fair_FreeFall", 4, FREEFALL_WAIT_AFTER,
			"Live_Fair_FreeFall0", "Live_Fair_FreeFall1", "Live_Fair_FreeFall2", "Live_Fair_FreeFall3",
			"Live_Fair_FreeFall4", "Live_Fair_FreeFall5", "Live_Fair_FreeFall6", "Live_Fair_FreeFall7",
			"Live_Fair_FreeFall8", "Live_Fair_FreeFall9", "Live_Fair_FreeFall10", "Live_Fair_FreeFall11",
			"Live_Fair_FreeFall12", "Live_Fair_FreeFall13", "Live_Fair_FreeFall14", "Live_Fair_FreeFall15",
			"Live_Fair_FreeFall16", "Live_Fair_FreeFall17", "Live_Fair_FreeFall18", "Live_Fair_FreeFall19",
			"Live_Fair_FreeFall20"),
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
				if (inst.ticksSinceFrame < inst.ticksUntilNext) {
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
		LiveInstance inst = new LiveInstance(world, origin, def);
		try {
			inst.placeCurrentFrame(true);
			inst.applyWaitAfterCurrentFrame();
			ACTIVE.add(inst);
			InstantMassiveStructures.LOGGER.info(
				"Started {} live animation at {} ({} frames, base {} ticks{})",
				def.baseName(), origin, def.frames().length, def.ticksPerFrame(),
				def.hasVariableWaits() ? ", variable waits" : "");
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

	/**
	 * Toggle FreeFall (or Ferris) ride for the player. FreeFall: Y-curve teleporter synced to
	 * frame ticks. Ferris ride path is documented but not ported (2D cart path + YSync).
	 * @return true if a ride was started or cancelled
	 */
	public static boolean toggleRide(ServerPlayer player) {
		// Cancel if already riding
		for (LiveInstance inst : ACTIVE) {
			if (player.getUUID().equals(inst.riderUuid)) {
				inst.clearRide();
				player.sendSystemMessage(Component.literal(
					"Thanks for your visit. We hope to see you again soon!"));
				return true;
			}
		}

		LiveInstance nearest = findNearestRideable(player, 48.0);
		if (nearest == null) {
			player.sendSystemMessage(Component.literal(
				"There is no freefall or ferris wheel in sight. Please step closer."));
			return false;
		}

		if ("Live_FerrisWheel".equals(nearest.baseName)) {
			player.sendSystemMessage(Component.literal(
				"Ferris /ride path (2D cart + YSync) is not ported yet — FreeFall ride works."));
			return false;
		}

		if (!"Live_Fair_FreeFall".equals(nearest.baseName)) {
			player.sendSystemMessage(Component.literal(
				"That live structure does not support /ride."));
			return false;
		}

		nearest.riderUuid = player.getUUID();
		// Legacy waits for slide==2; if already past that frame this cycle, seek mount now.
		nearest.rideProgress = nearest.frameIndex >= 2 ? -1 : -2;
		nearest.rideHoldX = player.getX();
		nearest.rideHoldZ = player.getZ();
		player.sendSystemMessage(Component.literal(
			"We'll pick you up on our next ride! Please hop aboard then."));
		InstantMassiveStructures.LOGGER.info("Player {} queued FreeFall ride at {}",
			player.getName().getString(), nearest.origin);
		return true;
	}

	private static LiveInstance findNearestRideable(ServerPlayer player, double maxDist) {
		LiveInstance best = null;
		double bestDist = maxDist;
		Vec3 pos = player.position();
		for (LiveInstance inst : ACTIVE) {
			if (!"Live_Fair_FreeFall".equals(inst.baseName)
				&& !"Live_FerrisWheel".equals(inst.baseName)) {
				continue;
			}
			if (inst.riderUuid != null) {
				continue;
			}
			double d = Math.sqrt(inst.origin.distToCenterSqr(pos.x, pos.y, pos.z));
			if (d < bestDist) {
				bestDist = d;
				best = inst;
			}
		}
		return best;
	}

	private static final class LiveInstance {
		final ServerLevel world;
		final BlockPos origin;
		final String baseName;
		final String[] frames;
		final int[] waitAfter;
		final int defaultTicks;
		int ticksUntilNext;
		int frameIndex;
		int ticksSinceFrame;
		int lastLength;
		int lastHeight;
		int lastWidth;

		// FreeFall ride subset
		UUID riderUuid;
		int rideProgress = -3; // -3 = none; -2 = waiting for slide 2; -1 = waiting mount; >=0 riding
		double rideHoldX;
		double rideHoldZ;

		LiveInstance(ServerLevel world, BlockPos origin, LiveDef def) {
			this.world = world;
			this.origin = origin.immutable();
			this.baseName = def.baseName();
			this.frames = def.frames();
			this.waitAfter = def.waitTicksAfterFrame();
			this.defaultTicks = def.ticksPerFrame();
			this.ticksUntilNext = def.ticksPerFrame();
			this.frameIndex = 0;
			this.ticksSinceFrame = 0;
		}

		void advanceFrame() throws Exception {
			clearLastBounds();
			frameIndex = (frameIndex + 1) % frames.length;
			placeCurrentFrame(false);
			applyWaitAfterCurrentFrame();
			tickRide();
		}

		void applyWaitAfterCurrentFrame() {
			if (waitAfter == null) {
				ticksUntilNext = defaultTicks;
				return;
			}
			int w = waitAfter[frameIndex];
			if (w < 0) {
				// Legacy random up to 15s; keep a usable non-zero range for playtest
				ticksUntilNext = 5 + ThreadLocalRandom.current().nextInt(196);
			} else {
				ticksUntilNext = Math.max(1, w);
			}
		}

		void placeCurrentFrame(boolean first) throws Exception {
			SchematicStructure structure = new SchematicStructure(frames[frameIndex]);
			structure.readFromFile();
			structure.process(world, origin.getX(), origin.getY(), origin.getZ());
			lastLength = structure.getLength();
			lastHeight = structure.getHeight();
			lastWidth = structure.getWidth();
			if (!first) {
				InstantMassiveStructures.LOGGER.debug("Live frame {} at {} (next wait {} ticks)",
					frames[frameIndex], origin, ticksUntilNext);
			}
		}

		void clearLastBounds() {
			if (lastLength <= 0 || lastHeight <= 0 || lastWidth <= 0) {
				return;
			}
			SchematicStructure.clearBounds(world, origin.getX(), origin.getY(), origin.getZ(),
				lastLength, lastHeight, lastWidth);
		}

		void clearRide() {
			riderUuid = null;
			rideProgress = -3;
		}

		void tickRide() {
			if (riderUuid == null || rideProgress == -3) {
				return;
			}
			ServerPlayer rider = world.getServer().getPlayerList().getPlayer(riderUuid);
			if (rider == null) {
				clearRide();
				return;
			}

			// Legacy: when currentSlide==2, progress -2 → -1 (search for mount).
			// If /ride was issued mid-cycle after frame 2, start seeking mount immediately
			// instead of waiting a full 21-frame loop.
			if (rideProgress == -2) {
				if (frameIndex == 2 || frameIndex > 2) {
					rideProgress = -1;
					InstantMassiveStructures.LOGGER.info("FreeFall ride: waiting for mount near {}", origin);
				}
				return;
			}

			double ox = origin.getX();
			double oy = origin.getY();
			double oz = origin.getZ();

			if (rideProgress == -1) {
				// Legacy mount pads around FreeFall (approx); also accept near origin for /imsm live starts
				if (nearMount(rider, ox, oy, oz) || rider.distanceToSqr(ox + 0.5, oy + 2.5, oz + 0.5) < 64) {
					rideProgress = 0;
					rideHoldX = rider.getX();
					rideHoldZ = rider.getZ();
					teleportRide(rider, oy);
					InstantMassiveStructures.LOGGER.info("FreeFall ride started for {}",
						rider.getName().getString());
				}
				return;
			}

			// Active ride: advance Y curve; cancel if player walks far from hold XZ
			if (Math.hypot(rider.getX() - rideHoldX, rider.getZ() - rideHoldZ) > 3.5) {
				rider.sendSystemMessage(Component.literal(
					"Thanks for your visit. We hope to see you again soon!"));
				clearRide();
				return;
			}

			if (rideProgress >= FREEFALL_RIDE_Y.length - 1) {
				rider.sendSystemMessage(Component.literal(
					"Thanks for your visit. We hope to see you again soon!"));
				clearRide();
				return;
			}

			rideProgress++;
			teleportRide(rider, oy);
		}

		private void teleportRide(ServerPlayer rider, double originY) {
			double y = originY + 2.5 + FREEFALL_RIDE_Y[Math.min(rideProgress, FREEFALL_RIDE_Y.length - 1)];
			rider.teleportTo(rideHoldX, y, rideHoldZ);
		}

		private static boolean nearMount(ServerPlayer p, double x, double y, double z) {
			return close(p.getX(), x + 0.5, 0.9) && close(p.getY(), y + 2.75, 1.5) && close(p.getZ(), z - 3.0, 2.9)
				|| close(p.getX(), x - 3.0, 2.9) && close(p.getY(), y + 2.75, 1.5) && close(p.getZ(), z - 6.5, 0.9)
				|| close(p.getX(), x - 6.5, 0.9) && close(p.getY(), y + 2.75, 1.5) && close(p.getZ(), z - 3.0, 2.9)
				|| close(p.getX(), x - 3.0, 2.9) && close(p.getY(), y + 2.75, 1.5) && close(p.getZ(), z - 0.5, 0.9);
		}

		private static boolean close(double a, double b, double howClose) {
			return a >= b - howClose && a <= b + howClose;
		}
	}
}
