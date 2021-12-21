package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.type.Carpet;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class ExtraEntityListener implements Listener {

    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        T[] value = enumClass.getEnumConstants();
        int index = Randomizer.nextInt(value.length);
        return value[index];
    }

    public static void applyFullEquip(LivingEntity entity) {
        EntityEquipment equip = entity.getEquipment();

        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        chestplate.addEnchantment(Enchantment.THORNS, 2);

        ItemStack legging = new ItemStack(Material.DIAMOND_LEGGINGS);
        legging.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
        boot.addEnchantment(Enchantment.DEPTH_STRIDER, 3);
        boot.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        boot.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        equip.setHelmet(helmet);
        equip.setHelmetDropChance(0.005F);

        equip.setChestplate(chestplate);
        equip.setChestplateDropChance(0.005F);

        equip.setLeggings(legging);
        equip.setLeggingsDropChance(0.005F);

        equip.setBoots(boot);
        equip.setBootsDropChance(0.005F);
    }

    public void applyFullSword(LivingEntity entity) {
        EntityEquipment equip = entity.getEquipment();

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        sword.addEnchantment(Enchantment.SWEEPING_EDGE, 3);

        equip.setItemInMainHand(sword);
        equip.setItemInMainHandDropChance(0.005F);
    }

    public void applyFullBow(LivingEntity entity) {
        EntityEquipment equip = entity.getEquipment();

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);

        equip.setItemInMainHand(bow);
        equip.setItemInMainHandDropChance(0.005F);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        Location location = event.getLocation();
        World world = location.getWorld();
        switch (event.getEntityType()) {
            case SKELETON_HORSE: {
                if (Randomizer.isSuccess(5)) {
                    Entity entity = world.spawnEntity(location.add(Randomizer.isSuccess(50) ? 1 : -1, 0, Randomizer.isSuccess(50) ? 1 : -1), EntityType.ZOMBIE_HORSE);
                    if (entity instanceof ZombieHorse) {
                        ZombieHorse zombieHorse = (ZombieHorse) entity;
                        zombieHorse.setTamed(true);
                        zombieHorse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                    }
                }
                break;
            }
            case SKELETON: {
                if (Randomizer.isSuccess(15)) {
                    ((Skeleton) event.getEntity()).getEquipment().setItemInMainHand(null);
                } else if (Randomizer.isSuccess(1)) { // 骷髏王
                    Skeleton skeleton = (Skeleton) event.getEntity();
                    applyFullEquip(skeleton);
                    applyFullBow(skeleton);
                }
                break;
            }
            case SHEEP: {
                if (Randomizer.isSuccess(75)) {
                    ((Sheep) event.getEntity()).setColor(randomEnum(DyeColor.class));
                }
                break;
            }
            case LLAMA: {
                Llama llama = (Llama) event.getEntity();
                if (Randomizer.isSuccess(75)) {
                    llama.setColor(randomEnum(Llama.Color.class));
                }
                if (Randomizer.isSuccess(30)) {
                    Material carpet = randomEnum(Carpet.class).getMaterial();
                    llama.getInventory().setDecor(new ItemStack(carpet));
                }
                break;
            }
            case CAT: {
                if (event.getSpawnReason() == SpawnReason.NATURAL && Randomizer.isSuccess(1)) {
                    world.spawnEntity(location.add(Randomizer.isSuccess(50) ? 1 : -1, 0, Randomizer.isSuccess(50) ? 1 : -1), EntityType.OCELOT);
                }
                break;
            }
            case ZOMBIE: { // 殭屍王
                if (Randomizer.isSuccess(1)) {
                    Zombie zombie = (Zombie) event.getEntity();
                    if (!zombie.isBaby()) {
                        applyFullEquip(zombie);
                        applyFullSword(zombie);
                    }
                }
                break;
            }
        }
    }
}
