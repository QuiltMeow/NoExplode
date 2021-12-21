package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class OtherUtil {

    public static void sendNoPermissionMessage(CommandSender sender) { // 因為太可愛所以寫進來了 >A<
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "你不是我的主人，尼奏開 !!!");
    }
}
