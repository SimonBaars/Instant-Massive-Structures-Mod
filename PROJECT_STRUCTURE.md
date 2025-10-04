# Project Structure

## Repository Layout (After Infrastructure Setup)

```
Instant-Massive-Structures-Mod/
├── .github/
│   └── workflows/
│       └── build.yml                    # CI/CD pipeline for automated builds
│
├── .old-forge/                          # Backup of original Forge build files
│   ├── build.gradle
│   └── gradle-wrapper.properties
│
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties    # Gradle 8.8 configuration
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── modid/
│   │   │       └── imsm/
│   │   │           ├── core/
│   │   │           │   ├── IMSM.java                    # ❗ OLD - Forge version (3,217 lines!)
│   │   │           │   ├── IMSMNew.java                 # ✅ NEW - Fabric main initializer
│   │   │           │   ├── IMSMProxy.java               # ❗ OLD - Forge proxy
│   │   │           │   ├── EventHandler.java            # ❗ OLD - Needs porting
│   │   │           │   ├── ForgeEventHandler.java       # ❗ OLD - Needs porting
│   │   │           │   ├── BlockStructure.java          # ❗ OLD - Needs updating
│   │   │           │   └── [15+ other core files]       # ❗ OLD - Need porting
│   │   │           │
│   │   │           ├── structures/                      # ❗ 500+ blocks to port!
│   │   │           │   ├── DecorationParkSouth.java
│   │   │           │   ├── FoodFarmEast.java
│   │   │           │   ├── BlockHouse.java
│   │   │           │   └── [500+ more files...]
│   │   │           │
│   │   │           ├── livestructures/                  # ❗ 20+ animated structures
│   │   │           │   ├── BlockFerrisWheel.java
│   │   │           │   ├── LiveAirplane.java
│   │   │           │   ├── Live_Mill.java
│   │   │           │   └── [20+ more files...]
│   │   │           │
│   │   │           ├── userstructures/                  # User structure loading
│   │   │           │   ├── BlockUnlimited.java
│   │   │           │   ├── BlockUserStructure.java
│   │   │           │   └── PMCParser.java
│   │   │           │
│   │   │           ├── worldgeneration/                 # World gen features
│   │   │           │   ├── BlockBigWorld.java
│   │   │           │   ├── MazeRunner.java
│   │   │           │   └── [10+ more files...]
│   │   │           │
│   │   │           └── structureloader/                 # Structure file loading
│   │   │               └── SchematicStructure.java
│   │   │
│   │   └── resources/
│   │       ├── mcmod.info                               # ❗ OLD - Forge metadata
│   │       ├── fabric.mod.json                          # ✅ NEW - Fabric metadata
│   │       │
│   │       └── assets/
│   │           └── imsm/
│   │               ├── icon.png                         # ✅ NEW - Mod icon
│   │               │
│   │               ├── lang/
│   │               │   └── en_us.json                   # ✅ NEW - Translations
│   │               │
│   │               ├── models/
│   │               │   ├── block/                       # ❗ Need JSON files for all blocks
│   │               │   │   ├── BlockHouse.json
│   │               │   │   └── [500+ more needed...]
│   │               │   │
│   │               │   └── item/                        # ❗ Need JSON files for all items
│   │               │       ├── BlockHouse.json
│   │               │       └── [500+ more needed...]
│   │               │
│   │               ├── blockstates/                     # ❗ Need JSON files for all blocks
│   │               │   ├── BlockHouse.json
│   │               │   └── [500+ more needed...]
│   │               │
│   │               └── textures/
│   │                   └── block/
│   │                       └── [many PNG files...]     # ✅ Textures already exist
│   │
│   └── client/
│       └── java/
│           └── modid/
│               └── imsm/
│                   └── core/
│                       ├── IMSMClient.java              # ❗ OLD - Forge client
│                       └── IMSMClientNew.java           # ✅ NEW - Fabric client initializer
│
├── data/                                                # ❗ NEW - Need to create
│   └── imsm/
│       └── recipes/                                     # ❗ Need 500+ recipe JSON files
│           ├── block_house.json
│           └── [500+ more needed...]
│
├── build.gradle                                         # ✅ UPDATED - Fabric Loom config
├── gradle.properties                                    # ✅ NEW - Version properties
├── settings.gradle                                      # ✅ NEW - Build settings
├── gradlew                                              # ✅ UPDATED - Gradle 8.8
├── gradlew.bat                                          # Gradle wrapper for Windows
│
├── .gitignore                                           # ✅ UPDATED - For Fabric dev
│
├── LICENSE-new.txt                                      # Existing license
├── LICENSE.md                                           # Existing license
├── README.md                                            # ❗ Could be updated
├── README.txt                                           # OLD - Forge setup instructions
│
├── FABRIC_README.md                                     # ✅ NEW - Port overview
├── PORTING_GUIDE.md                                     # ✅ NEW - Technical porting guide
├── EXAMPLE_BLOCK_PORT.md                                # ✅ NEW - Complete porting example
├── TODO.md                                              # ✅ NEW - Work breakdown
├── CONTRIBUTING.md                                      # ✅ NEW - Contributor guide
├── PROJECT_STATUS.md                                    # ✅ NEW - Comprehensive status
├── WORK_SUMMARY.md                                      # ✅ NEW - What was accomplished
├── PROJECT_STRUCTURE.md                                 # ✅ NEW - This file
│
└── verify-environment.sh                                # ✅ NEW - Environment checker
```

## Legend

- ✅ **NEW** - Created during Fabric port infrastructure setup
- ✅ **UPDATED** - Updated from Forge to Fabric  
- ❗ **OLD** - Forge code that needs porting to Fabric
- ❗ **Need to create** - Files that don't exist yet but are needed

## Key Directories Explained

### `/src/main/java/modid/imsm/`
Contains all Java source code. Most of it (917 files) needs porting from Forge to Fabric APIs.

### `/src/main/resources/`
Contains mod metadata, assets, and resources. Some converted to Fabric, but needs many new JSON files for models and blockstates.

### `/src/client/java/`
Client-side only code. Fabric uses a separate source set for client code.

### `/data/imsm/`
Data-driven content (recipes, loot tables, etc.). Needs to be created - Fabric uses JSON files for recipes instead of programmatic registration.

### `/.github/workflows/`
CI/CD pipeline configuration. Ready to use once the mod builds successfully.

## Statistics

### Existing Files (From Forge)
- **Java files**: 917
- **Texture files**: Hundreds (PNG images)
- **Model JSON files**: Many (but need updating for new format)
- **Total lines of code**: Tens of thousands

### New Files Created
- **Build config**: 3 files
- **Source code**: 2 Java files
- **Resources**: 3 files (icon, lang, fabric.mod.json)
- **Documentation**: 8 Markdown files (35,000+ words)
- **CI/CD**: 1 workflow file
- **Tools**: 1 shell script

### Files Still Needed
- **Recipe JSONs**: ~500 files
- **Block model JSONs**: ~500 files
- **Item model JSONs**: ~500 files
- **Blockstate JSONs**: ~500 files
- **Ported Java files**: 917 files need updating

## Development Workflow

### Current State
```
┌─────────────────┐
│  Infrastructure │  ✅ COMPLETE
│   & Build Sys  │
└────────┬────────┘
         │
         ├─> Build Configuration ✅
         ├─> Mod Metadata ✅
         ├─> Basic Mod Classes ✅
         ├─> CI/CD Pipeline ✅
         └─> Documentation ✅
```

### Next Phase (Core Systems)
```
┌─────────────────┐
│  Core Systems   │  ⏳ TODO (2-3 weeks)
└────────┬────────┘
         │
         ├─> Block Registration System
         ├─> Structure Loading System
         ├─> Schematic File Reader
         └─> Creative Tabs (20+)
```

### Subsequent Phases
```
┌──────────────────────┐
│  Structure Blocks    │  ⏳ TODO (6-8 weeks)
│  (500+ blocks)       │
└──────────────────────┘
         │
┌──────────────────────┐
│  Live Structures     │  ⏳ TODO (3-4 weeks)
│  (20+ animated)      │
└──────────────────────┘
         │
┌──────────────────────┐
│  Features & Polish   │  ⏳ TODO (8+ weeks)
│  (Commands, recipes, │
│   world gen, etc.)   │
└──────────────────────┘
```

## File Relationships

### How A Structure Block Works

```
User clicks block in creative inventory
         ↓
┌────────────────────────┐
│  Block Registration    │  ← ModBlocks.java
│  (in code)             │     registers the block
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│  Block Class           │  ← IndustryHigh_DensityBlueEast.java
│  (behavior)            │     extends BlockStructure
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│  Block Model           │  ← assets/imsm/models/block/*.json
│  (appearance)          │     defines how block looks
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│  Blockstate            │  ← assets/imsm/blockstates/*.json
│  (state→model mapping) │     maps block states to models
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│  Item Model            │  ← assets/imsm/models/item/*.json
│  (inventory icon)      │     how it looks in inventory
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│  Recipe                │  ← data/imsm/recipes/*.json
│  (how to craft)        │     crafting recipe
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│  Translation           │  ← assets/imsm/lang/en_us.json
│  (display name)        │     human-readable name
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│  Structure File        │  ← structures/*.structure
│  (what to build)       │     schematic of building
└────────────────────────┘
```

Each structure block needs ALL of these files to work correctly!

## Build Output

When built successfully, you'll get:
```
build/
├── libs/
│   ├── instant-massive-structures-2.0.0.jar        # Main mod JAR
│   └── instant-massive-structures-2.0.0-sources.jar # Source code JAR
│
├── classes/                                         # Compiled Java classes
├── resources/                                       # Processed resources
└── tmp/                                             # Temporary build files
```

## How to Navigate This Project

1. **Start with** `WORK_SUMMARY.md` - Understand what's been done
2. **Then read** `FABRIC_README.md` - Get the overview
3. **Study** `EXAMPLE_BLOCK_PORT.md` - See a complete example
4. **Reference** `PORTING_GUIDE.md` - For API conversion patterns
5. **Follow** `TODO.md` - For what to do next
6. **Contribute using** `CONTRIBUTING.md` - Guidelines and workflow

## Tips for Contributors

- Start in `src/main/java/modid/imsm/structures/` - This is where most blocks are
- Each Java file usually needs 4-5 accompanying JSON files
- Test each ported block before moving to the next
- Use `verify-environment.sh` before starting
- Follow patterns in `EXAMPLE_BLOCK_PORT.md`

---

This structure represents the current state after infrastructure setup. Most actual mod functionality still resides in the old Forge code and needs porting!
