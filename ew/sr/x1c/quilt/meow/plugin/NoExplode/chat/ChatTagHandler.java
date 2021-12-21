package ew.sr.x1c.quilt.meow.plugin.NoExplode.chat;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.database.DatabaseConnection;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatTagHandler implements Listener, CommandExecutor {

    private static final int MAX_TAG_LENGTH = 20;
    private static final Map<String, String> CHAT_TAG = new ConcurrentHashMap<>();

    public static String getChatTag(String uuid) {
        if (CHAT_TAG.containsKey(uuid)) {
            return ChatColor.translateAlternateColorCodes('&', CHAT_TAG.get(uuid));
        }
        return "";
    }

    public static void removeChatTagAsync(String uuid) {
        new Thread(() -> {
            Connection con = DatabaseConnection.getConnection();
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM chat_tag WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Main.getPlugin().getLogger().log(Level.WARNING, "刪除玩家聊天標籤時發生例外狀況", ex);
            }
        }).start();
    }

    public static String queryChatTag(String uuid) {
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM chat_tag WHERE uuid = ?")) {
            ps.setString(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tag");
                }
            }
        } catch (SQLException ex) {
            Main.getPlugin().getLogger().log(Level.WARNING, "查詢玩家聊天標籤時發生例外狀況", ex);
        }
        return null;
    }

    private static void loadChatTagAsync(String uuid) {
        new Thread(() -> {
            if (!CHAT_TAG.containsKey(uuid)) {
                String tag = queryChatTag(uuid);
                CHAT_TAG.put(uuid, tag == null ? "" : tag);
            }
        }).start();
    }

    public static void saveChatTagAsync(String uuid, String tag) {
        new Thread(() -> {
            Connection con = DatabaseConnection.getConnection();
            try (PreparedStatement ps = con.prepareStatement("REPLACE INTO chat_tag (uuid, tag) VALUES (?, ?)")) {
                ps.setString(1, uuid);
                ps.setString(2, tag);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Main.getPlugin().getLogger().log(Level.WARNING, "變更玩家聊天標籤時發生例外狀況", ex);
            }
        }).start();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();
        loadChatTagAsync(uuid);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "請輸入指定標籤");
            return false;
        }

        String uuid = ((Player) sender).getUniqueId().toString();
        String tag = ArgumentUtil.joinStringFrom(args, 0).replaceAll("\\[", "").replaceAll("\\]", "").trim();
        if (tag.equalsIgnoreCase("remove")) {
            CHAT_TAG.put(uuid, "");
            removeChatTagAsync(uuid);
            sender.sendMessage(ChatColor.GREEN + "聊天標籤移除完成");
            return true;
        }

        int length = tag.length();
        if (length <= 0 || length > MAX_TAG_LENGTH) {
            sender.sendMessage(ChatColor.RED + "輸入標籤長度過長或為空 (最大長度 : " + MAX_TAG_LENGTH + ")");
            return false;
        }

        CHAT_TAG.put(uuid, tag);
        saveChatTagAsync(uuid, tag);
        sender.sendMessage(ChatColor.GREEN + "聊天標籤設定完成 : " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', tag));
        return true;
    }
}
