package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class VillagerListener implements Listener {

    @EventHandler
    public void onVillagerHit(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER) {
            if (event.getCause() == DamageCause.ENTITY_ATTACK) {
                Villager villager = (Villager) event.getEntity();
                Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    if (!villager.isDead()) {
                        if (villager.getHealth() < 5 && Randomizer.isSuccess(20)) {
                            villager.setProfession(Profession.NITWIT);
                        }
                    }
                }, 20);
            }
        }
    }

    @EventHandler
    public void onVillagerDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.VILLAGER) {
            return;
        }
        Villager villager = (Villager) event.getEntity();
        Inventory villagerInventory = villager.getInventory();

        List<ItemStack> dropList = event.getDrops();
        for (ItemStack is : villagerInventory) {
            dropList.add(is);
        }
        villagerInventory.clear();

        if (villager.getKiller() != null) {
            event.setDroppedExp(villager.getVillagerExperience());
        }

        int drop = 2 - Randomizer.rand(0, 2);
        if (drop > 0) {
            dropList.add(new ItemStack(Material.EMERALD, drop));
        }
    }
}
