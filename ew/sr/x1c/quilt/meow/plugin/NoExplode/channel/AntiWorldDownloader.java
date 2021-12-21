package ew.sr.x1c.quilt.meow.plugin.NoExplode.channel;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class AntiWorldDownloader implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (channel.equals("wdl:init")) {
            player.kickPlayer("伺服器無法使用 World Downloader 模組");
        }
    }
}
