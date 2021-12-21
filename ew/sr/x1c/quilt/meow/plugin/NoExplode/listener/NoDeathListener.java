package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoDeathListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.getHealth() - event.getDamage() < 1) {
                // 他說他死掉就不想玩了 嗯 XDDD
                if (player.getUniqueId().toString().equalsIgnoreCase("7d3af5ec-627a-4f8e-9142-cd98bf60c6c7")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
