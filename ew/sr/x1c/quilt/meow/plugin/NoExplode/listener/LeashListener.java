package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LeashListener implements Listener {

    @EventHandler
    public void onEntityLeash(PlayerInteractEntityEvent event) {
        Entity target = event.getRightClicked();
        switch (target.getType()) {
            case VILLAGER:
            case TURTLE: {
                Player player = event.getPlayer();
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() != Material.LEAD) {
                    return;
                }

                LivingEntity livingEntity = (LivingEntity) target;
                if (!livingEntity.isLeashed()) {
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                        item.setAmount(item.getAmount() - 1);
                        livingEntity.setLeashHolder(player);
                    }, 1);
                    event.setCancelled(true);
                }
                break;
            }
        }
    }
}
