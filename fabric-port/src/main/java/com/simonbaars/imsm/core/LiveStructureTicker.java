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
 *   <li>FreeFall {@code /ride} Y-curve; Ferris {@code /ride} 2D cart path (legacy RideStructure #0)</li>
 *   <li>Path movers: LiveBoat/Live_Bus (+Z cruise); LiveAirplane / Live_Flying_Helicopter /
 *       LivePlane / LiveAirBalloon / LiveFlyingShip1/2 (climb → level → descend aviation path)</li>
 *   <li>{@code /removelive} clears active lives; world-folder {@code LiveStructures/} persistence</li>
 * </ul>
 * Chat-typed distance dialog N/A — replaced by {@code /imsm live <type> [distance]}.
 */
public final class LiveStructureTicker {
	/**
	 * One path step delta (legacy animation columns dx,dy,dz).
	 */
	public record PathStep(int dx, int dy, int dz) {
		public boolean isStationary() {
			return dx == 0 && dy == 0 && dz == 0;
		}
	}

	/**
	 * Path mover motion.
	 * <ul>
	 *   <li>Boat/bus: cruise only ({@code climbCount=0}, {@code descendCount=0}).</li>
	 *   <li>Aviation: climb → level (distance − 60) → descend. Legacy dialog required
	 *       distance &gt; 60 and wrote {@code animation[3][0]=distance-60}.</li>
	 * </ul>
	 */
	public record PathMotion(
		int ticksPerStep,
		/** Playtest boarding (~2s); legacy was 10000ms */
		int boardingTicks,
		int defaultDistance,
		/** Short continuous loop for demo; legacy removes after one trip */
		boolean loop,
		/** If true, level steps = max(1, distance − 60); climb/descend are fixed */
		boolean aviation,
		PathStep climb,
		int climbCount,
		PathStep cruise,
		PathStep descend,
		int descendCount
	) {
		/** Simple boat/bus-style single-direction cruise. */
		public PathMotion(int dx, int dy, int dz, int ticksPerStep, int boardingTicks,
			int defaultDistance, boolean loop) {
			this(ticksPerStep, boardingTicks, defaultDistance, loop, false,
				new PathStep(0, 0, 0), 0,
				new PathStep(dx, dy, dz),
				new PathStep(0, 0, 0), 0);
		}

		public static PathMotion aviation(int ticksPerStep, int boardingTicks, int defaultDistance,
			boolean loop, PathStep climb, int climbCount, PathStep cruise,
			PathStep descend, int descendCount) {
			return new PathMotion(ticksPerStep, boardingTicks, defaultDistance, loop, true,
				climb, climbCount, cruise, descend, descendCount);
		}

		/** Legacy aviation: level steps = distance − 60 (climb 30 + descend ≈30). */
		public int levelStepsForDistance(int distance) {
			if (!aviation) {
				return Math.max(1, distance > 0 ? distance : defaultDistance);
			}
			int d = distance > 0 ? distance : defaultDistance;
			return Math.max(1, d - 60);
		}
	}

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
			// Exact frame names only — digit-suffix prefix would collide
			// (LiveFlyingShip1 vs LiveFlyingShip20, Live_Bus vs Live_Bus2).
			for (String frame : frames) {
				if (frame.equals(structureName)) {
					return true;
				}
			}
			return false;
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

	/**
	 * Legacy RideStructure(0) Ferris cart path: anim[0]=Y, anim[1]=Z offsets.
	 * Rider at origin + (-4.5, 1+Y, -36+0.5−Z).
	 */
	private static final int[] FERRIS_RIDE_Y = {
		0, 0, 1, 1, 2, 4, 6, 8, 12, 15, 18, 22, 25, 28, 32,
		35, 38, 42, 45, 48, 52, 55, 58, 62, 64, 66, 68, 69, 69, 70,
		70, 70, 69, 69, 68, 66, 64, 62, 58, 55, 52, 48, 45, 42, 38, 35,
		32, 28, 25, 22, 18, 15, 12, 8, 6, 4, 2, 1, 1, 0, 0
	};
	private static final int[] FERRIS_RIDE_Z = {
		0, -3, -7, -10, -13, -17, -20, -23, -27, -29, -31, -33, -34, -34, -35,
		-35, -35, -34, -34, -33, -31, -29, -27, -23, -20, -17, -13, -10, -7, -3,
		0, 3, 7, 10, 13, 17, 20, 23, 27, 29, 31, 33, 34, 34, 35, 35,
		35, 34, 34, 33, 31, 29, 27, 23, 20, 17, 13, 10, 7, 3, 0
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
	 * Legacy LiveAirplane: climb 30×{0,+1,+1} @300ms → level (d−60)×{0,0,+1} → descend 31×{0,−1,+1}.
	 * Default distance 76 → 16 level steps; short loop for playtest.
	 */
	private static final PathMotion AIRPLANE_PATH = PathMotion.aviation(
		6, 40, 76, true,
		new PathStep(0, 1, 1), 30,
		new PathStep(0, 0, 1),
		new PathStep(0, -1, 1), 31
	);

	/**
	 * Legacy Live_Flying_Helicopter: climb 30×{0,+1,−1} @200ms → level (d−60)×{0,0,−1} →
	 * descend 31×{0,−1,−1}. Default 76 → 16 level; 4 rotor frames.
	 */
	private static final PathMotion FLYING_HELI_PATH = PathMotion.aviation(
		4, 40, 76, true,
		new PathStep(0, 1, -1), 30,
		new PathStep(0, 0, -1),
		new PathStep(0, -1, -1), 31
	);

	/**
	 * Legacy LivePlane: climb 30×{−1,+1,0} @100ms → level (d−60)×{−1,0,0} → descend 31×{−1,−1,0}.
	 * 100ms ≈ 2 ticks; default fly 76 → 16 level; short loop for playtest.
	 */
	private static final PathMotion PLANE_PATH = PathMotion.aviation(
		2, 40, 76, true,
		new PathStep(-1, 1, 0), 30,
		new PathStep(-1, 0, 0),
		new PathStep(-1, -1, 0), 31
	);

	/**
	 * Legacy LiveAirBalloon / LiveFlyingShip1: climb 30×{0,+1,−1} @500ms → level (d−60)×{0,0,−1} →
	 * descend 31×{0,−1,−1}. 500ms ≈ 10 ticks.
	 */
	private static final PathMotion BALLOON_SHIP1_PATH = PathMotion.aviation(
		10, 40, 76, true,
		new PathStep(0, 1, -1), 30,
		new PathStep(0, 0, -1),
		new PathStep(0, -1, -1), 31
	);

	/**
	 * Legacy LiveFlyingShip2: climb 30×{+1,+1,0} @500ms → level (d−60)×{+1,0,0} → descend 31×{+1,−1,0}.
	 */
	private static final PathMotion FLYING_SHIP2_PATH = PathMotion.aviation(
		10, 40, 76, true,
		new PathStep(1, 1, 0), 30,
		new PathStep(1, 0, 0),
		new PathStep(1, -1, 0), 31
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
		// Legacy LiveAirplane: nslides=1, aviation climb/level/descend +Z
		new LiveDef("LiveAirplane", AIRPLANE_PATH,
			"LiveAirplane0"),
		// Legacy Live_Flying_Helicopter: nslides=4 @200ms, aviation −Z
		new LiveDef("Live_Flying_Helicopter", FLYING_HELI_PATH,
			"Live_Flying_Helicopter0", "Live_Flying_Helicopter1",
			"Live_Flying_Helicopter2", "Live_Flying_Helicopter3"),
		// Legacy LivePlane: nslides=1 @100ms, aviation −X
		new LiveDef("LivePlane", PLANE_PATH,
			"LivePlane0"),
		// Legacy LiveAirBalloon: nslides=1 @500ms, aviation −Z (same path as FlyingShip1)
		new LiveDef("LiveAirBalloon", BALLOON_SHIP1_PATH,
			"LiveAirBalloon0"),
		// Legacy LiveFlyingShip → structure LiveFlyingShip1, nslides=3 @500ms, aviation −Z
		new LiveDef("LiveFlyingShip1", BALLOON_SHIP1_PATH,
			"LiveFlyingShip10", "LiveFlyingShip11", "LiveFlyingShip12"),
		// Legacy LiveFlyingShip2: nslides=3 @500ms, aviation +X
		new LiveDef("LiveFlyingShip2", FLYING_SHIP2_PATH,
			"LiveFlyingShip20", "LiveFlyingShip21", "LiveFlyingShip22"),
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
		LiveStructurePersistence.init();
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
					LiveStructurePersistence.saveInstance(inst);
				} catch (Exception e) {
					InstantMassiveStructures.LOGGER.error("Live structure animation failed for {}",
						inst.frames[Math.max(0, Math.min(inst.frameIndex, inst.frames.length - 1))], e);
					ACTIVE.remove(inst);
					LiveStructurePersistence.saveAll(ACTIVE);
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
			LiveStructurePersistence.saveAll(ACTIVE);
			if (def.isPathMover()) {
				PathMotion pm = def.path();
				InstantMassiveStructures.LOGGER.info(
					"Started {} path live at {} ({} frames, distance {}, levelSteps {}, climb {}/{}, descend {}/{}, step {}t, boarding {}t, loop={}, aviation={})",
					def.baseName(), origin, def.frames().length, inst.flyDistance,
					inst.levelSteps, pm.climbCount(),
					formatStep(pm.climb()), pm.descendCount(), formatStep(pm.descend()),
					pm.ticksPerStep(), pm.boardingTicks(), pm.loop(), pm.aviation());
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
	 * Legacy {@code /removelive}: stop all active live structures (leave last frame blocks in place).
	 * @return number removed
	 */
	public static int removeAllLives() {
		return removeAllLives(null);
	}

	public static int removeAllLives(net.minecraft.server.MinecraftServer server) {
		int n = ACTIVE.size();
		net.minecraft.server.MinecraftServer srv = server;
		for (LiveInstance inst : ACTIVE) {
			inst.clearRide();
			if (srv == null && inst.world != null) {
				srv = inst.world.getServer();
			}
		}
		ACTIVE.clear();
		if (srv != null) {
			LiveStructurePersistence.clearAllFiles(srv);
		} else {
			LiveStructurePersistence.rewriteIndex(ACTIVE);
		}
		InstantMassiveStructures.LOGGER.info("Removed {} live structures (/removelive)", n);
		return n;
	}

	public static int activeCount() {
		return ACTIVE.size();
	}

	static List<LiveInstance> activeSnapshot() {
		return List.copyOf(ACTIVE);
	}

	/** Restore a live from disk after world load (does not re-place first frame). */
	static void resumeFromSave(LiveInstance inst) {
		ACTIVE.add(inst);
		InstantMassiveStructures.LOGGER.info(
			"Resumed {} live at {} phase={} stepsLeft={} frame={}",
			inst.baseName, inst.origin, inst.pathPhase, inst.stepsRemaining, inst.frameIndex);
	}

	/**
	 * Toggle FreeFall or Ferris ride for the player.
	 * FreeFall: Y-curve teleporter. Ferris: legacy RideStructure #0 2D cart (Y+Z offsets).
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

		if (!"Live_Fair_FreeFall".equals(nearest.baseName)
			&& !"Live_FerrisWheel".equals(nearest.baseName)) {
			player.sendSystemMessage(Component.literal(
				"That live structure does not support /ride."));
			return false;
		}

		nearest.riderUuid = player.getUUID();
		if ("Live_FerrisWheel".equals(nearest.baseName)) {
			// Legacy: wait until slide 1 before seeking mount
			nearest.rideProgress = nearest.frameIndex >= 1 ? -1 : -2;
		} else {
			nearest.rideProgress = nearest.frameIndex >= 2 ? -1 : -2;
		}
		nearest.rideHoldX = player.getX();
		nearest.rideHoldZ = player.getZ();
		player.sendSystemMessage(Component.literal(
			"We'll pick you up on our next ride! Please hop aboard then."));
		InstantMassiveStructures.LOGGER.info("Player {} queued {} ride at {}",
			player.getName().getString(), nearest.baseName, nearest.origin);
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


	private static String formatStep(PathStep s) {
		if (s == null) {
			return "n/a";
		}
		return "{" + s.dx() + "," + s.dy() + "," + s.dz() + "}";
	}

	static final class LiveInstance {
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

		// Path mover state: 0=boarding, 1=climb, 2=level/cruise, 3=descend, 4=done
		int pathPhase;
		int stepsRemaining; // steps left in current motion phase
		int levelSteps;
		int flyDistance;
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
			this.flyDistance = def.isPathMover()
				? Math.max(1, distance > 0 ? distance : def.path().defaultDistance())
				: 0;
			this.levelSteps = def.isPathMover()
				? def.path().levelStepsForDistance(this.flyDistance)
				: 0;
			this.stepsRemaining = 0;
			this.stepsDone = 0;
		}

		/** Rebuild from {@code LiveStructures/N.txt} after world reload. */
		static LiveInstance restore(ServerLevel world, BlockPos startOrigin, BlockPos origin,
			LiveDef def, int distance, int pathPhase, int stepsDone, int stepsRemaining,
			int frameIndex, int levelSteps, int ticksUntilNext,
			int lastLength, int lastHeight, int lastWidth) {
			LiveInstance inst = new LiveInstance(world, startOrigin, def, distance);
			inst.origin = origin.immutable();
			inst.pathPhase = pathPhase;
			inst.stepsDone = stepsDone;
			inst.stepsRemaining = stepsRemaining;
			inst.frameIndex = Math.max(0, Math.min(frameIndex, def.frames().length - 1));
			inst.levelSteps = levelSteps;
			inst.ticksUntilNext = Math.max(1, ticksUntilNext);
			inst.ticksSinceFrame = 0;
			inst.lastLength = lastLength;
			inst.lastHeight = lastHeight;
			inst.lastWidth = lastWidth;
			inst.flyDistance = Math.max(1, distance > 0 ? distance : (def.isPathMover()
				? def.path().defaultDistance() : 0));
			return inst;
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
				enterMotionPhaseAfterBoard();
				return;
			}
			if (pathPhase == 4) {
				ACTIVE.remove(this);
				LiveStructurePersistence.saveAll(ACTIVE);
				return;
			}

			PathStep step = currentMotionStep();
			if (step == null) {
				finishOrLoop();
				return;
			}

			clearLastBounds();
			origin = origin.offset(step.dx(), step.dy(), step.dz());
			frameIndex = (frameIndex + 1) % frames.length;
			placeCurrentFrame(false);
			carryNearbyPlayers(step.dx(), step.dy(), step.dz());
			stepsDone++;
			stepsRemaining--;
			ticksUntilNext = Math.max(1, path.ticksPerStep());

			if (stepsRemaining <= 0) {
				advanceMotionPhase();
			}
		}

		private PathStep currentMotionStep() {
			return switch (pathPhase) {
				case 1 -> path.climb();
				case 2 -> path.cruise();
				case 3 -> path.descend();
				default -> null;
			};
		}

		/** After boarding: climb (aviation) or cruise (boat). */
		private void enterMotionPhaseAfterBoard() {
			if (path.aviation() && path.climbCount() > 0) {
				pathPhase = 1;
				stepsRemaining = path.climbCount();
				InstantMassiveStructures.LOGGER.info(
					"{} departing {} — climb {} steps {} then level {} (fly distance {})",
					baseName, origin, path.climbCount(), formatStep(path.climb()),
					levelSteps, flyDistance);
			} else {
				pathPhase = 2;
				stepsRemaining = levelSteps;
				InstantMassiveStructures.LOGGER.info(
					"{} departing {} for {} blocks {}",
					baseName, origin, levelSteps, formatStep(path.cruise()));
			}
			ticksUntilNext = Math.max(1, path.ticksPerStep());
		}

		private void advanceMotionPhase() throws Exception {
			if (pathPhase == 1) {
				// Climb done → level
				pathPhase = 2;
				stepsRemaining = levelSteps;
				InstantMassiveStructures.LOGGER.info(
					"{} climb complete at {}; level {} steps {}",
					baseName, origin, levelSteps, formatStep(path.cruise()));
				return;
			}
			if (pathPhase == 2) {
				if (path.aviation() && path.descendCount() > 0) {
					pathPhase = 3;
					stepsRemaining = path.descendCount();
					InstantMassiveStructures.LOGGER.info(
						"{} level complete at {}; descend {} steps {}",
						baseName, origin, path.descendCount(), formatStep(path.descend()));
					return;
				}
				finishOrLoop();
				return;
			}
			if (pathPhase == 3) {
				finishOrLoop();
			}
		}

		private void finishOrLoop() throws Exception {
			if (path.loop()) {
				clearLastBounds();
				origin = startOrigin;
				frameIndex = 0;
				placeCurrentFrame(false);
				stepsDone = 0;
				pathPhase = 0;
				stepsRemaining = 0;
				ticksUntilNext = Math.max(1, path.boardingTicks() / 2);
				InstantMassiveStructures.LOGGER.info(
					"{} completed short loop; returning to {} (reboard)", baseName, startOrigin);
			} else {
				clearLastBounds();
				pathPhase = 4;
				ticksUntilNext = 1;
				InstantMassiveStructures.LOGGER.info("{} voyage complete; removing", baseName);
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

			boolean ferris = "Live_FerrisWheel".equals(baseName);
			int waitSlide = ferris ? 1 : 2;

			if (rideProgress == -2) {
				if (frameIndex >= waitSlide) {
					rideProgress = -1;
					InstantMassiveStructures.LOGGER.info("{} ride: waiting for mount near {}",
						baseName, origin);
				}
				return;
			}

			double ox = origin.getX();
			double oy = origin.getY();
			double oz = origin.getZ();

			if (rideProgress == -1) {
				boolean mounted = ferris
					? nearFerrisMount(rider, ox, oy, oz)
					: (nearFreeFallMount(rider, ox, oy, oz)
						|| rider.distanceToSqr(ox + 0.5, oy + 2.5, oz + 0.5) < 64);
				if (mounted) {
					rideProgress = 0;
					if (ferris) {
						teleportFerrisRide(rider, ox, oy, oz);
					} else {
						rideHoldX = rider.getX();
						rideHoldZ = rider.getZ();
						teleportFreeFallRide(rider, oy);
					}
					InstantMassiveStructures.LOGGER.info("{} ride started for {}",
						baseName, rider.getName().getString());
				}
				return;
			}

			if (ferris) {
				int idx = Math.min(rideProgress, FERRIS_RIDE_Y.length - 1);
				double expectX = ox - 4.5;
				double expectY = oy + 1.0 + FERRIS_RIDE_Y[idx];
				double expectZ = oz - 36.0 - FERRIS_RIDE_Z[idx] + 0.5;
				if (rider.distanceToSqr(expectX, expectY, expectZ) > 4.0) {
					rider.sendSystemMessage(Component.literal(
						"Thanks for your visit. We hope to see you again soon!"));
					clearRide();
					return;
				}
				if (rideProgress >= FERRIS_RIDE_Y.length - 1) {
					rider.sendSystemMessage(Component.literal(
						"Thanks for your visit. We hope to see you again soon!"));
					clearRide();
					return;
				}
				rideProgress++;
				teleportFerrisRide(rider, ox, oy, oz);
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
			teleportFreeFallRide(rider, oy);
		}

		private void teleportFreeFallRide(ServerPlayer rider, double originY) {
			double y = originY + 2.5 + FREEFALL_RIDE_Y[Math.min(rideProgress, FREEFALL_RIDE_Y.length - 1)];
			rider.teleportTo(rideHoldX, y, rideHoldZ);
		}

		private void teleportFerrisRide(ServerPlayer rider, double ox, double oy, double oz) {
			int idx = Math.min(rideProgress, FERRIS_RIDE_Y.length - 1);
			double x = ox - 4.5;
			double y = oy + 1.0 + FERRIS_RIDE_Y[idx];
			double z = oz - 36.0 - FERRIS_RIDE_Z[idx] + 0.5;
			rideHoldX = x;
			rideHoldZ = z;
			rider.teleportTo(x, y, z);
		}

		/** Legacy Ferris mount: near (x-4, y+1, z-36). Softened for playtest. */
		private static boolean nearFerrisMount(ServerPlayer p, double x, double y, double z) {
			return p.distanceToSqr(x - 4.0, y + 1.0, z - 36.0) < 36.0;
		}

		private static boolean nearFreeFallMount(ServerPlayer p, double x, double y, double z) {
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
