package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import net.minecraft.server.v1_16_R3.EntityThrownTrident;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftTrident;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public class TridentListener implements Listener {

    @EventHandler
    public void onTridentHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.TRIDENT) {
            Entity target = event.getHitEntity();
            if (target != null) {
                Location location = target.getLocation();
                World world = location.getWorld();
                if (!world.isThundering() && Randomizer.isSuccess(10)) {
                    EntityThrownTrident nmsTridentEntity = ((CraftTrident) entity).getHandle();
                    net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = nmsTridentEntity.trident;
                    ItemStack itemStack = CraftItemStack.asCraftMirror(nmsItemStack);
                    if (itemStack.getEnchantments().containsKey(Enchantment.CHANNELING)) {
                        world.strikeLightning(location);
                    }
                }
            }
        }
    }
}
