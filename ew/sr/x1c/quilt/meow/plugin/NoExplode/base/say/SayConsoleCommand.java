package ew.sr.x1c.quilt.meow.plugin.NoExplode.base.say;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.ChatFormatListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SayConsoleCommand extends SayCommand {

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("quilt.say.console");
    }

    @Override
    public void sendMessage(String message) {
        Bukkit.broadcastMessage("[主控台] " + message);
        ChatFormatListener.getDynmapAPI().sendBroadcastToWeb("主控台", message);
    }
}
