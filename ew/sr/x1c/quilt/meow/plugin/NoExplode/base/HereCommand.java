package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        Location location = player.getLocation();
        Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " 所在位置 : " + location.getWorld().getName() + " 座標 : (" + (int) location.getX() + ", " + (int) location.getY() + ", " + (int) location.getZ() + ")");

        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 15 * 20, 0));
        player.sendMessage(ChatColor.AQUA + "已套用發光效果 15 秒");
        return true;
    }
}
