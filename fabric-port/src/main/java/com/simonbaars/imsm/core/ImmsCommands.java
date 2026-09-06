package com.simonbaars.imsm.core;

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

import java.util.Arrays;
import java.util.Locale;

/**
 * Playtest helpers: {@code /imsm live <type>} starts a wired live frame-cycler at the player.
 * {@code /ride} (and {@code /imsm ride}) toggles FreeFall Y-curve ride when near an active FreeFall.
 */
public final class ImmsCommands {
	private static final SuggestionProvider<CommandSourceStack> LIVE_SUGGESTIONS = (ctx, builder) ->
		SharedSuggestionProvider.suggest(Arrays.asList(
			"ferris", "mill", "watermill", "windmill", "helicopter", "cinema", "freefall",
			"Live_FerrisWheel", "Live_Mill", "Live_WaterMill",
			"Live_Power_Windmill_East", "Live_Helicopter", "Live_Cinema", "Live_Fair_FreeFall"
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
							return startLive(ctx.getSource(), player, type);
						})))
				.then(Commands.literal("ride")
					.executes(ctx -> {
						ServerPlayer player = ctx.getSource().getPlayerOrException();
						return LiveStructureTicker.toggleRide(player) ? 1 : 0;
					})));

			// Legacy alias
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
		});
	}

	private static int startLive(CommandSourceStack source, ServerPlayer player, String type) {
		String key = type.toLowerCase(Locale.ROOT);
		String base = switch (key) {
			case "ferris", "live_ferriswheel" -> "Live_FerrisWheel";
			case "mill", "live_mill" -> "Live_Mill";
			case "watermill", "water", "live_watermill" -> "Live_WaterMill";
			case "windmill", "power", "live_power_windmill_east" -> "Live_Power_Windmill_East";
			case "helicopter", "heli", "live_helicopter" -> "Live_Helicopter";
			case "cinema", "live_cinema" -> "Live_Cinema";
			case "freefall", "free_fall", "fair", "live_fair_freefall", "live_fair_free_fall" -> "Live_Fair_FreeFall";
			default -> type; // allow exact base names
		};
		LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition(base);
		if (def == null) {
			source.sendFailure(Component.literal(
				"Unknown live type '" + type
					+ "'. Try: ferris, mill, watermill, windmill, helicopter, cinema, freefall"));
			return 0;
		}
		ServerLevel level = player.level();
		BlockPos origin = player.blockPosition();
		try {
			String started = LiveStructureTicker.startLive(level, origin, def);
			String timing = def.hasVariableWaits()
				? def.frames().length + " frames / variable waits (legacy FreeFall timings)"
				: def.frames().length + " frames / " + def.ticksPerFrame() + " ticks";
			source.sendSuccess(() -> Component.literal(
				"Started live " + started + " at " + origin + " (" + timing + ")"), true);
			if ("Live_Fair_FreeFall".equals(started) || "Live_FerrisWheel".equals(started)) {
				source.sendSuccess(() -> Component.literal(
					"Use /ride to ride this structure!"), true);
			}
			return 1;
		} catch (Exception e) {
			source.sendFailure(Component.literal("Failed: " + e.getMessage()));
			return 0;
		}
	}
}
