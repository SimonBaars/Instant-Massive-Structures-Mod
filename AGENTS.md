# AGENTS.md

## Cursor Cloud specific instructions

### Project overview

This is a Minecraft Forge mod called "Instant Massive Structures Mod" (IMSM) for Minecraft 1.10.2 with Forge 12.18.0.2007. It uses Gradle 2.7 via the included wrapper and ForgeGradle 2.2-SNAPSHOT.

### Java version requirement

This project **requires Java 8** (OpenJDK 8). Gradle 2.7 and ForgeGradle 2.2 are incompatible with Java 9+. Ensure `JAVA_HOME` is set to `/usr/lib/jvm/java-8-openjdk-amd64` before running any Gradle commands. Java 8 must be the default `java` on `PATH`.

### Development commands

| Task | Command |
|---|---|
| Build | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ./gradlew build` |
| Test | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ./gradlew test` |
| Lint/Check | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ./gradlew check` |
| Run Minecraft Client | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 DISPLAY=:1 ./gradlew runClient` |
| Setup workspace (first time) | `JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 ./gradlew setupDecompWorkspace` |

### Known caveats

- **Transient SSL errors**: The ForgeGradle plugin attempts to contact `files.minecraftforge.net` and `export.mcpbot.bspk.rs` on every Gradle invocation. These connections may fail with SSL/connection reset errors. The errors are non-fatal and Gradle tasks will still execute if dependencies are cached from a prior `setupDecompWorkspace` run.
- **No audio in VM**: The Minecraft client will log OpenAL/ALSA errors about missing sound devices. It falls back to silent mode automatically—this is expected in headless/VM environments.
- **Asset download 400 errors**: Some Minecraft asset downloads may fail with HTTP 400. These are non-critical (icons, language files) and don't prevent the game from running.
- **Mapping warning**: `This mapping 'snapshot_20160518' was designed for MC 1.9.4!` is a harmless warning; the mappings work fine for 1.10.2.
- **Software rendering**: The VM uses `llvmpipe` (Mesa software renderer). Minecraft runs but may be slow. This is sufficient for testing mod loading and structure creation.
