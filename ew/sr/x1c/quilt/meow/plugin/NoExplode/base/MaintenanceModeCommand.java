package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class MaintenanceModeCommand implements CommandExecutor, Listener {

    private static boolean maintenanceMode;

    public static boolean isTestMode() {
        return maintenanceMode;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return false;
        }
        maintenanceMode = !maintenanceMode;
        Bukkit.broadcastMessage(ChatColor.GREEN + "[維護] " + ChatColor.YELLOW + "維護模式已" + (maintenanceMode ? "開啟" : "關閉"));
        return true;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (maintenanceMode) {
            Player player = event.getPlayer();
            if (!player.hasPermission("quilt.maintenance.login")) {
                event.disallow(Result.KICK_OTHER, "伺服器維護中，請稍後再進行連線");
            }
        }
    }
}
