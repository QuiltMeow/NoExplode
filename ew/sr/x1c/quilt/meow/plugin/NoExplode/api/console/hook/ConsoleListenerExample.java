package ew.sr.x1c.quilt.meow.plugin.NoExplode.api.console.hook;

import java.util.Collection;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// 使用範例
// 當然它的用途不是這樣 解析訊息後將可實現更多功能
public class ConsoleListenerExample implements Listener {

    @EventHandler
    public void onConsoleOutput(ConsoleLogEvent event) {
        Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();
        for (Player player : playerList) {
            if (player.hasPermission("quilt.console.message")) {
                player.sendMessage("[主控台輸出] " + ChatColor.GREEN + event.getMessage());
            }
        }
    }
}
