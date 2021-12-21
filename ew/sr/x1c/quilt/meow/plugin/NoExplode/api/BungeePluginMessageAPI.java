package ew.sr.x1c.quilt.meow.plugin.NoExplode.api;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

public abstract class BungeePluginMessageAPI implements PluginMessageListener {

    public BungeePluginMessageAPI() {
        registerPluginMessage();
    }

    public final void registerPluginMessage() {
        Plugin plugin = Main.getPlugin();
        Messenger messenger = plugin.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, "quilt:bungee_api");
        messenger.registerIncomingPluginChannel(plugin, "quilt:bungee_api", this);
    }

    @Override
    public abstract void onPluginMessageReceived(String channel, Player player, byte[] message);
}
