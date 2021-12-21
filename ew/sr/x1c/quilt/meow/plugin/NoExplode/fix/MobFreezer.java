package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobFreezer implements Listener {

    public static boolean ENABLE_VILLAGER = false;
    private static final boolean ENABLE_GUARDIAN = false;

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        switch (event.getEntityType()) {
            case VILLAGER: {
                if (ENABLE_VILLAGER) {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).setAI(false);
                    }
                }
                break;
            }
            case GUARDIAN: { // 笨魚 !
                if (ENABLE_GUARDIAN) {
                    if (Randomizer.isSuccess(50)) {
                        if (entity instanceof LivingEntity) {
                            ((LivingEntity) entity).setAI(false);
                        }
                    }
                }
                break;
            }
        }
    }
}
