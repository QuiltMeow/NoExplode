package ew.sr.x1c.quilt.meow.plugin.NoExplode.chat;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.dynmap.DynmapAPI;

// Deluxe Chat 不更新 只好自己來了 w
public class ChatFormatListener implements Listener {

    private static final TextComponent SPACE = new TextComponent(" ");
    private static final DynmapAPI DYNMAP_API = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");

    public static DynmapAPI getDynmapAPI() {
        return DYNMAP_API;
    }

    public static String getWhisperCommand(String name) {
        return new StringBuilder().append("/m ").append(name).append(" ").toString();
    }

    public static String getNickName(Player player) {
        return PlaceholderAPI.setPlaceholders(player, "%cmi_user_display_name%");
    }

    public static String getCurrentTime() {
        return PlaceholderAPI.setPlaceholders(null, "%server_time_HH:mm:ss%");
    }

    public static String getServerName() {
        return "極限生存"; // 暫時不必提供更多資訊
    }

    private static List<TextComponent> translateURL(String message) {
        String[] split = message.split(" ");
        List<TextComponent> ret = new ArrayList<>();
        for (String data : split) {
            TextComponent current = new TextComponent(data);
            if (data.startsWith("http")) {
                current.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, data));
            }
            ret.add(current);
            ret.add(SPACE);
        }
        ret.remove(ret.size() - 1);
        return ret;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = ChatColor.translateAlternateColorCodes('&', event.getMessage());
        Player player = event.getPlayer();
        String name = player.getName();
        String nick = new StringBuilder().append(getNickName(player)).append("§R").toString();

        String uuid = player.getUniqueId().toString();
        String tag = ChatTagHandler.getChatTag(uuid);
        if (!tag.equals("")) {
            tag = new StringBuilder().append("[").append(tag).append("§R] ").toString();
        }

        String prefix = new StringBuilder()
                .append("<").append(tag)
                .append("§3").append(nick)
                .append(">").toString();

        StringBuilder hover = new StringBuilder();
        hover.append("§E傳送時間 : §6").append(getCurrentTime()).append("\n");
        hover.append("§E分流名稱 : §B").append(getServerName()).append("\n");
        hover.append("§E玩家識別 : §B").append(uuid).append("\n");
        hover.append("§E玩家名稱 : §B").append(name).append("\n");
        hover.append("§E玩家暱稱 : §B").append(nick).append("\n");
        hover.append("§E玩家等級 : §B").append(player.getLevel()).append("\n");
        hover.append("§E所在位置 : §B").append(PlaceholderAPI.setPlaceholders(player, "%player_world% %player_biome% (%player_x%, %player_y%, %player_z%)")).append("\n");
        hover.append("§A點擊後可以對該玩家進行密語");

        Main.getPlugin().getLogger().log(Level.INFO, "{0} {1}", new Object[]{
            prefix, message
        });
        TextComponent output = new TextComponent(prefix);
        output.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getWhisperCommand(name)));
        output.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hover.toString())));

        List<TextComponent> send = new ArrayList<>();
        send.add(output);
        send.add(SPACE);
        send.addAll(translateURL(message));
        Bukkit.broadcast(send.toArray(new TextComponent[0]));

        DYNMAP_API.postPlayerMessageToWeb(player, message);
        event.setCancelled(true);
    }

    // 密語指令 (+ 擴展)
    private static final List<String> WHISPER_COMMAND = new ArrayList<>(Arrays.asList(new String[]{
        "/w",
        "/m",
        "/msg",
        "/message",
        "/whisper",
        "/tell",
        "/minecraft:w",
        "/minecraft:m",
        "/minecraft:msg",
        "/minecraft:message",
        "/minecraft:whisper",
        "/minecraft:tell"
    }));

    @EventHandler
    public void onPlayerWhisper(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String[] pattern = event.getMessage().split(" ");
        String prefix = pattern[0].toLowerCase();

        for (String command : WHISPER_COMMAND) {
            if (prefix.equalsIgnoreCase(command)) {
                event.setCancelled(true);
                if (pattern.length < 3) {
                    player.sendMessage(ChatColor.RED + "密語指令格式錯誤 需要包含玩家與訊息資訊");
                    return;
                }

                Player target = Bukkit.getPlayer(pattern[1]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "找不到指定玩家");
                    return;
                }

                String nick = getNickName(player);
                String targetNick = getNickName(target);
                String message = ChatColor.translateAlternateColorCodes('&', ArgumentUtil.joinStringFrom(pattern, 2));
                StringBuilder senderMessage = new StringBuilder();
                senderMessage.append("§B『密語頻道』§F[§A我 §F-> §E").append(targetNick).append("§F]");

                StringBuilder receiverMessage = new StringBuilder();
                receiverMessage.append("§B『密語頻道』§F[§E").append(nick).append(" §F-> §A我§F]");

                String hover = new StringBuilder().append("§E傳送時間 : §6").append(getCurrentTime()).toString();
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new StringBuilder().append(hover).append("\n§A點擊後可以對該玩家進行密語").toString()));

                TextComponent sender = new TextComponent(senderMessage.toString());
                sender.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getWhisperCommand(target.getName())));
                sender.setHoverEvent(hoverEvent);

                List<TextComponent> output = new ArrayList<>();
                output.add(sender);
                output.add(SPACE);
                output.addAll(translateURL(message));
                player.sendMessage(output.toArray(new TextComponent[0]));

                TextComponent receiver = new TextComponent(receiverMessage.toString());
                receiver.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getWhisperCommand(player.getName())));
                receiver.setHoverEvent(hoverEvent);
                output.set(0, receiver);
                target.sendMessage(output.toArray(new TextComponent[0]));

                String spyString = new StringBuilder().append("§8『§A密語轉發§8』§F[").append(nick).append(" §E-> §F").append(targetNick).append("§F]").toString();
                Main.getPlugin().getLogger().log(Level.INFO, "{0} {1}", new Object[]{
                    spyString, message
                });

                TextComponent spy = new TextComponent(spyString);
                spy.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hover)));
                output.set(0, spy);
                Bukkit.getOnlinePlayers().forEach(forward -> {
                    if (forward.isOp() && forward != player && forward != target) {
                        forward.sendMessage(output.toArray(new TextComponent[0]));
                    }
                });
                return;
            }
        }
    }
}
