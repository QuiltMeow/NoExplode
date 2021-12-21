package ew.sr.x1c.quilt.meow.plugin.NoExplode.base.say;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SayNoPrefixCommand extends SayCommand {

    @Override
    public void sendMessage(String message) {
        Bukkit.broadcastMessage(message);
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("quilt.say");
    }
}
