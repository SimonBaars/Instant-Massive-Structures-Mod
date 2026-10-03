package com.simonbaars.imsm.structureloader;

import com.mojang.serialization.Dynamic;
import com.simonbaars.imsm.InstantMassiveStructures;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Converts schematic ItemStack NBT, including pre-flattening ids and item metadata. */
public final class LegacyItems {

	// The original schematics predate DataVersion. Version 99 also accepts the string ids
	// present in newer files; vanilla then handles flattening and the component migration.
	private static final int SCHEMATIC_ITEM_VERSION = 99;
	private static final Map<CompoundTag, CompoundTag> CONVERTED = new ConcurrentHashMap<>();

	private LegacyItems() {}

	/** Resolve an old numeric id using the same complete mapping as vanilla world upgrades. */
	public static Item fromLegacyId(int legacyId) {
		if (legacyId <= 0 || legacyId > Short.MAX_VALUE) {
			return Items.AIR;
		}
		CompoundTag tag = new CompoundTag();
		tag.putShort("id", (short) legacyId);
		tag.putShort("Damage", (short) 0);
		tag.putByte("Count", (byte) 1);
		CompoundTag converted = convert(tag);
		Identifier id = Identifier.tryParse(converted.getString("id").orElse("minecraft:air"));
		return id == null ? Items.AIR : BuiltInRegistries.ITEM.get(id)
			.map(holder -> holder.value()).orElse(Items.AIR);
	}

	/**
	 * Preserve count, variants, durability, enchantments, potions and fireworks from the
	 * original stack. The registry lookup is needed to decode modern enchantment components.
	 */
	public static ItemStack fromLegacyStack(CompoundTag legacyTag, HolderLookup.Provider registries) {
		CompoundTag source = legacyTag.copy();
		source.remove("Slot");
		if (!source.contains("id") && source.contains("Id")) {
			Tag id = source.get("Id");
			if (id != null) source.put("id", id.copy());
		}
		source.remove("Id");
		if (!source.contains("id")) return ItemStack.EMPTY;
		// Vanilla's earliest potion migration only inspects an existing tag compound.
		// Many schematics omit it while still encoding potion type/splash in Damage.
		if (!source.contains("count") && !source.contains("tag")) {
			source.put("tag", new CompoundTag());
		}

		CompoundTag converted = source.contains("count") ? source : convert(source);
		Identifier itemId = Identifier.tryParse(converted.getString("id").orElse("minecraft:air"));
		if (itemId == null || BuiltInRegistries.ITEM.get(itemId)
			.map(holder -> holder.value() == Items.AIR).orElse(true)) {
			// Some files contain hacked fire/skull block ids that never had item forms.
			InstantMassiveStructures.LOGGER.debug("No modern item for schematic id {}", source.get("id"));
			return ItemStack.EMPTY;
		}
		return ItemStack.CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), converted)
			.resultOrPartial(error -> InstantMassiveStructures.LOGGER.warn(
				"Failed to decode schematic item {}: {}", source.get("id"), error))
			.orElse(ItemStack.EMPTY);
	}

	private static CompoundTag convert(CompoundTag source) {
		// Remove the position key in fromLegacyStack so recurring chest contents and live
		// frames share conversions. Only the codec reads cached output; it is never mutated.
		return CONVERTED.computeIfAbsent(source, key -> {
			Tag upgraded = DataFixers.getDataFixer().update(References.ITEM_STACK,
				new Dynamic<Tag>(NbtOps.INSTANCE, key), SCHEMATIC_ITEM_VERSION,
				SharedConstants.getCurrentVersion().dataVersion().version()).getValue();
			return upgraded instanceof CompoundTag compound ? compound : new CompoundTag();
		});
	}
}
