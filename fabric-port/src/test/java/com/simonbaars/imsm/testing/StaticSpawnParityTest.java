package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.structureloader.SpawnOrigin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Forge BlockStructure modifier + StructureUtils centering versus Fabric static spawn.
 * Run: {@code ./gradlew runStaticSpawnParity} from {@code fabric-port}.
 *
 * <p>Live shells and frames are asserted to stay on the single half-size process anchor.
 * That is the formula the separate frame-to-shell fix is built on.
 */
public class StaticSpawnParityTest {
	private static final int CLICK_X = 0;
	private static final int CLICK_Y = 64;
	private static final int CLICK_Z = 0;

	/** Forge BlockStructure classes with no .structure file and no Fabric block. */
	private static final Set<String> NO_SCHEMATIC = Set.of(
		"BlockApplepie", "BlockCactus2", "BlockCake2", "BlockCave", "BlockColumn",
		"BlockFloatingSphere", "BlockGlassHouse", "BlockHouse2", "BlockLeaves2",
		"BlockPenIron", "BlockPenNether", "BlockPenWood", "BlockShelter",
		"BlockSkyscraper2", "BlockStadium2", "BlockStandardBrickHouse", "BlockStreet"
	);

	/**
	 * Live shell registry triples on master. Not updated here: frame alignment is a
	 * separate change and several of these still differ from Forge on purpose.
	 */
	private static final Map<String, int[]> LIVE_SHELLS_UNCHANGED = Map.ofEntries(
		Map.entry("LiveBoat", new int[] {0, -2, 0}),
		Map.entry("LiveFlyingShip1", new int[] {15, -10, 24}),
		Map.entry("LiveFlyingShip2", new int[] {22, -8, 16}),
		Map.entry("LivePlane", new int[] {26, 0, 19}),
		Map.entry("Live_Cinema", new int[] {0, 0, 0}),
		Map.entry("Live_Mill", new int[] {0, 0, 0}),
		Map.entry("Live_WaterMill", new int[] {0, 0, 0}),
		Map.entry("Live_Power_Windmill_East", new int[] {0, 0, 0}),
		Map.entry("Live_FerrisWheel", new int[] {0, 0, 0}),
		Map.entry("Live_Fair_FreeFall", new int[] {0, 0, 0}),
		Map.entry("Live_Helicopter", new int[] {0, 0, 0}),
		Map.entry("LiveAirplane", new int[] {0, 0, 0}),
		Map.entry("LiveAirBalloon", new int[] {0, 0, 0}),
		Map.entry("Live_Bus", new int[] {0, 0, 0}),
		Map.entry("Live_Bus2", new int[] {0, 0, 0}),
		Map.entry("Live_Flying_Helicopter", new int[] {0, 0, 0})
	);

	private static int failures = 0;

	public static void main(String[] args) throws IOException {
		Path registryFile = find(
			"src/main/java/com/simonbaars/imsm/core/StructureRegistry.java",
			"fabric-port/src/main/java/com/simonbaars/imsm/core/StructureRegistry.java");
		Path forgeDir = find(
			"../src/main/java/modid/imsm/structures",
			"src/main/java/modid/imsm/structures");
		Path structsDir = find(
			"src/main/resources/assets/imsm/structs",
			"fabric-port/src/main/resources/assets/imsm/structs");
		Path structureBlock = registryFile.getParent().getParent().resolve("blocks/StructureBlock.java");
		Path schematic = registryFile.getParent().getParent().resolve("structureloader/SchematicStructure.java");
		Path liveTicker = registryFile.getParent().resolve("LiveStructureTicker.java");
		Path staticSpawn = registryFile.getParent().resolve("StaticSpawn.java");
		Path commands = registryFile.getParent().resolve("ImmsCommands.java");

		Map<String, int[]> forge = parseForge(forgeDir);
		Map<String, int[]> registry = parseRegistry(Files.readString(registryFile));

		check("forge catalog parsed", forge.size() == 844, "got " + forge.size());
		check("registry parsed", registry.size() == 952, "got " + registry.size());

		int modifierAlready = 0;
		int modifierFixed = 0;
		int originAlready = 0;
		int originFixedCenterOnly = 0;
		int originFixedWithModifier = 0;
		List<String> changedRows = new ArrayList<>();
		Set<String> missing = new TreeSet<>();

		for (Map.Entry<String, int[]> entry : new TreeMap<>(forge).entrySet()) {
			String name = entry.getKey();
			int[] mod = entry.getValue();
			Path file = structsDir.resolve(name + ".structure");
			if (!Files.isRegularFile(file)) {
				missing.add(name);
				check("unregistered missing schematic " + name, !registry.containsKey(name), "still registered");
				continue;
			}
			check("registered " + name, registry.containsKey(name), "missing from StructureRegistry");
			int[] reg = registry.get(name);
			if (reg == null) {
				continue;
			}
			boolean modsEqual = reg[0] == mod[0] && reg[1] == mod[1] && reg[2] == mod[2];
			check("modifier " + name, modsEqual,
				"registry " + reg[0] + "," + reg[1] + "," + reg[2]
					+ " forge " + mod[0] + "," + mod[1] + "," + mod[2]);
			if (mod[0] == 0 && mod[1] == 0 && mod[2] == 0) {
				modifierAlready++;
			} else {
				modifierFixed++;
			}

			int[] dim = readSize(file);
			int length = dim[0];
			int width = dim[1];
			assertCells(name, mod, length, width);

			int[] before = fabricAtClick(0, 0, 0, length, width);
			int[] after = forgeAtClick(mod, length, width);
			boolean originSame = Arrays.equals(before, after);
			if (originSame) {
				originAlready++;
			} else if (mod[0] == 0 && mod[1] == 0 && mod[2] == 0) {
				originFixedCenterOnly++;
			} else {
				originFixedWithModifier++;
				changedRows.add(row(name, mod, before, after, length, width));
			}
		}

		check("no-schematic set", missing.equals(NO_SCHEMATIC), "got " + missing);
		check("origin already matched is zero", originAlready == 0, "got " + originAlready);
		check("center-only fixes", originFixedCenterOnly == 663, "got " + originFixedCenterOnly);
		check("modifier-and-center fixes", originFixedWithModifier == 164, "got " + originFixedWithModifier);
		check("modifier already 0,0,0", modifierAlready == 663, "got " + modifierAlready);
		check("nonzero modifiers copied", modifierFixed == 164, "got " + modifierFixed);

		assertAlgebra();
		assertShowcase(structsDir);
		assertLivesUnchanged(registry);
		assertCallSites(structureBlock, schematic, liveTicker, staticSpawn, commands);

		// Schematics with no Forge BlockStructure (clouds, OtherLighthouse) still use mod 0,0,0
		// and the static anchor. Identity must hold for their real sizes.
		for (String extra : List.of("cloud1", "cloud2", "cloud3", "cloud4", "cloud5", "cloud6", "OtherLighthouse")) {
			check("extra registered " + extra, registry.containsKey(extra), "missing");
			int[] mod = registry.get(extra);
			check("extra mod 0 " + extra, mod != null && mod[0] == 0 && mod[1] == 0 && mod[2] == 0,
				Arrays.toString(mod));
			int[] dim = readSize(structsDir.resolve(extra + ".structure"));
			assertCells(extra, mod, dim[0], dim[1]);
		}

		writeReport(changedRows, originFixedCenterOnly, originFixedWithModifier, modifierAlready, modifierFixed);
		System.out.println("Static spawn parity: modifier already " + modifierAlready
			+ ", modifier fixed " + modifierFixed
			+ ", origin already " + originAlready
			+ ", origin fixed by centering only " + originFixedCenterOnly
			+ ", origin fixed by modifier+centering " + originFixedWithModifier
			+ ", no schematic " + missing.size());
		if (failures > 0) {
			System.out.println("FAILED " + failures);
			System.exit(1);
		}
		System.out.println("PASSED");
	}

	private static void assertCells(String name, int[] mod, int length, int width) {
		int[][] locals = {{0, 0, 0}, {1, 2, 3}, {Math.max(0, length - 1), 4, Math.max(0, width - 1)}};
		int[][] clicks = {{CLICK_X, CLICK_Y, CLICK_Z}, {500, 80, 200}, {-4, 3, -9}};
		for (int[] click : clicks) {
			int anchorX = SpawnOrigin.staticAnchorX(click[0], mod[0], length);
			int anchorY = SpawnOrigin.staticAnchorY(click[1], mod[1]);
			int anchorZ = SpawnOrigin.staticAnchorZ(click[2], mod[2], width);
			for (int[] local : locals) {
				int fx = SpawnOrigin.forgeWorldX(click[0], mod[0], length, local[0]);
				int fy = SpawnOrigin.forgeWorldY(click[1], mod[1], local[1]);
				int fz = SpawnOrigin.forgeWorldZ(click[2], mod[2], width, local[2]);
				int ax = SpawnOrigin.fabricWorldX(anchorX, length, local[0]);
				int ay = SpawnOrigin.fabricWorldY(anchorY, local[1]);
				int az = SpawnOrigin.fabricWorldZ(anchorZ, width, local[2]);
				if (fx != ax || fy != ay || fz != az) {
					fail(name + " cell " + Arrays.toString(local) + " forge " + fx + "," + fy + "," + fz
						+ " fabric " + ax + "," + ay + "," + az);
				}
			}
		}
	}

	private static void assertAlgebra() {
		int[] clicks = {-8, 0, 1, 500};
		int[] mods = {-3, 0, 1, 19, 107};
		int[] sizes = {1, 2, 7, 8, 9, 15, 16, 27, 147, 217};
		for (int click : clicks) {
			for (int mod : mods) {
				for (int size : sizes) {
					int anchor = SpawnOrigin.staticAnchorX(click, mod, size);
					for (int local : new int[] {0, 1, size - 1}) {
						int forge = SpawnOrigin.forgeWorldX(click, mod, size, local);
						int fabric = SpawnOrigin.fabricWorldX(anchor, size, local);
						check("algebra x size " + size + " mod " + mod + " local " + local,
							forge == fabric, forge + " vs " + fabric);
						int anchorZ = SpawnOrigin.staticAnchorZ(click, mod, size);
						int forgeZ = SpawnOrigin.forgeWorldZ(click, mod, size, local);
						int fabricZ = SpawnOrigin.fabricWorldZ(anchorZ, size, local);
						check("algebra z", forgeZ == fabricZ, forgeZ + " vs " + fabricZ);
					}
					check("y", SpawnOrigin.forgeWorldY(click, mod, 5) == SpawnOrigin.fabricWorldY(
						SpawnOrigin.staticAnchorY(click, mod), 5), "y drift");
				}
			}
		}
	}

	private static void assertShowcase(Path structsDir) throws IOException {
		// Click (0, 64, 0). Before = old Fabric: mod 0,0,0 and one half-size.
		expectOrigin(structsDir, "BlockHouse", new int[] {0, 0, 0}, new int[] {-2, 64, -3}, new int[] {-5, 64, -7});
		expectOrigin(structsDir, "BlockCastleTower", new int[] {19, -1, 27}, new int[] {-12, 64, -14}, new int[] {-6, 63, -2});
		expectOrigin(structsDir, "WoodenHouse", new int[] {13, -1, 5}, new int[] {-6, 64, -5}, new int[] {0, 63, -6});
		expectOrigin(structsDir, "BlockStadium", new int[] {107, -1, 73}, new int[] {-107, 64, -72}, new int[] {-108, 63, -72});
		expectOrigin(structsDir, "BlockBoat", new int[] {0, -1, 0}, new int[] {-3, 64, -13}, new int[] {-7, 63, -27});
		expectOrigin(structsDir, "BlockPlane", new int[] {26, 15, 19}, new int[] {-19, 64, -18}, new int[] {-13, 79, -18});
	}

	private static void expectOrigin(Path structsDir, String name, int[] mod, int[] before, int[] after) throws IOException {
		int[] dim = readSize(structsDir.resolve(name + ".structure"));
		int[] gotBefore = fabricAtClick(0, 0, 0, dim[0], dim[1]);
		int[] gotAfter = forgeAtClick(mod, dim[0], dim[1]);
		check(name + " before " + Arrays.toString(dim), Arrays.equals(before, gotBefore),
			"got " + Arrays.toString(gotBefore));
		check(name + " after", Arrays.equals(after, gotAfter), "got " + Arrays.toString(gotAfter));
	}

	private static void assertLivesUnchanged(Map<String, int[]> registry) {
		for (Map.Entry<String, int[]> entry : LIVE_SHELLS_UNCHANGED.entrySet()) {
			int[] got = registry.get(entry.getKey());
			int[] want = entry.getValue();
			check("live shell left alone " + entry.getKey(),
				got != null && got[0] == want[0] && got[1] == want[1] && got[2] == want[2],
				"got " + Arrays.toString(got));
		}
		// BlockPlane is static and must not inherit LivePlane's Y.
		int[] plane = registry.get("BlockPlane");
		check("BlockPlane not LivePlane", plane != null && plane[0] == 26 && plane[1] == 15 && plane[2] == 19,
			Arrays.toString(plane));
	}

	private static void assertCallSites(Path structureBlock, Path schematic, Path liveTicker,
			Path staticSpawn, Path commands) throws IOException {
		String block = Files.readString(structureBlock);
		String process = Files.readString(schematic);
		String live = Files.readString(liveTicker);
		String spawn = Files.readString(staticSpawn);
		String cmd = Files.readString(commands);
		check("creative static uses staticProcessAnchor", block.contains("structure.staticProcessAnchor("), "missing");
		check("live click still offsets by block modifier only",
			block.contains("BlockPos spawnPos = pos.offset(modX, modY, modZ);"), "live anchor changed");
		int processStart = process.indexOf(
			"public void process(ServerLevel world, int posX, int posY, int posZ, boolean replaceAir)");
		int processEnd = process.indexOf("public java.util.List<BlockPos> showOutline", processStart);
		String processMethod = processStart >= 0 && processEnd > processStart
			? process.substring(processStart, processEnd) : "";
		check("process keeps single half", processMethod.contains("SpawnOrigin.fabricOriginX(posX, length)"),
			"process formula moved");
		check("process does not static-shift", !processMethod.contains("staticAnchor"),
			"process folded in the second half");
		check("shell stays on click anchor",
			live.contains("shell.process(world, origin.getX(), origin.getY(), origin.getZ());"), "shell call changed");
		check("frame stays on click anchor",
			live.contains("structure.process(world, origin.getX(), origin.getY(), origin.getZ());"), "frame call changed");
		check("live ticker does not static-shift", !live.contains("staticProcessAnchor("), "live path calls static anchor");
		check("command uses StaticSpawn", cmd.contains("StaticSpawn.placeAnchor"), "command bypass");
		check("static spawn skips lives", spawn.contains("LiveStructureTicker.isAnimatedLive"), "live guard missing");
	}

	private static int[] fabricAtClick(int modX, int modY, int modZ, int length, int width) {
		// Historical Fabric: registry modifier was 0,0,0 and process used one half-size.
		// Callers now pass staticAnchor; this is the pre-fix origin at local (0,0,0).
		return new int[] {
			SpawnOrigin.fabricWorldX(CLICK_X + modX, length, 0),
			SpawnOrigin.fabricWorldY(CLICK_Y + modY, 0),
			SpawnOrigin.fabricWorldZ(CLICK_Z + modZ, width, 0)
		};
	}

	private static int[] forgeAtClick(int[] mod, int length, int width) {
		return new int[] {
			SpawnOrigin.forgeWorldX(CLICK_X, mod[0], length, 0),
			SpawnOrigin.forgeWorldY(CLICK_Y, mod[1], 0),
			SpawnOrigin.forgeWorldZ(CLICK_Z, mod[2], width, 0)
		};
	}

	private static String row(String name, int[] mod, int[] before, int[] after, int length, int width) {
		return "| " + name + " | " + mod[0] + ", " + mod[1] + ", " + mod[2]
			+ " | " + before[0] + ", " + before[1] + ", " + before[2]
			+ " | " + after[0] + ", " + after[1] + ", " + after[2]
			+ " | " + (after[0] - before[0]) + ", " + (after[1] - before[1]) + ", " + (after[2] - before[2])
			+ " | " + length + "×" + width + " |";
	}

	private static void writeReport(List<String> changedRows, int centerOnly, int withMod,
			int modifierAlready, int modifierFixed) throws IOException {
		Path outDir = Path.of("/opt/cursor/artifacts");
		if (!Files.isDirectory(outDir)) {
			outDir = Path.of("parity-dumps");
			Files.createDirectories(outDir);
		}
		Path out = outDir.resolve("static-spawn-parity.md");
		StringBuilder sb = new StringBuilder();
		sb.append("# Static spawn parity\n\n");
		sb.append("Click `(0, 64, 0)`. Origin is schematic local `(0,0,0)`.\n\n");
		sb.append("- Modifier triples already equal to Forge: ").append(modifierAlready).append('\n');
		sb.append("- Modifier triples corrected: ").append(modifierFixed).append('\n');
		sb.append("- World origins already equal to Forge: 0\n");
		sb.append("- Origins fixed by centering only (modifier was already 0,0,0): ").append(centerOnly).append('\n');
		sb.append("- Origins fixed by modifier and centering: ").append(withMod).append('\n');
		sb.append("- Forge classes with no schematic (not registered): ").append(NO_SCHEMATIC.size()).append('\n');
		sb.append("\nCentering-only shift of local `(0,0,0)` is `(-length/2, 0, -width/2)` ");
		sb.append("where length is schematic Width and width is schematic Length.\n\n");
		sb.append("| Structure | Forge mod | Fabric before | Fabric after | Delta | X×Z |\n");
		sb.append("|---|---|---|---|---|---|\n");
		for (String row : changedRows) {
			sb.append(row).append('\n');
		}
		Files.writeString(out, sb.toString());
		System.out.println("Wrote " + out.toAbsolutePath());
	}

	private static Map<String, int[]> parseForge(Path dir) throws IOException {
		Pattern pat = Pattern.compile(
			"super\\(\\s*\"([^\"]+)\"\\s*,\\s*(true|false)\\s*,\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\)",
			Pattern.DOTALL);
		Map<String, int[]> out = new LinkedHashMap<>();
		try (var files = Files.list(dir)) {
			for (Path file : files.filter(p -> p.toString().endsWith(".java")).toList()) {
				String text = Files.readString(file);
				if (!text.contains("extends BlockStructure")) {
					continue;
				}
				Matcher m = pat.matcher(text);
				if (!m.find()) {
					fail("unparsed forge " + file.getFileName());
					continue;
				}
				out.put(m.group(1), new int[] {
					Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5))
				});
			}
		}
		return out;
	}

	private static Map<String, int[]> parseRegistry(String text) {
		Pattern pat = Pattern.compile(
			"registerStructureBlock\\(\\s*\"[^\"]+\"\\s*,\\s*\"([^\"]+)\"\\s*,\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*,\\s*(-?\\d+)");
		Map<String, int[]> out = new LinkedHashMap<>();
		Matcher m = pat.matcher(text);
		while (m.find()) {
			out.put(m.group(1), new int[] {
				Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))
			});
		}
		return out;
	}

	/** @return Width (X length), Length (Z width), Height */
	private static int[] readSize(Path file) throws IOException {
		byte[] data;
		try (InputStream in = new GZIPInputStream(Files.newInputStream(file))) {
			data = in.readAllBytes();
		}
		if (data.length < 3 || (data[0] & 0xFF) != 10) {
			throw new IOException("not a compound " + file);
		}
		int nameLen = ((data[1] & 0xFF) << 8) | (data[2] & 0xFF);
		int i = 3 + nameLen;
		int width = Integer.MIN_VALUE;
		int length = Integer.MIN_VALUE;
		int height = Integer.MIN_VALUE;
		while (i < data.length) {
			int tag = data[i++] & 0xFF;
			if (tag == 0) {
				break;
			}
			int nlen = ((data[i] & 0xFF) << 8) | (data[i + 1] & 0xFF);
			i += 2;
			String name = new String(data, i, nlen, StandardCharsets.UTF_8);
			i += nlen;
			if (tag == 2) {
				int v = (short) (((data[i] & 0xFF) << 8) | (data[i + 1] & 0xFF));
				i += 2;
				if ("Width".equals(name)) {
					width = v;
				} else if ("Length".equals(name)) {
					length = v;
				} else if ("Height".equals(name)) {
					height = v;
				}
				if (width != Integer.MIN_VALUE && length != Integer.MIN_VALUE && height != Integer.MIN_VALUE) {
					return new int[] {width, length, height};
				}
				continue;
			}
			i = skip(data, i, tag);
		}
		throw new IOException("no size in " + file);
	}

	private static int skip(byte[] data, int i, int tag) {
		return switch (tag) {
			case 1 -> i + 1;
			case 2 -> i + 2;
			case 3, 5 -> i + 4;
			case 4, 6 -> i + 8;
			case 7 -> i + 4 + readInt(data, i);
			case 8 -> i + 2 + (((data[i] & 0xFF) << 8) | (data[i + 1] & 0xFF));
			case 9 -> {
				int listType = data[i++] & 0xFF;
				int n = readInt(data, i);
				i += 4;
				for (int k = 0; k < n; k++) {
					i = skip(data, i, listType);
				}
				yield i;
			}
			case 10 -> {
				while (true) {
					int t = data[i++] & 0xFF;
					if (t == 0) {
						yield i;
					}
					int nlen = ((data[i] & 0xFF) << 8) | (data[i + 1] & 0xFF);
					i += 2 + nlen;
					i = skip(data, i, t);
				}
			}
			case 11 -> i + 4 + 4 * readInt(data, i);
			default -> throw new IllegalStateException("tag " + tag);
		};
	}

	private static int readInt(byte[] data, int i) {
		return ((data[i] & 0xFF) << 24) | ((data[i + 1] & 0xFF) << 16)
			| ((data[i + 2] & 0xFF) << 8) | (data[i + 3] & 0xFF);
	}

	private static Path find(String... candidates) {
		Path cwd = Path.of("").toAbsolutePath();
		for (int up = 0; up < 4 && cwd != null; up++) {
			for (String candidate : candidates) {
				Path p = cwd.resolve(candidate);
				if (Files.exists(p)) {
					return p;
				}
			}
			cwd = cwd.getParent();
		}
		throw new IllegalStateException("none of " + Arrays.toString(candidates) + " from " + Path.of("").toAbsolutePath());
	}

	private static void check(String name, boolean ok, String detail) {
		if (!ok) {
			fail(name + " — " + detail);
		}
	}

	private static void fail(String message) {
		failures++;
		System.out.println("FAIL " + message);
	}
}
