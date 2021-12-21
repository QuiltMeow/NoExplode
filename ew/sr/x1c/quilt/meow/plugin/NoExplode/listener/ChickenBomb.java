package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

// 爆爆雞 (未使用)
public class ChickenBomb implements Listener {

    private static final boolean BREAK_BLOCK = false;
    private static final int CHICKEN_DISTANCE = 5;
    private static final int BOOM_POWER = 1;
    private static final double BOOM_CHANCE = 1;

    @EventHandler
    public void onPlayerNearChicken(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        List<Entity> entityList = player.getWorld().getEntities();
        for (Entity entity : entityList) {
            if (entity.getType() == EntityType.CHICKEN) {
                Chicken chicken = (Chicken) entity;
                if (player.getLocation().distanceSquared(chicken.getLocation()) < Math.pow(CHICKEN_DISTANCE, 2)) {
                    if (shouldChickenExplode(BOOM_CHANCE)) {
                        explode(player, chicken);
                    }
                }
            }
        }
    }

    public static boolean shouldChickenExplode(double chance) {
        return Randomizer.nextInt(1000) < chance * 10;
    }

    public void explode(Player player, Chicken chicken) {
        Location location = chicken.getLocation();
        player.getWorld().createExplosion(location, BOOM_POWER, false, BREAK_BLOCK);
    }
}
