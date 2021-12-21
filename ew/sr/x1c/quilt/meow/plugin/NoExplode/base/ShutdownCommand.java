package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ShutdownCommand implements CommandExecutor {

    private static BukkitTask task;

    public static void countDownShutdown(int second) {
        BossBar bossBar = Bukkit.createBossBar("伺服器即將關閉", BarColor.YELLOW, BarStyle.SOLID);
        if (task == null) {
            task = new BukkitRunnable() {

                private int current = second;

                @Override
                public void run() {
                    if (--current <= 0) {
                        bossBar.removeAll();
                        shutdown();
                        task.cancel();
                    } else {
                        bossBar.setProgress(current / (double) second);
                    }
                }
            }.runTaskTimer(Main.getPlugin(), 0, 20);
        }
        bossBar.setVisible(true);
        Bukkit.getOnlinePlayers().forEach(player -> {
            bossBar.addPlayer(player);
        });
    }

    public static void shutdown() {
        Bukkit.broadcastMessage(ChatColor.RED + "伺服器即將關閉 !");
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(Main.getPlugin(), 60);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("quilt.shutdown")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.YELLOW + "即將進行伺服器關閉");
            shutdown();
            return true;
        }

        int time;
        try {
            time = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            sender.sendMessage(ChatColor.RED + "數值輸入錯誤");
            return false;
        }
        if (time <= 0) {
            sender.sendMessage(ChatColor.RED + "關閉時間必須大於 0");
            return false;
        }

        sender.sendMessage(ChatColor.YELLOW + "送出伺服器關閉倒數");
        countDownShutdown(time);
        return true;
    }
}
