package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.shipshot=1} + quickPlay {@code imsplay}
 * LiveFlyingShip1 (then ship2) climb/level/descend with short distance (level=1).
 * Prefer {@code -Dimsm.lightpath=1} so phase logs complete without 35k voxels/step film cost.
 * Full film remains N/A under llvmpipe; motion completion is log-verified.
 */
public final class ShipPlaytestShot {
	private static final int PAD_X = 1600;
	private static final int PAD_Y = 120;
	private static final int PAD_Z = -50;
	/** Distance 61 → level 1 only; still full climb 30 + descend 31 @10t. */
	private static final int FLY_DISTANCE = 61;
	private static final boolean LIGHT = "1".equals(System.getProperty("imsm.lightpath"));
	/** board40 + 30*10 + 1*10 + 31*10 = 660t after start */
	private static final int VOYAGE_TICKS = 1200; // lightpath @1t/step: board40+62 ≈102; pad for lag

	private static int ticks = -1;
	private static int stage; // 0 setup, 1 ship1, 2 ship2, 3 done
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
			"ShipPlaytestShot armed (ship1+ship2 multi-phase, distance {}, lightpath={})",
			FLY_DISTANCE, LIGHT);
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

			if (stage == 0 && !teleported && ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("time set noon");
				conn.sendCommand("weather clear");
				conn.sendCommand("difficulty peaceful");
				conn.sendCommand("effect give @p minecraft:night_vision 999 0 true");
				conn.sendCommand("forceload add " + (PAD_X - 48) + " " + (PAD_Z - 160)
					+ " " + (PAD_X + 120) + " " + (PAD_Z + 64));
				for (int zz = PAD_Z - 160; zz <= PAD_Z + 60; zz += 16) {
					int z2 = Math.min(zz + 15, PAD_Z + 60);
					conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 20) + " " + zz
						+ " " + (PAD_X + 100) + " " + (PAD_Y + 60) + " " + z2 + " minecraft:air");
					conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 20) + " " + zz
						+ " " + (PAD_X + 100) + " " + (PAD_Y - 19) + " " + z2 + " minecraft:smooth_stone");
				}
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " 180 25");
				teleported = true;
				stage = 1;
				ticks = 0;
				InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: pad cleared at {},{},{}",
					PAD_X, PAD_Y, PAD_Z);
				return;
			}

			if (stage == 1 || stage == 2) {
				String type = stage == 1 ? "ship1" : "ship2";
				int lookYaw = stage == 1 ? 180 : -90;

				if (!started && ticks == 80) {
					conn.sendCommand("removelive");
					conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 8) + " " + PAD_Z + " " + lookYaw + " 20");
					conn.sendCommand("imsm live " + type + " " + FLY_DISTANCE);
					started = true;
					InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: /imsm live {} {}", type, FLY_DISTANCE);
				}

				if (started && !shotBoard && ticks == 110) {
					Screenshot.grab(client, false);
					shotBoard = true;
					InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot {} boarding", type);
				}

				if (started && !shotClimb && ticks == 280) {
					conn.sendCommand("tp @p " + (PAD_X + 40) + " " + (PAD_Y + 30) + " " + (PAD_Z - 20) + " 140 35");
					Screenshot.grab(client, false);
					shotClimb = true;
					InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot {} climb", type);
				}

				if (started && !shotLevel && ticks == 420) {
					conn.sendCommand("tp @p " + (PAD_X + 45) + " " + (PAD_Y + 45) + " " + (PAD_Z - 50) + " 150 25");
					Screenshot.grab(client, false);
					shotLevel = true;
					InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot {} level", type);
				}

				if (started && !shotDescend && ticks == 560) {
					conn.sendCommand("tp @p " + (PAD_X + 50) + " " + (PAD_Y + 25) + " " + (PAD_Z - 90) + " 160 30");
					Screenshot.grab(client, false);
					shotDescend = true;
					InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: screenshot {} descend", type);
				}

				if (started && !removed && ticks == VOYAGE_TICKS) {
					conn.sendCommand("removelive");
					removed = true;
					InstantMassiveStructures.LOGGER.info(
						"ShipPlaytestShot: /removelive after {} multi-phase window (expect climb/level/descend logs)",
						type);
				}

				if (removed && ticks == VOYAGE_TICKS + 30) {
					if (stage == 1) {
						stage = 2;
						ticks = 0;
						started = shotBoard = shotClimb = shotLevel = shotDescend = removed = false;
						InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: starting ship2 phase");
					} else {
						InstantMassiveStructures.LOGGER.info("ShipPlaytestShot: quitting");
						done = true;
						client.stop();
					}
				}
			}
		});
	}
}
