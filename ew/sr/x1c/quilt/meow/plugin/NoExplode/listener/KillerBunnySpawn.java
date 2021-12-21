package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class KillerBunnySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.RABBIT) {
            if (Randomizer.isSuccess(5)) {
                Rabbit rabbit = (Rabbit) event.getEntity();
                rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
            }
        }
    }

    @EventHandler
    public void onRabbitAttack(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.RABBIT) {
            return;
        }
        event.setDamage(4);
    }
}
