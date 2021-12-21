package ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.clear;

import com.google.common.collect.ImmutableList;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.clear.MessageInformation.MessageType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// [已測試] 要在原本就不支援移除的聊天框實作收回好像不太實際
// 複雜度 : O(n ^ 2)
public class MessageClear implements Listener {

    private static final int MAX_MESSAGE = 50;
    private static final String COMMAND_REMOVE_MESSAGE = "/messageclear";
    private static final Map<Player, List<MessageInformation>> PLAYER_MESSAGE = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        MessageInformation info = new MessageInformation(event.getFormat(), event.getMessage(), event.getPlayer());
        info.sendMessage();
        event.setCancelled(true);
    }

    public static String getRemoveMessageCommand() {
        return COMMAND_REMOVE_MESSAGE;
    }

    @EventHandler
    public void onMessageRemove(PlayerCommandPreprocessEvent event) {
        String[] argument = event.getMessage().split(" ");
        if (argument.length == 2 && argument[0].equals(COMMAND_REMOVE_MESSAGE)) {
            try {
                int id = Integer.parseInt(argument[1]);
                MessageInformation info = getMessageInfo(id);
                if (info != null) {
                    info.remove();
                    event.setCancelled(true);
                }
            } catch (NumberFormatException ex) {
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PLAYER_MESSAGE.remove(event.getPlayer());
    }

    public static MessageInformation getMessageInfo(int id) {
        for (Map.Entry<Player, List<MessageInformation>> entry : PLAYER_MESSAGE.entrySet()) {
            for (MessageInformation info : entry.getValue()) {
                if (info.getId() == id) {
                    return info;
                }
            }
        }
        return null;
    }

    public static List<MessageInformation> getMessageList(Player player) {
        return PLAYER_MESSAGE.get(player);
    }

    public static boolean addMessage(MessageInformation info) {
        ImmutableList.copyOf(Bukkit.getOnlinePlayers()).forEach(player -> {
            List<MessageInformation> messageList = PLAYER_MESSAGE.get(player);
            if (messageList == null) {
                messageList = new ArrayList<>();
                PLAYER_MESSAGE.put(player, messageList);
            }
            messageList.add(info);
        });
        return true;
    }

    public static boolean addMessage(Player player, MessageInformation info) {
        boolean ret;
        List<MessageInformation> messageList = PLAYER_MESSAGE.get(player);
        if (messageList != null) {
            ret = messageList.add(info);
            if (messageList.size() > MAX_MESSAGE) {
                messageList.remove(0);
            }
        } else {
            messageList = new ArrayList<>();
            ret = messageList.add(info);
            PLAYER_MESSAGE.put(player, messageList);
        }
        return ret;
    }

    public static boolean removeMessage(Player player, MessageInformation info) {
        List<MessageInformation> messageList = PLAYER_MESSAGE.get(player);
        if (messageList != null) {
            boolean ret = messageList.remove(info);
            return ret;
        }
        return false;
    }

    public static boolean removeMessage(MessageInformation info) {
        PLAYER_MESSAGE.entrySet().forEach(entry -> {
            entry.getValue().remove(info);
        });
        return true;
    }

    public static boolean containMessage(Player player, MessageInformation info) {
        return PLAYER_MESSAGE.containsKey(player) && PLAYER_MESSAGE.get(player).contains(info);
    }

    public static boolean containMessage(MessageInformation info) {
        for (Map.Entry<Player, List<MessageInformation>> entry : PLAYER_MESSAGE.entrySet()) {
            for (MessageInformation check : entry.getValue()) {
                if (check.equals(info)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containSendMessage(Player player, String message) {
        for (Map.Entry<Player, List<MessageInformation>> entry : PLAYER_MESSAGE.entrySet()) {
            for (MessageInformation info : entry.getValue()) {
                if (info.getType() == MessageType.PLAYER_MESSAGE && info.getSendMessage().equals(message)) {
                    return true;
                } else if (info.getType() == MessageType.PLUGIN_MESSAGE && info.getMessage().equals(message)) {
                    return true;
                }
            }
        }
        return false;
    }
}
