package com.simonbaars.imsm.structureloader;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.google.gson.JsonParser;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.Container;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.TagValueInput;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class SchematicStructure {
	private final String fileName;
	/** Pre-flattening block ids, including the optional AddBlocks high bits. */
	private int[][][] legacyIds;
	private int[][][] blockData;
	/** Tile entities keyed by schematic-local position. */
	private Map<BlockPos, CompoundTag> tileEntities;
	private ListTag entities;
	private BlockPos schematicSourceOrigin;
	private int dataVersion;
	// Assemble without observing incomplete supports, firing redstone, or spilling inventories.
	private static final int ASSEMBLY_FLAGS = Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE
		| Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_SKIP_BLOCK_ENTITY_SIDEEFFECTS | Block.UPDATE_SKIP_ON_PLACE;
	private int length;
	private int height;
	private int width;

	public SchematicStructure(String fileName) {
		this.fileName = "/assets/imsm/structs/" + fileName + ".structure";
	}

	public void readFromFile() throws Exception {
		CompoundTag nbt;
		try (InputStream fileStream = SchematicStructure.class.getResourceAsStream(fileName)) {
			if (fileStream == null) throw new Exception("Structure file not found: " + fileName);
			try (DataInputStream dataStream = new DataInputStream(new GZIPInputStream(fileStream))) {
				nbt = NbtIo.read(dataStream);
			}
		}

		this.length = nbt.getShort("Width").orElse((short) 0);
		this.width = nbt.getShort("Length").orElse((short) 0);
		this.height = nbt.getShort("Height").orElse((short) 0);
		this.dataVersion = nbt.getInt("DataVersion").orElse(99);
		this.entities = nbt.getList("Entities").orElse(new ListTag());
		// WorldEdit stored entities in source-world coordinates. This translates entity NBT
		// to local coordinates and does not change the verified block spawn anchor.
		this.schematicSourceOrigin = new BlockPos(nbt.getInt("WEOriginX").orElse(0),
			nbt.getInt("WEOriginY").orElse(0), nbt.getInt("WEOriginZ").orElse(0));
		long volume = (long) length * width * height;
		byte[] blockIds = nbt.getByteArray("Blocks").orElse(new byte[0]);
		byte[] metadata = nbt.getByteArray("Data").orElse(new byte[0]);
		byte[] addBlocks = nbt.getByteArray("AddBlocks").orElse(new byte[0]);
		if (length <= 0 || width <= 0 || height <= 0 || volume != blockIds.length
				|| metadata.length != volume || (addBlocks.length != 0 && addBlocks.length != (volume + 1) / 2)) {
			throw new Exception("Invalid schematic dimensions or block arrays: " + fileName);
		}

		this.legacyIds = new int[height][width][length];
		this.blockData = new int[height][width][length];
		this.tileEntities = new HashMap<>();
		for (int i = 0; i < blockIds.length; i++) {
			int x = i % length;
			int z = (i / length) % width;
			int y = i / (length * width);
			int highBits = addBlocks.length == 0 ? 0 : (addBlocks[i / 2] >> ((i % 2) * 4)) & 15;
			legacyIds[y][z][x] = (blockIds[i] & 255) | (highBits << 8);
			blockData[y][z][x] = metadata[i] & 15;
		}

		ListTag tiles = nbt.getList("TileEntities").orElse(new ListTag());
		for (int i = 0; i < tiles.size(); i++) {
			CompoundTag te = tiles.getCompound(i).orElseThrow();
			BlockPos pos = new BlockPos(te.getInt("x").orElse(0), te.getInt("y").orElse(0), te.getInt("z").orElse(0));
			if (!contains(pos.getX(), pos.getY(), pos.getZ())) {
				throw new Exception("Tile entity outside schematic bounds: " + pos + " in " + fileName);
			}
			tileEntities.put(pos, te);
		}
		InstantMassiveStructures.LOGGER.debug("Loaded structure {} with dimensions {}x{}x{}, {} tile entities",
			fileName, length, height, width, tileEntities.size());
	}

	private boolean contains(int x, int y, int z) {
		return x >= 0 && x < length && y >= 0 && y < height && z >= 0 && z < width;
	}

	/** Resolve properties that legacy schematics split across multiple cells or tile entities. */
	public BlockState getBlockState(int x, int y, int z) {
		int id = legacyIds[y][z][x];
		int meta = blockData[y][z][x];
		BlockState state = LegacyBlockStates.fromLegacy(id, meta);
		if (state == null) return null;
		if (state.getBlock() instanceof DoorBlock) {
			int lowerY = (meta & 8) == 0 ? y : y - 1;
			if (contains(x, lowerY, z) && contains(x, lowerY + 1, z)
					&& legacyIds[lowerY][z][x] == id && legacyIds[lowerY + 1][z][x] == id
					&& (blockData[lowerY][z][x] & 8) == 0 && (blockData[lowerY + 1][z][x] & 8) != 0) {
				BlockState lower = LegacyBlockStates.fromLegacy(id, blockData[lowerY][z][x]);
				BlockState upper = LegacyBlockStates.fromLegacy(id, blockData[lowerY + 1][z][x]);
				state = state.setValue(DoorBlock.FACING, lower.getValue(DoorBlock.FACING))
					.setValue(DoorBlock.OPEN, lower.getValue(DoorBlock.OPEN))
					.setValue(DoorBlock.HINGE, upper.getValue(DoorBlock.HINGE))
					.setValue(DoorBlock.POWERED, upper.getValue(DoorBlock.POWERED));
			}
		} else if (id == 175 && (meta & 8) != 0 && y > 0 && legacyIds[y - 1][z][x] == id) {
			state = LegacyBlockStates.fromLegacy(id, blockData[y - 1][z][x])
				.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
		} else if (id == 25) {
			CompoundTag note = tileEntities.get(new BlockPos(x, y, z));
			if (note != null) state = state.setValue(NoteBlock.NOTE, Math.clamp(note.getInt("note").orElse(0), 0, 24))
				.setValue(NoteBlock.POWERED, note.getBoolean("powered").orElse(false));
		} else if (id == 144) {
			CompoundTag skull = tileEntities.get(new BlockPos(x, y, z));
			if (skull != null) {
				int type = Math.clamp(skull.getInt("SkullType").orElse(0), 0, 5);
				if (state.hasProperty(WallSkullBlock.FACING)) {
					Block[] blocks = {Blocks.SKELETON_WALL_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL,
						Blocks.ZOMBIE_WALL_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_WALL_HEAD};
					state = blocks[type].defaultBlockState().setValue(WallSkullBlock.FACING, state.getValue(WallSkullBlock.FACING));
				} else {
					Block[] blocks = {Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL,
						Blocks.ZOMBIE_HEAD, Blocks.PLAYER_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD};
					state = blocks[type].defaultBlockState().setValue(SkullBlock.ROTATION, skull.getInt("Rot").orElse(0) & 15);
				}
			}
		} else if (id == 176 || id == 177) {
			CompoundTag banner = tileEntities.get(new BlockPos(x, y, z));
			if (banner != null) {
				String color = DyeColor.byId(15 - (banner.getInt("Base").orElse(0) & 15)).getSerializedName();
				Block block = BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(
					color + (id == 176 ? "_banner" : "_wall_banner")));
				state = block.withPropertiesOf(state);
			}
		} else if (id == 140) {
			CompoundTag pot = tileEntities.get(new BlockPos(x, y, z));
			if (pot != null) {
				Block plant;
				String name = pot.getString("Item").orElse("");
				if (!name.isEmpty()) {
					int plantId = switch (name) {
						case "minecraft:sapling" -> 6;
						case "minecraft:tallgrass" -> 31;
						case "minecraft:deadbush" -> 32;
						case "minecraft:yellow_flower" -> 37;
						case "minecraft:red_flower" -> 38;
						default -> -1;
					};
					if (plantId >= 0) {
						plant = LegacyBlockStates.fromLegacy(plantId, pot.getInt("Data").orElse(0)).getBlock();
					} else {
						plant = BuiltInRegistries.BLOCK.getValue(Identifier.parse(name));
					}
				} else {
					plant = LegacyBlockStates.fromLegacy(pot.getInt("Item").orElse(0), pot.getInt("Data").orElse(0)).getBlock();
				}
				for (Block block : BuiltInRegistries.BLOCK) {
					if (block instanceof FlowerPotBlock potted && potted.getPotted() == plant) {
						return block.defaultBlockState();
					}
				}
			}
		}
		return state;
	}

	public void process(ServerLevel world, int posX, int posY, int posZ) {
		process(world, posX, posY, posZ, true);
	}

	/**
	 * @param replaceAir when false (legacy Book toggle / overlay): skip schematic AIR so
	 *                   existing world blocks are not cleared. Non-air still places.
	 */
	public void process(ServerLevel world, int posX, int posY, int posZ, boolean replaceAir) {
		process(world, posX, posY, posZ, replaceAir, true);
	}

	/** Repeated live frames disable entity copying to avoid spawning captured mobs each tick. */
	public void process(ServerLevel world, int posX, int posY, int posZ, boolean replaceAir, boolean spawnEntities) {
		// Single half-size. Live shells and frames pass their click/spawn anchor through here.
		// Static spawns must pass staticProcessAnchor() first: Forge also subtracts center
		// (another size/2) inside StructureUtils. Do not fold that second shift into this
		// method — it would move every live shell relative to the frame fix.
		int originX = SpawnOrigin.fabricOriginX(posX, length);
		int originZ = SpawnOrigin.fabricOriginZ(posZ, width);

		int blocksPlaced = 0;
		List<BlockPos> chests = new ArrayList<>();
		List<BlockPos> analogUpdates = new ArrayList<>();
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					BlockState state = getBlockState(x, y, z);
					if (state == null || (!replaceAir && state.isAir())) continue;
					BlockPos pos = new BlockPos(originX + x, posY + y, originZ + z);
					if (state.hasAnalogOutputSignal() || world.getBlockState(pos).hasAnalogOutputSignal()) analogUpdates.add(pos);
					world.setBlock(pos, state, ASSEMBLY_FLAGS);
					blocksPlaced++;
					if (state.getBlock() instanceof ChestBlock) chests.add(pos);
				}
			}
		}

		// Legacy chests store facing only. Shape updates cannot join two modern SINGLE chests.
		for (BlockPos pos : chests) connectChest(world, pos);

		// Calculate connections and shapes against the completed structure, including stairs,
		// redstone wire, panes, fences and walls. Setting the identical state triggers no update.
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					BlockState source = getBlockState(x, y, z);
					if (source == null || (!replaceAir && source.isAir())) continue;
					BlockPos pos = new BlockPos(originX + x, posY + y, originZ + z);
					BlockState state = world.getBlockState(pos);
					BlockState updated = Block.updateFromNeighbourShapes(state, world, pos);
					if (updated != state) world.setBlock(pos, updated, ASSEMBLY_FLAGS);
				}
			}
		}

		int tilesPlaced = 0;
		for (Map.Entry<BlockPos, CompoundTag> entry : tileEntities.entrySet()) {
			BlockPos local = entry.getKey();
			BlockState source = getBlockState(local.getX(), local.getY(), local.getZ());
			if (source == null || (!replaceAir && source.isAir())) continue;
			BlockPos pos = entry.getKey().offset(originX, posY, originZ);
			BlockEntity entity = world.getBlockEntity(pos);
			// Flower pots and note blocks moved their old tile data into block states.
			if (entity == null) continue;
			CompoundTag data = entry.getValue().copy();
			ListTag items = data.getList("Items").orElse(null);
			data.remove("Items");
			String[] signLines = entity instanceof SignBlockEntity
				? extractLegacySignText(data) : null;
			if (signLines != null) {
				for (int i = 1; i <= 4; i++) data.remove("Text" + i);
			}
			data = (CompoundTag) DataFixers.getDataFixer().update(References.BLOCK_ENTITY,
				new Dynamic<>(NbtOps.INSTANCE, data), dataVersion,
				SharedConstants.getCurrentVersion().dataVersion().version()).getValue();
			data.putInt("x", pos.getX());
			data.putInt("y", pos.getY());
			data.putInt("z", pos.getZ());
			try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(
					entity.problemPath(), InstantMassiveStructures.LOGGER)) {
				// loadStatic constructs a different entity. Load the instance attached to the world.
				entity.loadWithComponents(TagValueInput.create(reporter, world.registryAccess(), data));
			}
			if (signLines != null) {
				applySignText((SignBlockEntity) entity, signLines);
			}
			if (entity instanceof Container container && items != null) {
				container.clearContent();
				for (int i = 0; i < items.size(); i++) {
					CompoundTag item = items.getCompound(i).orElseThrow();
					int slot = item.getByte("Slot").orElse((byte) 0) & 255;
					if (slot < container.getContainerSize()) {
						container.setItem(slot, LegacyItems.fromLegacyStack(item, world.registryAccess()));
					}
				}
			}
			entity.setChanged();
			BlockState state = world.getBlockState(pos);
			world.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
			tilesPlaced++;
		}

		// Activate blocks only once their partners, supports and inventories are ready.
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					BlockState source = getBlockState(x, y, z);
					if (source == null || (!replaceAir && source.isAir())) continue;
					BlockPos pos = new BlockPos(originX + x, posY + y, originZ + z);
					BlockState state = world.getBlockState(pos);
					state.onPlace(world, pos, Blocks.AIR.defaultBlockState(), false);
					state = world.getBlockState(pos);
					state.updateNeighbourShapes(world, pos, Block.UPDATE_CLIENTS, Block.UPDATE_LIMIT);
					state.updateIndirectNeighbourShapes(world, pos, Block.UPDATE_CLIENTS, Block.UPDATE_LIMIT);
					world.updateNeighborsAt(pos, state.getBlock());
				}
			}
		}
		for (BlockPos pos : analogUpdates) world.updateNeighbourForOutputSignal(pos, world.getBlockState(pos).getBlock());
		if (spawnEntities) LegacyEntities.place(world, entities, new BlockPos(originX, posY, originZ), schematicSourceOrigin, dataVersion);
		InstantMassiveStructures.LOGGER.debug("Placed {} blocks and {} tile entities for {} (replaceAir={})",
			blocksPlaced, tilesPlaced, fileName, replaceAir);
	}

	private static void connectChest(ServerLevel world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof ChestBlock) || state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) return;
		Direction facing = state.getValue(ChestBlock.FACING);
		for (Direction direction : new Direction[] {facing.getClockWise(), facing.getCounterClockWise()}) {
			BlockPos otherPos = pos.relative(direction);
			BlockState other = world.getBlockState(otherPos);
			if (other.is(state.getBlock()) && other.getValue(ChestBlock.FACING) == facing
					&& other.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
				ChestType type = direction == facing.getClockWise() ? ChestType.LEFT : ChestType.RIGHT;
				world.setBlock(pos, state.setValue(ChestBlock.TYPE, type), ASSEMBLY_FLAGS);
				world.setBlock(otherPos, other.setValue(ChestBlock.TYPE, type.getOpposite()), ASSEMBLY_FLAGS);
				return;
			}
		}
	}

	/**
	 * Legacy redstone outline: glass on the AABB shell faces (i==0||j==0||k==0).
	 * Returns positions written so fire-charge / re-click can clear them.
	 */
	public java.util.List<BlockPos> showOutline(ServerLevel world, int posX, int posY, int posZ,
			int modX, int modY, int modZ) {
		java.util.ArrayList<BlockPos> written = new java.util.ArrayList<>();
		// Same single half-size as process(). Static previews pass staticProcessAnchor and 0 mods
		// so the glass box is the box that will be filled. Forge's own glass outline uses a
		// different mirrored formula and does not match StructureUtils; this preview matches the spawn.
		int originX = SpawnOrigin.fabricOriginX(posX + modX, length);
		int originZ = SpawnOrigin.fabricOriginZ(posZ + modZ, width);
		int baseY = posY + modY;
		
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					if (!(x == 0 || y == 0 || z == 0 || x == length - 1 || y == height - 1 || z == width - 1)) continue;
					BlockPos pos0 = new BlockPos(originX + x, baseY + y, originZ + z);
					world.setBlock(pos0, Blocks.GLASS.defaultBlockState(), Block.UPDATE_ALL);
					written.add(pos0);
				}
			}
		}
		InstantMassiveStructures.LOGGER.debug("Outline {} glass blocks for {}", written.size(), fileName);
		return written;
	}

	public void removeOutline(ServerLevel world, java.util.List<BlockPos> positions) {
		if (positions == null) return;
		for (BlockPos pos : positions) {
			if (world.getBlockState(pos).is(Blocks.GLASS)) {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
			}
		}
	}


	/**
	 * Anchor for {@link #process}, {@link #showOutline}, and {@link #clearBounds} so a
	 * non-live schematic's cells match Forge {@code StructureUtils} relative to the clicked block.
	 * Requires {@link #readFromFile()}. Live shells must not use this.
	 */
	public BlockPos staticProcessAnchor(int clickX, int clickY, int clickZ, int modX, int modY, int modZ) {
		return new BlockPos(
			SpawnOrigin.staticAnchorX(clickX, modX, length),
			SpawnOrigin.staticAnchorY(clickY, modY),
			SpawnOrigin.staticAnchorZ(clickZ, modZ, width));
	}

	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getLegacyId(int x, int y, int z) {
		return legacyIds[y][z][x];
	}

	public int getLegacyMeta(int x, int y, int z) {
		return blockData[y][z][x];
	}

	/**
	 * Extract legacy sign text (Text1-4) before conversion for later API-based application.
	 */
	private static String[] extractLegacySignText(CompoundTag signTag) {
		if (!signTag.contains("Text1") && !signTag.contains("Text2")
				&& !signTag.contains("Text3") && !signTag.contains("Text4")) return null;
		String[] lines = new String[4];
		for (int i = 0; i < 4; i++) {
			String key = "Text" + (i + 1);
			lines[i] = signTag.contains(key) ? signTag.getString(key).orElse("") : "";
		}
		return lines;
	}

	/**
	 * Apply legacy sign text to SignBlockEntity using the SignText API.
	 * This ensures text is readable in-game, not just present in NBT.
	 */
	private static void applySignText(SignBlockEntity sign, String[] lines) {
		Component[] messages = new Component[4];
		
		for (int i = 0; i < 4; i++) {
			String line = lines[i];
			if (line == null || line.isEmpty() || line.equals("null")) {
				messages[i] = Component.empty();
			} else {
				// Schematics contain both plain text and JSON components from legacy Minecraft.
				try {
					messages[i] = ComponentSerialization.CODEC.parse(JsonOps.INSTANCE,
						JsonParser.parseString(line)).result().orElseGet(() -> Component.literal(line));
				} catch (RuntimeException invalidJson) {
					messages[i] = Component.literal(line);
				}
			}
		}
		sign.setText(new SignText(messages, messages.clone(), DyeColor.BLACK, false), true);
		sign.setChanged();
	}

	public static void clearBounds(ServerLevel world, int posX, int posY, int posZ,
			int length, int height, int width) {
		clearBounds(world, posX, posY, posZ, length, height, width, true);
	}

	/** Live frame replacement defers neighbor updates until its next frame is assembled. */
	public static void clearBounds(ServerLevel world, int posX, int posY, int posZ,
			int length, int height, int width, boolean notifyNeighbors) {
		// Same single half-size as process(). Pass the anchor process() received
		// (for static spawns, that is staticProcessAnchor, not the raw click).
		int originX = SpawnOrigin.fabricOriginX(posX, length);
		int originZ = SpawnOrigin.fabricOriginZ(posZ, width);
		List<BlockPos> analogUpdates = new ArrayList<>();
		if (notifyNeighbors) LegacyEntities.clearDecorations(world, new BlockPos(originX, posY, originZ), length, height, width);
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					BlockPos pos = new BlockPos(originX + x, posY + y, originZ + z);
					if (notifyNeighbors && world.getBlockState(pos).hasAnalogOutputSignal()) analogUpdates.add(pos);
					world.setBlock(pos, Blocks.AIR.defaultBlockState(), ASSEMBLY_FLAGS);
				}
			}
		}
		if (notifyNeighbors) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < width; z++) {
					for (int x = 0; x < length; x++) {
						if (x != 0 && x != length - 1 && y != 0 && y != height - 1 && z != 0 && z != width - 1) continue;
						BlockPos pos = new BlockPos(originX + x, posY + y, originZ + z);
						Blocks.AIR.defaultBlockState().updateNeighbourShapes(world, pos, Block.UPDATE_CLIENTS, Block.UPDATE_LIMIT);
						world.updateNeighborsAt(pos, Blocks.AIR);
					}
				}
			}
			for (BlockPos pos : analogUpdates) world.updateNeighbourForOutputSignal(pos, Blocks.AIR);
		}
	}
}
