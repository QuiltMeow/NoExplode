package ew.sr.x1c.quilt.meow.plugin.NoExplode.mspt;

import net.minecraft.server.v1_16_R3.MathHelper;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MSPTHandlerNMS {

    public static double[] getTPS() {
        return MinecraftServer.getServer().recentTps;
    }

    public static double getMSPT() {
        return MathHelper.a(MinecraftServer.getServer().h) * 1E-6D;
    }

    public static int getPlayerPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }
}
