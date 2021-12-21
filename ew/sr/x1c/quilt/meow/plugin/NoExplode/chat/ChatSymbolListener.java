package ew.sr.x1c.quilt.meow.plugin.NoExplode.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatSymbolListener implements Listener, CommandExecutor {

    public enum PrefixMode {

        DISABLE,
        KEEP,
        REMOVE
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "請輸入模式");
            return false;
        }

        int mode;
        try {
            mode = Integer.parseInt(args[0]);
        } catch (Exception ex) {
            sender.sendMessage(ChatColor.RED + "請輸入有效數字");
            return false;
        }

        String name = sender.getName();
        switch (mode) {
            case 0: {
                PLAYER_CHAT_PREFIX.put(name, PrefixMode.DISABLE);
                break;
            }
            case 1: {
                PLAYER_CHAT_PREFIX.put(name, PrefixMode.KEEP);
                break;
            }
            case 2: {
                PLAYER_CHAT_PREFIX.put(name, PrefixMode.REMOVE);
                break;
            }
            default: {
                sender.sendMessage(ChatColor.RED + "模式輸入錯誤");
                return false;
            }
        }
        sender.sendMessage(ChatColor.GREEN + "模式變更完成");
        return true;
    }

    private static final Map<String, PrefixMode> PLAYER_CHAT_PREFIX = new HashMap<>();

    @EventHandler
    public void onPlayerPrefix(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String trim = message.trim();

        if (trim.charAt(0) == '>' && trim.length() > 1) {
            String name = event.getPlayer().getName();
            PrefixMode mode = PrefixMode.DISABLE;
            if (PLAYER_CHAT_PREFIX.containsKey(name)) {
                mode = PLAYER_CHAT_PREFIX.get(name);
            }

            switch (mode) {
                case KEEP: {
                    event.setMessage(ChatColor.GREEN + trim);
                    break;
                }
                case REMOVE: {
                    event.setMessage(ChatColor.GREEN + trim.substring(1).trim());
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        boolean color = false;
        StringBuilder sb = new StringBuilder();

        Set<String> nameSet = new HashSet<>();
        StringBuilder nameBuilder = new StringBuilder();

        for (int i = 0; i < message.length(); ++i) {
            char current = message.charAt(i);
            if (current == '@') {
                if (i + 1 < message.length() && message.charAt(i + 1) == '@') {
                    sb.append(current);
                    sb.append(message.charAt(i + 1));
                    ++i;
                    continue;
                }
                sb.append(ChatColor.AQUA);
                color = true;
            } else if (color) {
                if (current == ' ') {
                    nameSet.add(nameBuilder.toString());
                    nameBuilder.setLength(0);

                    sb.append(ChatColor.WHITE);
                    color = false;
                } else if (i == message.length() - 1) {
                    nameBuilder.append(current);
                    nameSet.add(nameBuilder.toString());
                } else {
                    nameBuilder.append(current);
                }
            }
            sb.append(current);
        }

        nameSet.forEach(name -> {
            callPlayer(name);
        });
        event.setMessage(sb.toString());
    }

    public static boolean callPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            return true;
        }
        return false;
    }
}
