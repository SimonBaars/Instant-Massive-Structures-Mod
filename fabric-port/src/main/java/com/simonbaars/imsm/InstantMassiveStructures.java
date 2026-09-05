package com.simonbaars.imsm;

import com.simonbaars.imsm.core.LiveStructureTicker;
import com.simonbaars.imsm.core.StructureRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstantMassiveStructures implements ModInitializer {
	public static final String MOD_ID = "imsm";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Instant Massive Structures Mod");

		StructureRegistry.registerBlocks();
		StructureRegistry.registerItems();
		LiveStructureTicker.init();

		ResourceKey<CreativeModeTab> tabKey = ResourceKey.create(
			BuiltInRegistries.CREATIVE_MODE_TAB.key(),
			Identifier.fromNamespaceAndPath(MOD_ID, "structures")
		);

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, tabKey, 
			FabricCreativeModeTab.builder()
				.icon(() -> new ItemStack(StructureRegistry.getFirstStructureBlock()))
				.title(Component.translatable("itemGroup.imsm.structures"))
				.build());

		LOGGER.info("Instant Massive Structures Mod initialized successfully");
	}
}
