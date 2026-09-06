package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * Dev-only: {@code -Dimsm.persistencesmoke=1} starts a light live mill then quits
 * so {@code LiveStructures/0.txt} can be inspected after save.
 */
public final class PersistenceSmokeShot {
	private static int ticks = -1;
	private static boolean started;
	private static boolean done;

	private PersistenceSmokeShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.persistencesmoke"))) {
			return;
		}
		InstantMassiveStructures.LOGGER.info("PersistenceSmokeShot armed");
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
			if (!started && ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("tp @p 400 96 -150");
				conn.sendCommand("imsm live mill");
				started = true;
				InstantMassiveStructures.LOGGER.info("PersistenceSmokeShot: started mill");
			}
			if (started && ticks == 80) {
				InstantMassiveStructures.LOGGER.info("PersistenceSmokeShot: quitting for save");
				done = true;
				client.stop();
			}
		});
	}
}
