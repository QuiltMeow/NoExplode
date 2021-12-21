package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.limiter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LimiterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("quilt.command.limiter.use")) {
            sender.sendMessage(ChatColor.AQUA + "很抱歉 你沒有使用該指令權限 !");
            return false;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "輸入指令無效 !");
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "add": {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "指令使用方法 : /limiter add [玩家 ID]");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    CommandLimiterListener.addPlayer(args[1]);
                    sender.sendMessage(ChatColor.YELLOW + "已新增離線玩家 " + args[1] + " 至指令限制清單中");
                    return true;
                }
                String name = target.getName();
                CommandLimiterListener.addPlayer(name);
                target.sendMessage(ChatColor.RED + "您已被限制使用指令 !");
                sender.sendMessage(ChatColor.GREEN + "已新增線上玩家 " + name + " 至指令限制清單中");
                return true;
            }
            case "remove": {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "指令使用方法 : /limiter remove [玩家 ID]");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    CommandLimiterListener.removePlayer(args[1]);
                    sender.sendMessage(ChatColor.YELLOW + "已從指令限制清單中移除離線玩家 " + args[1]);
                    return true;
                }
                String name = target.getName();
                CommandLimiterListener.removePlayer(name);
                target.sendMessage(ChatColor.GREEN + "您已解除指令使用限制 !");
                sender.sendMessage(ChatColor.GREEN + "已從指令限制清單中移除線上玩家 " + name);
                return true;
            }
            default: {
                sender.sendMessage(ChatColor.RED + "找不到目標指令 !");
                return false;
            }
        }
    }
}
