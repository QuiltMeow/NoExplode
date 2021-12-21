package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    private long lastRain;

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        boolean rain = event.toWeatherState();
        long current = System.currentTimeMillis();

        if (rain) {
            // 避免刷指令造成降雨
            if (lastRain + 20 * 60 * 1000 > current) {
                event.setCancelled(true);
                return;
            }
            if (Randomizer.isSuccess(10)) {
                event.setCancelled(true);
                return;
            }

            // 落雷
            // randomLightning(Bukkit.getWorld("world"), 100);
            lastRain = current;
            // 三叉戟 >w<
            if (Randomizer.isSuccess(25)) {
                event.getWorld().setThundering(true);
            }
        }
    }

    public static void randomLightning(World world, int chance) {
        List<Player> playerList = world.getPlayers();
        if (playerList.isEmpty() || playerList.size() < 5) {
            return;
        }

        if (Randomizer.isSuccessThousand(chance)) {
            Player target = playerList.get(Randomizer.nextInt(playerList.size()));
            Location location = target.getLocation();
            if (world.getHighestBlockYAt(location) < location.getY()) {
                world.strikeLightning(location);
            }
        }
    }

    public static void registerWeatherTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
            World world = Bukkit.getWorld("world");
            if (world.hasStorm()) {
                randomLightning(world, 1);
            }
        }, 20 * 60 * 10, 20 * 60);
    }
}
