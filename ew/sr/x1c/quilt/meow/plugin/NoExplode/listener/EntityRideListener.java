package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityRideListener implements Listener {

    @EventHandler
    public void onEntityRide(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
            return;
        }

        Entity target = event.getRightClicked();
        switch (target.getType()) {
            case TURTLE: { // 有人想當浦島太郎
                if (ride(player, target)) {
                    ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30 * 20, 4));
                }
                break;
            }
        }
    }

    public boolean ride(Player player, Entity entity) {
        if (entity.getPassengers().isEmpty()) {
            entity.addPassenger(player);
            return true;
        }
        return false;
    }
}
