package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeowCommand implements CommandExecutor {

    private static final Sound[] SOUND = new Sound[]{
        Sound.ENTITY_CAT_STRAY_AMBIENT, Sound.ENTITY_CAT_AMBIENT
    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        player.getWorld().playSound(player.getLocation(), SOUND[Randomizer.nextInt(SOUND.length)], 1, 1);
        return true;
    }
}
