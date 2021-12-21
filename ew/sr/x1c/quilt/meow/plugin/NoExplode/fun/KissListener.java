package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

// Source Provider : Quilt
public class KissListener implements Listener {

    private static final String PERMISSION = "quilt.kiss";

    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission(PERMISSION) || !player.isSneaking()) {
            return;
        }

        Entity entity = event.getRightClicked();
        if (entity.getType() == EntityType.PLAYER) {
            Player target = (Player) entity;
            if (!target.hasPermission(PERMISSION)) {
                return;
            }
            World world = target.getWorld();
            world.spawnParticle(Particle.HEART, player.getLocation().add(0, 2, 0), 50);
            world.spawnParticle(Particle.HEART, target.getLocation().add(0, 2, 0), 50);
        }
    }
}
