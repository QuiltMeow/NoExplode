package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

public class TestModeCommand implements CommandExecutor, Listener {

    private static final Map<String, PermissionAttachment> PERMISSION = new HashMap<>();
    private static boolean testMode;

    public static boolean isTestMode() {
        return testMode;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return false;
        }
        testMode = !testMode;
        Bukkit.broadcastMessage(ChatColor.GREEN + "[除錯] " + ChatColor.YELLOW + "測試模式已" + (testMode ? "開啟" : "關閉"));
        return true;
    }

    // [未使用] 主要用於 Bypass 指令限制
    public static void registerTestPermission() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                PermissionAttachment attach = player.addAttachment(Main.getPlugin());
                attach.setPermission("*", true);
                PERMISSION.put(player.getName(), attach);
            }
        }
    }

    public static void deregisterTestPermission() {
        for (Map.Entry<String, PermissionAttachment> entry : PERMISSION.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                PermissionAttachment attach = entry.getValue();
                player.removeAttachment(attach);
            }
        }
        PERMISSION.clear();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        if (PERMISSION.containsKey(name)) {
            player.removeAttachment(PERMISSION.get(name));
            PERMISSION.remove(name);
        }
    }
}
