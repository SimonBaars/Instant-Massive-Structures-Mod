package com.simonbaars.imsm.core;

import com.simonbaars.imsm.InstantMassiveStructures;
import com.simonbaars.imsm.blocks.StructureBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;

public class StructureRegistry {
	private static final List<Block> STRUCTURE_BLOCKS = new ArrayList<>();
	private static final List<Item> STRUCTURE_ITEMS = new ArrayList<>();

	public static void registerBlocks() {
		registerStructureBlock("wooden_house", "WoodenHouse", 13, -1, 5);
		registerStructureBlock("house", "BlockHouse", 0, 0, 0);
		registerStructureBlock("cosy_house", "BlockCosyHouse", 0, 0, 0);
		registerStructureBlock("giant_tree", "BlockGiantTree", 0, 0, 0);
		registerStructureBlock("farm", "BlockFarm", 0, 0, 0);
	}

	private static void registerStructureBlock(String id, String structureName, 
		int modX, int modY, int modZ) {
		Block block = new StructureBlock(
			BlockBehaviour.Properties.of().strength(0.5f),
			structureName,
			modX, modY, modZ
		);
		String fullId = InstantMassiveStructures.MOD_ID + ":" + id;
		Registry.register(BuiltInRegistries.BLOCK, fullId, block);
		STRUCTURE_BLOCKS.add(block);
	}

	public static void registerItems() {
		for (Block block : STRUCTURE_BLOCKS) {
			String id = BuiltInRegistries.BLOCK.getKey(block).toString();
			Item item = new BlockItem(block, new Item.Properties());
			Registry.register(BuiltInRegistries.ITEM, id, item);
			STRUCTURE_ITEMS.add(item);
		}
	}

	public static Block getFirstStructureBlock() {
		return STRUCTURE_BLOCKS.isEmpty() ? null : STRUCTURE_BLOCKS.get(0);
	}
}
