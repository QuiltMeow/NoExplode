package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FindCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "指令格式錯誤");
            return false;
        }
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "找不到目標玩家");
            return true;
        }
        World world = player.getWorld();
        String targetWorld = target.getWorld().getName();
        if (!world.getName().equalsIgnoreCase(targetWorld)) {
            sender.sendMessage(ChatColor.RED + "與目標玩家不在同一世界 該玩家所在世界 : " + targetWorld);
            return true;
        }

        Location current = player.getLocation();
        Location goal = target.getLocation();
        Vector vector = goal.toVector().subtract(current.toVector()).normalize();
        for (int i = 1; i <= 15; ++i) {
            Vector multiply = vector.multiply(i * 3);
            world.spawnParticle(Particle.TOTEM, player.getLocation().add(multiply.getX(), 0, multiply.getZ()), 50);
        }
        sender.sendMessage(ChatColor.GREEN + "玩家 " + target.getName() + ChatColor.YELLOW + " 所在位置 : (" + (int) goal.getX() + ", " + (int) goal.getY() + ", " + (int) goal.getZ() + ")");

        Vector playerFace = player.getEyeLocation().getDirection().normalize();
        double angle = vector.angle(playerFace);
        angle = angle * 180 / Math.PI;
        sender.sendMessage(ChatColor.AQUA + "相對角度 : " + (int) angle);
        return true;
    }
}
