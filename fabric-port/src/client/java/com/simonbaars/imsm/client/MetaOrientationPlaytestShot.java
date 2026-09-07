package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;

/**
 * Dev-only: {@code -Dimsm.metashot=1} — batch {@code /imsm metastats} across many schematics,
 * then place+screenshot a diverse high-meta subset beyond WoodenHouse/Ferris.
 */
public final class MetaOrientationPlaytestShot {
	private static final int PAD_Y = 90;
	/** Structures for metastats-only (no place). */
	private static final String[] METASTATS = {
		"WoodenHouse",
		"Live_FerrisWheel",
		"BlockCosyHouse",
		"BlockMegaHouse",
		"BlockMegaTower",
		"BlockStadium",
		"BlockCastleTower",
		"BlockLighthouse",
		"ChristmasHouse",
		"ChristmasMarket",
		"BlockRollercoaster",
		"BlockWaterSlide",
		"DecorationSoccerStadiumNorthSouth",
		"PublicLibraryNorthSouth",
		"PublicHospitalBigNorth",
		"PublicTownhallBigNorthSouth",
		"RandomImmense_White_House",
		"ResidentalEnormous_DensityModernNorth",
		"ShoppingHigh_DensityQuartzNorthSouth",
		"OfficeHigh_DensitySpirolBuildingNorth",
		"Live_Cinema",
		"BlockHountedHouse",
		"BlockPrison",
		"FoodCarrotsNorthSouth"
	};

	/** Place+shot pads: name, x, z, camera offsets for 1–2 views. */
	private static final String[] PLACE_NAMES = {
		"BlockCosyHouse",
		"BlockCastleTower",
		"ChristmasHouse",
		"BlockStadium",
		"BlockLighthouse",
		"PublicLibraryNorthSouth",
		"BlockHountedHouse",
		"Live_Cinema",
		"BlockMegaHouse",
		"DecorationSoccerStadiumNorthSouth"
	};
	private static final int[] PLACE_X = {
		3400, 3500, 3600, 3700, 3800, 3900, 4000, 4100, 4200, 4300
	};
	private static final int[] PLACE_Z = {
		200, 200, 200, 250, 200, 200, 200, 250, 200, 250
	};

	private static int ticks = -1;
	private static int phase; // 0=metastats, 1=place loop, 2=done
	private static int placeIdx;
	private static int metaIdx;
	private static boolean done;

	private MetaOrientationPlaytestShot() {}

	public static void registerIfRequested() {
		if (!"1".equals(System.getProperty("imsm.metashot"))) return;
		InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot armed (batch)");
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
				}
				// Stagger metastats so chat/log stays readable
				if (ticks >= 60 && metaIdx < METASTATS.length && (ticks - 60) % 8 == 0) {
					String name = METASTATS[metaIdx++];
					conn.sendCommand("imsm metastats " + name);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: metastats {}", name);
				}
				if (metaIdx >= METASTATS.length && ticks >= 60 + METASTATS.length * 8 + 20) {
					phase = 1;
					placeIdx = 0;
					ticks = 0;
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: metastats batch done");
				}
				return;
			}

			if (phase == 1) {
				if (placeIdx >= PLACE_NAMES.length) {
					phase = 2;
					ticks = 0;
					return;
				}
				int px = PLACE_X[placeIdx];
				int pz = PLACE_Z[placeIdx];
				String name = PLACE_NAMES[placeIdx];

				if (ticks == 5) {
					conn.sendCommand("forceload add " + (px - 48) + " " + (pz - 48)
						+ " " + (px + 48) + " " + (pz + 48));
				}
				if (ticks == 15) {
					conn.sendCommand("fill " + (px - 40) + " " + PAD_Y + " " + (pz - 40)
						+ " " + (px + 40) + " " + (PAD_Y + 45) + " " + (pz + 40) + " minecraft:air");
				}
				if (ticks == 30) {
					conn.sendCommand("fill " + (px - 40) + " " + (PAD_Y - 1) + " " + (pz - 40)
						+ " " + (px + 40) + " " + (PAD_Y - 1) + " " + (pz + 40) + " minecraft:smooth_stone");
					conn.sendCommand("tp @p " + px + " " + PAD_Y + " " + pz + " 0 15");
				}
				if (ticks == 50) {
					conn.sendCommand("imsm place " + name);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: placed {}", name);
				}
				if (ticks == 90) {
					conn.sendCommand("tp @p " + (px - 12) + " " + (PAD_Y + 6) + " " + (pz - 18) + " -20 18");
				}
				if (ticks == 110) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: shot A {}", name);
				}
				if (ticks == 130) {
					conn.sendCommand("tp @p " + (px + 14) + " " + (PAD_Y + 10) + " " + (pz + 8) + " 140 28");
				}
				if (ticks == 150) {
					Screenshot.grab(client, false);
					InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: shot B {}", name);
				}
				if (ticks == 170) {
					// Clear pad for next (avoid fill limit: two passes)
					conn.sendCommand("fill " + (px - 40) + " " + PAD_Y + " " + (pz - 40)
						+ " " + (px + 40) + " " + (PAD_Y + 22) + " " + (pz + 40) + " minecraft:air");
				}
				if (ticks == 185) {
					conn.sendCommand("fill " + (px - 40) + " " + (PAD_Y + 23) + " " + (pz - 40)
						+ " " + (px + 40) + " " + (PAD_Y + 45) + " " + (pz + 40) + " minecraft:air");
					placeIdx++;
					ticks = 0;
				}
				return;
			}

			if (phase == 2 && ticks >= 20) {
				InstantMassiveStructures.LOGGER.info("MetaOrientationPlaytestShot: batch done; quitting");
				done = true;
				client.stop();
			}
		});
	}
}
