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
 */
public final class ImmsCommands {
	private static final SuggestionProvider<CommandSourceStack> LIVE_SUGGESTIONS = (ctx, builder) ->
		SharedSuggestionProvider.suggest(Arrays.asList(
			"ferris", "mill", "watermill", "windmill", "helicopter", "cinema",
			"Live_FerrisWheel", "Live_Mill", "Live_WaterMill",
			"Live_Power_Windmill_East", "Live_Helicopter", "Live_Cinema"
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
						}))));
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
			default -> type; // allow exact base names
		};
		LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition(base);
		if (def == null) {
			source.sendFailure(Component.literal(
				"Unknown live type '" + type + "'. Try: ferris, mill, watermill, windmill, helicopter, cinema"));
			return 0;
		}
		ServerLevel level = player.level();
		BlockPos origin = player.blockPosition();
		try {
			String started = LiveStructureTicker.startLive(level, origin, def);
			source.sendSuccess(() -> Component.literal(
				"Started live " + started + " at " + origin
					+ " (" + def.frames().length + " frames / " + def.ticksPerFrame() + " ticks)"), true);
			return 1;
		} catch (Exception e) {
			source.sendFailure(Component.literal("Failed: " + e.getMessage()));
			return 0;
		}
	}
}
