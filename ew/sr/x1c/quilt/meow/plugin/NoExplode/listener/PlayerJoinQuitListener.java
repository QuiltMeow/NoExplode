package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.ArmorHidePacketHandler;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Main.getPlugin().isUsePlaceHolder()) {
            String request = "§A[+] %cmi_user_display_name% §B加入了遊戲 §D!";
            event.setJoinMessage(PlaceholderAPI.setPlaceholders(player, request));
        } else {
            event.setJoinMessage("§A[+] " + player.getName() + " §B加入了遊戲 §D!");
        }
        player.sendMessage(ChatColor.AQUA + "伺服器極限模式已開啟 死亡時需等待時間才能進行復活 隨死亡次數增加而延長");
        player.sendMessage(ChatColor.YELLOW + "相關指令可透過 /help 查詢");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "裝備隱藏狀態 : " + (ArmorHidePacketHandler.isHide(player) ? "開啟" : "關閉"));
        player.spawnParticle(Particle.SPELL_MOB, player.getLocation(), 50);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Main.getPlugin().isUsePlaceHolder()) {
            String request = "§C[-] §A%cmi_user_display_name% §B離開了遊戲 §D!";
            event.setQuitMessage(PlaceholderAPI.setPlaceholders(player, request));
        } else {
            event.setQuitMessage("§C[-] §A" + player.getName() + " §B離開了遊戲 §D!");
        }
        player.spawnParticle(Particle.SPELL_MOB, player.getLocation(), 50);
    }
}
