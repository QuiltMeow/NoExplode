package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun;

import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Harassment implements Listener, CommandExecutor {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getUniqueId().toString().equalsIgnoreCase("7d3af5ec-627a-4f8e-9142-cd98bf60c6c7")) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "[公告] " + ChatColor.GREEN + "孫北女裝 !");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = Bukkit.getPlayer(UUID.fromString("7d3af5ec-627a-4f8e-9142-cd98bf60c6c7"));
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "目標玩家不在線上");
            return true;
        } else if (player == sender) {
            sender.sendMessage(ChatColor.AQUA + "喵 ?");
            return false;
        }
        Bukkit.broadcastMessage(ChatColor.AQUA + "[玩家公告] " + ChatColor.GOLD + sender.getName() + ChatColor.GREEN + " 要求孫北女裝 !");
        return true;
    }
}
