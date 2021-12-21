package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.limiter;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

// 從 QuiltPluginLite 移植
public class CommandLimiterListener implements Listener {

    private static final Set<String> LIMIT_PLAYER = new HashSet<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String name = player.getName().toLowerCase();
        if (LIMIT_PLAYER.contains(name) && !player.hasPermission("quilt.limiter.bypass")) {
            player.sendMessage(ChatColor.RED + "你是無法使用任何指令的 !");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        String name = player.getName().toLowerCase();
        if (LIMIT_PLAYER.contains(name) && !player.hasPermission("quilt.limiter.bypass")) {
            player.sendMessage(ChatColor.RED + "想要進行傳送 ? 想得美 !");
            event.setCancelled(true);
        }
    }

    public static void addPlayer(String player) {
        LIMIT_PLAYER.add(player.toLowerCase());
    }

    public static void removePlayer(String player) {
        LIMIT_PLAYER.remove(player.toLowerCase());
    }
}
