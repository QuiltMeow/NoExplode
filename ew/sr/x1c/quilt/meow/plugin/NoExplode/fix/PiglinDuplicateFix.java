package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class PiglinDuplicateFix implements Listener {

    private static final List<Material> GOLDEN_ITEM = new ArrayList<Material>() {
        {
            add(Material.BELL);
            add(Material.GOLD_BLOCK);
            add(Material.CLOCK);
            add(Material.ENCHANTED_GOLDEN_APPLE);
            add(Material.GILDED_BLACKSTONE);
            add(Material.GLISTERING_MELON_SLICE);
            add(Material.GOLD_INGOT);
            add(Material.GOLD_NUGGET);
            add(Material.GOLD_ORE);
            add(Material.GOLDEN_APPLE);
            add(Material.GOLDEN_AXE);
            add(Material.GOLDEN_BOOTS);
            add(Material.GOLDEN_CHESTPLATE);
            add(Material.GOLDEN_HELMET);
            add(Material.GOLDEN_HOE);
            add(Material.GOLDEN_HORSE_ARMOR);
            add(Material.GOLDEN_LEGGINGS);
            add(Material.GOLDEN_PICKAXE);
            add(Material.GOLDEN_SHOVEL);
            add(Material.GOLDEN_SWORD);
            add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
            add(Material.NETHER_GOLD_ORE);
        }
    };

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        List<ItemStack> drop = event.getDrops();
        if (event.getEntityType() != EntityType.PIGLIN || drop.size() <= 1) {
            return;
        }
        EntityEquipment equipment = event.getEntity().getEquipment();
        if (equipment != null) {
            ItemStack main = equipment.getItemInMainHand();
            if (GOLDEN_ITEM.contains(equipment.getItemInOffHand().getType()) && equipment.getItemInOffHandDropChance() == 2F && !GOLDEN_ITEM.contains(main.getType()) && equipment.getItemInMainHandDropChance() == 2F) {
                drop.remove(main);
            }
        }
    }
}
