package ew.sr.x1c.quilt.meow.plugin.NoExplode.potion;

import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public interface BrewAction {

    public void brew(BrewerInventory inventory, ItemStack item, ItemStack ingridient);
}
