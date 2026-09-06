package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.aviationshot=1} + quickPlay {@code imsplay}
 * starts LiveAirplane climb/level/descend one-shot voyage and screenshots phases.
 */
public final class AviationPlaytestShot {
	private static final int PAD_X = 1000;
	private static final int PAD_Y = 90;
	private static final int PAD_Z = -100;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean shotBoard;
	private static boolean shotClimb;
	private static boolean shotLevel;
	private static boolean shotDescend;
	private static boolean done;

	private AviationPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.aviationshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("AviationPlaytestShot armed (LiveAirplane climb/level/descend)");
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
				// Clear a large air corridor for climb (+Y+Z) then level (+Z) then descend (-Y+Z)
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 10)
					+ " " + (PAD_X + 40) + " " + (PAD_Y + 50) + " " + (PAD_Z + 120)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 10)
					+ " " + (PAD_X + 40) + " " + (PAD_Y - 1) + " " + (PAD_Z + 120)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + (PAD_Z - 25) + " 0 20");
				teleported = true;
				InstantMassiveStructures.LOGGER.info("AviationPlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
			}

			if (teleported && !started && ticks == 100) {
				// Distance 68 → level steps = 8 (legacy distance−60); short climb/level/descend demo
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + (PAD_Z - 25) + " 0 25");
				conn.sendCommand("imsm live airplane 68");
				started = true;
				InstantMassiveStructures.LOGGER.info("AviationPlaytestShot: /imsm live airplane 68");
			}

			// Boarding (~40t after start at t=100 → ~140): screenshot
			if (started && !shotBoard && ticks == 130) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("AviationPlaytestShot: screenshot boarding");
			}

			// Climb underway: start ~t140, mid climb ~t140+90 = 230
			if (started && !shotClimb && ticks == 230) {
				conn.sendCommand("tp @p " + (PAD_X + 15) + " " + (PAD_Y + 25) + " " + (PAD_Z + 10) + " -30 25");
			}
			if (started && !shotClimb && ticks == 245) {
				Screenshot.grab(client, false);
				shotClimb = true;
				InstantMassiveStructures.LOGGER.info("AviationPlaytestShot: screenshot climb");
			}

			// Level: climb ends ~t140+180=320; mid level ~340
			if (started && !shotLevel && ticks == 340) {
				conn.sendCommand("tp @p " + (PAD_X + 20) + " " + (PAD_Y + 42) + " " + (PAD_Z + 45) + " -20 15");
			}
			if (started && !shotLevel && ticks == 355) {
				Screenshot.grab(client, false);
				shotLevel = true;
				InstantMassiveStructures.LOGGER.info("AviationPlaytestShot: screenshot level");
			}

			// Descend: level ends ~t320+48=368; mid descend ~430
			if (started && !shotDescend && ticks == 420) {
				conn.sendCommand("tp @p " + (PAD_X + 25) + " " + (PAD_Y + 25) + " " + (PAD_Z + 80) + " -25 20");
			}
			if (started && !shotDescend && ticks == 435) {
				Screenshot.grab(client, false);
				shotDescend = true;
				InstantMassiveStructures.LOGGER.info("AviationPlaytestShot: screenshot descend");
			}

			if (shotDescend && ticks == 460) {
				InstantMassiveStructures.LOGGER.info("AviationPlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
