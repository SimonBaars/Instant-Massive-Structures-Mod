package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.structureloader.SchematicStructure;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.AABB;

/** Exercises real world placement, block entity lifecycle, and subsequent server ticks. */
public final class StructureSpawnGameTest {
	@GameTest(structure = "imsm_spawn_tests:house", maxTicks = 80)
	public void analogOutputNotifiesOutsideComparator(GameTestHelper test) {
		SchematicStructure structure = load("SpawnMetadataRegression");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		ServerLevel level = test.getLevel();
		BlockPos comparator = origin.offset(11, 1, 7);
		for (int x = 10; x <= 12; x++) level.setBlock(origin.offset(x, 0, 7), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(origin.offset(10, 1, 7), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(comparator, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.FACING, Direction.WEST), Block.UPDATE_ALL);
		place(structure, level, origin);
		test.runAfterDelay(20, () -> {
			assertEqual(test, 3, level.getSignal(comparator, Direction.WEST),
				"A comparator outside the structure reads the newly filled cauldron through a solid block");
			BlockPos anchor = processAnchor(structure, origin);
			SchematicStructure.clearBounds(level, anchor.getX(), anchor.getY(), anchor.getZ(),
				structure.getLength(), structure.getHeight(), structure.getWidth());
			test.runAfterDelay(20, () -> {
				assertEqual(test, 0, level.getSignal(comparator, Direction.WEST),
					"Clearing the analog source updates the outside comparator");
				test.succeed();
			});
		});
	}

	@GameTest(structure = "imsm_spawn_tests:house", maxTicks = 250)
	public void hangingEntitiesTranslateAndAvoidDuplicates(GameTestHelper test) {
		SchematicStructure structure = load("SpawnMetadataRegression");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		place(structure, test.getLevel(), origin);
		assertHangingEntities(test, origin);
		test.runAfterDelay(20, () -> {
			place(structure, test.getLevel(), origin);
			assertHangingEntities(test, origin);
			test.runAfterDelay(100, () -> {
				assertHangingEntities(test, origin);
				assertNoDrops(test, structure, origin);
				BlockPos anchor = processAnchor(structure, origin);
				SchematicStructure.clearBounds(test.getLevel(), anchor.getX(), anchor.getY(), anchor.getZ(),
					structure.getLength(), structure.getHeight(), structure.getWidth());
				test.runAfterDelay(100, () -> {
					AABB bounds = new AABB(origin.getX(), origin.getY(), origin.getZ(),
						origin.getX() + 10, origin.getY() + 5, origin.getZ() + 10);
					test.assertTrue(test.getLevel().getEntitiesOfClass(ItemFrame.class, bounds).isEmpty()
						&& test.getLevel().getEntitiesOfClass(Painting.class, bounds).isEmpty(),
						"Clearing structure bounds removes restored hanging decorations");
					assertNoDrops(test, structure, origin);
					test.succeed();
				});
			});
		});
	}

	private static void assertHangingEntities(GameTestHelper test, BlockPos origin) {
		AABB bounds = new AABB(origin.getX(), origin.getY(), origin.getZ(),
			origin.getX() + 10, origin.getY() + 5, origin.getZ() + 10);
		var frames = test.getLevel().getEntitiesOfClass(ItemFrame.class, bounds);
		var paintings = test.getLevel().getEntitiesOfClass(Painting.class, bounds);
		assertEqual(test, 1, frames.size(), "Exactly one item frame is restored");
		assertEqual(test, 1, paintings.size(), "Exactly one painting is restored");
		ItemFrame frame = frames.getFirst();
		assertEqual(test, origin.offset(8, 1, 6), frame.getPos(), "Frame anchor translates absolute legacy TileXYZ by WEOrigin");
		assertEqual(test, Direction.SOUTH, frame.getDirection(), "Frame facing is retained");
		test.assertTrue(Math.abs(frame.getX() - (origin.getX() + 8.5)) < 0.000001
			&& Math.abs(frame.getY() - (origin.getY() + 1.5)) < 0.000001
			&& Math.abs(frame.getZ() - (origin.getZ() + 6.03125)) < 0.000001,
			"Frame fractional world position translates from the schematic origin");
		test.assertTrue(frame.getItem().is(Items.DIAMOND) && frame.getItem().getCount() == 1,
			"Frame's displayed legacy item is converted and retained");
		assertEqual(test, 3, frame.getRotation(), "Frame's displayed item rotation is retained");
		Painting painting = paintings.getFirst();
		assertEqual(test, origin.offset(8, 3, 6), painting.getPos(), "Painting anchor translates legacy TileXYZ by WEOrigin");
		assertEqual(test, Direction.SOUTH, painting.getDirection(), "Painting facing is retained");
		assertEqual(test, "minecraft:kebab", painting.getVariant().value().assetId().toString(),
			"Painting artwork is migrated from its legacy Motive");
	}

	@GameTest(structure = "imsm_spawn_tests:house", maxTicks = 100)
	public void overlayKeepsInventoryAndClearingDoesNotDrop(GameTestHelper test) {
		SchematicStructure structure = load("BlockHouse");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		place(structure, test.getLevel(), origin);
		BlockPos extraPos = origin.offset(0, 7, 0);
		assertEqual(test, 0, structure.getLegacyId(0, 7, 0), "Overlay fixture cell is schematic air");
		test.getLevel().setBlock(extraPos, Blocks.CHEST.defaultBlockState(), Block.UPDATE_ALL);
		Container existing = container(test, test.getLevel(), extraPos);
		existing.setItem(0, new net.minecraft.world.item.ItemStack(Items.DIAMOND, 29));
		BlockPos anchor = processAnchor(structure, origin);
		structure.process(test.getLevel(), anchor.getX(), anchor.getY(), anchor.getZ(), false);
		test.assertTrue(existing == test.getLevel().getBlockEntity(extraPos), "Air overlay keeps the original container entity");
		assertItem(test, existing, 0, Items.DIAMOND, 29);
		assertHouse(test, origin);
		assertNoDrops(test, structure, origin);
		test.runAfterDelay(20, () -> {
			SchematicStructure.clearBounds(test.getLevel(), anchor.getX(), anchor.getY(), anchor.getZ(),
				structure.getLength(), structure.getHeight(), structure.getWidth());
			for (int y = 0; y < structure.getHeight(); y++) for (int z = 0; z < structure.getWidth(); z++) {
				for (int x = 0; x < structure.getLength(); x++) test.assertTrue(
					test.getLevel().getBlockState(origin.offset(x, y, z)).isAir(), "Cleared bounds contain only air");
			}
			assertNoDrops(test, structure, origin);
			test.runAfterDelay(20, () -> {
				assertNoDrops(test, structure, origin);
				test.succeed();
			});
		});
	}

	@GameTest(structure = "imsm_spawn_tests:house", maxTicks = 60)
	public void coupledMetadataAndFormerTileStates(GameTestHelper test) {
		SchematicStructure structure = load("SpawnMetadataRegression");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		place(structure, test.getLevel(), origin);
		assertMetadataFixture(test, structure, origin);
		test.runAfterDelay(20, () -> {
			assertMetadataFixture(test, structure, origin);
			assertNoDrops(test, structure, origin);
			test.succeed();
		});
	}

	private static void assertMetadataFixture(GameTestHelper test, SchematicStructure structure, BlockPos origin) {
		ServerLevel level = test.getLevel();
		for (int index = 0; index < 4; index++) {
			int x = 1 + 2 * index;
			for (int y = 1; y <= 2; y++) {
				BlockState state = level.getBlockState(origin.offset(x, y, 1));
				test.assertTrue(state.is(Blocks.OAK_DOOR), "Every fixture door half survives");
				assertEqual(test, new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}[index],
					state.getValue(DoorBlock.FACING), "All four door facing values combine across halves");
				assertEqual(test, index % 2 != 0 ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT,
					state.getValue(DoorBlock.HINGE), "Door hinge combines across halves");
				assertEqual(test, index % 2 != 0, state.getValue(DoorBlock.OPEN), "Door open combines across halves");
				assertEqual(test, index >= 2, state.getValue(DoorBlock.POWERED), "Door power combines across halves");
			}
		}
		BlockState note = level.getBlockState(origin.offset(1, 1, 3));
		test.assertTrue(note.is(Blocks.NOTE_BLOCK), "Note block remains present");
		assertEqual(test, 17, note.getValue(NoteBlock.NOTE), "Legacy Music.note becomes the note block property");
		BlockState skull = level.getBlockState(origin.offset(3, 1, 3));
		test.assertTrue(skull.is(Blocks.CREEPER_HEAD), "Legacy SkullType becomes the right skull block");
		assertEqual(test, 7, skull.getValue(SkullBlock.ROTATION), "Legacy skull rotation is preserved");
		test.assertTrue(level.getBlockState(origin.offset(5, 1, 3)).is(Blocks.POTTED_SPRUCE_SAPLING),
			"Legacy flower pot Item/Data determines its potted plant variant");
		BlockPos bannerPos = origin.offset(7, 1, 3);
		BlockState banner = level.getBlockState(bannerPos);
		test.assertTrue(banner.is(Blocks.BANNER.red()), "Legacy banner Base determines its block color");
		assertEqual(test, 3, banner.getValue(BannerBlock.ROTATION), "Banner rotation preserved");
		var entity = level.getBlockEntity(bannerPos);
		test.assertTrue(entity instanceof BannerBlockEntity, "Banner keeps its block entity");
		var bannerData = entity.saveWithFullMetadata(level.registryAccess());
		assertEqual(test, 1, bannerData.getList("patterns").orElseThrow().size(), "Legacy banner patterns load into attached entity");
		for (int y = 1; y <= 2; y++) {
			BlockState plant = level.getBlockState(origin.offset(1, y, 5));
			test.assertTrue(plant.is(Blocks.LILAC), "Upper tall plant copies species from lower metadata");
			assertEqual(test, y == 1 ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER,
				plant.getValue(net.minecraft.world.level.block.DoublePlantBlock.HALF), "Tall plant halves remain paired");
		}
		assertEqual(test, BedPart.FOOT, level.getBlockState(origin.offset(3, 1, 5)).getValue(BedBlock.PART), "Bed foot survives");
		assertEqual(test, BedPart.HEAD, level.getBlockState(origin.offset(3, 1, 6)).getValue(BedBlock.PART), "Bed head survives");
		for (int x = 1; x <= 3; x++) {
			BlockState pane = level.getBlockState(origin.offset(x, 1, 8));
			test.assertTrue(pane.is(Blocks.GLASS_PANE) && pane.getValue(IronBarsBlock.EAST)
				&& pane.getValue(IronBarsBlock.WEST), "Pane chain joins its complete supports on both sides");
		}
	}

	@GameTest(structure = "imsm_spawn_tests:house", maxTicks = 60)
	public void dungeonSpawnerLoadsAttachedEntity(GameTestHelper test) {
		SchematicStructure structure = load("BlockDungeon");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		place(structure, test.getLevel(), origin);
		assertSpawner(test, origin);
		test.runAfterDelay(20, () -> {
			assertSpawner(test, origin);
			assertNoDrops(test, structure, origin);
			test.succeed();
		});
	}

	@GameTest(structure = "imsm_spawn_tests:house", maxTicks = 60)
	public void sleighSignDecodesLegacyJsonText(GameTestHelper test) {
		SchematicStructure structure = load("ChristmasSleigh");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		place(structure, test.getLevel(), origin);
		assertSleighSign(test, origin);
		test.runAfterDelay(20, () -> {
			assertSleighSign(test, origin);
			assertNoDrops(test, structure, origin);
			test.succeed();
		});
	}

	private static void assertSpawner(GameTestHelper test, BlockPos origin) {
		var entity = test.getLevel().getBlockEntity(origin.offset(4, 1, 4));
		test.assertTrue(entity instanceof SpawnerBlockEntity, "Dungeon keeps its attached spawner entity");
		var data = entity.saveWithFullMetadata(test.getLevel().registryAccess());
		// The default newly-created spawner has Delay=20; this schematic explicitly stores 0.
		assertEqual(test, (short) 0, data.getShort("Delay").orElseThrow(),
			"Legacy spawner NBT loads into the instance attached to the world");
		String entityId = data.getCompound("SpawnData").orElseThrow().getCompound("entity")
			.orElseThrow().getString("id").orElseThrow();
		assertEqual(test, "minecraft:pig", entityId, "Legacy spawner entity id is migrated");
	}

	private static void assertSleighSign(GameTestHelper test, BlockPos origin) {
		var entity = test.getLevel().getBlockEntity(origin.offset(2, 4, 2));
		test.assertTrue(entity instanceof SignBlockEntity, "Sleigh sign keeps its attached block entity");
		var text = ((SignBlockEntity) entity).getFrontText();
		assertEqual(test, "Santa's Sack", text.getMessage(0, false).getString(),
			"Legacy JSON sign text is decoded, including Unicode escapes");
		for (int i = 1; i < 4; i++) assertEqual(test, "", text.getMessage(i, false).getString(),
			"Legacy null sign line is empty");
	}

	@GameTest(structure = "imsm_spawn_tests:house", maxTicks = 100)
	public void houseInventoriesSurviveRespawn(GameTestHelper test) {
		SchematicStructure structure = load("BlockHouse");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		place(structure, test.getLevel(), origin);
		assertHouse(test, origin);
		assertNoDrops(test, structure, origin);
		test.runAfterDelay(20, () -> {
			assertHouse(test, origin);
			assertNoDrops(test, structure, origin);
			// A second spawn replaces existing, populated block entities in the same cells.
			place(structure, test.getLevel(), origin);
			assertHouse(test, origin);
			assertNoDrops(test, structure, origin);
			test.runAfterDelay(20, () -> {
				assertHouse(test, origin);
				assertNoDrops(test, structure, origin);
				test.succeed();
			});
		});
	}

	@GameTest(structure = "imsm_spawn_tests:wooden_house", maxTicks = 100)
	public void woodenHouseMetadataSurvivesTicks(GameTestHelper test) {
		SchematicStructure structure = load("WoodenHouse");
		BlockPos origin = test.absolutePos(new BlockPos(3, 3, 3));
		place(structure, test.getLevel(), origin);
		assertWoodenHouse(test, structure, origin);
		test.runAfterDelay(20, () -> {
			assertWoodenHouse(test, structure, origin);
			assertNoDrops(test, structure, origin);
			place(structure, test.getLevel(), origin);
			test.runAfterDelay(20, () -> {
				assertWoodenHouse(test, structure, origin);
				assertNoDrops(test, structure, origin);
				test.succeed();
			});
		});
	}

	private static SchematicStructure load(String name) {
		try {
			SchematicStructure structure = new SchematicStructure(name);
			structure.readFromFile();
			return structure;
		} catch (Exception e) {
			throw new AssertionError("Unable to load regression schematic " + name, e);
		}
	}

	private static void place(SchematicStructure structure, ServerLevel level, BlockPos origin) {
		// Support the original schematics' bottom layer in the otherwise empty test world.
		for (int x = -1; x <= structure.getLength(); x++) {
			for (int z = -1; z <= structure.getWidth(); z++) {
				level.setBlock(origin.offset(x, -1, z), Blocks.STONE.defaultBlockState(), Block.UPDATE_CLIENTS);
			}
		}
		BlockPos anchor = processAnchor(structure, origin);
		structure.process(level, anchor.getX(), anchor.getY(), anchor.getZ());
	}

	private static BlockPos processAnchor(SchematicStructure structure, BlockPos origin) {
		return origin.offset(structure.getLength() / 2 - 1, 0, structure.getWidth() / 2 - 1);
	}

	private static void assertHouse(GameTestHelper test, BlockPos origin) {
		ServerLevel level = test.getLevel();
		BlockPos leftPos = origin.offset(2, 0, 7);
		BlockPos rightPos = origin.offset(3, 0, 7);
		BlockState left = level.getBlockState(leftPos);
		BlockState right = level.getBlockState(rightPos);
		test.assertTrue(left.is(Blocks.CHEST) && right.is(Blocks.CHEST), "House keeps both chest blocks");
		test.assertTrue(left.getValue(ChestBlock.TYPE) != ChestType.SINGLE,
			"House chest is joined after placement");
		test.assertTrue(right.getValue(ChestBlock.TYPE) != ChestType.SINGLE
			&& left.getValue(ChestBlock.TYPE) != right.getValue(ChestBlock.TYPE),
			"House has matching left/right double chest halves");
		test.assertTrue(left.getValue(ChestBlock.FACING) == right.getValue(ChestBlock.FACING),
			"Double chest halves retain matching facing");
		Container first = container(test, level, leftPos);
		Container second = container(test, level, rightPos);
		assertItem(test, first, 5, Items.MUSHROOM_STEW, 1);
		assertItem(test, first, 17, Items.MUSHROOM_STEW, 1);
		assertItem(test, first, 25, Items.STICK, 1);
		assertItem(test, first, 26, Items.PAINTING, 1);
		assertItem(test, second, 5, Items.OAK_SAPLING, 1);
		assertItem(test, second, 15, Items.OAK_SAPLING, 1);
		assertEqual(test, 4, occupiedSlots(first), "First House chest occupied slots");
		assertEqual(test, 2, occupiedSlots(second), "Second House chest occupied slots");
	}

	private static Container container(GameTestHelper test, ServerLevel level, BlockPos pos) {
		var entity = level.getBlockEntity(pos);
		test.assertTrue(entity instanceof Container, "Chest has its attached container block entity at " + pos);
		return (Container) entity;
	}

	private static void assertItem(GameTestHelper test, Container container, int slot, Item item, int count) {
		var stack = container.getItem(slot);
		test.assertTrue(stack.is(item) && stack.getCount() == count,
			"Container slot " + slot + " retains " + item + " x" + count + ", found " + stack);
	}

	private static int occupiedSlots(Container container) {
		int count = 0;
		for (int i = 0; i < container.getContainerSize(); i++) if (!container.getItem(i).isEmpty()) count++;
		return count;
	}

	private static void assertWoodenHouse(GameTestHelper test, SchematicStructure structure, BlockPos origin) {
		ServerLevel level = test.getLevel();
		int panes = 0, doors = 0, stairs = 0, torches = 0, fences = 0;
		for (int y = 0; y < structure.getHeight(); y++) {
			for (int z = 0; z < structure.getWidth(); z++) {
				for (int x = 0; x < structure.getLength(); x++) {
					int id = structure.getLegacyId(x, y, z);
					int meta = structure.getLegacyMeta(x, y, z);
					BlockPos pos = origin.offset(x, y, z);
					BlockState state = level.getBlockState(pos);
					if (id == 102 || id == 85) {
						test.assertTrue(state.is(id == 102 ? Blocks.GLASS_PANE : Blocks.OAK_FENCE),
							"Connectable block remains present at " + pos);
						assertEqual(test, state, Block.updateFromNeighbourShapes(state, level, pos),
							"Pane/fence connections reflect completed neighbors at " + pos);
						if (id == 102) {
							panes++;
							test.assertTrue(state.getValue(IronBarsBlock.NORTH) || state.getValue(IronBarsBlock.SOUTH)
								|| state.getValue(IronBarsBlock.EAST) || state.getValue(IronBarsBlock.WEST),
								"House window pane has a real connection at " + pos);
						} else fences++;
					} else if (id == 64) {
						doors++;
						boolean upper = (meta & 8) != 0;
						int lowerMeta = upper ? structure.getLegacyMeta(x, y - 1, z) : meta;
						int upperMeta = upper ? meta : structure.getLegacyMeta(x, y + 1, z);
						test.assertTrue(state.is(Blocks.OAK_DOOR), "Door half survives placement at " + pos);
						assertEqual(test, upper ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER,
							state.getValue(DoorBlock.HALF), "Door half at " + pos);
						assertEqual(test, new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}[lowerMeta & 3],
							state.getValue(DoorBlock.FACING), "Door facing from lower metadata at " + pos);
						assertEqual(test, (upperMeta & 1) != 0 ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT,
							state.getValue(DoorBlock.HINGE), "Door hinge from upper metadata at " + pos);
						assertEqual(test, (lowerMeta & 4) != 0, state.getValue(DoorBlock.OPEN), "Door open at " + pos);
					} else if (id == 53) {
						stairs++;
						test.assertTrue(state.is(Blocks.OAK_STAIRS), "Stairs survive placement at " + pos);
						assertEqual(test, new Direction[]{Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH}[meta & 3],
							state.getValue(StairBlock.FACING), "Stair facing at " + pos);
						assertEqual(test, (meta & 4) != 0 ? Half.TOP : Half.BOTTOM,
							state.getValue(StairBlock.HALF), "Stair half at " + pos);
						assertEqual(test, state, Block.updateFromNeighbourShapes(state, level, pos),
							"Stair corner shape reflects completed neighbors at " + pos);
					} else if (id == 50) {
						torches++;
						test.assertTrue(state.is(meta >= 1 && meta <= 4 ? Blocks.WALL_TORCH : Blocks.TORCH),
							"Torch keeps attachment type at " + pos);
						if (meta >= 1 && meta <= 4) assertEqual(test,
							new Direction[]{Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH}[meta - 1],
							state.getValue(WallTorchBlock.FACING), "Wall torch facing at " + pos);
					}
				}
			}
		}
		assertEqual(test, 25, panes, "WoodenHouse panes checked");
		assertEqual(test, 4, doors, "WoodenHouse door halves checked");
		assertEqual(test, 205, stairs, "WoodenHouse stairs checked");
		assertEqual(test, 15, torches, "WoodenHouse torches checked");
		assertEqual(test, 22, fences, "WoodenHouse fences checked");
	}

	private static <T> void assertEqual(GameTestHelper test, T expected, T actual, String message) {
		test.assertValueEqual(actual, expected, message);
	}

	private static void assertNoDrops(GameTestHelper test, SchematicStructure structure, BlockPos origin) {
		AABB bounds = new AABB(origin.getX(), origin.getY(), origin.getZ(),
			origin.getX() + structure.getLength(), origin.getY() + structure.getHeight(),
			origin.getZ() + structure.getWidth()).inflate(2);
		var drops = test.getLevel().getEntitiesOfClass(ItemEntity.class, bounds);
		test.assertTrue(drops.isEmpty(), "Structure placement creates no dropped item entities: " + drops);
	}
}
