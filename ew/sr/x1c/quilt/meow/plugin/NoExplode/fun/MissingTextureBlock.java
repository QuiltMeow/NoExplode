package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

// (未使用) 前置需求 : Opti Fine + 材質包
public class MissingTextureBlock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;

        ItemStack item = new ItemStack(Material.OAK_LEAVES);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);

        Damageable damage = (Damageable) meta;
        damage.setDamage(100);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
        return true;
    }
}
