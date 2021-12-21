package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.LineUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Source Provider : Quilt
public class LoveEffectCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        if (!sender.hasPermission("quilt.command.love.effect")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "指令格式錯誤");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "目標玩家不在線上");
            return true;
        }

        Location source = ((Player) sender).getLocation();
        Location target = player.getLocation();
        if (source.distance(target) > 20) {
            sender.sendMessage(ChatColor.RED + "距離太遠");
            return true;
        }
        LineUtil.spawnEffect(Particle.HEART, source, target, 1, 50);
        return true;
    }
}
