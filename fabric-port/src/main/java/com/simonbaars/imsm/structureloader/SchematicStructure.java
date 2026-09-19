package com.simonbaars.imsm.structureloader;

import com.simonbaars.imsm.InstantMassiveStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.TagValueInput;
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
		ListTag tileEntitiesList = nbt.getListOrEmpty("TileEntities", 10); // 10 = CompoundTag type
		for (int i = 0; i < tileEntitiesList.size(); i++) {
			CompoundTag te = tileEntitiesList.getCompound(i);
			int teX = te.getInt("x");
			int teY = te.getInt("y");
			int teZ = te.getInt("z");
			String posKey = teX + "," + teY + "," + teZ;
			tileEntities.put(posKey, te);
		}

		InstantMassiveStructures.LOGGER.info("Loaded structure {} with dimensions {}x{}x{}, {} tile entities", 
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
		int originX = posX - length / 2 + 1;
		int originZ = posZ - width / 2 + 1;

		int blocksPlaced = 0;
		int tilesPlaced = 0;

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
					} catch (Exception e) {
						InstantMassiveStructures.LOGGER.warn("Failed to place block at {}: {}", 
							pos, e.getMessage());
					}
				}
			}
		}
		
		// Second pass: place tile entities
		int containerItemsApplied = 0;
		for (Map.Entry<String, CompoundTag> entry : tileEntities.entrySet()) {
			String[] coords = entry.getKey().split(",");
			int schematicX = Integer.parseInt(coords[0]);
			int schematicY = Integer.parseInt(coords[1]);
			int schematicZ = Integer.parseInt(coords[2]);
			
			BlockPos worldPos = new BlockPos(originX + schematicX, posY + schematicY, originZ + schematicZ);
			
			try {
				BlockEntity blockEntity = world.getBlockEntity(worldPos);
				if (blockEntity != null) {
					CompoundTag tileEntityData = entry.getValue().copy();
					
					// Update position to world coordinates
					tileEntityData.putInt("x", worldPos.getX());
					tileEntityData.putInt("y", worldPos.getY());
					tileEntityData.putInt("z", worldPos.getZ());
					
					// Convert legacy tile entity ID to modern format if needed
					String teId = tileEntityData.getString("id");
					if (!teId.contains(":")) {
						tileEntityData.putString("id", "minecraft:" + teId.toLowerCase());
					}
					
					// MC 26.2: use TagValueInput.of() and loadWithComponents(ValueInput)
					try {
						blockEntity.loadWithComponents(TagValueInput.of(NbtOps.INSTANCE, tileEntityData), world.registryAccess());
						blockEntity.setChanged();
						tilesPlaced++;
					} catch (Exception loadEx) {
						InstantMassiveStructures.LOGGER.warn("Failed to load TE components at {}: {}", 
							worldPos, loadEx.getMessage());
					}
					
					// Apply legacy Items to Container (chests, furnaces, etc.)
					if (blockEntity instanceof Container container && tileEntityData.contains("Items", 9)) {
						ListTag itemsList = tileEntityData.getList("Items", 10); // 10 = CompoundTag
						for (int i = 0; i < itemsList.size(); i++) {
							CompoundTag itemTag = itemsList.getCompound(i);
							try {
								byte slot = itemTag.getByte("Slot");
								short legacyId = itemTag.getShort("id");
								byte count = itemTag.getByte("Count");
								short damage = itemTag.getShort("Damage");
								
								// Convert legacy item ID to modern Item
								var modernItem = LegacyItems.fromLegacyId(legacyId);
								if (modernItem != null && !modernItem.equals(net.minecraft.world.item.Items.AIR)) {
									ItemStack stack = new ItemStack(modernItem, count);
									// Note: damage/meta conversion would go here if needed
									if (slot >= 0 && slot < container.getContainerSize()) {
										container.setItem(slot, stack);
										containerItemsApplied++;
									}
								}
							} catch (Exception itemEx) {
								InstantMassiveStructures.LOGGER.warn("Failed to parse item in TE at {}: {}", 
									worldPos, itemEx.getMessage());
							}
						}
					}
				} else {
					String teType = entry.getValue().getString("id");
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

		InstantMassiveStructures.LOGGER.info("Placed {} blocks, {} tile entities, {} container items for structure {} (replaceAir={})", 
			blocksPlaced, tilesPlaced, containerItemsApplied, fileName, replaceAir);
	}

	/**
	 * Legacy redstone outline: glass on the AABB shell faces (i==0||j==0||k==0).
	 * Returns positions written so fire-charge / re-click can clear them.
	 */
	public java.util.List<BlockPos> showOutline(ServerLevel world, int posX, int posY, int posZ,
			int modX, int modY, int modZ) {
		java.util.ArrayList<BlockPos> written = new java.util.ArrayList<>();
		// Legacy: BlockPos(x-i+modifierx, y+j+modifiery, z-k+modifierz) for shell voxels
		int baseX = posX + modX;
		int baseY = posY + modY;
		int baseZ = posZ + modZ;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (int k = 0; k < length; k++) {
					if (!(i == 0 || j == 0 || k == 0)) continue;
					if (i == modX && j == modY && k == modZ) continue;
					BlockPos pos0 = new BlockPos(baseX - i, baseY + j, baseZ - k);
					world.setBlock(pos0, Blocks.GLASS.defaultBlockState(), Block.UPDATE_ALL);
					written.add(pos0);
				}
			}
		}
		InstantMassiveStructures.LOGGER.info("Outline {} glass blocks for {}", written.size(), fileName);
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
	public static void clearBounds(ServerLevel world, int posX, int posY, int posZ,
			int length, int height, int width) {
		posX -= length / 2 - 1;
		posZ -= width / 2 - 1;
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
