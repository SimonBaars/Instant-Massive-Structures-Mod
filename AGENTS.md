# AGENTS.md

## Cursor Cloud specific instructions

### Project overview

This is a Minecraft Forge mod called "Instant Massive Structures Mod" (IMSM) for Minecraft 1.15.2 with Forge 31.2.57 (branch `mc-1.15`). It uses ForgeGradle 3.0.197 with Gradle 4.10.3.

### Java version requirement

**Java 8** is required. Set `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64` before all Gradle commands.

### Development commands

| Task | Command |
|---|---|
| Build | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ./gradlew build` |
| Run client | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 DISPLAY=:1 ./gradlew runClient` |
| Clean | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ./gradlew clean` |

### Branch layout

| Branch | MC Version | Forge | Build System |
|---|---|---|---|
| `master` | 1.10.2 | 12.18.0.2007 | ForgeGradle 2.2, Gradle 2.7 |
| `mc-1.11` | 1.11.2 | 13.20.1.2386 | ForgeGradle 2.2, Gradle 2.7 |
| `mc-1.12` | 1.12.2 | 14.23.5.2847 | ForgeGradle 2.3, Gradle 4.9 |
| `mc-1.15` | 1.15.2 | 31.2.57 | ForgeGradle 3.0.197, Gradle 4.10.3 |

### Known caveats

- **LWJGL3 input in cloud VMs**: MC 1.15+ uses LWJGL3/GLFW for input, which does not respond to `xdotool` or `xte`. The `computerUse` subagent cannot interact with the MC 1.15+ game window. Manual testing via the Desktop pane is the only option, and even that may have trouble with mouse clicks depending on the VM's X11 implementation.
- **No audio in VM**: Sound initialization fails (OpenAL). The game falls back to silent mode automatically.
- **Auto-Creative mode**: The mod auto-sets Creative mode when a player joins (for testing convenience). Remove the `onPlayerLogin` handler in `IMSM.java` for production.
- **`/imsm <structure>` command**: Added for testing. Usage: `/imsm WoodenHouse` places a structure at the player's location. Structure names match filenames in `assets/imsm/structs/` without the `.structure` extension.
- **Block registration**: Uses reflection to auto-register all static `Block` fields in `IMSM.java` with lowercase registry names. `BlockItem` entries are auto-created for all blocks in the `Structures` item group.
- **Asset naming**: All blockstate JSONs, model JSONs, texture PNGs, and lang files must be lowercase. Blockstate variant keys use `""` (empty string) not `"normal"`. Texture paths use `block/` not `blocks/`.
- **Transient HTTPS outages**: The Forge Maven and Maven Central occasionally have SSL connection reset issues in the cloud VM. Retry after a few minutes if Gradle dependency resolution fails.
