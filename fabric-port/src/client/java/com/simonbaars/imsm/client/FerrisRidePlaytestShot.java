package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.ferrisrideshot=1} + quickPlay {@code imsplay}
 * starts Live_FerrisWheel, queues {@code /ride}, holds near mount, screenshots cart path,
 * waits for full 61-point loop (or mid evidence) then removelive.
 */
public final class FerrisRidePlaytestShot {
	private static final int PAD_X = 1100;
	private static final int PAD_Y = 80;
	private static final int PAD_Z = -50;
	/** fastride: 61 points × 2t ≈ 122t after mount; pad for board/mount. */
	private static final int RIDE_LATE_TICK = 120 + 40 + 280;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean rode;
	private static boolean shot1;
	private static boolean shot2;
	private static boolean shot3;
	private static boolean removed;
	private static boolean done;

	private FerrisRidePlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.ferrisrideshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot armed (2D cart /ride full loop)");
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
				conn.sendCommand("effect give @p minecraft:resistance 999 4 true");
				conn.sendCommand("forceload add " + (PAD_X - 48) + " " + (PAD_Z - 80)
					+ " " + (PAD_X + 32) + " " + (PAD_Z + 32));
				for (int zz = PAD_Z - 60; zz <= PAD_Z + 20; zz += 16) {
					int z2 = Math.min(zz + 15, PAD_Z + 20);
					conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 2) + " " + zz
						+ " " + (PAD_X + 20) + " " + (PAD_Y + 90) + " " + z2 + " minecraft:air");
					conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 2) + " " + zz
						+ " " + (PAD_X + 20) + " " + (PAD_Y - 1) + " " + z2 + " minecraft:smooth_stone");
				}
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 5) + " " + (PAD_Z + 5) + " 180 10");
				teleported = true;
			}

			if (teleported && !started && ticks == 90) {
				conn.sendCommand("removelive");
				conn.sendCommand("imsm live ferris");
				started = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: started ferris");
			}

			if (started && !rode && ticks == 120) {
				conn.sendCommand("ride");
				int ox = PAD_X;
				int oy = PAD_Y + 5;
				int oz = PAD_Z + 5;
				conn.sendCommand("tp @p " + (ox - 4) + " " + (oy + 1) + " " + (oz - 36) + " 0 0");
				rode = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: /ride + mount tp at {},{},{}",
					ox - 4, oy + 1, oz - 36);
			}
			// Re-seat while waiting for mount (frame gate) and periodically early ride
			if (rode && !shot1 && (ticks == 150 || ticks == 170 || ticks == 190)) {
				int ox = PAD_X;
				int oy = PAD_Y + 5;
				int oz = PAD_Z + 5;
				conn.sendCommand("tp @p " + (ox - 4) + " " + (oy + 1) + " " + (oz - 36) + " 0 0");
			}

			if (rode && !shot1 && ticks == 200) {
				Screenshot.grab(client, false);
				shot1 = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: screenshot early cart");
			}

			if (rode && !shot2 && ticks == 220) {
				Screenshot.grab(client, false);
				shot2 = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: screenshot mid cart");
			}

			if (rode && !shot3 && ticks == RIDE_LATE_TICK) {
				Screenshot.grab(client, false);
				shot3 = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: screenshot late cart (~full loop window)");
			}

			if (shot3 && !removed && ticks == RIDE_LATE_TICK + 40) {
				conn.sendCommand("removelive");
				removed = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: /removelive");
			}

			if (removed && ticks == RIDE_LATE_TICK + 60) {
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
