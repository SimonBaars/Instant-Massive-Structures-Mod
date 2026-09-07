package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.metashot=1} — place stairs-heavy WoodenHouse + Live_FerrisWheel;
 * screenshot close-ups proving legacy meta orientations.
 */
public final class MetaOrientationPlaytestShot {
	private static final int PAD_X = 3200;
	private static final int PAD_Y = 90;
	private static final int PAD_Z = 200;
	private static final int FERRIS_X = 3300;
	private static final int FERRIS_Z = 300;

	private static int ticks = -1;
	private static int phase;
	private static boolean done;

	private MetaOrientationPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.metashot"))) return;
		InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot armed");
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (done || client.level == null || client.player == null) return;
			if (client.isPaused()) client.setScreenAndShow(null);
			if (ticks < 0) ticks = 0;
			ticks++;
			var conn = client.player.connection;

			if (phase == 0) {
				if (ticks == 40) {
					conn.sendCommand("gamemode creative @p");
					conn.sendCommand("time set noon");
					conn.sendCommand("weather clear");
					conn.sendCommand("difficulty peaceful");
					conn.sendCommand("effect give @p minecraft:night_vision 999 0 true");
					conn.sendCommand("forceload add " + (PAD_X - 40) + " " + (PAD_Z - 40)
						+ " " + (PAD_X + 40) + " " + (PAD_Z + 40));
					conn.sendCommand("forceload add " + (FERRIS_X - 40) + " " + (FERRIS_Z - 40)
						+ " " + (FERRIS_X + 40) + " " + (FERRIS_Z + 40));
				}
				if (ticks == 60) {
					// Small pad fills under 32768 limit
					conn.sendCommand("fill " + (PAD_X - 30) + " " + PAD_Y + " " + (PAD_Z - 30)
						+ " " + (PAD_X + 30) + " " + (PAD_Y + 40) + " " + (PAD_Z + 30) + " minecraft:air");
				}
				if (ticks == 80) {
					conn.sendCommand("fill " + (PAD_X - 30) + " " + (PAD_Y - 1) + " " + (PAD_Z - 30)
						+ " " + (PAD_X + 30) + " " + (PAD_Y - 1) + " " + (PAD_Z + 30) + " minecraft:smooth_stone");
					conn.sendCommand("tp @p " + PAD_X + " " + PAD_Y + " " + PAD_Z + " 0 20");
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: pad ready");
				}
				if (ticks == 100) {
					conn.sendCommand("imsm metastats WoodenHouse");
					conn.sendCommand("imsm metastats Live_FerrisWheel");
				}
				if (ticks == 120) {
					conn.sendCommand("imsm place WoodenHouse");
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: placed WoodenHouse");
				}
				if (ticks == 160) {
					conn.sendCommand("tp @p " + (PAD_X - 8) + " " + (PAD_Y + 4) + " " + (PAD_Z - 10) + " -25 15");
				}
				if (ticks == 180) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: house close-up 1");
				}
				if (ticks == 200) {
					conn.sendCommand("tp @p " + (PAD_X + 10) + " " + (PAD_Y + 6) + " " + (PAD_Z + 4) + " 140 25");
				}
				if (ticks == 220) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: house close-up 2");
				}
				if (ticks == 240) {
					phase = 1;
					ticks = 0;
				}
				return;
			}

			if (phase == 1) {
				if (ticks == 10) {
					conn.sendCommand("removelive");
					conn.sendCommand("fill " + (FERRIS_X - 30) + " " + PAD_Y + " " + (FERRIS_Z - 30)
						+ " " + (FERRIS_X + 30) + " " + (PAD_Y + 50) + " " + (FERRIS_Z + 30) + " minecraft:air");
				}
				if (ticks == 30) {
					conn.sendCommand("fill " + (FERRIS_X - 30) + " " + (PAD_Y - 1) + " " + (FERRIS_Z - 30)
						+ " " + (FERRIS_X + 30) + " " + (PAD_Y - 1) + " " + (FERRIS_Z + 30) + " minecraft:smooth_stone");
					conn.sendCommand("tp @p " + FERRIS_X + " " + PAD_Y + " " + FERRIS_Z + " 0 10");
				}
				if (ticks == 50) {
					conn.sendCommand("imsm live ferris");
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: started ferris");
				}
				if (ticks == 100) {
					// Side view of wheel — look toward +Z/+X where rim/stairs cluster
					conn.sendCommand("tp @p " + (FERRIS_X - 20) + " " + (PAD_Y + 20) + " " + (FERRIS_Z - 45) + " 20 15");
				}
				if (ticks == 120) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: ferris wide");
				}
				if (ticks == 140) {
					conn.sendCommand("tp @p " + (FERRIS_X + 5) + " " + (PAD_Y + 8) + " " + (FERRIS_Z - 25) + " 0 5");
				}
				if (ticks == 160) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: ferris rim close-up");
				}
				if (ticks == 180) {
					conn.sendCommand("tp @p " + (FERRIS_X - 5) + " " + (PAD_Y + 35) + " " + (FERRIS_Z + 5) + " 90 40");
				}
				if (ticks == 200) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: ferris top close-up");
				}
				if (ticks == 220) {
					conn.sendCommand("removelive");
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: done; quitting");
					done = true;
					client.stop();
				}
			}
		});
	}
}
