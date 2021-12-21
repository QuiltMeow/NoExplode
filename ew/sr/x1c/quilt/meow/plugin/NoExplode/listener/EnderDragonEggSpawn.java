package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderDragonEggSpawn implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ENDER_DRAGON) {
            return;
        }

        World world = event.getEntity().getWorld();
        if (world.getEnvironment() != Environment.THE_END) {
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                Location location = new Location(world, 0, 0, 0);
                int highest = world.getHighestBlockYAt(0, 0);
                location.setY(highest);

                if (world.getBlockAt(location).getType() == Material.BEDROCK) {
                    location.setY(highest + 1);
                    world.getBlockAt(location).setType(Material.DRAGON_EGG);
                }
            }
        }.runTaskLater(Main.getPlugin(), 20 * 10);
    }
}
