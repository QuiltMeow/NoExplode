package ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.clear;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageInformation {

    private static final int CLEAR_LINE = 100;
    private static final ChatColor[] CHAT_COLOR = ChatColor.values();

    private final String format;
    private final String message;
    private final Player sender;
    private final int id;
    private final MessageType type;

    public MessageInformation(String message, Player sender) {
        format = null;
        this.message = message;
        this.sender = sender;
        id = initRandomId();
        type = MessageType.PLUGIN_MESSAGE;
    }

    public MessageInformation(String format, String message, Player sender) {
        this.format = format;
        this.message = message;
        this.sender = sender;
        id = initRandomId();
        type = MessageType.PLAYER_MESSAGE;
    }

    public static ChatColor[] getChatColorList() {
        return CHAT_COLOR;
    }

    public String getFormat() {
        return format;
    }

    public String getMessage() {
        return message;
    }

    public Player getSender() {
        return sender;
    }

    public int getId() {
        return id;
    }

    public MessageType getType() {
        return type;
    }

    public boolean exist() {
        return MessageClear.containMessage(this);
    }

    public boolean sendMessage() {
        return sendMessage(false, true);
    }

    public String getSendMessage() {
        return stringToChatColor(format.replace("%1$s", sender.getName()).replace("%2$s", message));
    }

    public boolean sendMessage(boolean force, boolean addToList) {
        if ((!force && !exist()) || force) {
            if (addToList) {
                MessageClear.addMessage(this);
            }
            Bukkit.getOnlinePlayers().forEach(player -> {
                sendMessage(player);
            });
            return true;
        }
        return false;
    }

    private void sendMessage(Player player) {
        if (type == MessageType.PLAYER_MESSAGE) {
            if (sender == player) {
                TextComponent send = new TextComponent(getSendMessage().replace("%2$s", message));
                TextComponent button = new TextComponent(ChatColor.RED + " [×]");
                button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, MessageClear.getRemoveMessageCommand() + " " + id));
                button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "收回該訊息").create()));
                send.addExtra(button);
                player.spigot().sendMessage(send);
            } else {
                player.sendMessage(getSendMessage());
            }
        } else if (type == MessageType.PLUGIN_MESSAGE) {
            player.sendMessage(message);
        }
    }

    public boolean remove() {
        if (exist()) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                for (int i = 0; i < CLEAR_LINE; ++i) {
                    player.sendMessage("");
                }
            });
            MessageClear.removeMessage(this);
            Bukkit.getOnlinePlayers().forEach(player -> {
                List<MessageInformation> messageList = new ArrayList<>();
                if (MessageClear.getMessageList(player) != null) {
                    messageList.addAll(MessageClear.getMessageList(player));
                }
                Collections.rotate(messageList, messageList.size());
                messageList.forEach(info -> {
                    info.sendMessage(player);
                });
            });
            return true;
        }
        return false;
    }

    private static int initRandomId() {
        int ret;
        for (ret = Randomizer.nextInt(); MessageClear.getMessageInfo(ret) != null; ret = Randomizer.nextInt()) {
        }
        return ret;
    }

    private static String stringToChatColor(String message) {
        for (int i = 0; i < CHAT_COLOR.length; ++i) {
            ChatColor color = CHAT_COLOR[i];
            message = message.replace("&" + color.getChar(), new StringBuilder().append(color).toString());
        }
        return message;
    }

    public enum MessageType {
        PLAYER_MESSAGE,
        PLUGIN_MESSAGE;
    }
}
