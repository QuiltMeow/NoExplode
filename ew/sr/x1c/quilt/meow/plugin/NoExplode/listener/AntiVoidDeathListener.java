package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class AntiVoidDeathListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (event.getCause() == DamageCause.VOID && entity.getType() == EntityType.PLAYER) {
            World world = entity.getWorld();
            if (world.getEnvironment() != Environment.THE_END) {
                entity.teleport(world.getSpawnLocation());
                event.setCancelled(true);
            }
        }
    }
}
