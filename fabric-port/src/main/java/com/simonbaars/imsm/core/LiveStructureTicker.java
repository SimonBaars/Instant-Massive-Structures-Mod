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
 * Live-structure animation:
 * <ul>
 *   <li>Stationary frame cyclers (Ferris, Mill, Water Mill, Windmill, Helicopter, Cinema, FreeFall)</li>
 *   <li>FreeFall {@code /ride} Y-curve teleporter subset</li>
 *   <li>Path movers: LiveBoat (+ optional Live_Bus) — legacy +Z cruise with frame cycling</li>
 * </ul>
 * Aviation path + chat distance dialog remain partially deferred (boat uses command/default distance).
 */
public final class LiveStructureTicker {
	/**
	 * Legacy boat/bus path: phases {times, speed_ms, dx, dy, dz}.
	 * After dialog sets times on cruise phase: board 10s, then N steps of +1 Z every 300ms.
	 */
	public record PathMotion(
		int dx,
		int dy,
		int dz,
		/** ~300ms legacy → 6 ticks */
		int ticksPerStep,
		/** Playtest boarding (~2s); legacy was 10000ms */
		int boardingTicks,
		int defaultDistance,
		/** Short continuous loop for demo; legacy boat removes after one trip */
		boolean loop
	) {}

	/** One animated live type: entry base name + frame schematic names + tick interval. */
	public record LiveDef(
		String baseName,
		int ticksPerFrame,
		/** Optional per-frame waits: after placing frames[i], wait waitTicksAfterFrame[i] before next.
		 *  Null → always use ticksPerFrame. Length must equal frames.length when non-null. */
		int[] waitTicksAfterFrame,
		/** Null = stationary frame cycler; non-null = path mover */
		PathMotion path,
		String... frames
	) {
		public LiveDef(String baseName, int ticksPerFrame, String... frames) {
			this(baseName, ticksPerFrame, null, null, frames);
		}

		public LiveDef(String baseName, int ticksPerFrame, int[] waitTicksAfterFrame, String... frames) {
			this(baseName, ticksPerFrame, waitTicksAfterFrame, null, frames);
		}

		public LiveDef(String baseName, PathMotion path, String... frames) {
			this(baseName, path.ticksPerStep(), null, path, frames);
		}

		public boolean matches(String structureName) {
			if (structureName == null) {
				return false;
			}
			if (structureName.equals(baseName)) {
				return true;
			}
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

		public boolean isPathMover() {
			return path != null;
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

	/** Shared boat/bus cruise: +Z, 6 ticks/step, short boarding, default 24-block loop. */
	private static final PathMotion BOAT_BUS_PATH = new PathMotion(
		0, 0, 1,
		6,
		40,
		24,
		true
	);

	/**
	 * Stationary frame-cyclers + path movers from legacy BlockLiveStructure / EventHandler.getAnimationFor.
	 */
	private static final LiveDef[] DEFINITIONS = {
		new LiveDef("Live_FerrisWheel", 40,
			"Live_FerrisWheel", "Live_FerrisWheel0", "Live_FerrisWheel1", "Live_FerrisWheel2"),
		new LiveDef("Live_Mill", 15,
			"Live_Mill0", "Live_Mill1", "Live_Mill2", "Live_Mill3", "Live_Mill4", "Live_Mill5"),
		new LiveDef("Live_WaterMill", 15,
			"Live_WaterMill0", "Live_WaterMill1", "Live_WaterMill2"),
		new LiveDef("Live_Power_Windmill_East", 15,
			"Live_Power_Windmill_East0", "Live_Power_Windmill_East1", "Live_Power_Windmill_East2"),
		new LiveDef("Live_Helicopter", 10,
			"Live_Helicopter0", "Live_Helicopter1", "Live_Helicopter2", "Live_Helicopter3"),
		new LiveDef("Live_Cinema", 20,
			"Live_Cinema0", "Live_Cinema1", "Live_Cinema2", "Live_Cinema3", "Live_Cinema4", "Live_Cinema5",
			"Live_Cinema6", "Live_Cinema7", "Live_Cinema8", "Live_Cinema9", "Live_Cinema10", "Live_Cinema11",
			"Live_Cinema12", "Live_Cinema13", "Live_Cinema14", "Live_Cinema15", "Live_Cinema16", "Live_Cinema17",
			"Live_Cinema18", "Live_Cinema19", "Live_Cinema20", "Live_Cinema21", "Live_Cinema22", "Live_Cinema23",
			"Live_Cinema24", "Live_Cinema25", "Live_Cinema26", "Live_Cinema27", "Live_Cinema28", "Live_Cinema29",
			"Live_Cinema30", "Live_Cinema31", "Live_Cinema32", "Live_Cinema33", "Live_Cinema34", "Live_Cinema35",
			"Live_Cinema36", "Live_Cinema37", "Live_Cinema38", "Live_Cinema39", "Live_Cinema40", "Live_Cinema41",
			"Live_Cinema42"),
		new LiveDef("Live_Fair_FreeFall", 4, FREEFALL_WAIT_AFTER,
			"Live_Fair_FreeFall0", "Live_Fair_FreeFall1", "Live_Fair_FreeFall2", "Live_Fair_FreeFall3",
			"Live_Fair_FreeFall4", "Live_Fair_FreeFall5", "Live_Fair_FreeFall6", "Live_Fair_FreeFall7",
			"Live_Fair_FreeFall8", "Live_Fair_FreeFall9", "Live_Fair_FreeFall10", "Live_Fair_FreeFall11",
			"Live_Fair_FreeFall12", "Live_Fair_FreeFall13", "Live_Fair_FreeFall14", "Live_Fair_FreeFall15",
			"Live_Fair_FreeFall16", "Live_Fair_FreeFall17", "Live_Fair_FreeFall18", "Live_Fair_FreeFall19",
			"Live_Fair_FreeFall20"),
		// Legacy LiveBoat: nslides=4, path +Z for dialog distance (EventHandler boat/bus animation)
		new LiveDef("LiveBoat", BOAT_BUS_PATH,
			"LiveBoat0", "LiveBoat1", "LiveBoat2", "LiveBoat3"),
		// Legacy Live_Bus: nslides=1 → Live_Bus0 only; same +Z path
		new LiveDef("Live_Bus", BOAT_BUS_PATH,
			"Live_Bus0"),
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
	 * Place the first frame at origin and start cycling (or path-cruising).
	 * Path movers use {@link PathMotion#defaultDistance()}.
	 */
	public static String startLive(ServerLevel world, BlockPos origin, LiveDef def) {
		int distance = def.isPathMover() ? def.path().defaultDistance() : 0;
		return startLive(world, origin, def, distance);
	}

	/**
	 * @param distance sail/ride distance in blocks for path movers (ignored for stationary)
	 */
	public static String startLive(ServerLevel world, BlockPos origin, LiveDef def, int distance) {
		LiveInstance inst = new LiveInstance(world, origin, def, distance);
		try {
			inst.placeCurrentFrame(true);
			inst.applyWaitAfterCurrentFrame();
			ACTIVE.add(inst);
			if (def.isPathMover()) {
				InstantMassiveStructures.LOGGER.info(
					"Started {} path live at {} ({} frames, distance {}, step {}t, boarding {}t, loop={})",
					def.baseName(), origin, def.frames().length, inst.stepsTotal,
					def.path().ticksPerStep(), def.path().boardingTicks(), def.path().loop());
			} else {
				InstantMassiveStructures.LOGGER.info(
					"Started {} live animation at {} ({} frames, base {} ticks{})",
					def.baseName(), origin, def.frames().length, def.ticksPerFrame(),
					def.hasVariableWaits() ? ", variable waits" : "");
			}
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
		/** Stationary: fixed origin. Path: reset point for loops. */
		final BlockPos startOrigin;
		BlockPos origin;
		final String baseName;
		final String[] frames;
		final int[] waitAfter;
		final int defaultTicks;
		final PathMotion path;
		int ticksUntilNext;
		int frameIndex;
		int ticksSinceFrame;
		int lastLength;
		int lastHeight;
		int lastWidth;

		// Path mover state
		int pathPhase; // 0=boarding, 1=cruising, 2=done
		int stepsRemaining;
		int stepsTotal;
		int stepsDone;

		// FreeFall ride subset
		UUID riderUuid;
		int rideProgress = -3; // -3 = none; -2 = waiting for slide 2; -1 = waiting mount; >=0 riding
		double rideHoldX;
		double rideHoldZ;

		LiveInstance(ServerLevel world, BlockPos origin, LiveDef def, int distance) {
			this.world = world;
			this.startOrigin = origin.immutable();
			this.origin = origin.immutable();
			this.baseName = def.baseName();
			this.frames = def.frames();
			this.waitAfter = def.waitTicksAfterFrame();
			this.defaultTicks = def.ticksPerFrame();
			this.path = def.path();
			this.ticksUntilNext = def.isPathMover()
				? Math.max(1, def.path().boardingTicks())
				: def.ticksPerFrame();
			this.frameIndex = 0;
			this.ticksSinceFrame = 0;
			this.pathPhase = def.isPathMover() ? 0 : -1;
			this.stepsTotal = def.isPathMover()
				? Math.max(1, distance > 0 ? distance : def.path().defaultDistance())
				: 0;
			this.stepsRemaining = this.stepsTotal;
			this.stepsDone = 0;
		}

		void advanceFrame() throws Exception {
			if (path != null) {
				advancePath();
				return;
			}
			clearLastBounds();
			frameIndex = (frameIndex + 1) % frames.length;
			placeCurrentFrame(false);
			applyWaitAfterCurrentFrame();
			tickRide();
		}

		void advancePath() throws Exception {
			if (pathPhase == 0) {
				// Boarding finished → start cruise
				pathPhase = 1;
				ticksUntilNext = Math.max(1, path.ticksPerStep());
				InstantMassiveStructures.LOGGER.info(
					"{} departing {} for {} blocks (+{} z)", baseName, origin, stepsTotal, path.dz());
				return;
			}
			if (pathPhase == 2) {
				ACTIVE.remove(this);
				return;
			}

			// Cruising: clear, step, place next frame, carry nearby players
			clearLastBounds();
			origin = origin.offset(path.dx(), path.dy(), path.dz());
			frameIndex = (frameIndex + 1) % frames.length;
			placeCurrentFrame(false);
			carryNearbyPlayers(path.dx(), path.dy(), path.dz());
			stepsDone++;
			stepsRemaining--;
			ticksUntilNext = Math.max(1, path.ticksPerStep());

			if (stepsRemaining <= 0) {
				if (path.loop()) {
					clearLastBounds();
					origin = startOrigin;
					frameIndex = 0;
					placeCurrentFrame(false);
					stepsRemaining = stepsTotal;
					stepsDone = 0;
					pathPhase = 0;
					ticksUntilNext = Math.max(1, path.boardingTicks() / 2); // shorter reboard on loop
					InstantMassiveStructures.LOGGER.info(
						"{} completed short loop; returning to {} (reboard)", baseName, startOrigin);
				} else {
					clearLastBounds();
					pathPhase = 2;
					ticksUntilNext = 1;
					InstantMassiveStructures.LOGGER.info("{} voyage complete; removing", baseName);
				}
			}
		}

		void applyWaitAfterCurrentFrame() {
			if (path != null) {
				// Boarding wait already set in ctor; cruise sets its own
				if (pathPhase == 0) {
					ticksUntilNext = Math.max(1, path.boardingTicks());
				} else {
					ticksUntilNext = Math.max(1, path.ticksPerStep());
				}
				return;
			}
			if (waitAfter == null) {
				ticksUntilNext = defaultTicks;
				return;
			}
			int w = waitAfter[frameIndex];
			if (w < 0) {
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

		/** Carry players standing on/near the moving structure by the step delta. */
		void carryNearbyPlayers(int dx, int dy, int dz) {
			if (dx == 0 && dy == 0 && dz == 0) {
				return;
			}
			double cx = origin.getX() + 0.5;
			double cy = origin.getY();
			double cz = origin.getZ() + 0.5;
			double radius = Math.max(lastLength, lastWidth) / 2.0 + 2.0;
			for (ServerPlayer player : world.players()) {
				if (player.level() != world) {
					continue;
				}
				double dxp = player.getX() - cx;
				double dzp = player.getZ() - cz;
				if (dxp * dxp + dzp * dzp > radius * radius) {
					continue;
				}
				if (player.getY() < cy - 2 || player.getY() > cy + lastHeight + 3) {
					continue;
				}
				player.teleportTo(player.getX() + dx, player.getY() + dy, player.getZ() + dz);
			}
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
