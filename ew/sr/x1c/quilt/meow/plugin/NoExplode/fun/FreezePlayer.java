package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

// 如何有效凍結一個玩家 也許這是答案 (並不是最佳處理方法)
public class FreezePlayer implements Listener {

    private static final Set<UUID> FREEZE_PLAYER = new HashSet();

    public static boolean freezePlayer(Player player) {
        return FREEZE_PLAYER.add(player.getUniqueId());
    }

    public static boolean unFreezePlayer(Player player) {
        return FREEZE_PLAYER.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (FREEZE_PLAYER.contains(player.getUniqueId())) {
            Location origin = event.getFrom();
            Location move = event.getTo();
            origin.setPitch(move.getPitch());
            origin.setYaw(move.getYaw());
            event.setTo(origin);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (FREEZE_PLAYER.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (FREEZE_PLAYER.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (FREEZE_PLAYER.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (FREEZE_PLAYER.contains(player.getUniqueId())) {
            player.closeInventory();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (FREEZE_PLAYER.contains(player.getUniqueId())) {
            player.closeInventory();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (FREEZE_PLAYER.contains(player.getUniqueId())) {
            player.closeInventory();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            if (FREEZE_PLAYER.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
        if (event.getDamager().getType() == EntityType.PLAYER) {
            Player attacker = (Player) event.getDamager();
            if (FREEZE_PLAYER.contains(attacker.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
