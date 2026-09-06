package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.shipshot=1} + quickPlay {@code imsplay}
 * LiveFlyingShip1 full climb/level/descend with short distance (level=1) in clear sky.
 * ~35k voxels/step — Xmx6G; forceload pad; do not abort mid-climb.
 */
public final class ShipPlaytestShot {
	private static final int PAD_X = 1600;
	private static final int PAD_Y = 120;
	private static final int PAD_Z = -50;
	/** Distance 61 → level 1 only; still full climb 30 + descend 31 @10t. */
	private static final int FLY_DISTANCE = 61;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean shotBoard;
	private static boolean shotClimb;
	private static boolean shotLevel;
	private static boolean shotDescend;
	private static boolean removed;
	private static boolean done;

	private ShipPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.shipshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info(
			"ShipPlaytestShot armed (LiveFlyingShip1 full multi-phase, distance {})", FLY_DISTANCE);
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
				// Command applies LiveFlyingShip1 spawn offset (+15,-10,+24); clear sky corridor −Z
				conn.sendCommand("forceload add " + (PAD_X - 48) + " " + (PAD_Z - 160)
					+ " " + (PAD_X + 80) + " " + (PAD_Z + 64));
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 20) + " " + (PAD_Z - 160)
					+ " " + (PAD_X + 70) + " " + (PAD_Y + 60) + " " + (PAD_Z + 60)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 20) + " " + (PAD_Z - 160)
					+ " " + (PAD_X + 70) + " " + (PAD_Y - 19) + " " + (PAD_Z + 60)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 180 25");
				teleported = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
			}

			if (teleported && !started && ticks == 140) {
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 180 20");
				conn.sendCommand("imsm live ship1 " + FLY_DISTANCE);
				started = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: /imsm live ship1 {}", FLY_DISTANCE);
			}

			// Boarding (~40t after start at t=140 → ~180)
			if (started && !shotBoard && ticks == 175) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot boarding");
			}

			// Climb mid: depart ~t180, mid ~t180+150=330 (30 steps * 10t)
			if (started && !shotClimb && ticks == 320) {
				conn.sendCommand("tp @p " + (PAD_X + 40) + " " + (PAD_Y + 30) + " " + (PAD_Z - 20) + " 140 35");
			}
			if (started && !shotClimb && ticks == 340) {
				Screenshot.grab(client, false);
				shotClimb = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot climb");
			}

			// Level: climb done ~t180+300=480; level 1 step @10t → ~490
			if (started && !shotLevel && ticks == 500) {
				conn.sendCommand("tp @p " + (PAD_X + 45) + " " + (PAD_Y + 45) + " " + (PAD_Z - 50) + " 150 25");
			}
			if (started && !shotLevel && ticks == 520) {
				Screenshot.grab(client, false);
				shotLevel = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot level");
			}

			// Descend mid: level done ~t490; descend 31*10=310 → mid ~t640
			if (started && !shotDescend && ticks == 640) {
				conn.sendCommand("tp @p " + (PAD_X + 50) + " " + (PAD_Y + 25) + " " + (PAD_Z - 90) + " 160 30");
			}
			if (started && !shotDescend && ticks == 660) {
				Screenshot.grab(client, false);
				shotDescend = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot descend");
			}

			// Voyage: board40 + 30*10 + 1*10 + 31*10 = 660t after start → ~t800 complete
			if (shotDescend && !removed && ticks == 820) {
				conn.sendCommand("removelive");
				removed = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: /removelive (after multi-phase window)");
			}

			if (removed && ticks == 840) {
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
