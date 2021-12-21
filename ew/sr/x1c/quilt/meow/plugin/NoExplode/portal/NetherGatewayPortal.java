package ew.sr.x1c.quilt.meow.plugin.NoExplode.portal;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class NetherGatewayPortal implements Listener {

    public static class Bound {

        private final int min, max;

        public Bound(int value) {
            min = max = value;
        }

        public Bound(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    public static class Gateway {

        private final World world;
        private final Bound xBound, yBound, zBound;

        public Gateway(World world, Bound xBound, Bound yBound, Bound zBound) {
            this.world = world;
            this.xBound = xBound;
            this.yBound = yBound;
            this.zBound = zBound;
        }

        public Gateway(World world, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
            this.world = world;
            xBound = new Bound(xMin, xMax);
            yBound = new Bound(yMin, yMax);
            zBound = new Bound(zMin, zMax);
        }

        public Bound getBoundX() {
            return xBound;
        }

        public Bound getBoundY() {
            return yBound;
        }

        public Bound getBoundZ() {
            return zBound;
        }

        public boolean isInBound(Location location) {
            return isInBound(location, true);
        }

        public boolean isInBound(Location location, boolean extend) {
            if (!world.getName().equalsIgnoreCase(location.getWorld().getName())) {
                return false;
            }

            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            if (extend) {
                return x >= xBound.getMin() - 1 && x <= xBound.getMax() + 1
                        && y >= yBound.getMin() - 1 && y <= yBound.getMax() + 1
                        && z >= zBound.getMin() - 1 && z <= zBound.getMax() + 1;
            } else {
                return x >= xBound.getMin() && x <= xBound.getMax()
                        && y >= yBound.getMin() && y <= yBound.getMax()
                        && z >= zBound.getMin() && z <= zBound.getMax();
            }
        }
    }

    private static final Map<Gateway, Location> GATEWAY = new HashMap<Gateway, Location>() {
        {
            put(new Gateway(Bukkit.getWorld("world_nether"), new Bound(10, 12), new Bound(108, 110), new Bound(-24)),
                    new Location(Bukkit.getWorld("world_nether"), 10145, 99, 10435));

            put(new Gateway(Bukkit.getWorld("world_nether"), new Bound(10147), new Bound(99, 101), new Bound(10433, 10434)),
                    new Location(Bukkit.getWorld("world_nether"), 10, 108, -20));

            put(new Gateway(Bukkit.getWorld("world_nether"), new Bound(10, 12), new Bound(108, 110), new Bound(8)),
                    new Location(Bukkit.getWorld("world_nether"), -40, 67, 10));

            put(new Gateway(Bukkit.getWorld("world_nether"), new Bound(-43), new Bound(66, 68), new Bound(12, 13)),
                    new Location(Bukkit.getWorld("world_nether"), 10, 108, 5));

            put(new Gateway(Bukkit.getWorld("world"), new Bound(-423), new Bound(61, 80), new Bound(156, 158)),
                    new Location(Bukkit.getWorld("world_nether"), -40, 108, 5));
        }
    };

    public static Location getTeleport(Location source) {
        for (Map.Entry<Gateway, Location> entry : GATEWAY.entrySet()) {
            Gateway gateway = entry.getKey();
            if (gateway.isInBound(source)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        switch (event.getCause()) {
            case NETHER_PORTAL:
            case END_GATEWAY: {
                Player player = event.getPlayer();
                Location target = getTeleport(event.getFrom());
                if (target != null) {
                    player.teleport(target);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            Location newLocation = player.getLocation().subtract(0, 1, 0);
                            newLocation.getWorld().spawnParticle(Particle.SPELL_MOB, newLocation, 50);
                            player.playSound(newLocation, Sound.BLOCK_PORTAL_TRAVEL, 0.25F, 1);
                        }
                    }.runTask(Main.getPlugin());
                    event.setCancelled(true);
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityTeleport(EntityTeleportEvent event) {
        Location from = event.getFrom();
        Location teleport = getTeleport(from);
        if (teleport != null) {
            event.setTo(teleport);
        }
    }
}
