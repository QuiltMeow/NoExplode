package ew.sr.x1c.quilt.meow.plugin.NoExplode;

import com.comphenix.protocol.wrappers.EnumWrappers.ItemSlot;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

// 1.16 ProtocolLib 尚未完善 使用簡易方式 (輪詢法)
public class ArmorHidePacketHandler implements CommandExecutor {

    private static final int EQUIP_UPDATE_TICK = 20 * 5;
    private static final Map<String, Boolean> HIDE_SETTING = new HashMap<>();

    public static void setHide(Player player, boolean hide) {
        HIDE_SETTING.put(player.getName(), hide);
    }

    public static boolean isHide(Player player) {
        String name = player.getName();
        Boolean ret = HIDE_SETTING.get(name);
        return ret != null ? ret : false;
    }

    public static boolean shouldHideSlot(ItemSlot slot) {
        switch (slot) {
            case FEET:
            case LEGS:
            case CHEST:
            case HEAD: {
                return true;
            }
        }
        return false;
    }

    public ArmorHidePacketHandler() {
        registerHideTimer();
    }

    private static void registerHideTimer() {
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                    if (isHide(onlinePlayer)) {
                        hideArmor(onlinePlayer);
                    }
                });
            }
        }.runTaskTimer(Main.getPlugin(), EQUIP_UPDATE_TICK, EQUIP_UPDATE_TICK);
    }

    public static void hideArmor(Player player) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId() != player.getUniqueId()) {
                int entityId = player.getEntityId();
                CraftPlayer craftPlayer = (CraftPlayer) onlinePlayer;
                PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityId, new ArrayList<>(Arrays.asList(
                        new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))),
                        new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))),
                        new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR))),
                        new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)))
                )));
                craftPlayer.getHandle().playerConnection.sendPacket(packet);
            }
        });
    }

    public static void showArmor(Player player) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId() != player.getUniqueId()) {
                int entityId = player.getEntityId();
                CraftPlayer craftPlayer = (CraftPlayer) onlinePlayer;
                PlayerInventory inventory = player.getInventory();
                PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityId, new ArrayList<>(Arrays.asList(
                        new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(inventory.getHelmet())),
                        new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(inventory.getChestplate())),
                        new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(inventory.getLeggings())),
                        new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(inventory.getBoots()))
                )));
                craftPlayer.getHandle().playerConnection.sendPacket(packet);
            }
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        boolean hide = !isHide(player);
        setHide(player, hide);
        if (hide) {
            hideArmor(player);
            sender.sendMessage(ChatColor.GREEN + "已開啟隱藏裝備功能");
        } else {
            showArmor(player);
            sender.sendMessage(ChatColor.GREEN + "已關閉隱藏裝備功能");
        }
        return true;
    }

    public static ItemStack getItemInSlot(Player player, ItemSlot slot) {
        PlayerInventory inventory = player.getInventory();
        ItemStack item = null;
        switch (slot) {
            case MAINHAND: {
                item = inventory.getItemInMainHand();
                break;
            }
            case OFFHAND: {
                item = inventory.getItemInOffHand();
                break;
            }
            case FEET: {
                item = inventory.getBoots();
                break;
            }
            case LEGS: {
                item = inventory.getLeggings();
                break;
            }
            case CHEST: {
                item = inventory.getChestplate();
                break;
            }
            case HEAD: {
                item = inventory.getHelmet();
                break;
            }
        }
        return item == null || item.getType() == Material.AIR ? null : item;
    }
}
