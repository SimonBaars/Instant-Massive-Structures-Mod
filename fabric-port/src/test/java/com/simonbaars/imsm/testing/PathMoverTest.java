package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.core.LiveStructureTicker;

/**
 * Verify path-moving live structure definitions for airplane, plane, ship1/2 variants.
 * Tests that PathMotion aviation patterns are correctly configured (climb/level/descend phases).
 * 
 * Run with: ./gradlew runTestClass -PtestClass=PathMoverTest
 */
public class PathMoverTest {
	
	private static int passed = 0;
	private static int failed = 0;
	
	public static void main(String[] args) {
		System.out.println("=== Path Mover Configuration Tests ===\n");
		
		testAirplaneDefinition();
		testPlaneDefinition();
		testFlyingShip1Definition();
		testFlyingShip2Definition();
		testPathMotionPhaseCalculation();
		testBoatBusNonAviationPath();
		testAllPathMoversHaveValidFrames();
		
		System.out.println("\n=== Test Results ===");
		System.out.println("Passed: " + passed);
		System.out.println("Failed: " + failed);
		
		if (failed > 0) {
			System.exit(1);
		}
	}
	
	private static void testAirplaneDefinition() {
		System.out.print("Test: LiveAirplane aviation path configuration... ");
		try {
			LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition("LiveAirplane");
			check(def != null, "LiveAirplane definition exists");
			check(def.isPathMover(), "LiveAirplane is a path mover");
			
			LiveStructureTicker.PathMotion path = def.path();
			check(path != null, "LiveAirplane has PathMotion");
			check(path.aviation(), "LiveAirplane uses aviation path");
			
			// Legacy: climb 30×{0,+1,+1} → level (d−60)×{0,0,+1} → descend 31×{0,−1,+1}
			check(path.climbCount() == 30, "Climb phase is 30 steps");
			check(path.descendCount() == 31, "Descend phase is 31 steps");
			check(path.defaultDistance() == 76, "Default fly distance is 76");
			
			LiveStructureTicker.PathStep climb = path.climb();
			check(climb.dx() == 0, "Climb dx is 0");
			check(climb.dy() == 1, "Climb dy is +1");
			check(climb.dz() == 1, "Climb dz is +1 (forward)");
			
			LiveStructureTicker.PathStep descend = path.descend();
			check(descend.dx() == 0, "Descend dx is 0");
			check(descend.dy() == -1, "Descend dy is -1");
			check(descend.dz() == 1, "Descend dz is +1 (forward)");
			
			// Level steps for default distance: max(1, distance − 60)
			int levelSteps = path.levelStepsForDistance(76);
			check(levelSteps == 16, "Level phase for distance 76 is 16 steps");
			
			System.out.println("PASS");
			passed++;
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
			e.printStackTrace();
			failed++;
		}
	}
	
	private static void testPlaneDefinition() {
		System.out.print("Test: LivePlane aviation path configuration... ");
		try {
			LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition("LivePlane");
			check(def != null, "LivePlane definition exists");
			check(def.isPathMover(), "LivePlane is a path mover");
			
			LiveStructureTicker.PathMotion path = def.path();
			check(path != null, "LivePlane has PathMotion");
			check(path.aviation(), "LivePlane uses aviation path");
			
			// Legacy: climb 30×{−1,+1,0} → level (d−60)×{−1,0,0} → descend 31×{−1,−1,0}
			check(path.climbCount() == 30, "Climb phase is 30 steps");
			check(path.descendCount() == 31, "Descend phase is 31 steps");
			
			LiveStructureTicker.PathStep climb = path.climb();
			check(climb.dx() == -1, "Climb dx is -1 (westward)");
			check(climb.dy() == 1, "Climb dy is +1");
			check(climb.dz() == 0, "Climb dz is 0");
			
			LiveStructureTicker.PathStep cruise = path.cruise();
			check(cruise.dx() == -1, "Cruise dx is -1 (westward)");
			check(cruise.dy() == 0, "Cruise dy is 0 (level)");
			check(cruise.dz() == 0, "Cruise dz is 0");
			
			System.out.println("PASS");
			passed++;
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
			e.printStackTrace();
			failed++;
		}
	}
	
	private static void testFlyingShip1Definition() {
		System.out.print("Test: LiveFlyingShip1 aviation path configuration... ");
		try {
			LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition("LiveFlyingShip1");
			check(def != null, "LiveFlyingShip1 definition exists");
			check(def.isPathMover(), "LiveFlyingShip1 is a path mover");
			
			LiveStructureTicker.PathMotion path = def.path();
			check(path != null, "LiveFlyingShip1 has PathMotion");
			check(path.aviation(), "LiveFlyingShip1 uses aviation path");
			
			// Legacy: climb 30×{0,+1,−1} → level (d−60)×{0,0,−1} → descend 31×{0,−1,−1}
			LiveStructureTicker.PathStep climb = path.climb();
			check(climb.dx() == 0, "Climb dx is 0");
			check(climb.dy() == 1, "Climb dy is +1");
			check(climb.dz() == -1, "Climb dz is -1 (northward)");
			
			check(def.frames().length == 3, "FlyingShip1 has 3 frames");
			check(def.frames()[0].equals("LiveFlyingShip10"), "First frame is LiveFlyingShip10");
			
			System.out.println("PASS");
			passed++;
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
			e.printStackTrace();
			failed++;
		}
	}
	
	private static void testFlyingShip2Definition() {
		System.out.print("Test: LiveFlyingShip2 aviation path configuration... ");
		try {
			LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition("LiveFlyingShip2");
			check(def != null, "LiveFlyingShip2 definition exists");
			check(def.isPathMover(), "LiveFlyingShip2 is a path mover");
			
			LiveStructureTicker.PathMotion path = def.path();
			check(path != null, "LiveFlyingShip2 has PathMotion");
			check(path.aviation(), "LiveFlyingShip2 uses aviation path");
			
			// Legacy: climb 30×{+1,+1,0} → level (d−60)×{+1,0,0} → descend 31×{+1,−1,0}
			LiveStructureTicker.PathStep climb = path.climb();
			check(climb.dx() == 1, "Climb dx is +1 (eastward)");
			check(climb.dy() == 1, "Climb dy is +1");
			check(climb.dz() == 0, "Climb dz is 0");
			
			check(def.frames().length == 3, "FlyingShip2 has 3 frames");
			
			System.out.println("PASS");
			passed++;
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
			e.printStackTrace();
			failed++;
		}
	}
	
	private static void testPathMotionPhaseCalculation() {
		System.out.print("Test: Aviation path phase calculation... ");
		try {
			LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition("LiveAirplane");
			LiveStructureTicker.PathMotion path = def.path();
			
			// Default distance 76: climb 30 + level 16 + descend 31 = 77 steps total
			int level76 = path.levelStepsForDistance(76);
			check(level76 == 16, "Distance 76 yields 16 level steps");
			
			// Minimum clamping: distance 10 should still yield at least 1 level step
			int level10 = path.levelStepsForDistance(10);
			check(level10 >= 1, "Level steps always at least 1");
			
			// Large distance: 150 blocks → level 90 steps
			int level150 = path.levelStepsForDistance(150);
			check(level150 == 90, "Distance 150 yields 90 level steps");
			
			System.out.println("PASS");
			passed++;
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
			e.printStackTrace();
			failed++;
		}
	}
	
	private static void testBoatBusNonAviationPath() {
		System.out.print("Test: Boat/Bus non-aviation cruise path... ");
		try {
			LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition("LiveBoat");
			check(def != null, "LiveBoat definition exists");
			check(def.isPathMover(), "LiveBoat is a path mover");
			
			LiveStructureTicker.PathMotion path = def.path();
			check(!path.aviation(), "Boat does NOT use aviation path");
			check(path.climbCount() == 0, "Boat has no climb phase");
			check(path.descendCount() == 0, "Boat has no descend phase");
			
			LiveStructureTicker.PathStep cruise = path.cruise();
			check(cruise.dx() == 0, "Boat cruise dx is 0");
			check(cruise.dy() == 0, "Boat cruise dy is 0");
			check(cruise.dz() == 1, "Boat cruise dz is +1 (southward)");
			
			System.out.println("PASS");
			passed++;
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
			e.printStackTrace();
			failed++;
		}
	}
	
	private static void testAllPathMoversHaveValidFrames() {
		System.out.print("Test: All path movers have valid frame definitions... ");
		try {
			String[] pathMovers = {
				"LiveAirplane", "LivePlane", "LiveBoat", "Live_Bus", "Live_Bus2",
				"Live_Flying_Helicopter", "LiveAirBalloon", "LiveFlyingShip1", "LiveFlyingShip2"
			};
			
			for (String name : pathMovers) {
				LiveStructureTicker.LiveDef def = LiveStructureTicker.findDefinition(name);
				check(def != null, name + " definition exists");
				check(def.isPathMover(), name + " is a path mover");
				check(def.frames().length > 0, name + " has at least one frame");
				check(def.path() != null, name + " has PathMotion");
				
				// Verify all frame names are non-empty
				for (String frame : def.frames()) {
					check(frame != null, name + " frame is not null");
					check(!frame.isEmpty(), name + " frame is not empty");
				}
			}
			
			System.out.println("PASS");
			passed++;
		} catch (Exception e) {
			System.out.println("FAIL: " + e.getMessage());
			e.printStackTrace();
			failed++;
		}
	}
	
	private static void check(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException("Assertion failed: " + message);
		}
	}
}
