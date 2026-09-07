package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.freefallrideshot=1} + quickPlay {@code imsplay}
 * starts Live_Fair_FreeFall, queues {@code /ride}, mounts near tower, screenshots Y-curve.
 */
public final class FreeFallRidePlaytestShot {
	private static final int PAD_X = 850;
	private static final int PAD_Y = 70;
	private static final int PAD_Z = -350;

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
				conn.sendCommand("removelive");
				conn.sendCommand("forceload add " + (PAD_X - 8) + " " + (PAD_Z - 8)
					+ " " + (PAD_X + 8) + " " + (PAD_Z + 8));
				// Keep fill under 32768 blocks
				conn.sendCommand("fill " + (PAD_X - 12) + " " + (PAD_Y - 2) + " " + (PAD_Z - 12)
					+ " " + (PAD_X + 12) + " " + (PAD_Y + 50) + " " + (PAD_Z + 12)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 12) + " " + (PAD_Y - 2) + " " + (PAD_Z - 12)
					+ " " + (PAD_X + 12) + " " + (PAD_Y - 1) + " " + (PAD_Z + 12)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + (PAD_Z + 6) + " 180 10");
				teleported = true;
			}

			if (teleported && !started && ticks == 90) {
				conn.sendCommand("removelive");
				conn.sendCommand("imsm live freefall");
				started = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: started freefall");
			}

			if (started && !rode && ticks == 140) {
				conn.sendCommand("ride");
				// Soft mount uses origin center within 8 blocks OR seat boxes
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 3) + " " + PAD_Z + " 0 0");
				rode = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: /ride + mount tp");
			}

			if (rode && ticks == 160) {
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 3) + " " + PAD_Z + " 0 0");
			}
			if (rode && ticks == 200) {
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 3) + " " + PAD_Z + " 0 0");
			}

			if (rode && !shot1 && ticks == 220) {
				Screenshot.grab(client, false);
				shot1 = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: screenshot early ride");
			}

			if (rode && !shot2 && ticks == 320) {
				Screenshot.grab(client, false);
				shot2 = true;
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: screenshot mid ride");
			}

			if (shot2 && ticks == 360) {
				conn.sendCommand("removelive");
				InstantMassiveStructures.LOGGER.info("FreeFallRidePlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
