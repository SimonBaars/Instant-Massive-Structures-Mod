package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.core.LiveStructureTicker;
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
	
	public static void testLegacyBlockStatesCompiles() {
		test("Legacy block states compiles", () -> {
			var oakPlanks = com.simonbaars.imsm.structureloader.LegacyBlockStates.fromLegacy(5, 0);
			assertNotNull(oakPlanks, "Oak planks (ID 5) should resolve to a block state");
			assertTrue(oakPlanks.is(net.minecraft.world.level.block.Blocks.OAK_PLANKS), 
				"ID 5 should be oak planks");
		});
	}
}
