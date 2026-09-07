package com.simonbaars.imsm.client;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Screenshot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Dev-only: {@code -Dimsm.staticheldshot=1} — redstone outline / book / fire-charge once on wooden_house.
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
			if (done || client.level == null || client.player == null || client.gameMode == null) return;
			if (client.isPaused()) client.setScreenAndShow(null);
			if (ticks < 0) ticks = 0;
			ticks++;
			var conn = client.player.connection;
			BlockPos blockPos = new BlockPos(PAD_X, PAD_Y, PAD_Z);

			if (ticks == 40) {
				conn.sendCommand("gamerule sendCommandFeedback false");
				conn.sendCommand("gamemode creative @p");
				conn.sendCommand("time set noon");
				conn.sendCommand("weather clear");
				conn.sendCommand("difficulty peaceful");
				conn.sendCommand("tp @p " + PAD_X + " " + (PAD_Y + 2) + " " + (PAD_Z - 3) + " 0 30");
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 40) + " " + (PAD_Y + 30) + " " + (PAD_Z + 40) + " minecraft:air");
				conn.sendCommand("fill " + (PAD_X - 20) + " " + (PAD_Y - 2) + " " + (PAD_Z - 20)
					+ " " + (PAD_X + 40) + " " + (PAD_Y - 1) + " " + (PAD_Z + 40) + " minecraft:grass_block");
			}
			if (ticks == 60) {
				conn.sendCommand("give @p imsm:wooden_house 4");
				conn.sendCommand("give @p minecraft:redstone 16");
				conn.sendCommand("give @p minecraft:book 1");
				conn.sendCommand("give @p minecraft:fire_charge 1");
			}
			if (ticks == 80) {
				conn.sendCommand("setblock " + PAD_X + " " + PAD_Y + " " + PAD_Z + " imsm:wooden_house");
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: placed wooden_house");
			}
			if (ticks == 100) {
				conn.sendCommand("item replace entity @p hotbar.0 with minecraft:redstone 16");
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: holding redstone");
			}
			if (ticks == 110) {
				useOn(client, blockPos);
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: used redstone (outline)");
			}
			if (ticks == 130) {
				Screenshot.grab(client, false);
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: screenshot outline");
			}
			if (ticks == 150) {
				conn.sendCommand("item replace entity @p hotbar.0 with minecraft:book 1");
			}
			if (ticks == 160) {
				useOn(client, blockPos);
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: used book (replace-air toggle)");
			}
			if (ticks == 180) {
				conn.sendCommand("item replace entity @p hotbar.0 with minecraft:fire_charge 1");
			}
			if (ticks == 190) {
				useOn(client, blockPos);
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: used fire_charge (undo)");
			}
			if (ticks == 210) {
				Screenshot.grab(client, false);
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: screenshot after held-item sequence");
			}
			if (ticks == 230) {
				InstantMassiveStructures.LOGGER.info("StaticHeldPlaytestShot: quitting");
				done = true;
				client.stop();
			}
		});
	}

	private static void useOn(net.minecraft.client.Minecraft client, BlockPos pos) {
		if (client.gameMode == null || client.player == null) return;
		BlockHitResult hit = new BlockHitResult(
			new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5),
			Direction.NORTH, pos, false);
		client.gameMode.useItemOn(client.player, InteractionHand.MAIN_HAND, hit);
	}
}
