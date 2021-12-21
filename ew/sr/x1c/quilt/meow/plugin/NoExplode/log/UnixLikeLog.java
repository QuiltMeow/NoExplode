package ew.sr.x1c.quilt.meow.plugin.NoExplode.log;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class UnixLikeLog {

    private static final long NANO_START_TIME = System.nanoTime();

    public static void log(LogType type, String data) {
        log(type, data, false);
    }

    public static void log(LogType type, String data, boolean inGame) {
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case OK: {
                sb.append("[  ").append(ChatColor.GREEN).append("OK").append(ChatColor.RESET).append("  ] ");
                break;
            }
            case INFO: {
                sb.append("[ ").append(ChatColor.AQUA).append("INFO").append(ChatColor.RESET).append(" ] ");
                break;
            }
            case FAILED: {
                sb.append("[").append(ChatColor.RED).append("FAILED").append(ChatColor.RESET).append("] ");
                break;
            }
            case DOT: {
                sb.append("[ .... ] ");
                break;
            }
            case TIME: {
                long current = System.nanoTime();
                double time = (double) ((current - NANO_START_TIME) / 1000) / 1000000;
                int pad = 12 - String.valueOf(time).length();

                sb.append("[");
                for (int i = 1; i <= pad; ++i) {
                    sb.append(" ");
                }
                sb.append(time).append("] ");
                break;
            }
        }
        sb.append(data);

        if (inGame) {
            Bukkit.broadcastMessage(sb.toString());
        } else {
            Bukkit.getConsoleSender().sendMessage(sb.toString());
        }
    }
}
