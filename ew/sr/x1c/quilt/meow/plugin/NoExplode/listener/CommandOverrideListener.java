package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandOverrideListener implements Listener {

    private static final List<String> DISABLE_PREFIX = new ArrayList<>(Arrays.asList(new String[]{
        "/cmi:",
        "/minecraft:"
    }));

    private static final List<String> DISABLE_COMMAND = new ArrayList<>(Arrays.asList(new String[]{
        "/tp",
        "/op",
        "/gamemode",
        "/tphere",
        "/kill"
    }));

    private static final List<String> VERSION_COMMAND = new ArrayList<>(Arrays.asList(new String[]{
        "/bukkit:ver",
        "/bukkit:version",
        "/ver",
        "/version",
        "/about",
        "/bukkit:about",
        "/icanhasbukkit"
    }));

    private static final List<String> HELP_COMMAND = new ArrayList<>(Arrays.asList(new String[]{
        "/minecraft:help",
        "/help",
        "/?",
        "/bukkit:help",
        "/bukkit:?"
    }));

    private static final List<String> PLUGIN_COMMAND = new ArrayList<>(Arrays.asList(new String[]{
        "/bukkit:pl",
        "/bukkit:plugins",
        "/pl",
        "/plugins"
    }));

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String playerCommand = event.getMessage().toLowerCase();
        String pattern = playerCommand.split(" ")[0];
        // 有人很偷懶 ouo
        if (pattern.equalsIgnoreCase("/fill") || pattern.equalsIgnoreCase("/summon")) {
            player.playSound(player.getLocation(), Sound.ENTITY_GHAST_HURT, 1, 1);
            return;
        }

        for (String command : DISABLE_PREFIX) {
            if (pattern.startsWith(command) && !player.getUniqueId().toString().equalsIgnoreCase(Main.AUTHOR_UUID)) {
                event.setCancelled(true);
                return;
            }
        }

        for (String command : DISABLE_COMMAND) {
            if (pattern.equalsIgnoreCase(command) && !player.getUniqueId().toString().equalsIgnoreCase(Main.AUTHOR_UUID)) {
                event.setCancelled(true);
                return;
            }
        }

        for (String command : VERSION_COMMAND) {
            if (pattern.equalsIgnoreCase(command)) {
                player.sendMessage("===== AkatsukiMeow Server Framework =====");
                player.sendMessage("伺服器正在執行棉被家族服務端架構 (遊戲版本 1.16.5) (實作 API 版本 : 1.16.5-R0.1-SNAPSHOT)");
                player.sendMessage("正在檢查新版本 請稍後 ...");
                player.sendMessage("您目前正在執行最新版本");
                event.setCancelled(true);
                return;
            }
        }
        for (String command : HELP_COMMAND) {
            if (pattern.equalsIgnoreCase(command)) {
                player.sendMessage(ChatColor.YELLOW + "當個創世神 (英語 : Minecraft) 是一款沙盒遊戲");
                player.sendMessage(ChatColor.YELLOW + "玩家可以在一個隨機生成的 3D 世界內，以帶材質貼圖的立方體為基礎進行遊戲");
                player.sendMessage(ChatColor.YELLOW + "遊戲中的其他特色包括探索世界、採集資源、合成物品及生存冒險等");
                player.sendMessage(ChatColor.AQUA + "總遊玩人數 : " + Bukkit.getOfflinePlayers().length);
                player.sendMessage("樣式代碼表 : §00 §11 §22 §33 §44 §55 §66 §77 §88 §99 §AA §BB §CC §DD §EE §FF 樣式字元 : &");
                player.sendMessage("格式代碼表 : K 隨機字符 §KMeow§R / L §L粗體§R / M §M刪除線§R / N §N下劃線§R / O §O斜體§R / R 重置文字樣式");
                player.sendMessage("動態地圖 : " + ChatColor.AQUA + "mcp.quilt.idv.tw:8123");
                player.sendMessage("/suicide 自殺 /respawn 復活 /death 母湯紀錄排行榜");
                player.sendMessage("/freecam 觀察者模式 /nick 修改暱稱 /tag 修改稱號");
                player.sendMessage("/sign 修改告示牌文字 /armorhide 隱藏自身裝備");
                player.sendMessage("/uptime 伺服器運行時間 /uptimefirst 距第一次開服伺服器運行時間");
                player.sendMessage("/info 顯示玩家狀態 /stats 顯示統計資料");
                player.sendMessage("/here 廣播目前所在位置 /find 找尋目標玩家所在相對方位");
                player.sendMessage("/peek 界伏盒 / 終界箱預覽");
                player.sendMessage("/call 標註其他玩家");
                player.sendMessage("/calc 計算機功能");
                player.sendMessage("/wiki 搜尋 Minecraft Wiki /hdb 頭顱資料庫");
                player.sendMessage("§A/glow 任意物品附魔指令 /meow 喵喵叫 §B指令前綴 : NoExplode");
                player.sendMessage("§B插件開源 : §Ehttps://smallquilt.quilt.idv.tw:8923/ouo/mc_dev");
                event.setCancelled(true);
                return;
            }
        }
        for (String command : PLUGIN_COMMAND) {
            if (pattern.equalsIgnoreCase(command)) {
                player.sendMessage("已安裝的插件 (1) : " + ChatColor.GREEN + "QuiltPlugin");
                event.setCancelled(true);
                return;
            }
        }
    }

    public static void removeBlock(Block block) {
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.AIR);
            }
        }.runTaskLater(Main.getPlugin(), 2);
    }
}
