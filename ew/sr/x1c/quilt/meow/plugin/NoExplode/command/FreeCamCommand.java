package ew.sr.x1c.quilt.meow.plugin.NoExplode.command;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.DeathListener;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreeCamCommand implements CommandExecutor {

    private static final Map<String, Location> FREE_CAM_LOCATION = new HashMap<>();

    public static Map<String, Location> getFreeCamLocation() {
        return FREE_CAM_LOCATION;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        GameMode gameMode = player.getGameMode();
        String name = player.getName();
        if (gameMode == GameMode.SURVIVAL) {
            FREE_CAM_LOCATION.put(name, player.getLocation());
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.GREEN + "Free Cam 已開啟");
        } else if (gameMode == GameMode.SPECTATOR) {
            if (DeathListener.isDeath(player)) {
                player.sendMessage(ChatColor.RED + "請先進行復活");
                return true;
            }

            Location location = FREE_CAM_LOCATION.get(name);
            if (location == null) {
                player.sendMessage(ChatColor.RED + "找不到返回點");
                return true;
            }

            player.teleport(location);
            player.setGameMode(GameMode.SURVIVAL);
            FREE_CAM_LOCATION.remove(name);
            player.sendMessage(ChatColor.GREEN + "Free Cam 已關閉");
        }
        return true;
    }
}
