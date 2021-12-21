package ew.sr.x1c.quilt.meow.plugin.NoExplode;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabProtect {

    private static final List<String> NO_TAB_COMMAND = new ArrayList<>(Arrays.asList(new String[]{
        "/ver",
        "/version",
        "/help",
        "/?"
    }));

    public static void registerTabProtect() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getPlugin(), ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.TAB_COMPLETE}) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                    PacketContainer packet = event.getPacket();
                    String message = ((String) packet.getSpecificModifier(String.class).read(0)).toLowerCase();
                    for (String command : NO_TAB_COMMAND) {
                        if (message.startsWith(command) || (message.startsWith("/") && (!message.contains(" ") || message.contains(":")))) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        });
    }
}
