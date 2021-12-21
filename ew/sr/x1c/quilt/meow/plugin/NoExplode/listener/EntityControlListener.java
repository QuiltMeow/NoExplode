package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class EntityControlListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Location location = event.getLocation();
        switch (event.getEntityType()) {
            case PHANTOM: {
                if (location.getBlock().getBiome() == Biome.MOUNTAINS) {
                    return;
                }
                event.setCancelled(true);
                break;
            }
            case BAT: {
                event.setCancelled(true);
                break;
            }
            case PIGLIN: {
                World world = location.getWorld();
                if (world.getEnvironment() == Environment.NETHER) {
                    if (location.getY() >= 128) {
                        world.spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
                        event.setCancelled(true);
                    }
                }
                break;
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Location location = event.getEntity().getLocation();
        switch (event.getEntityType()) {
            /* case CREEPER: {
                location.getWorld().dropItemNaturally(location, new ItemStack(Material.PHANTOM_MEMBRANE));
                break;
            } */
            case TURTLE: {
                if (Randomizer.isSuccess(5)) {
                    int drop = 2 - Randomizer.nextInt(2);
                    location.getWorld().dropItemNaturally(location, new ItemStack(Material.SCUTE, drop));
                }
                break;
            }
        }
    }
}
