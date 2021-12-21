package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.TickUtil;
import java.util.List;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

// [保留未使用] 從 1.12.2 QuiltPlugin 移植
// 雙倍金錢需使用 MoneyDrop 插件
// 以前寫得真爛
public class DoubleEventListener implements Listener {

    private static double expDoubleRate = 2;
    private static double moneyDoubleRate = 2;

    private static boolean expDoubleEvent = false;
    private static boolean moneyDoubleEvent = false;

    private static int expDoubleTime = 60;
    private static int moneyDoubleTime = 60;

    private static int expDoubleSchedule = -1;
    private static int moneyDoubleSchedule = -1;

    private static final int DOUBLE_EVENT_NOTICE_TIME = 3;

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        if (expDoubleEvent) {
            int xp = event.getAmount();
            if (xp > 0) {
                event.setAmount((int) (xp * expDoubleRate));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickUp(PlayerPickupItemEvent event) {
        if (moneyDoubleEvent) {
            ItemStack item = event.getItem().getItemStack();
            List<String> moneyLore = modifyMoneyLore(item);
            if (moneyLore != null) {
                item.getItemMeta().setLore(moneyLore);
            }
        }
    }

    public List<String> modifyMoneyLore(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (int i = 0; i < lore.size(); ++i) {
                String loreString = lore.get(i);
                if (loreString.endsWith(" MoneyDropMoney")) {
                    String pickUp = loreString.replace(" MoneyDropMoney", "");
                    try {
                        double amount = Double.parseDouble(pickUp);
                        lore.set(i, amount * moneyDoubleRate + " MoneyDropMoney");
                        return lore;
                    } catch (NumberFormatException ex) {
                        System.err.println("金錢數值轉換發生例外狀況 : " + ex);
                    }
                }
            }
        }
        return null;
    }

    public static void activeDoubleExpEvent(double rate, int time) {
        if (expDoubleEvent) {
            Bukkit.broadcastMessage(ChatColor.RED + "經驗加倍活動已在進行中 !");
            return;
        }

        expDoubleTime = time;
        long expEventEndTime = System.currentTimeMillis() + expDoubleTime * 1000 * 60;
        int noticeTime = TickUtil.minuteToTick(DOUBLE_EVENT_NOTICE_TIME);
        expDoubleSchedule = Main.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
            long current = System.currentTimeMillis();
            if (current >= expEventEndTime) {
                Main.getPlugin().getServer().getScheduler().cancelTask(expDoubleSchedule);
                expDoubleSchedule = -1;
                expDoubleEvent = false;
                Bukkit.broadcastMessage(ChatColor.YELLOW + "經驗加倍活已結束 !");
                return;
            }

            long remainingMinute = (expEventEndTime - current) / 1000 / 60;
            Bukkit.getOnlinePlayers().forEach(online -> {
                online.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "經驗加倍活動進行中 ! 剩餘 " + remainingMinute + " 分鐘 !"));
            });
        }, noticeTime, noticeTime);

        if (expDoubleSchedule == -1) {
            Bukkit.broadcastMessage(ChatColor.RED + "經驗加倍活動啟用失敗 !");
            return;
        }

        expDoubleRate = rate;
        expDoubleEvent = true;
        Bukkit.broadcastMessage(ChatColor.GREEN + "經驗加倍活動已開始 ! 持續時間 : " + time + " 分鐘 !");
    }

    public static void activeDoubleMoneyEvent(double rate, int time) {
        if (moneyDoubleEvent) {
            Bukkit.broadcastMessage(ChatColor.RED + "金錢加倍活動已在進行中 !");
            return;
        }

        moneyDoubleTime = time;
        long moneyEventEndTime = System.currentTimeMillis() + moneyDoubleTime * 1000 * 60;
        int noticeTime = TickUtil.minuteToTick(DOUBLE_EVENT_NOTICE_TIME);
        moneyDoubleSchedule = Main.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
            long current = System.currentTimeMillis();
            if (current >= moneyEventEndTime) {
                Main.getPlugin().getServer().getScheduler().cancelTask(moneyDoubleSchedule);
                moneyDoubleSchedule = -1;
                moneyDoubleEvent = false;
                Bukkit.broadcastMessage(ChatColor.YELLOW + "金錢加倍活已結束 !");
                return;
            }

            long remainingMinute = (moneyEventEndTime - current) / 1000 / 60;
            Bukkit.getOnlinePlayers().forEach(online -> {
                online.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "金錢加倍活動進行中 ! 剩餘 " + remainingMinute + " 分鐘 !"));
            });
        }, noticeTime, noticeTime);

        if (moneyDoubleSchedule == -1) {
            Bukkit.broadcastMessage(ChatColor.RED + "金錢加倍活動啟用失敗 !");
            return;
        }

        moneyDoubleRate = rate;
        moneyDoubleEvent = true;
        Bukkit.broadcastMessage(ChatColor.GREEN + "金錢加倍活動已開始 ! 持續時間 : " + time + " 分鐘 !");
    }

    public static void forceStopDoubleExpEvent() {
        if (expDoubleSchedule != -1) {
            Main.getPlugin().getServer().getScheduler().cancelTask(expDoubleSchedule);
            expDoubleSchedule = -1;
        }
        expDoubleEvent = false;
        Bukkit.broadcastMessage(ChatColor.YELLOW + "經驗加倍活動已強制停止 !");
    }

    public static void forceStopDoubleMoneyEvent() {
        if (moneyDoubleSchedule != -1) {
            Main.getPlugin().getServer().getScheduler().cancelTask(moneyDoubleSchedule);
            moneyDoubleSchedule = -1;
        }
        moneyDoubleEvent = false;
        Bukkit.broadcastMessage(ChatColor.YELLOW + "金錢加倍活動已強制停止 !");
    }
}
