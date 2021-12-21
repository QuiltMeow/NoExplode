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

public class CircleEffectCommand implements CommandExecutor {

    public static List<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double y = center.getY();

        double increment = 2 * Math.PI / amount;
        List<Location> ret = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            double angle = i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            ret.add(new Location(world, x, y, z));
        }
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
                if (distance <= 0 || distance > 20) {
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

        List<Location> circle = getCircle(player.getLocation(), distance, distance * 4);
        circle.forEach(location -> {
            world.spawnParticle(Particle.SPELL_MOB, location, 50);
        });
        return true;
    }
}
