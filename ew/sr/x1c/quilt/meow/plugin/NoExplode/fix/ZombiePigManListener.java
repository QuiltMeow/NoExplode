package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PigZombieAngerEvent;

public class ZombiePigManListener implements Listener {

    private static boolean enable = false;

    @EventHandler
    public void onZombiePigManAngry(PigZombieAngerEvent event) {
        if (enable) {
            event.setCancelled(true);
        }
    }

    public static void setEnable(boolean enable) {
        ZombiePigManListener.enable = enable;
    }

    public static boolean isEnable() {
        return enable;
    }
}
