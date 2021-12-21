package ew.sr.x1c.quilt.meow.plugin.NoExplode;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.TickUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NoticeBroadcast implements CommandExecutor {

    private static final String PREFIX = "§B[喵嗚嗚公告] §F";

    private static final String[] NOTICE = new String[]{
        "§A歡迎來到§B韓喵同樂伺服器",
        "§E伺服器位址 §F: §Amcp.quilt.idv.tw §F/ §E地圖 §F: §Amcp.quilt.idv.tw:8123",
        "§6Discord 群組 §F: §Ahttps://discord.gg/32kmMug",
        "§E咕嚕靈波 （●′∀‵）ノ♡",
        "§E貓貓最可愛了唷 喵嗚 ฅ^•ω•^ฅ ~"
    };

    private static int current;

    public static void registerNoticeTimer() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), () -> {
            if (current >= NOTICE.length) {
                current = 0;
            }
            Bukkit.broadcastMessage(PREFIX + NOTICE[current]);
            ++current;
        }, TickUtil.secondToTick(3600), TickUtil.secondToTick(3600));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("quilt.notice")) {
            sender.sendMessage(ChatColor.RED + "很抱歉 你沒有使用指令權限 !");
            return false;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "指令使用方法 : /notice [公告內容]");
            return false;
        }
        String notice = ChatColor.translateAlternateColorCodes('&', ArgumentUtil.joinStringFrom(args, 0));
        Bukkit.broadcastMessage(PREFIX + notice);
        return true;
    }
}
