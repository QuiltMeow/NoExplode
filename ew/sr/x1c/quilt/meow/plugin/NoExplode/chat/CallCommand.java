package ew.sr.x1c.quilt.meow.plugin.NoExplode.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CallCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        int count = 0;
        for (int i = 0; i < args.length; ++i) {
            boolean tag = ChatSymbolListener.callPlayer(args[i]);
            if (tag) {
                Player player = Bukkit.getPlayer(args[i]);
                if (player != null) {
                    player.sendMessage("玩家 " + ChatColor.YELLOW + sender.getName() + ChatColor.WHITE + " 標註了您");
                }
                sender.sendMessage("您標註了玩家 " + ChatColor.AQUA + player.getName());
                ++count;
            }
        }
        if (count == 0) {
            sender.sendMessage(ChatColor.RED + "未標註任何玩家");
        }
        return true;
    }
}
