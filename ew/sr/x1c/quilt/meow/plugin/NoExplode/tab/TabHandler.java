package ew.sr.x1c.quilt.meow.plugin.NoExplode.tab;

import com.google.common.collect.ImmutableList;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.logging.Level;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabHandler {

    private static final int UPDATE_INTERVAL = 5;
    private static final String SERVER_NAME = "韓喵同樂伺服器";

    public static String removeLastCharacter(String data, int remove) {
        return data.substring(0, data.length() - remove);
    }

    public static void registerTabTimer() {
        StringBuilder header = new StringBuilder();
        header.append("------------------------------------\n");
        header.append("§7歡迎來到").append(SERVER_NAME).append("\n");
        header.append("線上 §F");

        StringBuilder footer = new StringBuilder();
        footer.append("------------------------------------\n");
        new BukkitRunnable() {

            @Override
            public void run() {
                StringBuilder headerOutput = new StringBuilder(header);
                headerOutput.append(PlaceholderAPI.setPlaceholders(null, "%server_online% §7/ §F%server_max_players%")).append("\n");
                headerOutput.append("------------------------------------");

                String time = PlaceholderAPI.setPlaceholders(null, " §7時間 §F%server_time_HH:mm:ss%");
                String tps = removeLastCharacter(PlaceholderAPI.setPlaceholders(null, "§7TPS %server_tps%"), 2).replace(",", "§F,");
                String mspt = PlaceholderAPI.setPlaceholders(null, "§7MSPT %quilt_server_mspt%");
                try {
                    ImmutableList<Player> playerList = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
                    playerList.forEach(player -> {
                        StringBuilder footerOutput = new StringBuilder(footer);
                        try {
                            footerOutput.append(PlaceholderAPI.setPlaceholders(player, "§7%player_world% §F(%player_x%, %player_y%, %player_z%)")).append("\n");
                            footerOutput.append(PlaceholderAPI.setPlaceholders(player, "§7金錢 §F%vault_eco_balance_formatted%").toUpperCase()).append(time).append("\n");
                            footerOutput.append(PlaceholderAPI.setPlaceholders(player, "§7世界時間 §F%quilt_player_world_time_12%")).append("\n");
                            footerOutput.append(tps).append("\n");
                            footerOutput.append(mspt).append("\n");
                            footerOutput.append(PlaceholderAPI.setPlaceholders(player, "§7延遲 §F%player_ping% §7毫秒")).append("\n");
                            footerOutput.append("§F------------------------------------");
                            player.setPlayerListHeaderFooter(headerOutput.toString(), footerOutput.toString());
                        } catch (Exception ex) {
                            Main.getPlugin().getLogger().log(Level.WARNING, "TAB 清單更新時發生例外狀況", ex);
                        }
                    });
                } catch (Exception ex) {
                    Main.getPlugin().getLogger().log(Level.WARNING, "取得玩家列表時發生例外狀況", ex);
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), UPDATE_INTERVAL * 20, UPDATE_INTERVAL * 20);
    }
}
