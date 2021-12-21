package ew.sr.x1c.quilt.meow.plugin.NoExplode;

import com.comphenix.protocol.PacketType.Status.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerVersionChanger {

    public static void registerServerVersion() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getPlugin(), ListenerPriority.HIGHEST, Arrays.asList(Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {

            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                ping.setVersionName("§B韓喵架構 2.0");

                List<WrappedGameProfile> playerList = new ArrayList<>();
                ImmutableList<Player> onlineList = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
                onlineList.forEach(player -> {
                    playerList.add(new WrappedGameProfile(UUID.randomUUID(), PlaceholderAPI.setPlaceholders(player, "%cmi_user_display_name%")));
                });
                ping.setPlayers(playerList);
            }
        });
    }
}
