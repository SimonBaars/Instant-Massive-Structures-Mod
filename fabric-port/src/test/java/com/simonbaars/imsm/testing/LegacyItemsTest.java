package com.simonbaars.imsm.testing;

import com.simonbaars.imsm.structureloader.LegacyItems;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

/** Regression coverage for real schematic item formats and metadata, using vanilla registries. */
public class LegacyItemsTest {
	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		HolderLookup.Provider registries = VanillaRegistries.createLookup();
		BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(registries).forEach(initializer -> initializer.apply());
		checkItem(registries, numeric(282, 0), Items.MUSHROOM_STEW);
		checkItem(registries, numeric(2257, 0), Items.MUSIC_DISC_CAT);
		checkItem(registries, numeric(35, 14), BuiltInRegistries.ITEM.get(
			Identifier.withDefaultNamespace("red_wool")).orElseThrow().value());
		checkItem(registries, numeric(5, 3), Items.JUNGLE_PLANKS);
		checkItem(registries, numeric(351, 15), Items.BONE_MEAL);
		CompoundTag named = numeric(351, 3);
		named.putString("id", "minecraft:dye");
		checkItem(registries, named, Items.COCOA_BEANS);
		named.putString("id", "minecraft:boat");
		named.putShort("Damage", (short) 0);
		checkItem(registries, named, Items.OAK_BOAT);
		CompoundTag uppercase = numeric(264, 0);
		uppercase.put("Id", uppercase.get("id"));
		uppercase.remove("id");
		checkItem(registries, uppercase, Items.DIAMOND);
		ItemStack damaged = checkItem(registries, numeric(270, 50), Items.WOODEN_PICKAXE);
		require(damaged.getDamageValue() == 50, "tool durability lost");
		ItemStack potion = checkItem(registries, numeric(373, 16456), Items.SPLASH_POTION);
		require(potion.get(DataComponents.POTION_CONTENTS) != null, "potion contents lost");
		CompoundTag enchanted = numeric(279, 10);
		CompoundTag tag = new CompoundTag();
		ListTag ench = new ListTag();
		CompoundTag efficiency = new CompoundTag();
		efficiency.putShort("id", (short) 32);
		efficiency.putShort("lvl", (short) 4);
		ench.add(efficiency);
		tag.put("ench", ench);
		enchanted.put("tag", tag);
		ItemStack axe = checkItem(registries, enchanted, Items.DIAMOND_AXE);
		require(axe.getDamageValue() == 10 && axe.isEnchanted(), "enchanted tool data lost");
		require(LegacyItems.fromLegacyId(282) == Items.MUSHROOM_STEW, "numeric mapper lost stew");
		require(LegacyItems.fromLegacyId(9999) == Items.AIR, "unknown id must resolve to air");

		Path directory = Path.of("src/main/resources/assets/imsm/structs");
		if (!Files.isDirectory(directory)) directory = Path.of("fabric-port").resolve(directory);
		int structures = 0, stacks = 0, fireworks = 0, enchantments = 0, unavailable = 0;
		try (var paths = Files.list(directory)) {
			for (Path file : paths.filter(path -> path.toString().endsWith(".structure")).toList()) {
				CompoundTag schematic;
				try (DataInputStream in = new DataInputStream(new GZIPInputStream(Files.newInputStream(file)))) {
					schematic = NbtIo.read(in);
				}
				structures++;
				for (var tile : schematic.getList("TileEntities").orElse(new ListTag())) {
					CompoundTag blockEntity = (CompoundTag) tile;
					for (var item : blockEntity.getList("Items").orElse(new ListTag())) {
						CompoundTag source = (CompoundTag) item;
						CompoundTag original = source.copy();
						ItemStack stack = LegacyItems.fromLegacyStack(source, registries);
						require(source.equals(original), "item conversion mutated source " + file);
						if (stack.isEmpty()) {
							// Fire, skull and potato crop block ids had no vanilla item forms.
							int legacyId = source.getInt("id").orElse(0);
							require(legacyId == 51 || legacyId == 144 || legacyId == 142,
								"item disappeared in " + file + ": " + source);
							unavailable++;
							continue;
						}
						stacks++;
						require(stack.getCount() == source.getInt("Count").orElse(0), "count lost in " + file);
						CompoundTag extra = source.getCompound("tag").orElse(new CompoundTag());
						if (extra.contains("Fireworks")) {
							require(stack.get(DataComponents.FIREWORKS) != null, "fireworks lost in " + file);
							fireworks++;
						}
						if (extra.contains("ench")) {
							require(stack.isEnchanted(), "enchantments lost in " + file);
							enchantments++;
						}
					}
				}
			}
		}
		require(structures == 952 && stacks > 2000, "schematic corpus incomplete");
		require(fireworks == 36 && enchantments == 20, "item component fixtures incomplete");
		System.out.println("Legacy items passed: " + structures + " structures, " + stacks
			+ " stacks, " + fireworks + " fireworks, " + enchantments + " enchanted stacks; "
			+ unavailable + " legacy block items unavailable.");
	}

	private static CompoundTag numeric(int id, int damage) {
		CompoundTag tag = new CompoundTag();
		tag.putShort("id", (short) id);
		tag.putShort("Damage", (short) damage);
		tag.putByte("Count", (byte) 1);
		return tag;
	}

	private static ItemStack checkItem(HolderLookup.Provider registries, CompoundTag source, Item expected) {
		ItemStack stack = LegacyItems.fromLegacyStack(source, registries);
		require(stack.is(expected), "expected " + expected + " for " + source + ", got " + stack);
		return stack;
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}
}
