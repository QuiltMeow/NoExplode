package ew.sr.x1c.quilt.meow.plugin.NoExplode.base.say;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class SayCommand implements CommandExecutor {

    public abstract boolean hasPermission(CommandSender sender);

    public abstract void sendMessage(String message);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!hasPermission(sender)) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "請輸入訊息");
            return false;
        }
        String message = ChatColor.translateAlternateColorCodes('&', ArgumentUtil.joinStringFrom(args, 0));
        sendMessage(message);
        return true;
    }
}
