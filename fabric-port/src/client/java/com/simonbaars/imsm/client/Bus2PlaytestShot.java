package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.bus2shot=1} + quickPlay {@code imsplay}
 * starts Live_Bus2 +Z path (dedicated Live_Bus20 frame), waits for one-shot voyage complete.
 */
public final class Bus2PlaytestShot {
	private static final int PAD_X = 2100;
	private static final int PAD_Y = 100;
	private static final int PAD_Z = 200;
	private static final int DISTANCE = 16;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean shotBoard;
	private static boolean shotMid;
	private static boolean shotDone;
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
				// Force-load corridor chunks so pad fill sticks (+Z cruise)
				conn.sendCommand("forceload add " + (PAD_X - 32) + " " + (PAD_Z - 32)
					+ " " + (PAD_X + 32) + " " + (PAD_Z + 96));
				// Strip fills keep each command under MC's 32768 block limit
				for (int zz = PAD_Z - 20; zz <= PAD_Z + 80; zz += 16) {
					int z2 = Math.min(zz + 15, PAD_Z + 80);
					conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + zz
						+ " " + (PAD_X + 20) + " " + (PAD_Y + 16) + " " + z2 + " minecraft:air");
					conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + zz
						+ " " + (PAD_X + 20) + " " + (PAD_Y - 1) + " " + z2 + " minecraft:smooth_stone");
				}
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + PAD_Z + " 0 20");
				teleported = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
			}

			// Extra settle so fill + forceload apply before spawn
			if (teleported && !started && ticks == 100) {
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + PAD_Z + " 0 15");
				conn.sendCommand("imsm live bus2 " + DISTANCE);
				started = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: /imsm live bus2 {}", DISTANCE);
			}

			// Boarding (~40t after start @t100 → ~t140)
			if (started && !shotBoard && ticks == 135) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: screenshot boarding");
			}

			// Mid cruise: depart ~t140; ~8 of 16 steps @6t → ~t188
			if (started && !shotMid && ticks == 280) {
				conn.sendCommand("tp @p " + (PAD_X + 12) + " " + (PAD_Y + 6) + " " + (PAD_Z + 8) + " -30 20");
			}
			if (started && !shotMid && ticks == 320) {
				Screenshot.grab(client, false);
				shotMid = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: screenshot mid cruise");
			}

			// One-shot: board 40 + 16*6 = 136t after start → complete ~t236; capture then removelive
			if (started && !shotDone && ticks == 700) {
				Screenshot.grab(client, false);
				shotDone = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: screenshot voyage-end window");
			}

			if (shotDone && !removed && ticks == 720) {
				conn.sendCommand("removelive");
				removed = true;
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: /removelive");
			}

			if (removed && ticks == 740) {
				InstantMassiveStructures.LOGGER.info("Bus2PlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
