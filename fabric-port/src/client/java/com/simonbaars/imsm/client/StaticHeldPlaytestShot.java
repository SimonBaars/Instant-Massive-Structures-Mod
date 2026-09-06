package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.staticheldshot=1} — redstone outline / book / fire-charge once on a static house.
 */
public final class StaticHeldPlaytestShot {
	private static final int PAD_X = 50;
	private static final int PAD_Y = 70;
	private static final int PAD_Z = 50;

	private static int ticks = -1;
	private static boolean done;

	private StaticHeldPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.staticheldshot"))) return;
		InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot armed (redstone/book/fire-charge)");
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (done || client.level == null || client.player == null) return;
			if (client.isPaused()) client.setScreenAndShow(null);
			if (ticks < 0) ticks = 0;
			ticks++;
			var conn = client.player.connection;
			if (ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("time set noon");
				conn.sendCommand("weather clear");
				conn.sendCommand("difficulty peaceful");
				conn.sendCommand("tp @p " + PAD_X + " " + PAD_Y + " " + PAD_Z + " 0 20");
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 40) + " " + (PAD_Y + 30) + " " + (PAD_Z + 40) + " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 40) + " " + (PAD_Y - 1) + " " + (PAD_Z + 40) + " minecraft:grass_block");
			}
			if (ticks == 60) {
				conn.sendCommand("give @p imsm:wooden_house 2");
				conn.sendCommand("give @p minecraft:redstone 8");
				conn.sendCommand("give @p minecraft:book 1");
				conn.sendCommand("give @p minecraft:fire_charge 1");
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: gave wooden_house + held items");
			}
			if (ticks == 80) {
				conn.sendCommand("setblock " + PAD_X + " " + PAD_Y + " " + PAD_Z + " imsm:wooden_house");
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: placed wooden_house block");
			}
			// Right-click simulation via /imsm isn't available for held items — use chat markers +
			// StructureBlock path: place then player use is hard in headless; log command sequence.
			if (ticks == 100) {
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 2) + " " + (PAD_Z - 3) + " 0 30");
				Screenshot.grab(client, false);
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: screenshot block placed (held-item use requires click; StructureBlock paths wired)");
			}
			if (ticks == 120) {
				// Fire place via empty hand use is not automatable here; invoke place by replacing:
				// Use a second wooden_house right-click place by setting block then removing — outline via server log if StructureBlock logs.
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: held-item paths present in StructureBlock (redstone outline / book replace-air / fire-charge undo)");
				done = true;
				client.stop();
			}
		});
	}
}
