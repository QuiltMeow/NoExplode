package ew.sr.x1c.quilt.meow.plugin.NoExplode.potion;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public class BrewingRecipe {

    private static final List<BrewingRecipe> RECIPE_LIST = new ArrayList<BrewingRecipe>();
    private final ItemStack ingredient;
    private final BrewAction action;
    private final boolean perfect;

    public BrewingRecipe(ItemStack ingredient, BrewAction action, boolean perfect) {
        this.ingredient = ingredient;
        this.action = action;
        this.perfect = perfect;
        RECIPE_LIST.add(this);
    }

    public BrewingRecipe(Material ingredient, BrewAction action) {
        this(new ItemStack(ingredient), action, false);
    }

    public ItemStack getIngredient() {
        return ingredient;
    }

    public BrewAction getAction() {
        return action;
    }

    public boolean isPerfect() {
        return perfect;
    }

    public static BrewingRecipe getRecipe(BrewerInventory inventory) {
        boolean notAllAir = false;
        for (int i = 0; i < 3 && !notAllAir; ++i) {
            ItemStack item = inventory.getItem(i);
            if (item == null) {
                continue;
            } else if (item.getType() == Material.AIR) {
                continue;
            }
            notAllAir = true;
        }
        if (!notAllAir) {
            return null;
        }

        ItemStack inventoryIngredient = inventory.getIngredient();
        if (inventoryIngredient == null) {
            return null;
        }
        for (BrewingRecipe recipe : RECIPE_LIST) {
            ItemStack recipeIngredient = recipe.getIngredient();
            if (!recipe.isPerfect() && inventoryIngredient.getType() == recipeIngredient.getType()) {
                return recipe;
            } else if (recipe.isPerfect() && inventoryIngredient.isSimilar(recipeIngredient)) {
                return recipe;
            }
        }
        return null;
    }

    public void startBrewing(BrewerInventory inventory) {
        new BrewClock(this, inventory);
    }
}
