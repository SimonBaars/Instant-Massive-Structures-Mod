package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/** Dev-only: {@code -Dimsm.flyinghelishot=1} — Live_Flying_Helicopter climb/level/descend (−Z). */
public final class FlyingHeliPlaytestShot {
	private static final int PAD_X = 1300;
	private static final int PAD_Y = 100;
	private static final int PAD_Z = -80;
	private static final int FLY = 68;

	private static int ticks = -1;
	private static boolean teleported, started, shotBoard, shotClimb, shotLevel, shotDescend, done;

	private FlyingHeliPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.flyinghelishot"))) return;
		InstantMassiveStructures.LOGGER.info("FlyingHeliPlaytestShot armed");
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (done || client.level == null || client.player == null) return;
			if (client.isPaused()) client.setScreenAndShow(null);
			if (ticks < 0) ticks = 0;
			ticks++;
			var conn = client.player.connection;
			if (!teleported && ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("time set noon");
				conn.sendCommand("weather clear");
				conn.sendCommand("difficulty peaceful");
				conn.sendCommand("effect give @p minecraft:night_vision 999 0 true");
				conn.sendCommand("forceload add " + (PAD_X - 32) + " " + (PAD_Z - 120)
					+ " " + (PAD_X + 32) + " " + (PAD_Z + 40));
				conn.sendCommand("fill " + (PAD_X - 25) + " " + (PAD_Y - 5) + " " + (PAD_Z - 120)
					+ " " + (PAD_X + 25) + " " + (PAD_Y + 50) + " " + (PAD_Z + 40) + " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 25) + " " + (PAD_Y - 5) + " " + (PAD_Z - 120)
					+ " " + (PAD_X + 25) + " " + (PAD_Y - 4) + " " + (PAD_Z + 40) + " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 6) + " " + PAD_Z + " 180 20");
				teleported = true;
			}
			if (teleported && !started && ticks == 100) {
				conn.sendCommand("imsm live flyingheli " + FLY);
				started = true;
				InstantMassiveStructures.LOGGER.info("FlyingHeliPlaytestShot: /imsm live flyingheli {}", FLY);
			}
			if (started && !shotBoard && ticks == 130) {
				Screenshot.grab(client, false);
				shotBoard = true;
				InstantMassiveStructures.LOGGER.info("FlyingHeliPlaytestShot: screenshot boarding");
			}
			// 4 ticks/step; climb 30 → ~120t; start~140
			if (started && !shotClimb && ticks == 220) {
				conn.sendCommand("tp @p " + (PAD_X + 20) + " " + (PAD_Y + 20) + " " + (PAD_Z - 20) + " 140 30");
			}
			if (started && !shotClimb && ticks == 240) {
				Screenshot.grab(client, false);
				shotClimb = true;
				InstantMassiveStructures.LOGGER.info("FlyingHeliPlaytestShot: screenshot climb");
			}
			if (started && !shotLevel && ticks == 340) {
				conn.sendCommand("tp @p " + (PAD_X + 25) + " " + (PAD_Y + 40) + " " + (PAD_Z - 50) + " 150 20");
			}
			if (started && !shotLevel && ticks == 450) {
				Screenshot.grab(client, false);
				shotLevel = true;
				InstantMassiveStructures.LOGGER.info("FlyingHeliPlaytestShot: screenshot level");
			}
			if (started && !shotDescend && ticks == 360) {
				conn.sendCommand("tp @p " + (PAD_X + 30) + " " + (PAD_Y + 20) + " " + (PAD_Z - 90) + " 160 25");
			}
			if (started && !shotDescend && ticks == 470) {
				Screenshot.grab(client, false);
				shotDescend = true;
				InstantMassiveStructures.LOGGER.info("FlyingHeliPlaytestShot: screenshot descend");
			}
			if (shotDescend && ticks == 520) {
				conn.sendCommand("removelive");
				InstantMassiveStructures.LOGGER.info("FlyingHeliPlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}
}
