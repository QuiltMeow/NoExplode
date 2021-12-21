package ew.sr.x1c.quilt.meow.plugin.NoExplode.potion;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PotionListener implements Listener {

    public static void handleBrewing(BrewerInventory brewerInventory) {
        if (brewerInventory.getHolder().getFuelLevel() <= 0) {
            return;
        }
        if (brewerInventory.getIngredient() == null) {
            return;
        }
        BrewingRecipe recipe = BrewingRecipe.getRecipe(brewerInventory);
        if (recipe == null) {
            return;
        }
        recipe.startBrewing(brewerInventory);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPotionItemPlace(InventoryClickEvent event) {
        Inventory clickInventory = event.getClickedInventory();
        if (clickInventory == null) {
            return;
        }
        if (clickInventory.getType() != InventoryType.BREWING) {
            return;
        }

        BrewerInventory brewerInventory = (BrewerInventory) clickInventory;
        if (BrewClock.getBrewingStand().contains(brewerInventory.getHolder())) {
            event.setCancelled(true);
            return;
        }
        if (event.getClick() != ClickType.LEFT) {
            return;
        }
        ItemStack isSlot = event.getCurrentItem();
        ItemStack isCursor = event.getCursor();
        if (isCursor == null || isCursor.getType() == Material.AIR) {
            return;
        }
        ItemStack isCursorClone = isCursor.clone();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            event.setCursor(isSlot);
            brewerInventory.setItem(event.getSlot(), isCursorClone);
            handleBrewing(brewerInventory);
        }, 1);
    }
}
