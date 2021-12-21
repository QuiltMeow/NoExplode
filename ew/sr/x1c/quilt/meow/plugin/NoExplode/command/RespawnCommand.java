package ew.sr.x1c.quilt.meow.plugin.NoExplode.command;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.DeathListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RespawnCommand implements CommandExecutor {

    public static int getRemainingCooltime(long current, long target) {
        int ret = (int) ((target - current) / 1000 / 60);
        if (ret < 0) {
            ret = 0;
        }
        return ret;
    }

    public long getRespawnTime(long deathTime, long cooltime) {
        return deathTime + cooltime * 60 * 1000;
    }

    public static void respawn(Player player) {
        Location location = player.getBedSpawnLocation();
        if (location == null) {
            location = Bukkit.getWorld("world").getSpawnLocation();
        }
        player.teleport(location);
        player.setGameMode(GameMode.SURVIVAL);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        if (player.getGameMode() != GameMode.SPECTATOR) {
            sender.sendMessage(ChatColor.RED + "觀察者遊戲模式下才能進行復活");
            return false;
        }
        String name = player.getName();
        if (FreeCamCommand.getFreeCamLocation().get(name) != null) {
            sender.sendMessage(ChatColor.RED + "Free Cam 模式下無法進行復活");
            return true;
        }

        Long deathTime = DeathListener.getDeathTime().get(name);
        if (deathTime == null) {
            respawn(player);
            return true;
        }

        int deathCount = DeathListener.getDeathCount(name);
        int cooltime = DeathListener.getRespawnCooltime(deathCount);

        long current = System.currentTimeMillis();
        if (getRespawnTime(deathTime, cooltime) <= current) {
            respawn(player);
            DeathListener.getDeathTime().remove(name);
            player.sendMessage(ChatColor.GREEN + "您已復活成功");
        } else {
            player.sendMessage(ChatColor.RED + "請於等待時間結束後再進行復活 剩餘時間 : " + getRemainingCooltime(current, getRespawnTime(deathTime, cooltime)) + " 分鐘");
        }
        return true;
    }
}
