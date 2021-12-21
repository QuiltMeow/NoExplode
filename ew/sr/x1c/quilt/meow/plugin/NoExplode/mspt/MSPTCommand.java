package ew.sr.x1c.quilt.meow.plugin.NoExplode.mspt;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MSPTCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Main.getPlugin().getMSPT().requestMSPT(sender);
        return true;
    }
}
