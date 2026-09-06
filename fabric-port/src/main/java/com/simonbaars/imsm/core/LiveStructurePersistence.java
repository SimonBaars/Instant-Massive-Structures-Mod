package com.simonbaars.imsm.core;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Legacy-compatible live-structure persistence under {@code <world>/LiveStructures/N.txt}.
 * <p>
 * Legacy format (EventHandler.loadLiveCreators / LiveStructure.registerLiveCreator):
 * name, x, y, z, doPlaceAir, animationPhase, animationTimes, doLoop, amountOfSlides, waitTime,
 * plus an optional distance line for path movers.
 * <p>
 * Port maps animationPhase→pathPhase and animationTimes→steps done within phase; restores
 * origin and resumes the ticker without re-placing the first frame (blocks already in world).
 */
public final class LiveStructurePersistence {
	private static final String DIR = "LiveStructures";
	private static boolean registered;

	private LiveStructurePersistence() {}

	public static void init() {
		if (registered) {
			return;
		}
		registered = true;
		ServerLevelEvents.LOAD.register((server, level) -> {
			if (level.dimension() == Level.OVERWORLD) {
				loadFor(level);
			}
		});
		ServerLifecycleEvents.BEFORE_SAVE.register((server, flush, force) -> {
			saveAll(LiveStructureTicker.activeSnapshot());
		});
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			saveAll(LiveStructureTicker.activeSnapshot());
		});
		InstantMassiveStructures.LOGGER.info("LiveStructurePersistence hooked (LOAD + BEFORE_SAVE)");
	}

	static Path dir(MinecraftServer server) {
		return server.getWorldPath(LevelResource.ROOT).resolve(DIR);
	}

	static void saveAll(List<LiveStructureTicker.LiveInstance> active) {
		if (active.isEmpty()) {
			return;
		}
		MinecraftServer server = null;
		for (LiveStructureTicker.LiveInstance inst : active) {
			if (inst.world != null && inst.world.getServer() != null) {
				server = inst.world.getServer();
				break;
			}
		}
		if (server == null) {
			return;
		}
		try {
			Path folder = dir(server);
			Files.createDirectories(folder);
			// Rewrite dense index 0..n-1 like legacy
			try (Stream<Path> existing = Files.list(folder)) {
				existing.filter(p -> p.getFileName().toString().endsWith(".txt"))
					.forEach(p -> {
						try {
							Files.deleteIfExists(p);
						} catch (IOException ignored) {
						}
					});
			}
			int i = 0;
			for (LiveStructureTicker.LiveInstance inst : active) {
				writeOne(folder.resolve(i + ".txt"), inst);
				i++;
			}
		} catch (IOException e) {
			InstantMassiveStructures.LOGGER.error("Failed to save live structures", e);
		}
	}

	static void saveInstance(LiveStructureTicker.LiveInstance inst) {
		// Full rewrite keeps dense indexing simple and matches legacy registerLiveCreator churn
		saveAll(LiveStructureTicker.activeSnapshot());
	}

	static void deleteInstance(LiveStructureTicker.LiveInstance inst) {
		// saveAll after clear handles delete
	}

	static void rewriteIndex(List<LiveStructureTicker.LiveInstance> active) {
		MinecraftServer server = null;
		for (LiveStructureTicker.LiveInstance inst : active) {
			if (inst.world != null && inst.world.getServer() != null) {
				server = inst.world.getServer();
				break;
			}
		}
		if (server == null) {
			// Still try to clear leftover files when removing all
			return;
		}
		try {
			Path folder = dir(server);
			if (!Files.isDirectory(folder)) {
				return;
			}
			if (active.isEmpty()) {
				try (Stream<Path> existing = Files.list(folder)) {
					existing.filter(p -> p.getFileName().toString().endsWith(".txt"))
						.forEach(p -> {
							try {
								Files.deleteIfExists(p);
							} catch (IOException ignored) {
							}
						});
				}
				return;
			}
			saveAll(active);
		} catch (IOException e) {
			InstantMassiveStructures.LOGGER.error("Failed to rewrite live structure index", e);
		}
	}

	/** Clear LiveStructures when removelive emptied ACTIVE but we still know the server. */
	public static void clearAllFiles(MinecraftServer server) {
		try {
			Path folder = dir(server);
			if (!Files.isDirectory(folder)) {
				return;
			}
			try (Stream<Path> existing = Files.list(folder)) {
				existing.filter(p -> p.getFileName().toString().endsWith(".txt"))
					.forEach(p -> {
						try {
							Files.deleteIfExists(p);
						} catch (IOException ignored) {
						}
					});
			}
		} catch (IOException e) {
			InstantMassiveStructures.LOGGER.error("Failed to clear LiveStructures files", e);
		}
	}

	private static void writeOne(Path file, LiveStructureTicker.LiveInstance inst) throws IOException {
		try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(file, StandardCharsets.UTF_8))) {
			w.println(inst.baseName);
			w.println(inst.origin.getX());
			w.println(inst.origin.getY());
			w.println(inst.origin.getZ());
			w.println("true"); // doPlaceAir
			w.println(inst.pathPhase); // animationPhase (−1 = stationary cycler)
			w.println(inst.stepsDone); // animationTimes (approx)
			w.println(inst.path != null && inst.path.loop());
			w.println(inst.frames.length);
			w.println(inst.ticksUntilNext);
			if (inst.path != null) {
				w.println(inst.flyDistance);
				w.println(inst.startOrigin.getX());
				w.println(inst.startOrigin.getY());
				w.println(inst.startOrigin.getZ());
				w.println(inst.stepsRemaining);
				w.println(inst.frameIndex);
				w.println(inst.levelSteps);
				w.println(inst.lastLength);
				w.println(inst.lastHeight);
				w.println(inst.lastWidth);
			}
		}
	}

	static void loadFor(ServerLevel level) {
		MinecraftServer server = level.getServer();
		Path folder = dir(server);
		if (!Files.isDirectory(folder)) {
			return;
		}
		if (!LiveStructureTicker.activeSnapshot().isEmpty()) {
			InstantMassiveStructures.LOGGER.debug(
				"Skip LiveStructures load — {} already active", LiveStructureTicker.activeCount());
			return;
		}
		int loaded = 0;
		int i = 0;
		while (Files.isRegularFile(folder.resolve(i + ".txt"))) {
			Path file = folder.resolve(i + ".txt");
			try {
				if (loadOne(level, file)) {
					loaded++;
				}
			} catch (Exception e) {
				InstantMassiveStructures.LOGGER.error("Failed to load live structure {}", file, e);
			}
			i++;
		}
		if (loaded > 0) {
			InstantMassiveStructures.LOGGER.info(
				"Loaded {} live structure(s) from {}/{}", loaded, DIR, level.dimension().identifier());
		}
	}

	private static boolean loadOne(ServerLevel level, Path file) throws IOException {
		try (BufferedReader in = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
			String name = in.readLine();
			int x = Integer.parseInt(in.readLine().trim());
			int y = Integer.parseInt(in.readLine().trim());
			int z = Integer.parseInt(in.readLine().trim());
			in.readLine(); // doPlaceAir
			int phase = Integer.parseInt(in.readLine().trim());
			int times = Integer.parseInt(in.readLine().trim());
			in.readLine(); // doLoop
			in.readLine(); // amountOfSlides
			int wait = Integer.parseInt(in.readLine().trim());

			LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition(name);
			if (def == null) {
				InstantMassiveStructures.LOGGER.warn("Skipping unknown live '{}'", name);
				return false;
			}

			// Legacy: waitTime==2000000000 means still in dialog / never started → remove
			if (wait == 2000000000) {
				InstantMassiveStructures.LOGGER.info(
					"Dropping live '{}' still in boarding-dialog sentinel", name);
				return false;
			}

			int distance = def.isPathMover() ? def.path().defaultDistance() : 0;
			int startX = x, startY = y, startZ = z;
			int stepsRemaining = 0;
			int frameIndex = 0;
			int levelSteps = def.isPathMover() ? def.path().levelStepsForDistance(distance) : 0;
			int lastL = 0, lastH = 0, lastW = 0;

			String distLine = in.readLine();
			if (distLine != null && !distLine.isBlank()) {
				distance = Integer.parseInt(distLine.trim());
				String sx = in.readLine();
				if (sx != null && !sx.isBlank()) {
					startX = Integer.parseInt(sx.trim());
					startY = Integer.parseInt(in.readLine().trim());
					startZ = Integer.parseInt(in.readLine().trim());
					stepsRemaining = Integer.parseInt(in.readLine().trim());
					frameIndex = Integer.parseInt(in.readLine().trim());
					levelSteps = Integer.parseInt(in.readLine().trim());
					lastL = Integer.parseInt(in.readLine().trim());
					lastH = Integer.parseInt(in.readLine().trim());
					lastW = Integer.parseInt(in.readLine().trim());
				} else if (def.isPathMover()) {
					levelSteps = def.path().levelStepsForDistance(distance);
				}
			}

			LiveStructureTicker.LiveInstance inst = LiveStructureTicker.LiveInstance.restore(
				level,
				new BlockPos(startX, startY, startZ),
				new BlockPos(x, y, z),
				def,
				distance,
				phase,
				times,
				stepsRemaining,
				frameIndex,
				levelSteps,
				Math.max(1, wait),
				lastL, lastH, lastW
			);
			LiveStructureTicker.resumeFromSave(inst);
			return true;
		}
	}
}
