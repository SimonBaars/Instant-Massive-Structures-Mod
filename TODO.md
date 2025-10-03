# Porting TODO List

This document tracks all the work needed to complete the Fabric port of the Instant Massive Structures Mod.

## ✅ Phase 0: Infrastructure (COMPLETE)
- [x] Convert build.gradle to Fabric Loom
- [x] Update gradle wrapper to 8.8
- [x] Create gradle.properties with MC 1.21.1 settings
- [x] Create fabric.mod.json
- [x] Create basic mod initialization classes
- [x] Set up CI/CD pipeline
- [x] Create documentation (PORTING_GUIDE.md, EXAMPLE_BLOCK_PORT.md)

## 🔄 Phase 1: Core Systems

### Block Registration System
- [ ] Create ModBlocks class for centralized registration
- [ ] Create base BlockStructure class with Fabric APIs
- [ ] Implement structure loading system
- [ ] Port schematic file reader
- [ ] Implement outline visualization system
- [ ] Implement structure placement logic

### Item Registration System  
- [ ] Create ModItems class (if needed)
- [ ] Register BlockItems for all structure blocks

### Creative Tabs
- [ ] Create all 20+ creative tabs:
  - [ ] Structures
  - [ ] Decoration
  - [ ] Food
  - [ ] IndustryHigh_Density
  - [ ] IndustryMedium_Density
  - [ ] IndustryLow_Density
  - [ ] Office
  - [ ] Public
  - [ ] ResidentalEnormous_Density
  - [ ] ResidentalHigh_Density
  - [ ] ResidentalMedium_Density
  - [ ] ResidentalLow_Density
  - [ ] Shopping
  - [ ] TransportHarbour
  - [ ] TransportPublic
  - [ ] TransportRoads
  - [ ] TransportWater
  - [ ] Utility
  - [ ] Remover
  - [ ] Other
  - [ ] LiveStructures
  - [ ] User

## 📦 Phase 2: Structure Blocks (Massive!)

### Decoration Structures (~20 blocks)
- [ ] DecorationGrassNorthEastSouthWest
- [ ] DecorationPark (4 directions)
- [ ] DecorationParkingGarage (4 directions)
- [ ] DecorationParkingLots (4 directions)
- [ ] DecorationPlaza variants
- [ ] DecorationSoccerStadium (2 directions)
- [ ] DecorationSquare variants
- [ ] *...and more*

### Food Structures (~10 blocks)
- [ ] FoodCarrots (2 directions)
- [ ] FoodFarm (4 directions)
- [ ] FoodPotatoes
- [ ] FoodStable (2 directions)
- [ ] FoodWheat

### Industry High Density (~40 blocks)
- [ ] IndustryHigh_DensityBlue (4 directions)
- [ ] IndustryHigh_DensityBrick (4 directions)
- [ ] IndustryHigh_DensityChimney (4 directions)
- [ ] IndustryHigh_DensityComputerChip (4 directions)
- [ ] IndustryHigh_DensityGreen (4 directions)
- [ ] IndustryHigh_DensityLightBlue (4 directions)
- [ ] *...and more*

### Industry Medium Density (~30 blocks)
- [ ] Port all Industry Medium Density blocks

### Industry Low Density (~60 blocks)
- [ ] IndustryLow_Density3DPrinting (4 directions)
- [ ] IndustryLow_DensityBlue (4 directions)
- [ ] IndustryLow_DensityBrick variants
- [ ] IndustryLow_DensityBrown variants
- [ ] *...and more*

### Office Structures (~30 blocks)
- [ ] Port all Office structure blocks

### Public Structures (~30 blocks)
- [ ] Port all Public structure blocks

### Residential Structures (~200+ blocks)
- [ ] ResidentalEnormous_Density blocks
- [ ] ResidentalHigh_Density blocks
- [ ] ResidentalMedium_Density blocks
- [ ] ResidentalLow_Density blocks

### Shopping Structures (~30 blocks)
- [ ] Port all Shopping structure blocks

### Transport Structures (~100 blocks)
- [ ] TransportAirport blocks
- [ ] TransportHarbour blocks
- [ ] TransportPublic blocks
- [ ] TransportRoads blocks
- [ ] TransportWater blocks

### Utility Structures (~20 blocks)
- [ ] Port all Utility structure blocks

### Other Structures (~50 blocks)
- [ ] BlockHouse and variants
- [ ] BlockMegaHouse
- [ ] BlockStadium
- [ ] BlockRollerCoaster variants
- [ ] Random structures
- [ ] BlockWaterSlide
- [ ] BlockMaze
- [ ] *...and more*

### Remover Blocks (~5 blocks)
- [ ] RemoverLast
- [ ] RemoverAll
- [ ] RemoverSpecific
- [ ] *...etc*

## 🎪 Phase 3: Live Structures (Animated)

### Live Structure System
- [ ] Port base LiveStructure class
- [ ] Port LiveStructureClient class
- [ ] Port LiveStructureServer class
- [ ] Port UpdateThread
- [ ] Port animation system
- [ ] Update rendering to modern system

### Individual Live Structures (~20 blocks)
- [ ] BlockFerrisWheel
- [ ] LiveAirBalloon
- [ ] LiveAirplane
- [ ] LiveBoat
- [ ] LiveFlyingShip (2 variants)
- [ ] LivePlane
- [ ] Live_Bus (2 variants)
- [ ] Live_Cinema
- [ ] Live_Fair_FreeFall
- [ ] Live_Flying_Helicopter
- [ ] Live_Helicopter
- [ ] Live_Mill
- [ ] Live_Power_Windmill_East
- [ ] Live_WaterMill
- [ ] LiveStructureRemover

## 👤 Phase 4: User Structures

- [ ] Port BlockUnlimited
- [ ] Port BlockUserStructure
- [ ] Port PMCParser
- [ ] Update file loading system
- [ ] Test user structure imports

## 🌍 Phase 5: World Generation

- [ ] Port BlockBigWorld
- [ ] Port BlockCheckerboard
- [ ] Port BlockCloud
- [ ] Port MazeRunner system
- [ ] Convert to data-driven world gen where possible

## 🎮 Phase 6: Commands

- [ ] Port LiveCommand
- [ ] Port MazeCommand  
- [ ] Port RideCommand
- [ ] Port UndoCommand
- [ ] Update to Fabric's Brigadier command system

## 📝 Phase 7: Recipes (Huge!)

Each structure block needs a crafting recipe. Estimate ~500+ recipes to create as JSON files.

- [ ] Create recipe JSON files for all blocks
- [ ] Organize recipes by category
- [ ] Test all recipes work correctly

## 🎨 Phase 8: Assets

### Models & Textures
- [ ] Verify all existing textures are compatible
- [ ] Create block model JSONs for all blocks
- [ ] Create item model JSONs for all blocks
- [ ] Create blockstate JSONs for all blocks

### Translations
- [ ] Add all block translations to en_us.json
- [ ] Add all item group translations
- [ ] Add any command feedback translations

## 🔧 Phase 9: Event Handlers

- [ ] Port EventHandler class
- [ ] Port ForgeEventHandler class
- [ ] Convert to Fabric event system
- [ ] Test all events fire correctly

## 📊 Phase 10: Data Management

- [ ] Port structure save/load system
- [ ] Update file paths for Fabric
- [ ] Port schematic structure reader
- [ ] Update any file format changes

## 🧪 Phase 11: Testing

### Functional Testing
- [ ] Test each structure category places correctly
- [ ] Test all live structures animate properly
- [ ] Test user structure loading
- [ ] Test world generation features
- [ ] Test all commands
- [ ] Test multiplayer compatibility

### Performance Testing
- [ ] Profile structure placement performance
- [ ] Profile live structure animation performance
- [ ] Test with many structures placed
- [ ] Memory usage testing

### Compatibility Testing
- [ ] Test on Windows
- [ ] Test on Linux
- [ ] Test on macOS
- [ ] Test with different Fabric API versions
- [ ] Test with other popular mods

## 📦 Phase 12: Release Preparation

- [ ] Write user documentation
- [ ] Create showcase screenshots/videos
- [ ] Set up CurseForge/Modrinth pages
- [ ] Create release notes
- [ ] Set up issue templates on GitHub
- [ ] Create contributing guidelines

## 📈 Estimated Effort

- **Phase 1 (Core Systems)**: 2-3 weeks
- **Phase 2 (Structure Blocks)**: 6-8 weeks (due to volume)
- **Phase 3 (Live Structures)**: 3-4 weeks (complex rendering)
- **Phase 4 (User Structures)**: 1 week
- **Phase 5 (World Generation)**: 2 weeks
- **Phase 6 (Commands)**: 1 week
- **Phase 7 (Recipes)**: 2-3 weeks (creating ~500 JSON files)
- **Phase 8 (Assets)**: 3-4 weeks (creating model/blockstate JSONs)
- **Phase 9 (Event Handlers)**: 1 week
- **Phase 10 (Data Management)**: 1 week
- **Phase 11 (Testing)**: 2-3 weeks
- **Phase 12 (Release Prep)**: 1 week

**Total Estimated Time**: ~25-35 weeks (6-8 months) for a single developer

## 💡 Contribution Opportunities

Good places for new contributors to start:
1. Port blocks from a single category (e.g., all Food structures)
2. Create recipe JSON files for existing blocks
3. Create model/blockstate JSON files
4. Test and report bugs
5. Write documentation
6. Create screenshots/videos for showcase

## 🚀 Quick Wins

To get something working quickly:
1. Port 5-10 simple structure blocks
2. Implement basic structure placement
3. Create recipes for those blocks
4. Get them working in-game
5. Expand from there

## 📞 Help Needed

This is a massive undertaking. Community help is greatly appreciated! Check the main README for how to contribute.
