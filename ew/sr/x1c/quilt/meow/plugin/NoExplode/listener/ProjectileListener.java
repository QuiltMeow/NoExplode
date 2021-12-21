package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import org.bukkit.Effect;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ProjectileListener implements Listener {

    private static final double SNOWBALL_DAMAGE = 0.01;
    private static final double EGG_DAMAGE = 0.01;

    private static void playEffect(Entity entity) {
        entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, 80, 1);
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (damager instanceof Snowball) {
            event.setDamage(SNOWBALL_DAMAGE);
            playEffect(entity);
        } else if (damager instanceof Egg) {
            event.setDamage(EGG_DAMAGE);
            playEffect(entity);
        }
    }
}
