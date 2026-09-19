package com.simonbaars.imsm.structureloader;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class SchematicStructure {
	private final String fileName;
	/** Pre-flattening block ids; -1 = unset / unmapped. */
	private int[][][] legacyIds;
	private int[][][] blockData;
	/** Tile entities from schematic, keyed by relative position "x,y,z" */
	private Map<String, CompoundTag> tileEntities;
	private int length;
	private int height;
	private int width;

	public SchematicStructure(String fileName) {
		this.fileName = "/assets/imsm/structs/" + fileName + ".structure";
	}

	public void readFromFile() throws Exception {
		InputStream fileStream = SchematicStructure.class.getResourceAsStream(fileName);
		if (fileStream == null) {
			throw new Exception("Structure file not found: " + fileName);
		}

		CompoundTag nbt;
		try (DataInputStream dataStream = new DataInputStream(new GZIPInputStream(fileStream))) {
			nbt = NbtIo.read(dataStream);
		}

		this.length = nbt.getShort("Width").orElse((short)0);
		this.width = nbt.getShort("Length").orElse((short)0);
		this.height = nbt.getShort("Height").orElse((short)0);

		this.legacyIds = new int[height][width][length];
		this.blockData = new int[height][width][length];
		this.tileEntities = new HashMap<>();
		
		for (int y0 = 0; y0 < height; y0++) {
			for (int z0 = 0; z0 < width; z0++) {
				for (int x0 = 0; x0 < length; x0++) {
					this.legacyIds[y0][z0][x0] = -1;
				}
			}
		}

		byte[] blockIds = nbt.getByteArray("Blocks").orElse(new byte[0]);
		byte[] blockDataBytes = nbt.getByteArray("Data").orElse(new byte[0]);

		int x = 1, y = 1, z = 1;
		for (int i = 0; i < blockIds.length; i++) {
			int blockId = blockIds[i] & 0xFF;
			int meta = i < blockDataBytes.length ? (blockDataBytes[i] & 0xFF) : 0;
			// Keep raw id+meta; BlockState resolved at process-time via LegacyBlockStates
			this.legacyIds[y - 1][z - 1][x - 1] = blockId;
			this.blockData[y - 1][z - 1][x - 1] = meta;

			x++;
			if (x > length) {
				x = 1;
				z++;
			}
			if (z > width) {
				z = 1;
				y++;
			}
		}
		
		// Read tile entities
		ListTag tileEntitiesList = nbt.getList("TileEntities").orElse(new ListTag());
		for (int i = 0; i < tileEntitiesList.size(); i++) {
			CompoundTag te = tileEntitiesList.getCompound(i).orElseThrow();
			int teX = te.getInt("x").orElse(0);
			int teY = te.getInt("y").orElse(0);
			int teZ = te.getInt("z").orElse(0);
			String posKey = teX + "," + teY + "," + teZ;
			tileEntities.put(posKey, te);
		}

		InstantMassiveStructures.LOGGER.debug("Loaded structure {} with dimensions {}x{}x{}, {} tile entities", 
			fileName, length, height, width, tileEntities.size());
	}

	public void process(ServerLevel world, int posX, int posY, int posZ) {
		process(world, posX, posY, posZ, true);
	}

	/**
	 * @param replaceAir when false (legacy Book toggle / overlay): skip schematic AIR so
	 *                   existing world blocks are not cleared. Non-air still places.
	 */
	public void process(ServerLevel world, int posX, int posY, int posZ, boolean replaceAir) {
		int originX = posX - (length / 2) + 1;
		int originZ = posZ - (width / 2) + 1;

		int blocksPlaced = 0;
		int tilesPlaced = 0;
		List<BlockPos> glassConnectableBlocks = new ArrayList<>();

		// First pass: place all blocks
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					int legacyId = legacyIds[y][z][x];
					if (legacyId < 0) continue;

					BlockState state = LegacyBlockStates.fromLegacy(legacyId, blockData[y][z][x]);
					if (state == null) continue;
					if (!replaceAir && state.isAir()) continue;

					BlockPos pos = new BlockPos(originX + x, posY + y, originZ + z);
					
					try {
						world.setBlock(pos, state, Block.UPDATE_ALL);
						blocksPlaced++;
						
						if (isConnectableBlock(state.getBlock())) {
							glassConnectableBlocks.add(pos);
						}
					} catch (Exception e) {
						InstantMassiveStructures.LOGGER.warn("Failed to place block at {}: {}", 
							pos, e.getMessage());
					}
				}
			}
		}
		
		// Second pass: trigger neighbor updates for connectable blocks (panes, fences, walls)
		for (BlockPos pos : glassConnectableBlocks) {
			BlockState state = world.getBlockState(pos);
			world.setBlock(pos, state, Block.UPDATE_NEIGHBORS);
		}
		
		// Third pass: place tile entities
		int containerItemsApplied = 0;
		for (Map.Entry<String, CompoundTag> entry : tileEntities.entrySet()) {
			String[] coords = entry.getKey().split(",");
			int schematicX = Integer.parseInt(coords[0]);
			int schematicY = Integer.parseInt(coords[1]);
			int schematicZ = Integer.parseInt(coords[2]);
			
			BlockPos worldPos = new BlockPos(originX + schematicX, posY + schematicY, originZ + schematicZ);
			
			try {
				BlockState blockState = world.getBlockState(worldPos);
				
				// For signs, ensure BlockEntity is created if it doesn't exist
				if (blockState.getBlock() instanceof net.minecraft.world.level.block.SignBlock) {
					BlockEntity existing = world.getBlockEntity(worldPos);
					if (existing == null) {
						// Re-set the block to force BE creation
						world.setBlock(worldPos, blockState, Block.UPDATE_ALL | Block.UPDATE_CLIENTS);
						InstantMassiveStructures.LOGGER.debug("Forced sign BE creation at {}", worldPos);
					}
				}
				
				BlockEntity blockEntity = world.getBlockEntity(worldPos);
				if (blockEntity != null) {
					CompoundTag tileEntityData = entry.getValue().copy();
					
					// Update position to world coordinates
					tileEntityData.putInt("x", worldPos.getX());
					tileEntityData.putInt("y", worldPos.getY());
					tileEntityData.putInt("z", worldPos.getZ());
					
					// Convert legacy tile entity ID to modern format if needed
					String teId = tileEntityData.getString("id").orElse("");
					if (!teId.contains(":")) {
						tileEntityData.putString("id", "minecraft:" + teId.toLowerCase());
					}

				// Extract legacy sign text before conversion
				String[] legacySignLines = null;
				if (blockEntity instanceof net.minecraft.world.level.block.entity.SignBlockEntity) {
					legacySignLines = extractLegacySignText(tileEntityData);
					convertLegacySignText(tileEntityData);
				}

				// Strip legacy Items before loadStatic to avoid decode warnings
				ListTag legacyItems = null;
				if (tileEntityData.contains("Items")) {
					legacyItems = tileEntityData.getList("Items").orElse(new ListTag());
					tileEntityData.remove("Items");
				}

				// MC 26.2: load tile entity data
				try {
					blockEntity.loadStatic(worldPos, blockEntity.getBlockState(), tileEntityData, world.registryAccess());
					blockEntity.setChanged();
					tilesPlaced++;
				} catch (Exception loadEx) {
					InstantMassiveStructures.LOGGER.warn("Failed to load TE components at {}: {}",
						worldPos, loadEx.getMessage());
				}

				// Apply legacy sign text to SignBlockEntity after loadStatic
				if (blockEntity instanceof net.minecraft.world.level.block.entity.SignBlockEntity sign && legacySignLines != null) {
					try {
						applySignText(sign, legacySignLines);
					} catch (Exception signEx) {
						InstantMassiveStructures.LOGGER.warn("Failed to apply sign text at {}: {}",
							worldPos, signEx.getMessage());
					}
				}

				// Apply legacy Items to Container (chests, furnaces, etc.) after loadStatic
				if (blockEntity instanceof Container container && legacyItems != null) {
						for (int i = 0; i < legacyItems.size(); i++) {
							CompoundTag itemTag = legacyItems.getCompound(i).orElse(new CompoundTag());
							try {
								byte slot = itemTag.getByte("Slot").orElse((byte)0);
								// Legacy schematics may use capital "Id" instead of lowercase "id"
								short legacyId = itemTag.contains("Id") 
									? itemTag.getShort("Id").orElse((short)0)
									: itemTag.getShort("id").orElse((short)0);
								byte count = itemTag.getByte("Count").orElse((byte)0);
								short damage = itemTag.getShort("Damage").orElse((short)0);
								
								if (legacyId == 0) {
									continue;
								}
								
								// Convert legacy item ID to modern Item
								var modernItem = LegacyItems.fromLegacyId(legacyId);
								if (modernItem != null && !modernItem.equals(net.minecraft.world.item.Items.AIR)) {
									ItemStack stack = new ItemStack(modernItem, count);
									if (slot >= 0 && slot < container.getContainerSize()) {
										container.setItem(slot, stack);
										containerItemsApplied++;
									}
								} else if (legacyId > 0) {
									InstantMassiveStructures.LOGGER.debug("Unknown legacy item ID {} at slot {} in TE at {}",
										legacyId, slot, worldPos);
								}
							} catch (Exception itemEx) {
								InstantMassiveStructures.LOGGER.warn("Failed to parse item in TE at {}: {}", 
									worldPos, itemEx.getMessage());
							}
						}
					}
				} else {
					String teType = entry.getValue().getString("id").orElse("");
					// Known modern blocks without block entities (cauldron, etc.) - suppress warning
					if (!teType.equals("Cauldron")) {
						InstantMassiveStructures.LOGGER.warn("No block entity at {} for tile entity type {}", 
							worldPos, teType);
					}
				}
			} catch (Exception e) {
				InstantMassiveStructures.LOGGER.error("Failed to place tile entity at {}: {}", 
					worldPos, e.getMessage());
			}
		}

		InstantMassiveStructures.LOGGER.debug("Placed {} blocks, {} tile entities, {} container items for structure {} (replaceAir={})", 
			blocksPlaced, tilesPlaced, containerItemsApplied, fileName, replaceAir);
	}

	/**
	 * Legacy redstone outline: glass on the AABB shell faces (i==0||j==0||k==0).
	 * Returns positions written so fire-charge / re-click can clear them.
	 */
	public java.util.List<BlockPos> showOutline(ServerLevel world, int posX, int posY, int posZ,
			int modX, int modY, int modZ) {
		java.util.ArrayList<BlockPos> written = new java.util.ArrayList<>();
		int originX = (posX + modX) - (length / 2) + 1;
		int originZ = (posZ + modZ) - (width / 2) + 1;
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

	/** Clear the same centered bounding box that {@link #process} would occupy. */
	private static boolean isConnectableBlock(Block block) {
		return block instanceof net.minecraft.world.level.block.IronBarsBlock
			|| block instanceof net.minecraft.world.level.block.FenceBlock
			|| block instanceof net.minecraft.world.level.block.WallBlock;
	}
	
	/**
	 * Extract legacy sign text (Text1-4) before conversion for later API-based application.
	 */
	private static String[] extractLegacySignText(CompoundTag signTag) {
		String[] lines = new String[4];
		for (int i = 0; i < 4; i++) {
			String key = "Text" + (i + 1);
			lines[i] = signTag.contains(key) ? signTag.getString(key).orElse("") : "";
		}
		return lines;
	}

	/**
	 * Convert legacy sign text (Text1-4 plain strings) to modern format (front_text with messages).
	 * Legacy: Text1, Text2, Text3, Text4 as plain strings
	 * Modern (MC 1.20+): front_text/back_text compounds with messages array of JSON text components
	 */
	private static void convertLegacySignText(CompoundTag signTag) {
		if (!signTag.contains("Text1") && !signTag.contains("Text2") 
			&& !signTag.contains("Text3") && !signTag.contains("Text4")) {
			return;
		}
		
		CompoundTag frontText = new CompoundTag();
		ListTag messages = new ListTag();
		
		for (int i = 1; i <= 4; i++) {
			String key = "Text" + i;
			String text = signTag.contains(key) ? signTag.getString(key).orElse("") : "";
			
			if (text.isEmpty()) {
				text = "\"\"";
			} else if (!text.startsWith("{") && !text.startsWith("\"")) {
				text = "{\"text\":\"" + text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}";
			}
			
			messages.add(net.minecraft.nbt.StringTag.valueOf(text));
			signTag.remove(key);
		}
		
		frontText.put("messages", messages);
		frontText.putBoolean("has_glowing_text", false);
		signTag.put("front_text", frontText);
	}

	/**
	 * Apply legacy sign text to SignBlockEntity using the SignText API.
	 * This ensures text is readable in-game, not just present in NBT.
	 */
	private static void applySignText(net.minecraft.world.level.block.entity.SignBlockEntity sign, String[] lines) {
		// Create SignText with the legacy lines converted to Components
		net.minecraft.network.chat.Component[] filteredMessages = new net.minecraft.network.chat.Component[4];
		net.minecraft.network.chat.Component[] messages = new net.minecraft.network.chat.Component[4];
		
		for (int i = 0; i < 4; i++) {
			String line = lines[i];
			if (line == null || line.isEmpty()) {
				messages[i] = net.minecraft.network.chat.Component.empty();
			} else {
				// Plain text - legacy signs used plain strings
				messages[i] = net.minecraft.network.chat.Component.literal(line);
			}
			filteredMessages[i] = messages[i];
		}
		
		// Create new SignText with the component arrays
		// SignText(Component[] messages, Component[] filteredMessages, DyeColor color, boolean hasGlowingText)
		net.minecraft.world.level.block.entity.SignText frontText = new net.minecraft.world.level.block.entity.SignText(
			messages,
			filteredMessages,
			net.minecraft.world.item.DyeColor.BLACK,
			false  // has_glowing_text
		);
		
		// Apply to the sign's front face
		sign.updateText(text -> frontText, true);
		sign.setChanged();
	}

	public static void clearBounds(ServerLevel world, int posX, int posY, int posZ,
			int length, int height, int width) {
		posX -= (length / 2) - 1;
		posZ -= (width / 2) - 1;
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < width; z++) {
				for (int x = 0; x < length; x++) {
					BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);
					world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}
	}
}
