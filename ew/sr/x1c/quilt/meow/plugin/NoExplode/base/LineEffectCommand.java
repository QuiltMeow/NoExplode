package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LineEffectCommand implements CommandExecutor {

    public static List<Location> getLine(Location center, int distance) {
        World world = center.getWorld();
        List<Location> ret = new ArrayList<>();
        for (int i = -distance; i <= distance; ++i) {
            if (i == 0) {
                continue;
            }
            double originX = center.getX();
            double originY = center.getY();
            double originZ = center.getZ();

            double x = center.getX() + i;
            ret.add(new Location(world, x, originY, originZ));

            double y = center.getY() + i;
            ret.add(new Location(world, originX, y, originZ));

            double z = center.getZ() + i;
            ret.add(new Location(world, originX, originY, z));
        }
        ret.add(center.clone());
        return ret;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }

        int distance = 5;
        if (args.length > 0) {
            try {
                distance = Integer.parseInt(args[0]);
                if (distance <= 0 || distance > 50) {
                    sender.sendMessage(ChatColor.RED + "輸入數值錯誤");
                    return false;
                }
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "輸入參數錯誤");
                return false;
            }
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        List<Location> line = getLine(player.getLocation(), distance);
        line.forEach(location -> {
            world.spawnParticle(Particle.SPELL_MOB, location, 50);
        });
        return true;
    }
}
