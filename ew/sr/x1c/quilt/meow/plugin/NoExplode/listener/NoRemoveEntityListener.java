package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NoRemoveEntityListener implements Listener {

    @EventHandler
    public void onNameEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.NAME_TAG) {
            Entity entity = event.getRightClicked();
            if (entity instanceof LivingEntity) {
                LivingEntity creature = (LivingEntity) entity;
                creature.setRemoveWhenFarAway(true);
                creature.setPersistent(true);
            }
        }
    }
}
