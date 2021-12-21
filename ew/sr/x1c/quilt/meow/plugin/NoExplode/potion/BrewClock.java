package ew.sr.x1c.quilt.meow.plugin.NoExplode.potion;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BrewClock extends BukkitRunnable {

    private static final Set<BrewingStand> BREWING = new HashSet<>();

    private final BrewerInventory inventory;
    private final BrewingRecipe recipe;
    private final ItemStack ingridient;
    private final BrewingStand stand;
    private int time = 400;

    public static Set<BrewingStand> getBrewingStand() {
        return BREWING;
    }

    public BrewClock(BrewingRecipe recipe, BrewerInventory inventory) {
        this.recipe = recipe;
        this.inventory = inventory;
        ingridient = inventory.getIngredient();
        stand = inventory.getHolder();

        if (BREWING.contains(stand)) {
            return;
        }
        runTaskTimer(Main.getPlugin(), 0, 1);
        BREWING.add(stand);
    }

    @Override
    public void run() {
        --time;
        stand.setBrewingTime(time);
        stand.update();

        ItemStack current = inventory.getIngredient();
        if (current == null || !current.isSimilar(ingridient)) {
            stand.setBrewingTime(400);
            stand.update();
            BREWING.remove(stand);
            cancel();
            return;
        }

        if (time == 0) {
            stand.setFuelLevel(stand.getFuelLevel() - 1);
            stand.update();

            inventory.setIngredient(new ItemStack(Material.AIR));
            for (int i = 0; i < 3; ++i) {
                ItemStack item = inventory.getItem(i);
                if (item == null || item.getType() == Material.AIR) {
                    continue;
                }
                recipe.getAction().brew(inventory, item, ingridient);
            }
            Location location = stand.getLocation();
            location.getWorld().playSound(location, Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
            BREWING.remove(stand);
            cancel();
        }
    }
}
