package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.EconomyManager;
import java.util.Collection;
// import net.md_5.bungee.api.ChatMessageType;
// import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OnlineEconomy {

    public static void registerOnlineTimer() {
        new BukkitRunnable() {

            @Override
            public void run() {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();
                        playerList.forEach(player -> {
                            EconomyManager.gainMoney(player, 10);
                            // player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§D獲得線上金錢獎勵"));
                        });
                    }
                }.runTask(Main.getPlugin());
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 3600, 3600);
    }
}
