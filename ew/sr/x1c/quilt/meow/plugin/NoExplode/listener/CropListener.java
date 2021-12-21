package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import java.util.Collection;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CropListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block != null) {
                Material type = block.getType();
                if (!isCrop(type)) {
                    return;
                }
                harvestCrop(type, event);
            }
        }
    }

    public static boolean isCrop(Material material) {
        switch (material) {
            case WHEAT:
            case POTATOES:
            case CARROTS:
            case BEETROOTS:
            case NETHER_WART: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static Material getSeed(Material material) {
        if (material == null) {
            return Material.AIR;
        }
        switch (material) {
            case WHEAT: {
                return Material.WHEAT_SEEDS;
            }
            case POTATOES: {
                return Material.POTATO;
            }
            case CARROTS: {
                return Material.CARROT;
            }
            case BEETROOTS: {
                return Material.BEETROOT_SEEDS;
            }
            case NETHER_WART: {
                return Material.NETHER_WART;
            }
            default: {
                return Material.AIR;
            }
        }
    }

    public static int getMaxGrowth(Material material) {
        if (material == null) {
            return 0;
        }
        switch (material) {
            case WHEAT:
            case CARROTS:
            case POTATOES: {
                return 7;
            }
            case BEETROOTS:
            case NETHER_WART: {
                return 3;
            }
            default: {
                return 0;
            }
        }
    }

    public static ItemStack getSeedItemStack(Material material) {
        return new ItemStack(getSeed(material));
    }

    public static void rePlant(Player player, Block block) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Material material = block.getType();
        Location location = player.getLocation();

        entityPlayer.playerInteractManager.breakBlock(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        block.breakNaturally();

        World world = player.getWorld();
        if (material == Material.NETHER_WART) {
            world.playSound(location, Sound.BLOCK_NETHER_WART_BREAK, 1, 1);
            world.playSound(location, Sound.ITEM_NETHER_WART_PLANT, 0.8F, 1);
        } else {
            world.playSound(location, Sound.BLOCK_CROP_BREAK, 1, 1);
            world.playSound(location, Sound.ITEM_CROP_PLANT, 0.8F, 1);
        }
        block.setType(material);
    }

    public static void harvestCrop(Material material, PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block.getData() != getMaxGrowth(material)) {
            return;
        }

        boolean seedInDrop = false;
        Collection<ItemStack> dropList = block.getDrops();
        for (ItemStack is : dropList) {
            if (is.getType() == getSeed(material)) {
                seedInDrop = true;
                break;
            }
        }

        Player player = event.getPlayer();
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Inventory inventory = player.getInventory();

        PacketPlayOutAnimation animation = new PacketPlayOutAnimation(entityPlayer, 0);
        entityPlayer.playerConnection.sendPacket(animation);

        if (seedInDrop) {
            dropList.remove(getSeedItemStack(material));
            rePlant(player, block);
        } else if (inventory.containsAtLeast(getSeedItemStack(material), 1)) {
            inventory.remove(getSeedItemStack(material));
            rePlant(player, block);
        } else {
            Location location = player.getLocation();
            entityPlayer.playerInteractManager.breakBlock(new BlockPosition(block.getX(), block.getY(), block.getZ()));
            block.breakNaturally();

            World world = player.getWorld();
            if (material == Material.NETHER_WART) {
                world.playSound(location, Sound.BLOCK_NETHER_WART_BREAK, 1, 1);
            } else {
                world.playSound(location, Sound.BLOCK_CROP_BREAK, 0.8F, 1);
            }
        }
    }
}
