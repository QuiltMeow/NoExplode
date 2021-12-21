package ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.clear;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MessageProtocol {

    private static boolean wasText;

    public static void registerMessagePacket() {
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        pm.addPacketListener(new PacketAdapter(Main.getPlugin(), ListenerPriority.NORMAL, new PacketType[]{Server.CHAT}) {

            @Override
            public void onPacketSending(PacketEvent event) {
                String message = "";
                try {
                    String jsonMessage = event.getPacket().getChatComponents().getValues().get(0).getJson();
                    if (jsonMessage != null && !jsonMessage.isEmpty()) {
                        message = jsonToString(jsonMessage);
                    }
                } catch (Exception ex) {
                }
                if (!message.equals("") && !message.equals("{\"text\":\"\"}") && !MessageClear.containSendMessage(event.getPlayer(), message)) {
                    MessageClear.addMessage(event.getPlayer(), new MessageInformation(message, event.getPlayer()));
                }
            }
        });
    }

    private static ChatColor getChatColor(String colorName) {
        ChatColor[] colorList = MessageInformation.getChatColorList();
        for (int i = 0; i < colorList.length; ++i) {
            ChatColor color = colorList[i];
            if (color.name().equalsIgnoreCase(colorName)) {
                return color;
            }
        }
        return null;
    }

    private static String getStringValue(String key, String value) {
        if (key.equalsIgnoreCase("text")) {
            return value;
        }
        if (key.equalsIgnoreCase("color")) {
            return new StringBuilder().append(getChatColor(value)).toString();
        }
        return null;
    }

    private static String getBooleanValue(String key, boolean value) {
        if (!value) {
            return "";
        }
        ChatColor color = getChatColor(key);
        if (color != null) {
            return color.toString();
        }
        if (key.equalsIgnoreCase("underlined")) {
            return ChatColor.UNDERLINE.toString();
        }
        if (key.equalsIgnoreCase("obfuscated")) {
            return ChatColor.MAGIC.toString();
        }
        return null;
    }

    private static String jsonToString(JSONObject source) {
        String result = "";
        for (Object key : source.keySet()) {
            Object value = source.get(key);
            String stringKey = (String) key;
            if (stringKey.equalsIgnoreCase("text")) {
                if (wasText && value instanceof String) {
                    value = ChatColor.WHITE + (String) value;
                } else {
                    wasText = true;
                }
            } else if (stringKey.equalsIgnoreCase("color")) {
                wasText = false;
            }
            if (value instanceof String) {
                if (!(key instanceof String)) {
                    continue;
                }
                result += getStringValue(stringKey, (String) value);
            } else {
                if (!(value instanceof Boolean)) {
                    continue;
                }
                if (!(key instanceof String)) {
                    continue;
                }
                result += getBooleanValue(stringKey, (boolean) value);
            }
        }
        return result;
    }

    private static String jsonToString(JSONArray source) {
        String result = "";
        wasText = false;
        for (Object value : source) {
            if (value instanceof String) {
                result += (String) value;
            } else if (value instanceof JSONObject) {
                result += jsonToString((JSONObject) value);
            } else {
                if (!(value instanceof JSONArray)) {
                    continue;
                }
                result += jsonToString((JSONArray) value);
            }
        }
        return result;
    }

    private static String jsonToString(String json) {
        JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
        if (jsonObject == null || json.isEmpty()) {
            return json;
        }
        JSONArray array = (JSONArray) jsonObject.get("extra");
        if (array == null || array.isEmpty()) {
            return json;
        }
        return jsonToString(array);
    }
}
