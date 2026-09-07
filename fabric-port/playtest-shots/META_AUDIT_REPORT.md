# IMS Schematic Metadata Audit Report

Date: 2026-09-06 (America/Phoenix)
Source: `src/main/resources/assets/imsm/structs/*.structure`
Mapper: `LegacyBlockStates.fromLegacy` (coverage model)

## Summary

| Metric | Value |
|--------|------:|
| Structure files found | 952 |
| Schematics scanned OK | 952 |
| Parse errors | 0 |
| Unique (id, meta) pairs | 626 |
| Pairs with meta≠0 | 504 |
| Covered (mapKnown + orient) | 424 |
| Uncovered high-impact | 0 |
| Uncovered harmless/N/A | 78 |
| Uncovered other | 0 |
| Unmapped meta≠0 (suspicious) | 2 |

## Covered meta≠0 (mapKnown / orient) — sample by volume

| id | name | meta | voxels | #structs | note |
|---:|------|-----:|-------:|---------:|------|
| 159 | terracotta | 4 | 184361 | 64 |  |
| 155 | quartz | 2 | 144549 | 69 |  |
| 95 | stained_glass | 3 | 116984 | 28 |  |
| 35 | wool | 7 | 103993 | 211 |  |
| 5 | planks | 2 | 75074 | 81 |  |
| 24 | sandstone | 2 | 61649 | 39 |  |
| 159 | terracotta | 3 | 60994 | 79 |  |
| 44 | stone_slab | 8 | 57876 | 309 |  |
| 95 | stained_glass | 9 | 48448 | 21 |  |
| 44 | stone_slab | 1 | 48084 | 316 |  |
| 44 | stone_slab | 7 | 47592 | 181 |  |
| 159 | terracotta | 6 | 40953 | 29 |  |
| 156 | quartz_stairs | 5 | 36343 | 153 |  |
| 156 | quartz_stairs | 7 | 34715 | 153 |  |
| 156 | quartz_stairs | 6 | 34472 | 156 |  |
| 156 | quartz_stairs | 4 | 33951 | 154 |  |
| 35 | wool | 8 | 27980 | 21 |  |
| 5 | planks | 1 | 25861 | 98 |  |
| 159 | terracotta | 8 | 24433 | 24 |  |
| 35 | wool | 3 | 22517 | 56 |  |
| 31 | id_31 | 1 | 18391 | 85 |  |
| 159 | terracotta | 5 | 17036 | 39 |  |
| 155 | quartz | 1 | 16537 | 46 |  |
| 171 | carpet | 5 | 15722 | 2 |  |
| 44 | stone_slab | 15 | 15010 | 94 |  |
| 159 | terracotta | 1 | 14797 | 12 |  |
| 18 | leaves | 7 | 13907 | 14 |  |
| 35 | wool | 14 | 13681 | 52 |  |
| 50 | torch | 3 | 12780 | 450 |  |
| 50 | torch | 4 | 12776 | 448 |  |
| 50 | torch | 1 | 12728 | 462 |  |
| 50 | torch | 2 | 12508 | 454 |  |
| 35 | wool | 5 | 12406 | 45 |  |
| 159 | terracotta | 11 | 10420 | 12 |  |
| 35 | wool | 13 | 10380 | 56 |  |
| 1 | stone | 3 | 9520 | 22 |  |
| 1 | stone | 4 | 8800 | 22 |  |
| 5 | planks | 3 | 7993 | 121 |  |
| 159 | terracotta | 15 | 7934 | 13 |  |
| 35 | wool | 4 | 7926 | 10 |  |
| … | (384 more) | | | | |

## HIGH-IMPACT uncovered (mapper ignores meaningful meta)

_None._

## Harmless / N/A uncovered (age, fluid level, pressure, etc.)

| id | name | meta | voxels | #structs | note |
|---:|------|-----:|-------:|---------:|------|
| 9 | water | 9 | 24030 | 20 | water level |
| 9 | water | 10 | 4324 | 10 | water level |
| 9 | water | 1 | 3320 | 36 | water level |
| 60 | farmland | 7 | 2128 | 12 | farmland moisture |
| 9 | water | 2 | 1306 | 23 | water level |
| 9 | water | 3 | 918 | 9 | water level |
| 9 | water | 4 | 891 | 8 | water level |
| 9 | water | 5 | 843 | 7 | water level |
| 9 | water | 6 | 783 | 5 | water level |
| 9 | water | 7 | 742 | 5 | water level |
| 141 | carrots | 7 | 702 | 4 | carrots age |
| 59 | wheat | 7 | 383 | 5 | wheat age |
| 118 | cauldron | 3 | 367 | 4 | cauldron level |
| 142 | potatoes | 7 | 272 | 3 | potatoes age |
| 9 | water | 15 | 264 | 4 | water level |
| 55 | redstone_wire | 15 | 137 | 13 | redstone_wire power |
| 9 | water | 8 | 115 | 4 | water level |
| 59 | wheat | 1 | 66 | 1 | wheat age |
| 55 | redstone_wire | 14 | 58 | 13 | redstone_wire power |
| 83 | sugar_cane | 4 | 52 | 1 | sugar_cane age |
| 83 | sugar_cane | 5 | 40 | 1 | sugar_cane age |
| 83 | sugar_cane | 6 | 40 | 1 | sugar_cane age |
| 51 | fire | 15 | 37 | 15 | fire age |
| 9 | water | 14 | 34 | 4 | water level |
| 59 | wheat | 2 | 33 | 1 | wheat age |
| 55 | redstone_wire | 13 | 28 | 12 | redstone_wire power |
| 55 | redstone_wire | 12 | 25 | 12 | redstone_wire power |
| 55 | redstone_wire | 2 | 24 | 8 | redstone_wire power |
| 55 | redstone_wire | 3 | 24 | 8 | redstone_wire power |
| 83 | sugar_cane | 3 | 23 | 1 | sugar_cane age |
| 83 | sugar_cane | 8 | 23 | 1 | sugar_cane age |
| 105 | melon_stem | 1 | 23 | 1 | melon_stem age |
| 151 | daylight_detector | 3 | 23 | 6 | daylight_detector power |
| 55 | redstone_wire | 4 | 22 | 7 | redstone_wire power |
| 55 | redstone_wire | 11 | 21 | 12 | redstone_wire power |
| 60 | farmland | 6 | 21 | 1 | farmland moisture |
| 83 | sugar_cane | 7 | 20 | 1 | sugar_cane age |
| 55 | redstone_wire | 1 | 19 | 8 | redstone_wire power |
| 60 | farmland | 5 | 19 | 1 | farmland moisture |
| 55 | redstone_wire | 6 | 18 | 6 | redstone_wire power |
| … | (38 more) | | | | |

## Other uncovered

_None._

## Unmapped meta≠0 (not in gap catalog — investigate)

| id | name | meta | voxels | #structs | note |
|---:|------|-----:|-------:|---------:|------|
| 84 | id_84 | 1 | 2 | 2 |  |
| 52 | id_52 | 2 | 1 | 1 |  |

## Top structures by uncovered meta≠0 voxel volume

| structure | meta≠0 voxels | uncovered voxels |
|-----------|--------------:|-----------------:|
| UtilityPower_NuclearEast | 12152 | 8674 |
| UtilityPower_NuclearNorth | 12176 | 8674 |
| UtilityPower_NuclearWest | 12152 | 8674 |
| UtilityPower_NuclearSouth | 12165 | 8667 |
| FoodCarrotsEastWest | 1434 | 688 |
| FoodCarrotsNorthSouth | 1436 | 688 |
| FoodPotatoesNorthEastSouthWest | 504 | 504 |
| FoodWheatNorthEastSouthWest | 504 | 504 |
| BlockWaterSlide | 451 | 451 |
| RandomImmense_White_House | 276327 | 448 |
| BlockFarm | 347 | 347 |
| PublicTownhallBigEastWest | 37810 | 328 |
| PublicTownhallBigNorthSouth | 37816 | 328 |
| BlockFarm3 | 286 | 286 |
| BlockFarm4 | 274 | 274 |
| BlockFarm2 | 227 | 227 |
| RandomImmense_Buildingcomplex | 122169 | 191 |
| RandomSandstoneBuilding | 5527 | 178 |
| PublicLibraryEastWest | 16038 | 164 |
| PublicLibraryNorthSouth | 15957 | 164 |
| ShoppingHigh_DensityQuartzEastWest | 16157 | 164 |
| ShoppingHigh_DensityQuartzNorthSouth | 16109 | 164 |
| OtherSurvivorHouse8 | 2077 | 140 |
| BlockMegaHouse | 1490 | 128 |
| RandomSandstonewithFarm | 686 | 108 |
| DecorationPlazaFountainNorthEastSouthWest | 340 | 104 |
| IndustryLow_DensityTelescopeWest | 588 | 94 |
| IndustryLow_DensityTelescopeSouth | 571 | 85 |
| ResidentalLow_DensityStoneEast | 1748 | 80 |
| ResidentalLow_DensityStoneNorth | 1767 | 80 |
| ResidentalLow_DensityStoneSouth | 1772 | 80 |
| ResidentalLow_DensityStoneWest | 1749 | 80 |
| BlockMegaHouse2 | 1863 | 54 |
| Live_WaterMill | 1250 | 52 |
| RandomImmense_greenroof | 48322 | 40 |
| Live_WaterMill2 | 121 | 39 |
| IndustryLow_DensityTelescopeEast | 537 | 36 |
| IndustryLow_DensityTelescopeNorth | 531 | 36 |
| Live_WaterMill1 | 116 | 34 |
| Live_WaterMill0 | 119 | 31 |

## Suggested playtest set (high meta≠0 density)

| structure | meta≠0 voxels |
|-----------|--------------:|
| RandomImmense_White_House | 276327 |
| RandomImmense_Buildingcomplex | 122169 |
| RandomImmense_greenroof | 48322 |
| OfficeHigh_DensitySpirolBuildingNorth | 42282 |
| OfficeHigh_DensitySpirolBuildingSouth | 42272 |
| OfficeHigh_DensitySpirolBuildingEast | 42223 |
| OfficeHigh_DensitySpirolBuildingWest | 42195 |
| PublicTownhallBigNorthSouth | 37816 |
| PublicTownhallBigEastWest | 37810 |
| Live_Cinema | 25668 |
| BlockMegaTower | 25335 |
| ResidentalEnormous_DensityModernEast | 16260 |
| ResidentalEnormous_DensityModernNorth | 16202 |
| ShoppingHigh_DensityQuartzEastWest | 16157 |
| ShoppingHigh_DensityQuartzNorthSouth | 16109 |
| PublicLibraryEastWest | 16038 |
| ResidentalEnormous_DensityModernWest | 16031 |
| ResidentalEnormous_DensityModernSouth | 15967 |
| PublicLibraryNorthSouth | 15957 |
| PublicHospitalBigSouth | 14199 |
| PublicHospitalBigNorth | 14122 |
| PublicHospitalBigWest | 14092 |
| PublicHospitalBigEast | 14060 |
| DecorationSoccerStadiumNorthSouth | 13301 |
| DecorationSoccerStadiumEastWest | 13300 |
| UtilityPower_OilCoalEast | 12895 |
| UtilityPower_OilCoalSouth | 12895 |
| UtilityPower_OilCoalNorth | 12893 |
| UtilityPower_OilCoalWest | 12856 |
| UtilityPower_NuclearNorth | 12176 |

## All legacy IDs that appear with meta≠0

1(stone), 5(planks), 9(water), 17(log), 18(leaves), 23(dispenser), 24(sandstone), 26(bed), 27(powered_rail), 28(detector_rail), 29(sticky_piston), 31(id_31), 33(piston), 34(piston_head), 35(wool), 38(id_38), 43(double_stone_slab), 44(stone_slab), 50(torch), 51(fire), 52(id_52), 53(oak_stairs), 54(chest), 55(redstone_wire), 59(wheat), 60(farmland), 61(furnace), 62(lit_furnace), 64(oak_door), 65(ladder), 66(rail), 67(cobble_stairs), 68(wall_sign), 69(lever), 71(iron_door), 75(unlit_redstone_torch), 76(redstone_torch), 77(stone_button), 81(cactus), 83(sugar_cane), 84(id_84), 86(pumpkin), 90(id_90), 93(repeater), 94(powered_repeater), 95(stained_glass), 96(trapdoor), 97(id_97), 98(stonebrick), 104(pumpkin_stem), 105(melon_stem), 106(vine), 107(fence_gate), 108(brick_stairs), 109(stone_brick_stairs), 114(nether_brick_stairs), 117(brewing_stand), 118(cauldron), 125(double_wood_slab), 126(wood_slab), 128(sandstone_stairs), 131(tripwire_hook), 134(spruce_stairs), 135(birch_stairs), 136(jungle_stairs), 140(flower_pot), 141(carrots), 142(potatoes), 143(wood_button), 144(skull), 145(anvil), 151(daylight_detector), 154(hopper), 155(quartz), 156(quartz_stairs), 159(terracotta), 160(stained_glass_pane), 161(leaves2), 162(log2), 163(acacia_stairs), 164(dark_oak_stairs), 170(hay), 171(carpet), 175(double_plant), 180(red_sandstone_stairs), 183(spruce_fence_gate), 193(spruce_door)

## In-game metastats batch (2026-09-06 PT evening / 2026-09-07 UTC)

Harness: `-Pmetashot` → `MetaOrientationPlaytestShot` (24 metastats + 10 place/shot pairs).

| structure | blocks | stairs | facings | upsideDown | nonWhiteColor | logAxisNonY |
|-----------|-------:|-------:|---------|------------:|--------------:|------------:|
| WoodenHouse | 1493 | 15 | N5 S2 W4 E4 | 4 | 0 | 16 |
| Live_FerrisWheel | 9723 | 1596 | S20 W20 E1556 | 0 | 3628 | 0 |
| BlockCosyHouse | 718 | 6 | S2 W2 E2 | 2 | 0 | 0 |
| BlockCastleTower | 2018 | 16 | S10 E6 | 0 | 12 | 0 |
| BlockStadium | 75265 | 2190 | E2190 | 0 | 432 | 0 |
| ChristmasHouse | 1036 | 0 | {} | 0 | 13 | 0 |
| ChristmasMarket | 3888 | 34 | E34 | 0 | 236 | 0 |
| BlockMegaHouse | 3851 | 105 | N17 S16 W14 E58 | 25 | 0 | 0 |
| BlockHountedHouse | 4562 | 97 | N1 S3 W3 E90 | 1 | 0 | 0 |
| BlockPrison | 2144 | 144 | N10 S4 W4 E126 | 10 | 0 | 0 |
| BlockLighthouse | 646 | 1 | W1 | 1 | 0 | 0 |
| Live_Cinema | 30854 | 65 | mixed | 17 | 18724 | 0 |
| DecorationSoccerStadiumNorthSouth | 62390 | 0 | {} | 0 | 11549 | 0 |
| PublicLibraryNorthSouth | 29515 | 411 | mixed | 93 | 4996 | 176 |
| PublicHospitalBigNorth | 27228 | 537 | mixed | 123 | 11252 | 0 |
| PublicTownhallBigNorthSouth | 70490 | 1015 | mixed | 269 | 11160 | 352 |
| RandomImmense_White_House | 540832 | 8615 | mixed | 1908 | 154911 | 4 |
| ResidentalEnormous_DensityModernNorth | 30862 | 609 | mixed | 167 | 0 | 0 |
| ShoppingHigh_DensityQuartzNorthSouth | 28743 | 399 | mixed | 82 | 4996 | 176 |
| OfficeHigh_DensitySpirolBuildingNorth | 52664 | 812 | N107 S12 W562 E131 | 603 | 0 | 0 |
| FoodCarrotsNorthSouth | 3788 | 112 | mixed | 24 | 0 | 0 |

Screenshots: `playtest-shots/118-meta-*.png` … `137-meta-*.png` (10 structures × 2 views).

## Mapper fixes applied this pass

Extended `LegacyBlockStates` for schematic gaps found by offline audit:

- Doors (64/71/193–197): half, facing, open, hinge, powered
- Bed (26): facing, head/foot, occupied
- Vines (106): NESW attachment bits
- Tripwire hook (131), repeaters (93/94)
- Skull (144) → wall skull facing
- Wall/standing sign rotation (68/63), banner rotation (176)
- Tall grass/fern (31), flower variants (38), infested stone (97)
- Nether portal axis (90), flower-pot contents (140)

## Residual risks (honest)

- **Harmless N/A (78 pairs):** water/lava level, crop age, farmland moisture, fire age,
  redstone power, pressure-plate power, cauldron level, sugar-cane age, etc.
  Static IMS placements do not need these for visual correctness.
- **Unmapped meta≠0 (2 pairs):** jukebox `has_record` (id 84, 2 voxels) and spawner meta
  (id 52, 1 voxel) — no visual state; leave as default.
- **Door halves** are placed independently from schematic bytes; vanilla may later
  reconcile hinge/facing with the neighboring half on update — acceptable for port fidelity.
- **Mushroom-block faces (99/100)** did not appear with meta≠0 in the 952-schematic corpus.
- Soft `HORIZONTAL_FACING` fallback remains for miscellaneous wall-mounted blocks with meta 2–5.

