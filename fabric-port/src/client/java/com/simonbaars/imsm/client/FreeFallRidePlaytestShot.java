package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.freefallrideshot=1} + quickPlay {@code imsplay}
 * starts Live_Fair_FreeFall, queues {@code /ride}, mounts near tower, screenshots Y-curve.
 */
public final class FreeFallRidePlaytestShot {
	private static final int PAD_X = 800;
	private static final int PAD_Y = 64;
	private static final int PAD_Z = -300;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean rode;
	private static boolean shot1;
	private static boolean shot2;
	private static boolean done;

	private FreeFallRidePlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.freefallrideshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot armed (/ride Y-curve)");
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
				conn.sendCommand("forceload add " + (PAD_X - 16) + " " + (PAD_Z - 16)
					+ " " + (PAD_X + 16) + " " + (PAD_Z + 16));
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 20) + " " + (PAD_Y + 110) + " " + (PAD_Z + 20)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 20) + " " + (PAD_Y - 1) + " " + (PAD_Z + 20)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + (PAD_Z + 8) + " 180 10");
				teleported = true;
			}

			if (teleported && !started && ticks == 90) {
				conn.sendCommand("imsm live freefall");
				started = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: started freefall");
			}

			if (started && !rode && ticks == 130) {
				conn.sendCommand("ride");
				// Soft mount: near origin center / FreeFall seats
				conn.sendCommand("tp @p " + (PAD_X) + " " + (PAD_Y + 3) + " " + PAD_Z + " 0 0");
				rode = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: /ride + mount tp");
			}

			if (rode && !shot1 && ticks == 180) {
				Screenshot.grab(client, false);
				shot1 = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: screenshot early ride");
			}

			if (rode && !shot2 && ticks == 280) {
				Screenshot.grab(client, false);
				shot2 = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: screenshot mid ride");
			}

			if (shot2 && ticks == 320) {
				conn.sendCommand("removelive");
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
