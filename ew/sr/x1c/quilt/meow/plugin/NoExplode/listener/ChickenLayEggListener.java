package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ChickenLayEggListener implements Listener {

    @EventHandler
    public void onChickenLayEgg(EntityDropItemEvent event) {
        Item item = event.getItemDrop();
        if (event.getEntityType() == EntityType.CHICKEN && item.getType() == EntityType.EGG) {
            if (Randomizer.isSuccess(10)) {
                item.setItemStack(new ItemStack(Material.GOLD_INGOT));
            }
        }
    }
}
