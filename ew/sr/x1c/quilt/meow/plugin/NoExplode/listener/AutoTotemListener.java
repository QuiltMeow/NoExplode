package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import java.util.Collection;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

// 未使用
public class AutoTotemListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getEntity();
        Inventory inventory = player.getInventory();
        if (!inventory.containsAtLeast(new ItemStack(Material.TOTEM_OF_UNDYING), 1)) {
            return;
        }

        if (player.getHealth() - event.getDamage() < 1) {
            inventory.remove(new ItemStack(Material.TOTEM_OF_UNDYING));

            player.setHealth(1);
            Collection<PotionEffect> effect = player.getActivePotionEffects();
            effect.forEach(potionEffect -> {
                effect.remove(potionEffect);
            });
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 45, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 5, 2));

            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(nmsPlayer, (byte) 35);
            nmsPlayer.playerConnection.sendPacket(status);
            event.setCancelled(true);
        }
    }
}
