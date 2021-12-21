package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

// 本功能僅用於無規則伺服器
public class RandomTeleportListener implements Listener {

    private static final int[] X_BORDER = new int[]{-2000, 2000};
    private static final int[] Z_BORDER = new int[]{-2000, 2000};

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            randomTeleport(player);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (event.isBedSpawn()) {
            return;
        }
        event.setRespawnLocation(randomLocation(Bukkit.getWorld("world")));
    }

    public static void randomTeleport(Player player) {
        player.teleport(randomLocation(Bukkit.getWorld("world")));
        player.sendMessage(ChatColor.RED + "您已被傳送至隨機重生點");
    }

    public static Location randomLocation(World world) {
        int x = Randomizer.rand(X_BORDER[0], X_BORDER[1]);
        int z = Randomizer.rand(Z_BORDER[0], Z_BORDER[1]);
        int y = 0;

        Location location = new Location(world, x, y, z);
        Chunk chunk = location.getChunk();
        if (!chunk.isLoaded()) {
            chunk.load(true);
        }

        location.setY(world.getHighestBlockYAt(location));
        Block underBlock = world.getBlockAt(location);
        Material type = underBlock.getType();
        if (type == Material.LAVA) {
            underBlock.setType(Material.STONE);
            location.setY(location.getY() + 1);
        }
        return location;
    }
}
