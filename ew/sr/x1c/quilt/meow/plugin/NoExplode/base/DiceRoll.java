package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DiceRoll implements Listener, CommandExecutor {

    // 格式 : 1D100
    public static void processDiceRoll(CommandSender sender, int count, int max) {
        StringBuilder sb = new StringBuilder();
        sb.append(count).append("D").append(max).append(" : ").append(ChatColor.AQUA);

        int sum = 0;
        for (int i = 1; i <= count; ++i) {
            int current = Randomizer.nextInt(max) + 1;
            sb.append("<").append(current).append("> ");
            sum += current;
        }
        sb.append(ChatColor.WHITE).append(": ").append(ChatColor.YELLOW).append(sum);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (sender != null) {
                    sender.sendMessage(sb.toString());
                } else {
                    Bukkit.broadcastMessage(sb.toString());
                }
            }
        }.runTaskLaterAsynchronously(Main.getPlugin(), 10);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String[] token = message.split(" ");
        for (String data : token) {
            String[] value = data.toLowerCase().split("d");
            if (value.length == 2) {
                try {
                    int count = Integer.parseInt(value[0]);
                    int max = Integer.parseInt(value[1]);
                    if (count <= 0 || count > 30 || max <= 0 || max > 30000) {
                        continue;
                    }
                    processDiceRoll(null, count, max);
                } catch (NumberFormatException ex) {
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "請輸入數值 ...");
            return false;
        }

        int run = 0;
        for (String data : args) {
            String[] value = data.toLowerCase().split("d");
            if (value.length == 2) {
                try {
                    int count = Integer.parseInt(value[0]);
                    int max = Integer.parseInt(value[1]);
                    if (count <= 0 || count > 30 || max <= 0 || max > 30000) {
                        continue;
                    }
                    processDiceRoll(sender, count, max);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(ChatColor.RED + data + " 解析失敗");
                }
                ++run;
            }
        }
        if (run <= 0) {
            sender.sendMessage(ChatColor.RED + "請輸入正確資料 ...");
        }
        return true;
    }
}
