package modid.imsm.core;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;

public final class MinecraftAccess {
    private MinecraftAccess() {
    }

    public static IntegratedServer getIntegratedServer() {
        return Objects.requireNonNull(Minecraft.getInstance().getIntegratedServer(), "Integrated server is not available");
    }

    public static RegistryKey<World> getCurrentDimensionKey() {
        ClientPlayerEntity player = Objects.requireNonNull(Minecraft.getInstance().player, "Client player is not available");
        return player.world.getDimensionKey();
    }

    public static ServerWorld getIntegratedWorld() {
        ServerWorld world = getIntegratedServer().getWorld(getCurrentDimensionKey());
        return Objects.requireNonNull(world, "Integrated server world is not available");
    }

    public static File getSaveSubdir(String name) {
        return getSaveRoot().resolve(name).toFile();
    }

    public static File getSaveFile(String subdir, String name) {
        return getSaveSubdir(subdir).toPath().resolve(name).toFile();
    }

    private static Path getSaveRoot() {
        return getIntegratedServer().func_240776_a_(FolderName.DOT);
    }
}
