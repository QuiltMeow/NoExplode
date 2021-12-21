package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WikiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "請輸入搜尋內容");
            return false;
        }
        String name = sender.getName();
        String search = args[0];
        String command = "tellraw " + name + " { \"text\": \"[WIKI] 搜尋 " + search + "\", \"underlined\": \"true\", \"clickEvent\": { \"action\": \"open_url\", \"value\": \"https://minecraft-zh.gamepedia.com/index.php?search=" + search + "\" } }";
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        return true;
    }
}
