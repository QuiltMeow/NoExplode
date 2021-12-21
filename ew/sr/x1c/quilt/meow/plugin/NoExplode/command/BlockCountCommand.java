package ew.sr.x1c.quilt.meow.plugin.NoExplode.command;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Cuboid;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.data.Pair;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockCountCommand implements CommandExecutor {

    private static final Location ZERO = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    private static final Map<String, Pair<Location, Location>> PLAYER_POSITION = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        if (args.length <= 0) {
            sender.sendMessage("輸入指令格式錯誤");
            return false;
        }
        Player player = (Player) sender;
        String name = player.getName();
        Pair<Location, Location> position = PLAYER_POSITION.get(name);
        if (position == null) {
            position = new Pair<>(ZERO, ZERO);
            PLAYER_POSITION.put(name, position);
        }
        switch (args[0].toLowerCase()) {
            case "pos1": {
                Location location = player.getLocation();
                position.setLeft(location);
                sender.sendMessage(ChatColor.GREEN + "已設置第一座標點 : (" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ")");
                break;
            }
            case "pos2": {
                Location location = player.getLocation();
                position.setRight(location);
                sender.sendMessage(ChatColor.GREEN + "已設置第二座標點 : (" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ")");
                break;
            }
            case "count": {
                try {
                    Cuboid cuboid = new Cuboid(position.getLeft(), position.getRight());
                    sender.sendMessage(ChatColor.AQUA + cuboid.toString());

                    Location center = cuboid.getCenter();
                    sender.sendMessage(ChatColor.GREEN + "中心點 : (" + center.getBlockX() + ", " + center.getBlockY() + ", " + center.getBlockZ() + ") 大小 : (" + cuboid.getSizeX() + ", " + cuboid.getSizeY() + ", " + cuboid.getSizeZ() + ")");
                    sender.sendMessage(ChatColor.GREEN + "體積 : " + cuboid.getVolume() + " 區塊 : " + cuboid.getChunkCount());
                } catch (IllegalArgumentException ex) {
                    sender.sendMessage(ChatColor.RED + "發生例外狀況 : " + ex.getMessage());
                }
                break;
            }
            default: {
                sender.sendMessage(ChatColor.RED + "輸入參數錯誤");
                return false;
            }
        }
        return true;
    }
}
