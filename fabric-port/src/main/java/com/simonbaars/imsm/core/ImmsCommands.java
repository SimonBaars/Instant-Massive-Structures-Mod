package com.simonbaars.imsm.core;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import com.simonbaars.imsm.InstantMassiveStructures;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.Half;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import com.simonbaars.imsm.structureloader.LegacyBlockStates;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import java.util.EnumMap;
import java.util.Map;

import java.util.Arrays;
import java.util.Locale;

/**
 * Playtest helpers: {@code /imsm live <type> [distance]} starts a wired live at the player.
 * Path movers accept optional distance and optional {@code loop} (playtest short-loop;
 * default is legacy one-shot). Aviation: total fly distance (level = distance − 60).
 * {@code /ride} toggles FreeFall/Ferris rides. {@code /removelive} stops all active lives.
 */
public final class ImmsCommands {
	private static final SuggestionProvider<CommandSourceStack> LIVE_SUGGESTIONS = (ctx, builder) ->
		SharedSuggestionProvider.suggest(Arrays.asList(
			"ferris", "mill", "watermill", "windmill", "helicopter", "cinema", "freefall",
			"boat", "bus", "bus2", "airplane", "flyingheli", "plane", "balloon", "ship1", "ship2",
			"Live_FerrisWheel", "Live_Mill", "Live_WaterMill",
			"Live_Power_Windmill_East", "Live_Helicopter", "Live_Cinema", "Live_Fair_FreeFall",
			"LiveBoat", "Live_Bus", "Live_Bus2", "LiveAirplane", "Live_Flying_Helicopter",
			"LivePlane", "LiveAirBalloon", "LiveFlyingShip1", "LiveFlyingShip2"
		), builder);

	private ImmsCommands() {}

	public static void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("imsm")
				.then(Commands.literal("live")
					.then(Commands.argument("type", StringArgumentType.word())
						.suggests(LIVE_SUGGESTIONS)
						.executes(ctx -> {
							ServerPlayer player = ctx.getSource().getPlayerOrException();
							String type = StringArgumentType.getString(ctx, "type");
							return startLive(ctx.getSource(), player, type, -1, null);
						})
						.then(Commands.literal("loop")
							.executes(ctx -> {
								ServerPlayer player = ctx.getSource().getPlayerOrException();
								String type = StringArgumentType.getString(ctx, "type");
								return startLive(ctx.getSource(), player, type, -1, true);
							}))
						.then(Commands.argument("distance", IntegerArgumentType.integer(1, 256))
							.executes(ctx -> {
								ServerPlayer player = ctx.getSource().getPlayerOrException();
								String type = StringArgumentType.getString(ctx, "type");
								int distance = IntegerArgumentType.getInteger(ctx, "distance");
								return startLive(ctx.getSource(), player, type, distance, null);
							})
							.then(Commands.literal("loop")
								.executes(ctx -> {
									ServerPlayer player = ctx.getSource().getPlayerOrException();
									String type = StringArgumentType.getString(ctx, "type");
									int distance = IntegerArgumentType.getInteger(ctx, "distance");
									return startLive(ctx.getSource(), player, type, distance, true);
								})))))
				.then(Commands.literal("ride")
					.executes(ctx -> {
						ServerPlayer player = ctx.getSource().getPlayerOrException();
						return LiveStructureTicker.toggleRide(player) ? 1 : 0;
					}))
				.then(Commands.literal("place")
					.then(Commands.argument("structure", StringArgumentType.word())
						.executes(ctx -> placeStatic(ctx.getSource(),
							StringArgumentType.getString(ctx, "structure")))))
				.then(Commands.literal("metastats")
					.then(Commands.argument("structure", StringArgumentType.word())
						.executes(ctx -> metaStats(ctx.getSource(),
							StringArgumentType.getString(ctx, "structure")))))
				.then(Commands.literal("removelive")
					.executes(ctx -> removelive(ctx.getSource()))));

			dispatcher.register(Commands.literal("ride")
				.executes(ctx -> {
					ServerPlayer player = ctx.getSource().getPlayerOrException();
					return LiveStructureTicker.toggleRide(player) ? 1 : 0;
				}));
			dispatcher.register(Commands.literal("ridestructure")
				.executes(ctx -> {
					ServerPlayer player = ctx.getSource().getPlayerOrException();
					return LiveStructureTicker.toggleRide(player) ? 1 : 0;
				}));
			dispatcher.register(Commands.literal("ridethis")
				.executes(ctx -> {
					ServerPlayer player = ctx.getSource().getPlayerOrException();
					return LiveStructureTicker.toggleRide(player) ? 1 : 0;
				}));

			// Legacy /removelive (+ aliases)
			dispatcher.register(Commands.literal("removelive")
				.executes(ctx -> removelive(ctx.getSource())));
			dispatcher.register(Commands.literal("removelivestructures")
				.executes(ctx -> removelive(ctx.getSource())));
			dispatcher.register(Commands.literal("liveremove")
				.executes(ctx -> removelive(ctx.getSource())));
			dispatcher.register(Commands.literal("livestructuresremove")
				.executes(ctx -> removelive(ctx.getSource())));
		});
	}


	/** Place a static schematic at the player (playtest / meta verification). */
	private static int placeStatic(CommandSourceStack source, String structureName) {
		try {
			ServerPlayer player = source.getPlayerOrException();
			ServerLevel world = player.level();
			BlockPos origin = player.blockPosition();
			SchematicStructure structure = new SchematicStructure(structureName);
			structure.readFromFile();
			structure.process(world, origin.getX(), origin.getY(), origin.getZ(), true);
			source.sendSuccess(() -> Component.literal(
				"Placed static " + structureName + " at " + origin
					+ " (" + structure.getLength() + "x" + structure.getHeight() + "x" + structure.getWidth() + ")"), true);
			return 1;
		} catch (Exception e) {
			source.sendFailure(Component.literal("place failed: " + e.getMessage()));
			return 0;
		}
	}

	/** Count stair facings produced by legacy id+meta mapping (no world write). */
	private static int metaStats(CommandSourceStack source, String structureName) {
		try {
			SchematicStructure structure = new SchematicStructure(structureName);
			structure.readFromFile();
			Map<Direction, Integer> facings = new EnumMap<>(Direction.class);
			int stairs = 0, nonDefaultHalf = 0, colored = 0, axisNonY = 0, total = 0;
			for (int y = 0; y < structure.getHeight(); y++) {
				for (int z = 0; z < structure.getWidth(); z++) {
					for (int x = 0; x < structure.getLength(); x++) {
						int id = structure.getLegacyId(x, y, z);
						if (id < 0) continue;
						BlockState state = LegacyBlockStates.fromLegacy(id, structure.getLegacyMeta(x, y, z));
						if (state == null || state.isAir()) continue;
						total++;
						if (state.getBlock() instanceof StairBlock) {
							stairs++;
							Direction f = state.getValue(StairBlock.FACING);
							facings.merge(f, 1, Integer::sum);
							if (state.getValue(StairBlock.HALF) == Half.TOP) {
								nonDefaultHalf++;
							}
						}
						String key = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
						if (key.contains("_wool") || key.contains("_terracotta") || key.contains("_carpet")) {
							if (!key.startsWith("white_")) colored++;
						}
						if (state.hasProperty(RotatedPillarBlock.AXIS)
								&& state.getValue(RotatedPillarBlock.AXIS) != Direction.Axis.Y) {
							axisNonY++;
						}
					}
				}
			}
			String msg = "metastats " + structureName + ": blocks=" + total
				+ " stairs=" + stairs + " facings=" + facings
				+ " upsideDown=" + nonDefaultHalf
				+ " nonWhiteColor=" + colored
				+ " logAxisNonY=" + axisNonY;
			source.sendSuccess(() -> Component.literal(msg), true);
			InstantMassiveStructures.LOGGER.info(msg);
			return stairs;
		} catch (Exception e) {
			source.sendFailure(Component.literal("metastats failed: " + e.getMessage()));
			return 0;
		}
	}

	private static int removelive(CommandSourceStack source) {
		int n = LiveStructureTicker.removeAllLives(source.getServer());
		source.sendSuccess(() -> Component.literal("Removed " + n + " Live Structures."), true);
		return n;
	}

	private static String formatStep(LiveStructureTicker.PathStep s) {
		return "{" + s.dx() + "," + s.dy() + "," + s.dz() + "}";
	}

	/** Legacy BlockLiveStructure spawn modifiers for path craft. */
	private static BlockPos originFor(ServerPlayer player, String baseName) {
		BlockPos p = player.blockPosition();
		return switch (baseName) {
			case "LiveBoat" -> p.offset(0, -2, 0);
			case "LivePlane" -> p.offset(26, 0, 19);
			case "LiveFlyingShip1" -> p.offset(15, -10, 24);
			case "LiveFlyingShip2" -> p.offset(22, -8, 16);
			default -> p;
		};
	}

	private static int startLive(CommandSourceStack source, ServerPlayer player, String type, int distance,
			Boolean loopOverride) {
		String key = type.toLowerCase(Locale.ROOT);
		String base = switch (key) {
			case "ferris", "live_ferriswheel" -> "Live_FerrisWheel";
			case "mill", "live_mill" -> "Live_Mill";
			case "watermill", "water", "live_watermill" -> "Live_WaterMill";
			case "windmill", "power", "live_power_windmill_east" -> "Live_Power_Windmill_East";
			case "helicopter", "heli", "live_helicopter" -> "Live_Helicopter";
			case "cinema", "live_cinema" -> "Live_Cinema";
			case "freefall", "free_fall", "fair", "live_fair_freefall", "live_fair_free_fall" -> "Live_Fair_FreeFall";
			case "boat", "liveboat", "live_boat" -> "LiveBoat";
			case "bus", "live_bus", "live__bus" -> "Live_Bus";
			case "bus2", "live_bus2", "live__bus2" -> "Live_Bus2";
			case "airplane", "liveairplane", "live_airplane" -> "LiveAirplane";
			case "flyingheli", "flying_heli", "flyheli", "live_flying_helicopter",
				"live__flying__helicopter" -> "Live_Flying_Helicopter";
			case "plane", "liveplane", "live_plane" -> "LivePlane";
			case "balloon", "airballoon", "liveairballoon", "live_air_balloon" -> "LiveAirBalloon";
			case "ship1", "flyingship", "flyingship1", "liveflyingship", "liveflyingship1",
				"live_flying_ship1" -> "LiveFlyingShip1";
			case "ship2", "flyingship2", "liveflyingship2", "live_flying_ship2" -> "LiveFlyingShip2";
			default -> type;
		};
		LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition(base);
		if (def == null) {
			source.sendFailure(Component.literal(
				"Unknown live type '" + type
					+ "'. Try: ferris, mill, watermill, windmill, helicopter, cinema, freefall, "
					+ "boat, bus, bus2, airplane, flyingheli, plane, balloon, ship1, ship2"));
			return 0;
		}
		ServerLevel level = player.level();
		BlockPos origin = originFor(player, def.baseName());
		try {
			int dist = distance > 0
				? distance
				: (def.isPathMover() ? def.path().defaultDistance() : 0);
			String started = LiveStructureTicker.startLive(level, origin, def, dist, loopOverride);
			if (def.isPathMover()) {
				int finalDist = dist;
				LiveStructureTicker.PathMotion pm = def.path();
				boolean looping = loopOverride != null ? loopOverride : pm.loop();
				String brand = pm.aviation() ? "Aviation"
					: ("LiveBoat".equals(started) ? "Maritime" : "Bus Depot");
				int levelSteps = pm.levelStepsForDistance(finalDist);
				String detail = pm.aviation()
					? ("fly " + finalDist + " (climb " + pm.climbCount()
						+ " → level " + levelSteps + " → descend " + pm.descendCount()
						+ "), " + formatStep(pm.climb()) + "/" + formatStep(pm.cruise())
						+ "/" + formatStep(pm.descend()))
					: ("sail/ride " + finalDist + " blocks " + formatStep(pm.cruise()));
				String loopMsg = looping ? "short loop on" : "legacy one-shot";
				source.sendSuccess(() -> Component.literal(
					"Thanks for choosing SimJoo's " + brand
						+ " Solutions. " + detail + ", " + def.frames().length
						+ " frames, " + pm.ticksPerStep() + " ticks/step, " + loopMsg + "."), true);
				source.sendSuccess(() -> Component.literal(
					"Please hop aboard quickly — departing after boarding wait (~"
						+ (pm.boardingTicks() / 20.0) + "s)."), true);
			} else {
				String timing = def.hasVariableWaits()
					? def.frames().length + " frames / variable waits (legacy FreeFall timings)"
					: def.frames().length + " frames / " + def.ticksPerFrame() + " ticks";
				source.sendSuccess(() -> Component.literal(
					"Started live " + started + " at " + origin + " (" + timing + ")"), true);
				if ("Live_Fair_FreeFall".equals(started) || "Live_FerrisWheel".equals(started)) {
					source.sendSuccess(() -> Component.literal(
						"Use /ride to ride this structure!"), true);
				}
			}
			return 1;
		} catch (Exception e) {
			source.sendFailure(Component.literal("Failed: " + e.getMessage()));
			return 0;
		}
	}
}
