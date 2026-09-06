package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.ferrisrideshot=1} + quickPlay {@code imsplay}
 * starts Live_FerrisWheel, queues {@code /ride}, hops near mount, screenshots cart path.
 */
public final class FerrisRidePlaytestShot {
	private static final int PAD_X = 1100;
	private static final int PAD_Y = 80;
	private static final int PAD_Z = -50;

	private static int ticks = -1;
	private static boolean teleported;
	private static boolean started;
	private static boolean rode;
	private static boolean shot1;
	private static boolean shot2;
	private static boolean done;

	private FerrisRidePlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.ferrisrideshot"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot armed (2D cart /ride)");
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
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 2) + " " + (PAD_Z - 50)
					+ " " + (PAD_X + 20) + " " + (PAD_Y + 90) + " " + (PAD_Z + 20)
					+ " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 2) + " " + (PAD_Z - 50)
					+ " " + (PAD_X + 20) + " " + (PAD_Y - 1) + " " + (PAD_Z + 20)
					+ " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 5) + " " + (PAD_Z + 5) + " 180 10");
				teleported = true;
			}

			if (teleported && !started && ticks == 90) {
				conn.sendCommand("imsm live ferris");
				started = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: started ferris");
			}

			if (started && !rode && ticks == 120) {
				conn.sendCommand("ride");
				// Legacy mount near (originX-4, originY+1, originZ-36)
				conn.sendCommand("tp @p " + (PAD_X - 4) + " " + (PAD_Y + 2) + " " + (PAD_Z - 36) + " 0 0");
				rode = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: /ride + mount tp");
			}

			if (rode && !shot1 && ticks == 160) {
				Screenshot.grab(client, false);
				shot1 = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: screenshot early cart");
			}

			if (rode && !shot2 && ticks == 280) {
				Screenshot.grab(client, false);
				shot2 = true;
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: screenshot mid cart");
			}

			if (shot2 && ticks == 320) {
				InstantMassiveStructures.LOGGER.info("FerrisRidePlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
