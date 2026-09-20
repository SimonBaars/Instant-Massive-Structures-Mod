package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

/**
 * Verification tool for CoS playtest issues:
 * 1. Chest items present (withItems > 0)
 * 2. Signs have BlockEntity and text
 * 3. Live shells place correctly
 */
public class PlacementVerifier {
	
	/**
	 * Place a structure and verify chest items and sign text.
	 * @return verification report
	 */
	public static String verifyStructure(ServerLevel world, String structureName, int x, int y, int z) {
		StringBuilder report = new StringBuilder();
		report.append("=== Verification: ").append(structureName).append(" at ")
			.append(x).append(",").append(y).append(",").append(z).append(" ===\n");
		
		try {
			SchematicStructure structure = new SchematicStructure(structureName);
			structure.readFromFile();
			
			int originX = x - (structure.getLength() / 2) + 1;
			int originZ = z - (structure.getWidth() / 2) + 1;
			
			report.append("Dimensions: ")
				.append(structure.getLength()).append("x")
				.append(structure.getHeight()).append("x")
				.append(structure.getWidth()).append("\n");
			
			// Place the structure
			structure.process(world, x, y, z);
			
			// Wait a tick for block entities to settle
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// ignore
			}
			
			// Scan for chests and signs in the placed area
			int chestsFound = 0;
			int chestsWithItems = 0;
			int totalItems = 0;
			int signsFound = 0;
			int signsWithText = 0;
			
			for (int dy = 0; dy < structure.getHeight(); dy++) {
				for (int dz = 0; dz < structure.getWidth(); dz++) {
					for (int dx = 0; dx < structure.getLength(); dx++) {
						BlockPos pos = new BlockPos(originX + dx, y + dy, originZ + dz);
						BlockEntity be = world.getBlockEntity(pos);
						
						if (be instanceof Container container) {
							chestsFound++;
							int itemsInChest = 0;
							for (int slot = 0; slot < container.getContainerSize(); slot++) {
								ItemStack stack = container.getItem(slot);
								if (!stack.isEmpty()) {
									itemsInChest++;
									totalItems += stack.getCount();
								}
							}
							if (itemsInChest > 0) {
								chestsWithItems++;
								report.append("  Chest at ").append(pos).append(": ")
									.append(itemsInChest).append(" item types, ")
									.append(totalItems).append(" total items\n");
							}
						}
						
						if (be instanceof SignBlockEntity sign) {
							signsFound++;
							// Check if any line has text
							boolean hasText = false;
							try {
								for (int i = 0; i < 4; i++) {
									String line = sign.getFrontText().getMessage(i, false).getString();
									if (!line.isEmpty()) {
										hasText = true;
										if (i == 0) {
											report.append("  Sign at ").append(pos).append(": \"")
												.append(line).append("...\"\n");
										}
										break;
									}
								}
							} catch (Exception e) {
								report.append("  Sign at ").append(pos).append(": ERROR reading text: ")
									.append(e.getMessage()).append("\n");
							}
							if (hasText) {
								signsWithText++;
							}
						}
					}
				}
			}
			
			report.append("\n--- Summary ---\n");
			report.append("Chests: ").append(chestsFound).append(" found, ")
				.append(chestsWithItems).append(" with items (")
				.append(totalItems).append(" items total)\n");
			report.append("Signs: ").append(signsFound).append(" found, ")
				.append(signsWithText).append(" with text\n");
			
			// Verification results
			boolean chestsOk = chestsWithItems > 0 || chestsFound == 0;
			boolean signsOk = signsWithText == signsFound;
			
			report.append("\n--- Status ---\n");
			report.append("Chests: ").append(chestsOk ? "PASS" : "FAIL")
				.append(chestsOk ? "" : " - chests found but empty!").append("\n");
			report.append("Signs: ").append(signsOk ? "PASS" : "FAIL")
				.append(signsOk ? "" : " - some signs missing text!").append("\n");
			
		} catch (Exception e) {
			report.append("ERROR: ").append(e.getMessage()).append("\n");
			e.printStackTrace();
		}
		
		return report.toString();
	}
}
