package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.core.LiveStructureTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;

/**
 * Verification tests for CoS playtest bug fixes.
 * Run with: ./gradlew runTestClass -PtestClass=PlaytestFixesTest
 */
public class PlaytestFixesTest {
	
	private static int passed = 0;
	private static int failed = 0;
	
	public static void main(String[] args) {
		System.out.println("=== CoS Playtest Fixes Verification ===\n");
		
		testChestItemsLegacyIdSupport();
		testFerrisWheelShellNoDoublePlacement();
		testOtherLiveShellStructuresCorrect();
		testLiveStructureFrameCounts();
		testForgeLivePlacementOffsets();
		testFabricFrameAnchorMatchesShellCells();
		testLegacyBlockStatesCompiles();
		
		System.out.println("\n=== Test Results ===");
		System.out.println("Passed: " + passed);
		System.out.println("Failed: " + failed);
		
		if (failed > 0) {
			System.exit(1);
		}
	}
	
	private static void test(String name, Runnable testCode) {
		try {
			testCode.run();
			passed++;
			System.out.println("✓ " + name);
		} catch (AssertionError e) {
			failed++;
			System.out.println("✗ " + name + ": " + e.getMessage());
		} catch (Exception e) {
			failed++;
			System.out.println("✗ " + name + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void assertEquals(Object expected, Object actual, String message) {
		if (!expected.equals(actual)) {
			throw new AssertionError(message + " (expected: " + expected + ", actual: " + actual + ")");
		}
	}
	
	private static void assertNotNull(Object obj, String message) {
		if (obj == null) {
			throw new AssertionError(message);
		}
	}
	
	private static void assertTrue(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
	
	public static void testChestItemsLegacyIdSupport() {
		test("Chest items legacy ID support", () -> {
			// Legacy item ID mappings should work
			var item263 = com.simonbaars.imsm.structureloader.LegacyItems.fromLegacyId(263); // coal
			assertNotNull(item263, "Legacy item ID 263 (coal) should map to a modern item");
			assertEquals(Items.COAL, item263, "Item 263 should be COAL");
			
			var item264 = com.simonbaars.imsm.structureloader.LegacyItems.fromLegacyId(264); // diamond
			assertNotNull(item264, "Legacy item ID 264 (diamond) should map to a modern item");
			assertEquals(Items.DIAMOND, item264, "Item 264 should be DIAMOND");
			
			var item267 = com.simonbaars.imsm.structureloader.LegacyItems.fromLegacyId(267); // iron sword
			assertNotNull(item267, "Legacy item ID 267 (iron sword) should map to a modern item");
			assertEquals(Items.IRON_SWORD, item267, "Item 267 should be IRON_SWORD");
			
			// ID 0 should return AIR (not null)
			var air = com.simonbaars.imsm.structureloader.LegacyItems.fromLegacyId(0);
			assertNotNull(air, "Legacy item ID 0 should return AIR (not null)");
			assertEquals(Items.AIR, air, "Item 0 should be AIR");
			
			// Unknown IDs should return AIR (safe fallback)
			var unknown = com.simonbaars.imsm.structureloader.LegacyItems.fromLegacyId(9999);
			assertNotNull(unknown, "Unknown legacy item ID should return AIR (not null)");
			assertEquals(Items.AIR, unknown, "Unknown item should default to AIR");
		});
	}
	
	public static void testFerrisWheelShellNoDoublePlacement() {
		test("Ferris wheel shell no double placement", () -> {
			LiveStructureTicker.LiveDef ferris = LiveStructureTicker.findDefinition("Live_FerrisWheel");
			assertNotNull(ferris, "Ferris wheel definition should exist");
			
			// Verify shell structure name
			assertEquals("Live_FerrisWheel", ferris.shellStructure(), 
				"Shell structure should be Live_FerrisWheel");
			
			// Verify frames array
			String[] frames = ferris.frames();
			assertTrue(frames.length >= 3, "Ferris should have at least 3 frames");
			
			// CRITICAL: First frame should NOT be the same as the shell
			assertEquals("Live_FerrisWheel0", frames[0], 
				"First frame should be Live_FerrisWheel0 (not Live_FerrisWheel, to avoid double placement)");
			
			// Verify remaining frames
			assertEquals("Live_FerrisWheel0", frames[1], "Second frame should be Live_FerrisWheel0");
			assertEquals("Live_FerrisWheel1", frames[2], "Third frame should be Live_FerrisWheel1");
			if (frames.length > 3) {
				assertEquals("Live_FerrisWheel2", frames[3], "Fourth frame should be Live_FerrisWheel2");
			}
		});
	}
	
	public static void testOtherLiveShellStructuresCorrect() {
		test("Other live shell structures correct", () -> {
			// Cinema
			LiveStructureTicker.LiveDef cinema = LiveStructureTicker.findDefinition("Live_Cinema");
			assertNotNull(cinema, "Cinema definition should exist");
			assertEquals("Live_Cinema", cinema.shellStructure(), "Cinema shell should be Live_Cinema");
			assertTrue(cinema.frames()[0].equals("Live_Cinema0"), 
				"Cinema first frame should be Live_Cinema0 (distinct from shell)");
			
			// WaterMill
			LiveStructureTicker.LiveDef watermill = LiveStructureTicker.findDefinition("Live_WaterMill");
			assertNotNull(watermill, "WaterMill definition should exist");
			assertEquals("Live_WaterMill", watermill.shellStructure(), "WaterMill shell should be Live_WaterMill");
			assertTrue(watermill.frames()[0].equals("Live_WaterMill0"), 
				"WaterMill first frame should be Live_WaterMill0 (distinct from shell)");
			
			// Mill
			LiveStructureTicker.LiveDef mill = LiveStructureTicker.findDefinition("Live_Mill");
			assertNotNull(mill, "Mill definition should exist");
			assertEquals("Live_Mill", mill.shellStructure(), "Mill shell should be Live_Mill");
			assertTrue(mill.frames()[0].equals("Live_Mill0"), 
				"Mill first frame should be Live_Mill0 (distinct from shell)");
			
			// Windmill
			LiveStructureTicker.LiveDef windmill = LiveStructureTicker.findDefinition("Live_Power_Windmill_East");
			assertNotNull(windmill, "Windmill definition should exist");
			assertEquals("Live_Power_Windmill_East", windmill.shellStructure(), 
				"Windmill shell should be Live_Power_Windmill_East");
			assertTrue(windmill.frames()[0].equals("Live_Power_Windmill_East0"), 
				"Windmill first frame should be Live_Power_Windmill_East0 (distinct from shell)");
		});
	}
	
	public static void testLiveStructureFrameCounts() {
		test("Live structure frame counts", () -> {
			LiveStructureTicker.LiveDef ferris = LiveStructureTicker.findDefinition("Live_FerrisWheel");
			assertEquals(4, ferris.frames().length, "Ferris should have 4 frames");
			
			LiveStructureTicker.LiveDef cinema = LiveStructureTicker.findDefinition("Live_Cinema");
			assertEquals(43, cinema.frames().length, "Cinema should have 43 frames");
			
			LiveStructureTicker.LiveDef watermill = LiveStructureTicker.findDefinition("Live_WaterMill");
			assertEquals(3, watermill.frames().length, "WaterMill should have 3 frames");
			
			LiveStructureTicker.LiveDef windmill = LiveStructureTicker.findDefinition("Live_Power_Windmill_East");
			assertEquals(3, windmill.frames().length, "Windmill should have 3 frames");
		});
	}
	
	public static void testForgeLivePlacementOffsets() {
		test("Forge live shell mods and frame deltas", () -> {
			// shell = Forge modifier; spawn = spawnPosModifier; frames at shell − spawn.
			assertOffset("Live_Cinema", 49, -1, 25, 3, -3, 10);
			assertOffset("Live_Power_Windmill_East", 0, 0, 0, 6, -16, 0);
			assertOffset("Live_Mill", 15, -1, 15, 14, -13, 0);
			assertOffset("Live_WaterMill", 14, -3, 9, 2, -2, 3);
			assertOffset("Live_FerrisWheel", 1, -1, 36, 4, -2, 0);
			assertOffset("Live_Fair_FreeFall", 0, 0, 0, 4, 0, 4);
			assertOffset("Live_Helicopter", 0, 0, 0, 0, 0, 0);
			assertOffset("LiveBoat", 0, -2, 0, 0, 0, 0);
			assertOffset("Live_Bus", 0, 0, 0, 0, 0, 0);
			assertOffset("Live_Bus2", 0, 0, 0, 0, 0, 0);
			assertOffset("LiveAirplane", 0, 0, 0, 0, 0, 0);
			assertOffset("Live_Flying_Helicopter", 0, 0, 0, 0, 0, 0);
			assertOffset("LivePlane", 26, 0, 19, 0, 0, 0);
			assertOffset("LiveAirBalloon", 0, 0, 0, 0, 0, 0);
			assertOffset("LiveFlyingShip1", 15, -10, 24, 0, 0, 0);
			assertOffset("LiveFlyingShip2", 22, -8, 16, 0, 0, 0);

			LiveStructureTicker.LiveDef cinema = LiveStructureTicker.findDefinition("Live_Cinema");
			assertEquals(46, cinema.shellModX() - cinema.spawnModX(), "Cinema live X = 49-(69-66)");
			assertEquals(2, cinema.shellModY() - cinema.spawnModY(), "Cinema live Y = -1-(3-6)");
			assertEquals(15, cinema.shellModZ() - cinema.spawnModZ(), "Cinema live Z = 25-(46-36)");
		});
	}

	private static void assertOffset(String base, int sx, int sy, int sz, int px, int py, int pz) {
		LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition(base);
		assertNotNull(def, base + " definition should exist");
		assertEquals(sx, def.shellModX(), base + " shellModX");
		assertEquals(sy, def.shellModY(), base + " shellModY");
		assertEquals(sz, def.shellModZ(), base + " shellModZ");
		assertEquals(px, def.spawnModX(), base + " spawnModX");
		assertEquals(py, def.spawnModY(), base + " spawnModY");
		assertEquals(pz, def.spawnModZ(), base + " spawnModZ");
	}

	/**
	 * Fabric centers with {@code anchor - size/2 + 1}. Forge also subtracts a full half-size,
	 * so the process() anchor must add {@code shellHalf - frameHalf} or frame voxels miss the shell.
	 * Expected anchors are relative to shell (0,0,0) and were checked against schematic bytes:
	 * every non-air frame cell maps onto a shell cell under Forge's transform.
	 */
	public static void testFabricFrameAnchorMatchesShellCells() {
		test("Fabric frame anchor corrects shell/frame size", () -> {
			BlockPos shell = BlockPos.ZERO;
			// Windmill East: shell 16x32, blades 1x32, spawn (6,-16,0) → place (2,16,0)
			assertAnchor(shell, "windmill", 6, -16, 0, 16, 32, 1, 32, 2, 16, 0);
			// Playtest shell (500,80,200) → fabric blades (502,96,200), not forge-only (494,96,200)
			BlockPos millShell = new BlockPos(500, 80, 200);
			BlockPos blades = LiveStructureTicker.fabricFrameAnchor(millShell, 6, -16, 0, 16, 32, 1, 32);
			assertEquals(502, blades.getX(), "windmill blade X on hub");
			assertEquals(96, blades.getY(), "windmill blade Y");
			assertEquals(200, blades.getZ(), "windmill blade Z");
			// Cinema: shell 51x50, screen 1x30, spawn (3,-3,10) → place (22,3,0)
			assertAnchor(shell, "cinema", 3, -3, 10, 51, 50, 1, 30, 22, 3, 0);
			// Mill 19x33 / 4x33, spawn (14,-13,0) → (-7,13,0)
			assertAnchor(shell, "mill", 14, -13, 0, 19, 33, 4, 33, -7, 13, 0);
			// Water mill 19x25 / 11x5, spawn (2,-2,3) → (2,2,7)
			assertAnchor(shell, "watermill", 2, -2, 3, 19, 25, 11, 5, 2, 2, 7);
			// Ferris 18x75 / 10x75, spawn (4,-2,0) → (0,2,0)
			assertAnchor(shell, "ferris", 4, -2, 0, 18, 75, 10, 75, 0, 2, 0);
			// FreeFall 16x16 / 8x8, spawn (4,0,4) → (0,0,0) (half-size cancels spawn)
			assertAnchor(shell, "freefall", 4, 0, 4, 16, 16, 8, 8, 0, 0, 0);
			// Helicopter same size, spawn 0
			assertAnchor(shell, "heli", 0, 0, 0, 23, 27, 23, 27, 0, 0, 0);
			// Path mover: no shell, anchor stays at the spawn modifier
			BlockPos boat = LiveStructureTicker.fabricFrameAnchor(
				new BlockPos(0, -2, 0), 0, 0, 0, 0, 0, 20, 10);
			assertEquals(0, boat.getX(), "boat X");
			assertEquals(-2, boat.getY(), "boat Y");
			assertEquals(0, boat.getZ(), "boat Z");
		});
	}

	private static void assertAnchor(BlockPos shell, String name,
			int sx, int sy, int sz, int shellL, int shellW, int frameL, int frameW,
			int ex, int ey, int ez) {
		BlockPos at = LiveStructureTicker.fabricFrameAnchor(shell, sx, sy, sz, shellL, shellW, frameL, frameW);
		assertEquals(ex, at.getX(), name + " X");
		assertEquals(ey, at.getY(), name + " Y");
		assertEquals(ez, at.getZ(), name + " Z");
	}

	public static void testLegacyBlockStatesCompiles() {
		test("Legacy block states compiles", () -> {
			var oakPlanks = com.simonbaars.imsm.structureloader.LegacyBlockStates.fromLegacy(5, 0);
			assertNotNull(oakPlanks, "Oak planks (ID 5) should resolve to a block state");
			assertTrue(oakPlanks.is(net.minecraft.world.level.block.Blocks.OAK_PLANKS), 
				"ID 5 should be oak planks");
		});
	}
}
