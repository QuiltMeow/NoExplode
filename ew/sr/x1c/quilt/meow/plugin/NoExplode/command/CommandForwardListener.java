package ew.sr.x1c.quilt.meow.plugin.NoExplode.command;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandForwardListener implements Listener {

    private static final int MAX_NICK_LENGTH = 10;

    private static final Map<String, String> FORWARD_COMMAND = new HashMap<String, String>() {
        {
            put("/info", "cmi info");
            put("/ride", "cmi ride");
            put("/shakeitoff", "cmi shakeitoff");
        }
    };

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String playerCommand = event.getMessage().toLowerCase();

        String[] pattern = playerCommand.split(" ");
        String prefix = pattern[0];

        if (FORWARD_COMMAND.containsKey(prefix)) {
            String forward = FORWARD_COMMAND.get(prefix);
            if (pattern.length > 1) {
                String other = ArgumentUtil.joinStringFrom(pattern, 1);
                forward = forward + " " + other;
            }
            Bukkit.dispatchCommand(player, forward);
            event.setCancelled(true);
        } else if (prefix.equals("/nick")) {
            if (pattern.length <= 1) {
                player.sendMessage(ChatColor.RED + "無效的指令參數");
            } else {
                String nick = ArgumentUtil.joinStringFrom(pattern, 1);
                handleNickChange(player, nick.equalsIgnoreCase("remove") ? null : nick);
            }
            event.setCancelled(true);
        }
    }

    private static void handleNickChange(Player player, String nick) { // 轉發
        String name = player.getName();
        CommandSender sender = Bukkit.getConsoleSender();
        if (nick == null) {
            Bukkit.dispatchCommand(sender, "cmi nick off " + name);
            Bukkit.dispatchCommand(sender, "nte player " + name + " clear");
            player.sendMessage(ChatColor.GREEN + "已移除暱稱");
            return;
        }

        nick = nick.replaceAll("\\[", "").replaceAll("\\]", "").trim();
        if (nick.isEmpty()) {
            player.sendMessage(ChatColor.RED + "請輸入暱稱");
            return;
        }
        if (nick.length() > MAX_NICK_LENGTH) {
            player.sendMessage(ChatColor.RED + "輸入暱稱太長，無法使用");
            return;
        }
        Bukkit.dispatchCommand(sender, "cmi nick " + nick + " " + name);
        Bukkit.dispatchCommand(sender, "nte player " + name + " prefix [" + nick + "&r] &f");
        player.sendMessage(ChatColor.GREEN + "暱稱變更完成");
    }
}
