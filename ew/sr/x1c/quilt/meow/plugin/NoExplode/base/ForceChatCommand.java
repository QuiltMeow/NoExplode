package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("quilt.force.chat")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "指令使用方式 : /forcechat [玩家 ID] [訊息]");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "目標玩家不在線上");
            return true;
        }
        String message = ArgumentUtil.joinStringFrom(args, 1);
        target.chat(message);
        return true;
    }
}
