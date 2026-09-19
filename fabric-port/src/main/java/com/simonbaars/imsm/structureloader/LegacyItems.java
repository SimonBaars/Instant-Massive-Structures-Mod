package com.simonbaars.imsm.structureloader;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Converts pre-flattening item IDs to modern items.
 * Legacy schematics store {@code id} (short) in ItemStack NBT.
 */
public final class LegacyItems {
	
	private LegacyItems() {}
	
	/**
	 * Map legacy numeric item ID to modern Item.
	 * Pre-1.13 items had numeric IDs; many matched block IDs but not all.
	 */
	public static Item fromLegacyId(int legacyId) {
		// Common legacy item IDs (subset for structure chests/furnaces)
		return switch (legacyId) {
			case 0 -> Items.AIR;
			case 1 -> Items.STONE;
			case 2 -> Items.GRASS_BLOCK;
			case 3 -> Items.DIRT;
			case 4 -> Items.COBBLESTONE;
			case 5 -> Items.OAK_PLANKS;
			case 6 -> Items.OAK_SAPLING;
			case 7 -> Items.BEDROCK;
			case 12 -> Items.SAND;
			case 13 -> Items.GRAVEL;
			case 14 -> Items.GOLD_ORE;
			case 15 -> Items.IRON_ORE;
			case 16 -> Items.COAL_ORE;
			case 17 -> Items.OAK_LOG;
			case 18 -> Items.OAK_LEAVES;
			case 20 -> Items.GLASS;
			case 22 -> Items.LAPIS_BLOCK;
			case 24 -> Items.SANDSTONE;
			case 35 -> Items.WHITE_WOOL;
			case 41 -> Items.GOLD_BLOCK;
			case 42 -> Items.IRON_BLOCK;
			case 45 -> Items.BRICKS;
			case 46 -> Items.TNT;
			case 49 -> Items.OBSIDIAN;
			case 50 -> Items.TORCH;
			case 54 -> Items.CHEST;
			case 56 -> Items.DIAMOND_ORE;
			case 57 -> Items.DIAMOND_BLOCK;
			case 58 -> Items.CRAFTING_TABLE;
			case 61 -> Items.FURNACE;
			case 65 -> Items.LADDER;
			case 79 -> Items.ICE;
			case 80 -> Items.SNOW_BLOCK;
			case 81 -> Items.CACTUS;
			case 82 -> Items.CLAY;
			case 85 -> Items.OAK_FENCE;
			case 86 -> Items.PUMPKIN;
			case 87 -> Items.NETHERRACK;
			case 89 -> Items.GLOWSTONE;
			case 98 -> Items.STONE_BRICKS;
			case 152 -> Items.REDSTONE_BLOCK;
			
			// Tools and armor
			case 256 -> Items.IRON_SHOVEL;
			case 257 -> Items.IRON_PICKAXE;
			case 258 -> Items.IRON_AXE;
			case 259 -> Items.FLINT_AND_STEEL;
			case 260 -> Items.APPLE;
			case 261 -> Items.BOW;
			case 262 -> Items.ARROW;
			case 263 -> Items.COAL;
			case 264 -> Items.DIAMOND;
			case 265 -> Items.IRON_INGOT;
			case 266 -> Items.GOLD_INGOT;
			case 267 -> Items.IRON_SWORD;
			case 268 -> Items.WOODEN_SWORD;
			case 269 -> Items.WOODEN_SHOVEL;
			case 270 -> Items.WOODEN_PICKAXE;
			case 271 -> Items.WOODEN_AXE;
			case 272 -> Items.STONE_SWORD;
			case 273 -> Items.STONE_SHOVEL;
			case 274 -> Items.STONE_PICKAXE;
			case 275 -> Items.STONE_AXE;
			case 276 -> Items.DIAMOND_SWORD;
			case 277 -> Items.DIAMOND_SHOVEL;
			case 278 -> Items.DIAMOND_PICKAXE;
			case 279 -> Items.DIAMOND_AXE;
			case 280 -> Items.STICK;
			case 281 -> Items.BOWL;
			case 287 -> Items.STRING;
			case 288 -> Items.FEATHER;
			case 289 -> Items.GUNPOWDER;
			case 290 -> Items.WOODEN_HOE;
			case 291 -> Items.STONE_HOE;
			case 292 -> Items.IRON_HOE;
			case 293 -> Items.DIAMOND_HOE;
			case 294 -> Items.GOLDEN_HOE;
			case 295 -> Items.WHEAT_SEEDS;
			case 296 -> Items.WHEAT;
			case 297 -> Items.BREAD;
			case 298 -> Items.LEATHER_HELMET;
			case 299 -> Items.LEATHER_CHESTPLATE;
			case 300 -> Items.LEATHER_LEGGINGS;
			case 301 -> Items.LEATHER_BOOTS;
			case 302 -> Items.CHAINMAIL_HELMET;
			case 303 -> Items.CHAINMAIL_CHESTPLATE;
			case 304 -> Items.CHAINMAIL_LEGGINGS;
			case 305 -> Items.CHAINMAIL_BOOTS;
			case 306 -> Items.IRON_HELMET;
			case 307 -> Items.IRON_CHESTPLATE;
			case 308 -> Items.IRON_LEGGINGS;
			case 309 -> Items.IRON_BOOTS;
			case 310 -> Items.DIAMOND_HELMET;
			case 311 -> Items.DIAMOND_CHESTPLATE;
			case 312 -> Items.DIAMOND_LEGGINGS;
			case 313 -> Items.DIAMOND_BOOTS;
			case 314 -> Items.GOLDEN_HELMET;
			case 315 -> Items.GOLDEN_CHESTPLATE;
			case 316 -> Items.GOLDEN_LEGGINGS;
			case 317 -> Items.GOLDEN_BOOTS;
			case 318 -> Items.FLINT;
			case 319 -> Items.PORKCHOP;
			case 320 -> Items.COOKED_PORKCHOP;
			case 321 -> Items.PAINTING;
			case 322 -> Items.GOLDEN_APPLE;
			case 323 -> Items.OAK_SIGN;
			case 324 -> Items.OAK_DOOR;
			case 325 -> Items.BUCKET;
			case 326 -> Items.WATER_BUCKET;
			case 327 -> Items.LAVA_BUCKET;
			case 328 -> Items.MINECART;
			case 329 -> Items.SADDLE;
			case 330 -> Items.IRON_DOOR;
			case 331 -> Items.REDSTONE;
			case 332 -> Items.SNOWBALL;
			case 333 -> Items.OAK_BOAT;
			case 334 -> Items.LEATHER;
			case 335 -> Items.MILK_BUCKET;
			case 336 -> Items.BRICK;
			case 337 -> Items.CLAY_BALL;
			case 338 -> Items.SUGAR_CANE;
			case 339 -> Items.PAPER;
			case 340 -> Items.BOOK;
			case 341 -> Items.SLIME_BALL;
			case 342 -> Items.CHEST_MINECART;
			case 344 -> Items.EGG;
			case 345 -> Items.COMPASS;
			case 346 -> Items.FISHING_ROD;
			case 347 -> Items.CLOCK;
			case 348 -> Items.GLOWSTONE_DUST;
			case 349 -> Items.COD;
			case 350 -> Items.COOKED_COD;
			case 351 -> Items.INK_SAC; // was "dye" with meta
			case 352 -> Items.BONE;
			case 353 -> Items.SUGAR;
			case 354 -> Items.CAKE;
			case 355 -> Items.WHITE_BED;
			case 356 -> Items.REPEATER;
			case 357 -> Items.COOKIE;
			case 358 -> Items.FILLED_MAP;
			case 359 -> Items.SHEARS;
			case 360 -> Items.MELON_SLICE;
			case 361 -> Items.PUMPKIN_SEEDS;
			case 362 -> Items.MELON_SEEDS;
			case 363 -> Items.BEEF;
			case 364 -> Items.COOKED_BEEF;
			case 365 -> Items.CHICKEN;
			case 366 -> Items.COOKED_CHICKEN;
			case 367 -> Items.ROTTEN_FLESH;
			case 368 -> Items.ENDER_PEARL;
			case 369 -> Items.BLAZE_ROD;
			case 370 -> Items.GHAST_TEAR;
			case 371 -> Items.GOLD_NUGGET;
			case 372 -> Items.NETHER_WART;
			case 373 -> Items.POTION;
			case 374 -> Items.GLASS_BOTTLE;
			case 375 -> Items.SPIDER_EYE;
			case 376 -> Items.FERMENTED_SPIDER_EYE;
			case 377 -> Items.BLAZE_POWDER;
			case 378 -> Items.MAGMA_CREAM;
			case 379 -> Items.BREWING_STAND;
			case 380 -> Items.CAULDRON;
			case 381 -> Items.ENDER_EYE;
			case 382 -> Items.GLISTERING_MELON_SLICE;
			case 383 -> Items.CHICKEN_SPAWN_EGG; // was spawn_egg with meta
			case 384 -> Items.EXPERIENCE_BOTTLE;
			case 385 -> Items.FIRE_CHARGE;
			case 386 -> Items.WRITABLE_BOOK;
			case 387 -> Items.WRITTEN_BOOK;
			case 388 -> Items.EMERALD;
			case 389 -> Items.ITEM_FRAME;
			case 390 -> Items.FLOWER_POT;
			case 391 -> Items.CARROT;
			case 392 -> Items.POTATO;
			case 393 -> Items.BAKED_POTATO;
			case 394 -> Items.POISONOUS_POTATO;
			case 395 -> Items.MAP;
			case 396 -> Items.GOLDEN_CARROT;
			case 397 -> Items.SKELETON_SKULL;
			case 398 -> Items.CARROT_ON_A_STICK;
			case 399 -> Items.NETHER_STAR;
			case 400 -> Items.PUMPKIN_PIE;
			case 401 -> Items.FIREWORK_ROCKET;
			case 402 -> Items.FIREWORK_STAR;
			case 403 -> Items.ENCHANTED_BOOK;
			case 404 -> Items.COMPARATOR;
			case 405 -> Items.NETHER_BRICK;
			case 406 -> Items.QUARTZ;
			case 407 -> Items.TNT_MINECART;
			case 408 -> Items.HOPPER_MINECART;
			case 417 -> Items.IRON_HORSE_ARMOR;
			case 418 -> Items.GOLDEN_HORSE_ARMOR;
			case 419 -> Items.DIAMOND_HORSE_ARMOR;
			case 420 -> Items.LEAD;
			case 421 -> Items.NAME_TAG;
			default -> Items.AIR; // Unknown legacy ID
		};
	}
}
