package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.Collection;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

// 就是有人不喜歡站中間
public class PigZombieTowerLocation implements CommandExecutor {

    private static final Location LOCATION = new Location(Bukkit.getWorld("world_nether"), 72, 242, 44);

    public static int fixLocation() {
        int ret = 0;
        Collection<Player> nearByPlayer = LOCATION.getNearbyPlayers(3);
        for (Player player : nearByPlayer) {
            player.teleport(LOCATION);
            ++ret;
        }
        return ret;
    }

    public static void registerAutomaticFix() {
        new BukkitRunnable() {

            @Override
            public void run() {
                List<Entity> entityList = Bukkit.getWorld("world_nether").getEntities();

                int count = 0;
                for (Entity entity : entityList) {
                    if (entity.getType() == EntityType.EXPERIENCE_ORB) {
                        ++count;
                    }
                }

                if (count >= 100) {
                    fixLocation();
                }
            }
        }.runTaskTimer(Main.getPlugin(), 20 * 60, 20 * 60);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("quilt.fix.pig.zombie.tower.location")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        int fix = fixLocation();
        sender.sendMessage(ChatColor.GREEN + "已變更 " + fix + " 位玩家所在位置");
        return true;
    }
}
