package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/** Dev-only: {@code -Dimsm.boatbusshot=1} — boat then bus one-shot +Z complete without explode. */
public final class BoatBusPlaytestShot {
	private static final int PAD_X = 900;
	private static final int PAD_Y = 68;
	private static final int PAD_Z = -200;

	private static int ticks = -1;
	private static int stage; // 0 setup, 1 boat, 2 bus, 3 done
	private static boolean started, shotMid, removed, done;

	private BoatBusPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.boatbusshot"))) return;
		InstantMassiveStructures.LOGGER.info("BoatBusPlaytestShot armed");
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (done || client.level == null || client.player == null) return;
			if (client.isPaused()) client.setScreenAndShow(null);
			if (ticks < 0) ticks = 0;
			ticks++;
			var conn = client.player.connection;
			if (stage == 0 && ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("time set noon");
				conn.sendCommand("weather clear");
				conn.sendCommand("difficulty peaceful");
				conn.sendCommand("effect give @p minecraft:night_vision 999 0 true");
				conn.sendCommand("forceload add " + (PAD_X - 40) + " " + (PAD_Z - 40)
					+ " " + (PAD_X + 40) + " " + (PAD_Z + 100));
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 5) + " " + (PAD_Z - 30)
					+ " " + (PAD_X + 40) + " " + (PAD_Y + 25) + " " + (PAD_Z + 80) + " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 40) + " " + (PAD_Y - 5) + " " + (PAD_Z - 30)
					+ " " + (PAD_X + 40) + " " + (PAD_Y - 4) + " " + (PAD_Z + 80) + " minecraft:smooth_stone");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + PAD_Z + " 0 15");
				stage = 1;
				ticks = 0;
				return;
			}
			if (stage == 1 || stage == 2) {
				String type = stage == 1 ? "boat" : "bus";
				if (!started && ticks == 40) {
					conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 4) + " " + PAD_Z + " 0 15");
					conn.sendCommand("imsm live " + type + " 16");
					started = true;
					InstantMassiveStructures.LOGGER.info("BoatBusPlaytestShot: /imsm live {} 16", type);
				}
				if (started && !shotMid && ticks == 160) {
					Screenshot.grab(client, false);
					shotMid = true;
					InstantMassiveStructures.LOGGER.info("BoatBusPlaytestShot: mid {}", type);
				}
				if (started && !removed && ticks == 220) {
					conn.sendCommand("removelive");
					removed = true;
					InstantMassiveStructures.LOGGER.info("BoatBusPlaytestShot: removelive after {}", type);
				}
				if (removed && ticks == 240) {
					if (stage == 1) {
						stage = 2;
						ticks = 0;
						started = shotMid = removed = false;
					} else {
						InstantMassiveStructures.LOGGER.info("BoatBusPlaytestShot: quitting");
						done = true;
						client.stop();
					}
				}
			}
		});
	}
}
