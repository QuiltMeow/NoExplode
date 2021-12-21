package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeadListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        dropHead(player.getLocation(), player.getName());
    }

    public static void dropHead(Location location, String playerName) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(playerName);
        meta.setDisplayName(ChatColor.GOLD + playerName + " 的頭顱");
        head.setItemMeta(meta);

        World world = location.getWorld();
        world.playEffect(location, Effect.SMOKE, BlockFace.UP);
        world.dropItem(location, head);
    }
}
