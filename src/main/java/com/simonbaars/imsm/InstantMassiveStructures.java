package com.simonbaars.imsm;

import com.simonbaars.imsm.core.StructureRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstantMassiveStructures implements ModInitializer {
	public static final String MOD_ID = "imsm";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<CreativeModeTab> STRUCTURES_GROUP = 
		ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), 
			BuiltInRegistries.CREATIVE_MODE_TAB.getKey(BuiltInRegistries.CREATIVE_MODE_TAB.iterator().next()));

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Instant Massive Structures Mod");

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID + ":structures", 
			FabricItemGroup.builder()
				.icon(() -> new ItemStack(StructureRegistry.getFirstStructureBlock()))
				.title(Component.translatable("itemGroup.imsm.structures"))
				.build());

		StructureRegistry.registerBlocks();
		StructureRegistry.registerItems();

		LOGGER.info("Instant Massive Structures Mod initialized successfully");
	}
}
