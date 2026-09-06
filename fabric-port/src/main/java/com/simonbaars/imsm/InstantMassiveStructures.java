package com.simonbaars.imsm;

import com.simonbaars.imsm.core.LiveStructureTicker;
import com.simonbaars.imsm.core.StructureRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstantMassiveStructures implements ModInitializer {
	public static final String MOD_ID = "imsm";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<CreativeModeTab> STRUCTURES_TAB = ResourceKey.create(
		Registries.CREATIVE_MODE_TAB,
		Identifier.fromNamespaceAndPath(MOD_ID, "structures")
	);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Instant Massive Structures Mod");

		StructureRegistry.registerBlocks();
		StructureRegistry.registerItems();
		LiveStructureTicker.init();

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, STRUCTURES_TAB,
			CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
				.icon(() -> new ItemStack(StructureRegistry.getFirstStructureBlock()))
				.title(Component.translatable("itemGroup.imsm.structures"))
				.displayItems((params, output) -> {
					for (Item item : StructureRegistry.getStructureItems()) {
						output.accept(item);
					}
				})
				.build());

		LOGGER.info("Instant Massive Structures Mod initialized; creative tab has {} items",
			StructureRegistry.getStructureItems().size());
	}
}
