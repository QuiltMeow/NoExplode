package ew.sr.x1c.quilt.meow.plugin.NoExplode.potion;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class BrewManager {

    public static boolean isBasePotion(ItemStack item, PotionType potion) {
        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta instanceof PotionMeta) {
                PotionMeta meta = (PotionMeta) itemMeta;
                PotionData data = meta.getBasePotionData();
                return data.getType() == potion;
            }
        }
        return false;
    }

    public static boolean isCustomPotion(ItemStack item, PotionEffectType effect) {
        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta instanceof PotionMeta) {
                PotionMeta meta = (PotionMeta) itemMeta;
                return meta.hasCustomEffect(effect);
            }
        }
        return false;
    }

    public static void loadRecipe() {
        new BrewingRecipe(Material.WITHER_SKELETON_SKULL, (inventory, item, ingridient) -> {
            if (!isBasePotion(item, PotionType.AWKWARD)) {
                return;
            }
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            if (potionMeta.hasCustomEffects()) {
                return;
            }
            potionMeta.setDisplayName("§B凋零藥水");
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 30 * 20, 0), true);
            item.setItemMeta(potionMeta);
        });

        new BrewingRecipe(Material.GLOWSTONE, (inventory, item, ingridient) -> {
            if (!isBasePotion(item, PotionType.AWKWARD)) {
                return;
            }
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            if (potionMeta.hasCustomEffects()) {
                return;
            }
            potionMeta.setDisplayName("§B發光藥水");
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 180 * 20, 0), true);
            item.setItemMeta(potionMeta);
        });

        new BrewingRecipe(Material.SHULKER_SHELL, (inventory, item, ingridient) -> {
            if (!isBasePotion(item, PotionType.AWKWARD)) {
                return;
            }
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            if (potionMeta.hasCustomEffects()) {
                return;
            }
            potionMeta.setDisplayName("§B懸浮藥水");
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 60 * 20, 0), true);
            item.setItemMeta(potionMeta);
        });

        new BrewingRecipe(Material.INK_SAC, (inventory, item, ingridient) -> {
            if (!isBasePotion(item, PotionType.AWKWARD)) {
                return;
            }
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            if (potionMeta.hasCustomEffects()) {
                return;
            }
            potionMeta.setDisplayName("§B失明藥水");
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30 * 20, 0), true);
            item.setItemMeta(potionMeta);
        });

        new BrewingRecipe(Material.TNT, (inventory, item, ingridient) -> {
            PotionEffectType[] effectType = new PotionEffectType[]{
                PotionEffectType.WITHER, PotionEffectType.GLOWING, PotionEffectType.LEVITATION, PotionEffectType.CONFUSION, PotionEffectType.BLINDNESS
            };
            boolean check = false;
            for (PotionEffectType type : effectType) {
                if (isCustomPotion(item, type)) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                return;
            }
            item.setType(Material.SPLASH_POTION);
        });
    }
}
