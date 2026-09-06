package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.balloonshot=1} + quickPlay {@code imsplay}
 * starts LiveAirBalloon climb/level/descend (−Z aviation @10 ticks/step) and screenshots phases.
 */
public final class BalloonPlaytestShot {
	private static final int PAD_X = 1400;
	private static final int PAD_Y = 100;
	private static final int PAD_Z = -50;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean shotBoard;
	private static boolean shotClimb;
	private static boolean shotLevel;
	private static boolean shotDescend;
	private static boolean removed;
	private static boolean done;

	private BalloonPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.balloonshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot armed (LiveAirBalloon −Z aviation)");
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
				// Corridor for −Z climb/level/descend (balloon flies north / −Z)
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 2) + " " + (PAD_Z - 140)
					+ " " + (PAD_X + 40) + " " + (PAD_Y + 50) + " " + (PAD_Z + 40)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 2) + " " + (PAD_Z - 140)
					+ " " + (PAD_X + 40) + " " + (PAD_Y - 1) + " " + (PAD_Z + 40)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 180 25");
				teleported = true;
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
			}

			if (teleported && !started && ticks == 100) {
				// Distance 68 → level 8; LiveAirBalloon origin = player (no spawn offset)
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 180 20");
				conn.sendCommand("imsm live balloon 68");
				started = true;
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: /imsm live balloon 68");
			}

			// Boarding (~40t after start): screenshot
			if (started && !shotBoard && ticks == 130) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: screenshot boarding");
			}

			// Climb: 10 ticks/step × 30 = 300t after depart (~t140) → mid ~t290
			if (started && !shotClimb && ticks == 280) {
				conn.sendCommand("tp @p " + (PAD_X + 25) + " " + (PAD_Y + 25) + " " + (PAD_Z - 20) + " 150 30");
			}
			if (started && !shotClimb && ticks == 295) {
				Screenshot.grab(client, false);
				shotClimb = true;
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: screenshot climb");
			}

			// Level: climb ends ~t140+300=440; mid level ~480
			if (started && !shotLevel && ticks == 470) {
				conn.sendCommand("tp @p " + (PAD_X + 30) + " " + (PAD_Y + 42) + " " + (PAD_Z - 50) + " 160 20");
			}
			if (started && !shotLevel && ticks == 485) {
				Screenshot.grab(client, false);
				shotLevel = true;
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: screenshot level");
			}

			// Descend: level ends ~t440+80=520; mid descend ~670
			if (started && !shotDescend && ticks == 650) {
				conn.sendCommand("tp @p " + (PAD_X + 35) + " " + (PAD_Y + 25) + " " + (PAD_Z - 90) + " 170 25");
			}
			if (started && !shotDescend && ticks == 665) {
				Screenshot.grab(client, false);
				shotDescend = true;
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: screenshot descend");
			}

			if (shotDescend && !removed && ticks == 690) {
				conn.sendCommand("removelive");
				removed = true;
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: /removelive");
			}

			if (removed && ticks == 710) {
				InstantMassiveStructures.LOGGER.info("BalloonPlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
