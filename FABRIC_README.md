# Instant Massive Structures Mod - Fabric Port

## ⚠️ Work in Progress

This repository contains a **work-in-progress port** of the Instant Massive Structures Mod from Forge 1.10.2 to Fabric 1.21.1.

### Current Status: Infrastructure Setup Complete ✅

The build system and basic mod structure have been set up, but **the vast majority of functionality is not yet ported**.

## What's Been Done

- ✅ Gradle build system converted to Fabric Loom
- ✅ Mod metadata updated (`fabric.mod.json`)
- ✅ Basic mod initialization structure created
- ✅ CI/CD pipeline created (`.github/workflows/build.yml`)
- ✅ Updated to Minecraft 1.21.1 with latest Fabric API

## What's NOT Done Yet

The original Forge mod contains **917 Java files** with extensive features including:

- 🔲 Hundreds of structure blocks (residential, commercial, industrial, transportation, etc.)
- 🔲 Live animated structures (Ferris wheels, windmills, helicopters, etc.)
- 🔲 Custom world generation features
- 🔲 Crafting recipes for all structures
- 🔲 User-created structure loading system
- 🔲 Custom commands
- 🔲 Creative inventory tabs

**This is a multi-month porting project.**

## Building

### Requirements
- Java 21 or higher
- Access to `maven.fabricmc.net` (Fabric's Maven repository)

### Build Commands

```bash
# Build the mod
./gradlew build

# Run in development (when ported code is added)
./gradlew runClient

# Run dedicated server
./gradlew runServer
```

## Contributing

If you want to help with the porting effort:

1. Read the [PORTING_GUIDE.md](PORTING_GUIDE.md) for detailed instructions
2. Pick a category of structures to port
3. Follow the Fabric API patterns for registration and events
4. Test thoroughly before submitting PRs

## Original Mod

This is a port of the popular Instant Massive Structures Mod by SimJoo:
- Original Planet Minecraft page: [Link](https://www.planetminecraft.com/mod/11-instant-massive-structures-mod-v10/)
- Original repository: [GitHub](https://github.com/SimonBaars/Instant-Massive-Structures-Mod)

The original Forge version supports Minecraft 1.10.2.

## License

See [LICENSE](LICENSE-new.txt) file for license information.

## Support

This is a community port effort. For questions:
- Check the [PORTING_GUIDE.md](PORTING_GUIDE.md)
- Open an issue on GitHub
- Join the Fabric Discord for API questions

---

**Note**: This mod is in very early stages of porting. It will not work in its current state until the core functionality is ported from Forge to Fabric APIs.
