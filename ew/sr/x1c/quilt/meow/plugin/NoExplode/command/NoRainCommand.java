package ew.sr.x1c.quilt.meow.plugin.NoExplode.command;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

// 避免低配電腦 LAG 的簡單粗暴解決方案
public class NoRainCommand implements CommandExecutor, Listener {

    private static final Set<String> WEATHER_CONTROL = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }

        Player player = (Player) sender;
        String name = player.getName();
        if (WEATHER_CONTROL.contains(name)) {
            player.resetPlayerWeather();
            WEATHER_CONTROL.remove(name);
            player.sendMessage(ChatColor.GREEN + "已回復天氣效果");
        } else {
            player.setPlayerWeather(WeatherType.CLEAR);
            WEATHER_CONTROL.add(name);
            player.sendMessage(ChatColor.GREEN + "已關閉天氣效果 (無下雨特效)");
        }
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        if (WEATHER_CONTROL.contains(name)) {
            player.setPlayerWeather(WeatherType.CLEAR);
        }
    }
}
