package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class N2OListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = event.getPlayer();
            Entity entity = player.getVehicle();
            if (entity != null) {
                switch (entity.getType()) {
                    case HORSE:
                    case DONKEY:
                    case MULE:
                    case PIG: {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item.getType() == Material.COAL) {
                            LivingEntity vehicle = (LivingEntity) entity;
                            if (!vehicle.hasPotionEffect(PotionEffectType.SPEED)) {
                                vehicle.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 9));

                                Location location = vehicle.getLocation();
                                World world = location.getWorld();
                                world.spawnParticle(Particle.LAVA, location, 50);
                                world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                                item.setAmount(item.getAmount() - 1);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
