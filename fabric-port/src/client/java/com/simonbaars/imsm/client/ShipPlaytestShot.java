package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.shipshot=1} + quickPlay {@code imsplay}
 * starts LiveFlyingShip1 climb (−Z @10 ticks/step) with short distance for film.
 * Ships are ~33–35k voxels/step — may OOM on llvmpipe; capture what we can then quit.
 */
public final class ShipPlaytestShot {
	private static final int PAD_X = 1600;
	private static final int PAD_Y = 110;
	private static final int PAD_Z = -50;
	/** Distance 62 → level 2 only (still full climb/descend); keeps film shorter. */
	private static final int FLY_DISTANCE = 62;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean shotBoard;
	private static boolean shotClimb;
	private static boolean removed;
	private static boolean done;

	private ShipPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.shipshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info(
			"ShipPlaytestShot armed (LiveFlyingShip1 −Z aviation, distance {})", FLY_DISTANCE);
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
				// Command applies LiveFlyingShip1 spawn offset (+15,-10,+24); stand so origin lands on pad
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 15) + " " + (PAD_Z - 140)
					+ " " + (PAD_X + 60) + " " + (PAD_Y + 50) + " " + (PAD_Z + 50)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 15) + " " + (PAD_Z - 140)
					+ " " + (PAD_X + 60) + " " + (PAD_Y - 14) + " " + (PAD_Z + 50)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 180 25");
				teleported = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
			}

			if (teleported && !started && ticks == 120) {
				// Extra settle for chunk load before huge schematic
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 180 20");
				conn.sendCommand("imsm live ship1 " + FLY_DISTANCE);
				started = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: /imsm live ship1 {}", FLY_DISTANCE);
			}

			// Boarding (~40t after start at t=120 → ~160)
			if (started && !shotBoard && ticks == 155) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot boarding");
			}

			// Climb mid: depart ~t160, mid ~t160+150=310
			if (started && !shotClimb && ticks == 300) {
				conn.sendCommand("tp @p " + (PAD_X + 40) + " " + (PAD_Y + 30) + " " + (PAD_Z - 15) + " 140 35");
			}
			if (started && !shotClimb && ticks == 320) {
				Screenshot.grab(client, false);
				shotClimb = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot climb");
			}

			// Stop after climb shot — full multi-phase film of 35k voxels is optional / OOM risk
			if (shotClimb && !removed && ticks == 340) {
				conn.sendCommand("removelive");
				removed = true;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: /removelive (phase subset)");
			}

			if (removed && ticks == 360) {
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
