# Contributing to IMSM Fabric Port

Thank you for your interest in helping port the Instant Massive Structures Mod to Fabric! This is a massive community effort and all help is appreciated.

## 📋 Before You Start

1. Read [FABRIC_README.md](FABRIC_README.md) to understand the current state
2. Review [PORTING_GUIDE.md](PORTING_GUIDE.md) for technical details on API changes
3. Check [TODO.md](TODO.md) to see what needs to be done
4. Look at [EXAMPLE_BLOCK_PORT.md](EXAMPLE_BLOCK_PORT.md) for a complete porting example

## 🛠️ Setting Up Your Development Environment

### Prerequisites
- Java 21 or higher ([Download Adoptium JDK](https://adoptium.net/))
- Git
- A Java IDE (IntelliJ IDEA Community Edition recommended)
- Access to maven.fabricmc.net (check with `verify-environment.sh`)

### Initial Setup

```bash
# Clone the repository
git clone https://github.com/SimonBaars/Instant-Massive-Structures-Mod.git
cd Instant-Massive-Structures-Mod

# Verify your environment
./verify-environment.sh

# Build the mod
./gradlew build

# Generate IDE run configurations
./gradlew genSources

# Open in IntelliJ IDEA
# File -> Open -> Select the project directory
```

### Running in Development

```bash
# Run Minecraft client with the mod
./gradlew runClient

# Run dedicated server
./gradlew runServer
```

## 🎯 Good First Tasks

If you're new to the project, consider starting with:

### 1. Port a Category of Simple Blocks
Choose a small category from TODO.md and port all blocks:
- Food structures (10 blocks)
- Decoration structures (20 blocks)
- Remover blocks (5 blocks)

### 2. Create Recipe JSONs
Many blocks need crafting recipes. Each recipe is a simple JSON file.
See existing recipes in `data/imsm/recipes/` for examples.

### 3. Create Model Files
Each block needs:
- Block model JSON (`assets/imsm/models/block/`)
- Item model JSON (`assets/imsm/models/item/`)
- Blockstate JSON (`assets/imsm/blockstates/`)

### 4. Add Translations
Update `assets/imsm/lang/en_us.json` with block names and descriptions.

### 5. Documentation
- Add more examples to EXAMPLE_BLOCK_PORT.md
- Create video tutorials
- Write troubleshooting guides
- Take screenshots for documentation

## 📝 Porting Process

### For Structure Blocks

1. **Find the original Forge block** in `src/main/java/modid/imsm/structures/`
2. **Create the Fabric version** following the pattern in EXAMPLE_BLOCK_PORT.md
3. **Register the block** in `ModBlocks.java`
4. **Add to creative tab** via ItemGroupEvents
5. **Create the recipe** as a JSON file in `data/imsm/recipes/`
6. **Create model files**:
   - `assets/imsm/models/block/[name].json`
   - `assets/imsm/models/item/[name].json`
   - `assets/imsm/blockstates/[name].json`
7. **Add translation** to `assets/imsm/lang/en_us.json`
8. **Test in-game**:
   - Block appears in creative inventory
   - Recipe works
   - Block places and breaks correctly
   - Structure loads when clicked

### For Live Structures

Live structures are more complex as they involve:
- Animation systems
- Tick handlers
- Client/server synchronization
- Entity management
- Custom rendering

See Phase 3 in TODO.md for details.

## 🔄 Pull Request Process

1. **Create a feature branch**
   ```bash
   git checkout -b feature/port-food-structures
   ```

2. **Make your changes**
   - Follow existing code style
   - Keep commits atomic and well-described
   - Test thoroughly

3. **Update documentation**
   - Mark completed items in TODO.md
   - Update FABRIC_README.md if needed
   - Add your changes to CHANGELOG.md (create if doesn't exist)

4. **Test your changes**
   ```bash
   # Build
   ./gradlew build
   
   # Run in dev
   ./gradlew runClient
   
   # Test your ported features
   ```

5. **Commit with clear messages**
   ```bash
   git add .
   git commit -m "Port all Food structure blocks to Fabric
   
   - Ported FoodCarrots (2 directions)
   - Ported FoodFarm (4 directions)
   - Ported FoodStable (2 directions)
   - Created recipes for all blocks
   - Created model/blockstate files
   - Added translations
   - Tested in-game"
   ```

6. **Push and create PR**
   ```bash
   git push origin feature/port-food-structures
   ```
   Then create a Pull Request on GitHub.

## 📐 Code Style Guidelines

### Naming Conventions
- **Block constants**: `UPPER_SNAKE_CASE` (e.g., `BLOCK_HOUSE`)
- **Registry names**: `lower_snake_case` (e.g., `block_house`)
- **Class names**: `PascalCase` (e.g., `ModBlocks`)
- **Methods**: `camelCase` (e.g., `registerBlock`)

### File Organization
```
src/main/java/modid/imsm/
├── core/
│   ├── IMSMNew.java           # Main mod class
│   ├── IMSMClientNew.java     # Client init
│   ├── ModBlocks.java         # Block registration
│   ├── ModItems.java          # Item registration
│   └── ModItemGroups.java     # Creative tabs
├── blocks/
│   └── BlockStructure.java    # Base structure block
├── structures/
│   └── ...                    # Structure-specific logic
└── util/
    └── ...                    # Utility classes
```

### Documentation
- Add JavaDoc comments to public classes and methods
- Include `@param` and `@return` tags
- Explain complex logic with inline comments

## 🐛 Reporting Issues

When reporting bugs or requesting features:

1. **Search existing issues** to avoid duplicates
2. **Use issue templates** if available
3. **Provide details**:
   - Minecraft version
   - Fabric Loader version
   - Fabric API version
   - Steps to reproduce
   - Expected vs actual behavior
   - Crash logs/screenshots if applicable

## 💬 Getting Help

- **Questions about Fabric API?** Check [Fabric Wiki](https://fabricmc.net/wiki/) or join [Fabric Discord](https://discord.gg/v6v4pMv)
- **Questions about this port?** Open a GitHub Discussion or Issue
- **Want to coordinate with other contributors?** Comment on relevant issues in TODO.md

## 🎨 Design Principles

When porting, follow these principles:

1. **Stay true to the original** - Keep the same functionality when possible
2. **Use Fabric best practices** - Follow Fabric's recommended patterns
3. **Modern Minecraft APIs** - Use 1.21.1 APIs, not deprecated methods
4. **Performance** - Consider performance impact of changes
5. **Compatibility** - Test with common mods
6. **Documentation** - Document complex systems

## 🏆 Recognition

Contributors will be:
- Listed in the mod's credits
- Mentioned in release notes
- Credited in the mod's about screen

## 📜 License

By contributing, you agree that your contributions will be licensed under the same license as the project (see LICENSE files).

## 🤝 Code of Conduct

- Be respectful and constructive
- Help others learn
- Give credit where due
- Focus on the code, not the person
- Be patient with new contributors

## 🚀 Let's Build Together!

This is a huge project, but with community effort, we can bring this amazing mod to modern Minecraft! Every contribution, no matter how small, helps move the project forward.

Thank you for contributing! 🎉
