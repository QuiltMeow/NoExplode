package ew.sr.x1c.quilt.meow.plugin.NoExplode.command;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.enchantment.Glow;
import java.lang.reflect.Field;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowCommand implements CommandExecutor {

    public static void registerGlow() {
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            NamespacedKey key = new NamespacedKey(Main.getPlugin(), Main.getPlugin().getDescription().getName());
            Glow glow = new Glow(key);
            Enchantment.registerEnchantment(glow);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta.hasEnchants()) {
                player.sendMessage(ChatColor.RED + "該道具無法加入附魔效果");
                return true;
            }
            NamespacedKey key = new NamespacedKey(Main.getPlugin(), Main.getPlugin().getDescription().getName());
            Glow glow = new Glow(key);
            meta.addEnchant(glow, 1, true);
            item.setItemMeta(meta);
            player.sendMessage(ChatColor.GREEN + "已加入附魔效果");
        } else {
            player.sendMessage(ChatColor.RED + "該道具無法加入附魔效果");
        }
        return true;
    }
}
