package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class CommandSuggestionRemove implements Listener {

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent event) {
        event.getCommands().clear();
    }

    // 封包層 可以直接過濾掉上層 Bungee Cord 指令表
    public static void registerCommandSuggestionPacket() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getPlugin(), ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Server.COMMANDS}) {

            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.COMMANDS) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
