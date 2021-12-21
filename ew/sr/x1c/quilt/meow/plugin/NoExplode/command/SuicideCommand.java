package ew.sr.x1c.quilt.meow.plugin.NoExplode.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§C該指令必須在遊戲內由玩家使用");
            return false;
        }
        ((Player) sender).setHealth(0);
        return true;
    }
}
