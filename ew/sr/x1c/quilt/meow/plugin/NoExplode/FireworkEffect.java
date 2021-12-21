package ew.sr.x1c.quilt.meow.plugin.NoExplode;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.FireworkUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkEffect {

    private static final Location FIREWORK_LOCATION = new Location(Bukkit.getWorld("world"), -335, 64, 185);

    public static boolean isDay(String world) {
        long time = Main.getPlugin().getServer().getWorld(world).getTime();
        return time < 12300 || time > 23850;
    }

    public static void registerFirework() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (int i = 1; i <= 2; ++i) {
                    Firework firework = (Firework) FIREWORK_LOCATION.getWorld().spawnEntity(FIREWORK_LOCATION, EntityType.FIREWORK);
                    FireworkUtil.addRandomFireworkEffect(firework);
                }
            }
        }.runTaskTimer(Main.getPlugin(), 20 * 60 * 10, 20 * 60 * 10);
    }
}
