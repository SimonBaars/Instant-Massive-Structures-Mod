package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.planeshot=1} + quickPlay {@code imsplay}
 * starts LivePlane climb/level/descend (−X aviation) one-shot voyage and screenshots phases.
 */
public final class PlanePlaytestShot {
	private static final int PAD_X = 1200;
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

	private PlanePlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.planeshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("PlanePlaytestShot armed (LivePlane −X aviation + /removelive)");
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
				// Corridor for −X climb/level/descend (plane flies west)
				conn.sendCommand("fill " + (PAD_X - 140) + " " + (PAD_Y - 2) + " " + (PAD_Z - 30)
					+ " " + (PAD_X + 40) + " " + (PAD_Y + 50) + " " + (PAD_Z + 40)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 140) + " " + (PAD_Y - 2) + " " + (PAD_Z - 30)
					+ " " + (PAD_X + 40) + " " + (PAD_Y - 1) + " " + (PAD_Z + 40)
					+ " minecraft:smooth_stone");
				// Command applies LivePlane spawn offset (+26,0,+19); stand so origin lands on pad
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 90 25");
				teleported = true;
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
			}

			if (teleported && !started && ticks == 100) {
				// Distance 68 → level 8; LivePlane origin offset +26,+19 from player
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 90 20");
				conn.sendCommand("imsm live plane 68");
				started = true;
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: /imsm live plane 68");
			}

			// Boarding (~40t after start): screenshot
			if (started && !shotBoard && ticks == 130) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: screenshot boarding");
			}

			// Climb: 2 ticks/step × 30 = 60t after depart (~t140) → mid ~t170
			if (started && !shotClimb && ticks == 175) {
				conn.sendCommand("tp @p " + (PAD_X - 20) + " " + (PAD_Y + 25) + " " + (PAD_Z + 25) + " 120 30");
			}
			if (started && !shotClimb && ticks == 190) {
				Screenshot.grab(client, false);
				shotClimb = true;
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: screenshot climb");
			}

			// Level: climb ends ~t140+60=200; mid level ~220
			if (started && !shotLevel && ticks == 220) {
				conn.sendCommand("tp @p " + (PAD_X - 50) + " " + (PAD_Y + 40) + " " + (PAD_Z + 30) + " 110 20");
			}
			if (started && !shotLevel && ticks == 235) {
				Screenshot.grab(client, false);
				shotLevel = true;
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: screenshot level");
			}

			// Descend + removelive demo
			if (started && !shotDescend && ticks == 280) {
				conn.sendCommand("tp @p " + (PAD_X - 80) + " " + (PAD_Y + 25) + " " + (PAD_Z + 35) + " 100 25");
			}
			if (started && !shotDescend && ticks == 295) {
				Screenshot.grab(client, false);
				shotDescend = true;
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: screenshot descend");
			}

			if (shotDescend && !removed && ticks == 310) {
				conn.sendCommand("removelive");
				removed = true;
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: /removelive");
			}

			if (removed && ticks == 330) {
				InstantMassiveStructures.LOGGER.info("PlanePlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
