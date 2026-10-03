package com.simonbaars.imsm.structureloader;

import com.mojang.serialization.Dynamic;
import com.simonbaars.imsm.InstantMassiveStructures;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.AABB;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Imports the entities stored by classic WorldEdit schematics after their blocks are assembled. */
public final class LegacyEntities {
	private static final Map<SourceEntity, CompoundTag> CONVERTED = new ConcurrentHashMap<>();
	private record SourceEntity(CompoundTag tag, int version) {}

	private LegacyEntities() {}

	/** Remove decorations anchored in the cleared structure, without dropping their items. */
	public static int clearDecorations(ServerLevel world, BlockPos worldOrigin,
			int length, int height, int width) {
		if (length <= 0 || height <= 0 || width <= 0) return 0;
		BlockPos end = worldOrigin.offset(length, height, width);
		int removed = 0;
		for (HangingEntity decoration : world.getEntitiesOfClass(HangingEntity.class,
				new AABB(worldOrigin.getX(), worldOrigin.getY(), worldOrigin.getZ(),
					end.getX(), end.getY(), end.getZ()).inflate(16), entity -> {
					BlockPos anchor = entity.getPos();
					return anchor.getX() >= worldOrigin.getX() && anchor.getX() < end.getX()
						&& anchor.getY() >= worldOrigin.getY() && anchor.getY() < end.getY()
						&& anchor.getZ() >= worldOrigin.getZ() && anchor.getZ() < end.getZ();
				})) {
			decoration.discard();
			removed++;
		}
		return removed;
	}

	public static int place(ServerLevel world, ListTag sourceEntities, BlockPos worldOrigin,
			BlockPos schematicSourceOrigin, int dataVersion) {
		int placed = 0;
		for (Tag source : sourceEntities) {
			if (!(source instanceof CompoundTag legacy)) continue;
			try {
				CompoundTag tag = toWorldTag(legacy, worldOrigin, schematicSourceOrigin, dataVersion);
				Entity entity = EntityType.loadEntityRecursive(
					TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), tag),
					world, EntitySpawnReason.STRUCTURE, loaded -> {
						loaded.setUUID(UUID.randomUUID());
						return loaded;
					});
				if (entity == null) {
					InstantMassiveStructures.LOGGER.warn("Unknown schematic entity {}", tag.get("id"));
					continue;
				}
				if (entity instanceof HangingEntity hanging) {
					// Replacing the same decoration must not duplicate frames, pictures, or their
					// contents. Leave other hanging anchors and all existing mobs alone.
					for (HangingEntity old : world.getEntitiesOfClass(HangingEntity.class,
							new AABB(hanging.getPos()).inflate(4), existing ->
							existing.getType() == hanging.getType()
								&& existing.getPos().equals(hanging.getPos())
								&& existing.getDirection() == hanging.getDirection())) {
						old.discard();
					}
					if (!hanging.survives()) {
						InstantMassiveStructures.LOGGER.debug("Schematic decoration {} has no support at {}",
							tag.get("id"), hanging.getPos());
						continue;
					}
				}
				if (world.tryAddFreshEntityWithPassengers(entity)) placed++;
			} catch (Exception e) {
				InstantMassiveStructures.LOGGER.warn("Failed to import schematic entity {}: {}",
					legacy.get("id"), e.getMessage());
			}
		}
		return placed;
	}

	/** Upgrade entity data, then translate both fractional position and hanging-block anchor. */
	public static CompoundTag toWorldTag(CompoundTag source, BlockPos worldOrigin,
			BlockPos schematicSourceOrigin, int dataVersion) {
		SourceEntity key = new SourceEntity(source.copy(), dataVersion);
		CompoundTag upgraded = CONVERTED.computeIfAbsent(key, original -> {
			Tag value = DataFixers.getDataFixer().update(References.ENTITY_TREE,
				new Dynamic<Tag>(NbtOps.INSTANCE, original.tag()), original.version(),
				SharedConstants.getCurrentVersion().dataVersion().version()).getValue();
			return value instanceof CompoundTag compound ? compound : new CompoundTag();
		}).copy();
		translate(upgraded, worldOrigin.subtract(schematicSourceOrigin));
		return upgraded;
	}

	private static void translate(CompoundTag entity, BlockPos offset) {
		// Source UUIDs identify the original world entity; each schematic spawn is a new copy.
		for (String key : new String[] {"UUID", "UUIDMost", "UUIDLeast", "uuid", "WorldUUIDMost", "WorldUUIDLeast"}) {
			entity.remove(key);
		}
		for (String key : new String[] {"Pos", "pos"}) {
			ListTag pos = entity.getList(key).orElse(new ListTag());
			if (pos.size() != 3) continue;
			ListTag translated = new ListTag();
			translated.add(DoubleTag.valueOf(pos.getDouble(0).orElse(0.0) + offset.getX()));
			translated.add(DoubleTag.valueOf(pos.getDouble(1).orElse(0.0) + offset.getY()));
			translated.add(DoubleTag.valueOf(pos.getDouble(2).orElse(0.0) + offset.getZ()));
			entity.put(key, translated);
		}
		entity.getIntArray("block_pos").ifPresent(anchor -> {
			if (anchor.length == 3) entity.putIntArray("block_pos", new int[] {
				anchor[0] + offset.getX(), anchor[1] + offset.getY(), anchor[2] + offset.getZ()});
		});
		for (Tag passenger : entity.getList("Passengers").orElse(new ListTag())) {
			if (passenger instanceof CompoundTag compound) translate(compound, offset);
		}
	}
}
