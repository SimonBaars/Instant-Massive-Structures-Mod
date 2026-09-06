package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.bus2shot=1} + quickPlay {@code imsplay}
 * starts Live_Bus2 +Z path (dedicated Live_Bus20 frame) and screenshots boarding/mid.
 */
public final class Bus2PlaytestShot {
	private static final int PAD_X = 1100;
	private static final int PAD_Y = 70;
	private static final int PAD_Z = -100;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean shotBoard;
	private static boolean shotMid;
	private static boolean removed;
	private static boolean done;

	private Bus2PlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.bus2shot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot armed (Live_Bus2 +Z, frame Live_Bus20)");
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (done || client.level == null || client.player == null) {
				return;
			}
			if (client.isPaused()) {
				client.setScreenAndShow(null);
			}
			if (ticks < 0) {
				ticks = 0;
			}
			ticks++;
			var conn = client.player.connection;

			if (!teleported && ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("time set noon");
				conn.sendCommand("weather clear");
				conn.sendCommand("difficulty peaceful");
				conn.sendCommand("effect give @p minecraft:night_vision 999 0 true");
				// Corridor for +Z cruise (bus rides south / +Z)
				conn.sendCommand("fill " + (PAD_X - 30) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 30) + " " + (PAD_Y + 20) + " " + (PAD_Z + 80)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 30) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 30) + " " + (PAD_Y - 1) + " " + (PAD_Z + 80)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + PAD_Z + " 0 20");
				teleported = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
			}

			if (teleported && !started && ticks == 80) {
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + PAD_Z + " 0 15");
				conn.sendCommand("imsm live bus2 16");
				started = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: /imsm live bus2 16");
			}

			// Boarding (~40t after start @t80 → ~t120)
			if (started && !shotBoard && ticks == 115) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: screenshot boarding");
			}

			// Mid cruise: depart ~t120; 8 of 16 steps @6t → ~t168
			if (started && !shotMid && ticks == 160) {
				conn.sendCommand("tp @p " + (PAD_X + 12) + " " + (PAD_Y + 6) + " " + (PAD_Z + 8) + " -30 20");
			}
			if (started && !shotMid && ticks == 175) {
				Screenshot.grab(client, false);
				shotMid = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: screenshot mid cruise");
			}

			if (shotMid && !removed && ticks == 230) {
				conn.sendCommand("removelive");
				removed = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: /removelive");
			}

			if (removed && ticks == 250) {
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
