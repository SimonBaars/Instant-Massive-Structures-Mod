package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.framecyclershot=1} — smoke each stationary cycler with a short log window.
 */
public final class FrameCyclerPlaytestShot {
	private static final String[] TYPES = {
		"ferris", "mill", "watermill", "windmill", "helicopter", "cinema", "freefall"
	};
	private static final int[] PAD_X = {200, 280, 360, 440, 520, 600, 700};
	private static final int PAD_Y = 80;
	private static final int PAD_Z = -150;

	private static int ticks = -1;
	private static int phase; // 0=setup, then 1..TYPES*2 (start/shot), then done
	private static boolean done;

	private FrameCyclerPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.framecyclershot"))) return;
		InstantMassiveStructures.LOGGER.info("FrameCyclerPlaytestShot armed ({} types)", TYPES.length);
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (done || client.level == null || client.player == null) return;
			if (client.isPaused()) client.setScreenAndShow(null);
			if (ticks < 0) ticks = 0;
			ticks++;
			var conn = client.player.connection;

			if (phase == 0 && ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("time set noon");
				conn.sendCommand("weather clear");
				conn.sendCommand("difficulty peaceful");
				conn.sendCommand("effect give @p minecraft:night_vision 999 0 true");
				conn.sendCommand("forceload add 180 -180 720 -100");
				conn.sendCommand("fill 180 70 -180 720 160 -100 minecraft:air");
				conn.sendCommand("fill 180 70 -180 720 71 -100 minecraft:smooth_stone");
				phase = 1;
				ticks = 0;
				InstantMassiveStructures.LOGGER.info("FrameCyclerPlaytestShot: pad ready");
				return;
			}

			if (phase >= 1 && phase <= TYPES.length) {
				int idx = phase - 1;
				if (ticks == 20) {
					conn.sendCommand("removelive");
					conn.sendCommand("tp @p " + PAD_X[idx] + " " + (PAD_Y + 6) + " " + PAD_Z + " 0 15");
					conn.sendCommand("imsm live " + TYPES[idx]);
					InstantMassiveStructures.LOGGER.info("FrameCyclerPlaytestShot: started {}", TYPES[idx]);
				}
				if (ticks == 60) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("FrameCyclerPlaytestShot: screenshot {}", TYPES[idx]);
					phase++;
					ticks = 0;
				}
				return;
			}

			if (phase == TYPES.length + 1 && ticks == 10) {
				conn.sendCommand("removelive");
				InstantMassiveStructures.LOGGER.info("FrameCyclerPlaytestShot: all cyclers smoked; quitting");
				done = true;
				client.stop();
			}
		});
	}
}
