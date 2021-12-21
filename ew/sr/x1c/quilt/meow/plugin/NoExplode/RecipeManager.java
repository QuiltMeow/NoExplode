package ew.sr.x1c.quilt.meow.plugin.NoExplode;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RecipeManager {

    public static void initRecipe() {
        loadGrass();
        loadSlimeBall();
        loadChainMail();
        loadSpiderWeb();
        loadMagma();
        loadNameTag();
        loadExpBottle();
        loadFurnaceRecipe​();
        loadHorseArmor();
        loadResistancePotionRecipe();
        loadNauseaPotionRecipe();
        loadEnchantGoldenApple();
        loadPetrifiedOakSlab();
        loadMultiChest();
    }

    public static void loadGrass() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "grass"), new ItemStack(Material.GRASS_BLOCK, 1));
        sr.shape(new String[]{"ABA", "ACA", "ADA"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.BONE_MEAL);
        sr.setIngredient('C', Material.WHEAT_SEEDS);
        sr.setIngredient('D', Material.DIRT);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadSlimeBall() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "slime_ball"), new ItemStack(Material.SLIME_BALL, 2));
        sr.shape(new String[]{"ABA", "ACA", "ADA"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.SUGAR_CANE);
        sr.setIngredient('C', Material.CLAY_BALL);
        sr.setIngredient('D', Material.WATER_BUCKET);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadChainMail() {
        loadChainMailHelmet();
        loadChainMailChestPlate();
        loadChainMailLeggings();
        loadChainMailBoots();
    }

    public static void loadHorseArmor() {
        loadIronHorseArmor();
        loadGoldenHorseArmor();
        loadDiamondHorseArmor();
    }

    public static void loadChainMailHelmet() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "chainmail_helmet"), new ItemStack(Material.CHAINMAIL_HELMET, 1));
        sr.shape(new String[]{"BBB", "BAB", "AAA"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.IRON_BARS);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadChainMailChestPlate() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "chainmail_chestplate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        sr.shape(new String[]{"BAB", "BBB", "BBB"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.IRON_BARS);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadChainMailLeggings() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "chainmail_leggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        sr.shape(new String[]{"BBB", "BAB", "BAB"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.IRON_BARS);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadChainMailBoots() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "chainmail_boots"), new ItemStack(Material.CHAINMAIL_BOOTS, 1));
        sr.shape(new String[]{"AAA", "BAB", "BAB"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.IRON_BARS);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadSpiderWeb() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "web"), new ItemStack(Material.COBWEB, 2));
        sr.shape(new String[]{"BAB", "ABA", "BAB"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.STRING);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadMagma() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "magma"), new ItemStack(Material.MAGMA_BLOCK, 4));
        sr.shape(new String[]{"AAA", "ABA", "AAA"});
        sr.setIngredient('A', Material.COBBLESTONE);
        sr.setIngredient('B', Material.LAVA_BUCKET);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadNameTag() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "name_tag"), new ItemStack(Material.NAME_TAG, 1));
        sr.shape(new String[]{"AAB", "ACA", "CAA"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.IRON_INGOT);
        sr.setIngredient('C', Material.PAPER);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadExpBottle() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "experience_bottle"), new ItemStack(Material.EXPERIENCE_BOTTLE, 4));
        sr.shape(new String[]{"ABA", "BCB", "ABA"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.GLASS_BOTTLE);
        sr.setIngredient('C', Material.ENCHANTED_BOOK);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadFurnaceRecipe​() {
        Bukkit.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.PACKED_ICE, 1), Material.ICE));
    }

    public static void loadEnchantGoldenApple() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "enchant_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
        sr.shape(new String[]{"AAA", "ABA", "AAA"});
        sr.setIngredient('A', Material.GOLD_BLOCK);
        sr.setIngredient('B', Material.GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadResistancePotionRecipe() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "resistance_potion"), getResistancePotion());
        sr.shape(new String[]{"AAA", "BCB", "ADA"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.IRON_INGOT);
        sr.setIngredient('C', Material.POTION);
        sr.setIngredient('D', Material.LAVA_BUCKET);
        Bukkit.getServer().addRecipe(sr);
    }

    public static ItemStack getResistancePotion() {
        ItemStack resistancePotion = new ItemStack(Material.POTION, 1);
        PotionMeta resistancePotionMeta = (PotionMeta) resistancePotion.getItemMeta();
        resistancePotionMeta.setDisplayName("§B抗性藥水");
        resistancePotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 0), true);
        resistancePotion.setItemMeta(resistancePotionMeta);
        return resistancePotion;
    }

    public static void loadNauseaPotionRecipe() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "nausea_potion"), getNauseaPotion());
        sr.shape(new String[]{"ABC", "DEF", "GHI"});
        sr.setIngredient('A', Material.SPIDER_EYE);
        sr.setIngredient('B', Material.SLIME_BALL);
        sr.setIngredient('C', Material.RED_MUSHROOM);
        sr.setIngredient('D', Material.MELON_SLICE);
        sr.setIngredient('E', Material.POTION);
        sr.setIngredient('F', Material.CARROT);
        sr.setIngredient('G', Material.REDSTONE);
        sr.setIngredient('H', Material.SUGAR);
        sr.setIngredient('I', Material.GLOWSTONE_DUST);
        Bukkit.getServer().addRecipe(sr);
    }

    public static ItemStack getNauseaPotion() {
        ItemStack nauseaPotion = new ItemStack(Material.POTION, 1);
        PotionMeta nauseaPotionMeta = (PotionMeta) nauseaPotion.getItemMeta();
        nauseaPotionMeta.setDisplayName("§B噁心藥水");
        nauseaPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 60 * 20, 0), true);
        nauseaPotion.setItemMeta(nauseaPotionMeta);
        return nauseaPotion;
    }

    public static void loadIronHorseArmor() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "iron_horse_armor"), new ItemStack(Material.IRON_HORSE_ARMOR, 1));
        sr.shape(new String[]{"AAB", "BCB", "BBB"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.IRON_INGOT);
        sr.setIngredient('C', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadGoldenHorseArmor() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "golden_horse_armor"), new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));
        sr.shape(new String[]{"AAB", "BCB", "BBB"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.GOLD_INGOT);
        sr.setIngredient('C', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadDiamondHorseArmor() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "diamond_horse_armor"), new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));
        sr.shape(new String[]{"AAB", "BCB", "BBB"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.DIAMOND);
        sr.setIngredient('C', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void loadPetrifiedOakSlab() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "petrified_oak_slab"), new ItemStack(Material.PETRIFIED_OAK_SLAB, 12));
        sr.shape(new String[]{"AAA", "BBB", "CCC"});
        sr.setIngredient('A', Material.AIR);
        sr.setIngredient('B', Material.OAK_PLANKS);
        sr.setIngredient('C', Material.STONE);
        Bukkit.getServer().addRecipe(sr);
    }

    private static final List<Material> LOG_MATERIAL = new ArrayList<Material>() {
        {
            add(Material.ACACIA_LOG);
            add(Material.BIRCH_LOG);
            add(Material.DARK_OAK_LOG);
            add(Material.JUNGLE_LOG);
            add(Material.OAK_LOG);
            add(Material.SPRUCE_LOG);
            add(Material.STRIPPED_ACACIA_LOG);
            add(Material.STRIPPED_BIRCH_LOG);
            add(Material.STRIPPED_DARK_OAK_LOG);
            add(Material.STRIPPED_JUNGLE_LOG);
            add(Material.STRIPPED_OAK_LOG);
            add(Material.STRIPPED_SPRUCE_LOG);

            // 1.16
            add(Material.CRIMSON_STEM); // 緋紅蕈柄
            add(Material.CRIMSON_HYPHAE); // 緋紅菌絲體
            add(Material.STRIPPED_CRIMSON_STEM); // 剝皮緋紅蕈柄
            add(Material.STRIPPED_CRIMSON_HYPHAE); // 剝皮緋紅菌絲體
            add(Material.WARPED_STEM); // 扭曲蕈柄
            add(Material.WARPED_HYPHAE); // 扭曲菌絲體
            add(Material.STRIPPED_WARPED_STEM); // 剝皮扭曲蕈柄
            add(Material.STRIPPED_WARPED_HYPHAE); // 剝皮扭曲菌絲體
        }
    };

    public static void loadMultiChest() {
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "chest"), new ItemStack(Material.CHEST, 4));
        sr.shape(new String[]{"AAA", "A A", "AAA"});
        sr.setIngredient('A', new RecipeChoice.MaterialChoice(LOG_MATERIAL));
        Bukkit.getServer().addRecipe(sr);
    }
}
