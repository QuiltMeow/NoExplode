package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.FreeCamCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    private static final Map<String, Long> DEATH_TIME = new HashMap<>();
    private static final Map<String, Integer> FREE_DEATH = new HashMap<>();
    private static final List<String> PROTECT_UUID = new ArrayList<>();

    public static boolean isProtectPlayer(Player player) {
        String playerUUID = player.getUniqueId().toString();
        for (String uuid : PROTECT_UUID) {
            if (playerUUID.equalsIgnoreCase(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static void loadDeathProtectPlayer() {
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM death_protect")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PROTECT_UUID.add(rs.getString("uuid"));
                }
            }
        } catch (SQLException ex) {
            Main.getPlugin().getLogger().log(Level.WARNING, "????????????????????????????????????", ex);
        }
    }

    public int attachFreeDeath(Player player) {
        String name = player.getName();
        Integer count = FREE_DEATH.get(name);
        if (count == null || count == 0) {
            return -1;
        }
        --count;
        FREE_DEATH.put(name, count);
        updateFreeDeathAsync(name, count);
        return count;
    }

    public static void queryFreeDeath() {
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM free_death")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FREE_DEATH.put(rs.getString("name"), rs.getInt("remaining"));
                }
            }
        } catch (SQLException ex) {
            Main.getPlugin().getLogger().log(Level.WARNING, "????????????????????????????????????", ex);
        }
    }

    public static void updateFreeDeathAsync(String name, int count) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Connection con = DatabaseConnection.getConnection();
                try (PreparedStatement ps = con.prepareStatement("UPDATE free_death SET remaining = ? WHERE name = ?")) {
                    ps.setInt(1, count);
                    ps.setString(2, name);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    Main.getPlugin().getLogger().log(Level.WARNING, "????????????????????????????????????", ex);
                }
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location location = player.getLocation();
        player.sendMessage(ChatColor.YELLOW + "?????????????????? (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")");

        String name = player.getName();
        long time = System.currentTimeMillis();
        if (isProtectPlayer(player)) {
            DEATH_TIME.put(name, 0L);
            player.sendMessage(ChatColor.AQUA + "????????????????????? ???????????????????????????");
            return;
        }

        int freeDeath = attachFreeDeath(player);
        if (freeDeath >= 0) {
            DEATH_TIME.put(name, 0L);
            player.sendMessage(ChatColor.AQUA + "????????????????????? ???????????????????????? : " + freeDeath);
            return;
        }
        DEATH_TIME.put(name, time);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (FreeCamCommand.getFreeCamLocation().get(player.getName()) != null) {
                event.setCancelled(true);
            }
        }
    }

    public static Map<String, Long> getDeathTime() {
        return DEATH_TIME;
    }

    public static void showRespawnMessage(Player player) {
        int deathCount = getDeathCount(player.getName());
        int cooltime = getRespawnCooltime(deathCount);
        player.sendMessage(ChatColor.YELLOW + "?????????????????? : " + deathCount + " ???????????????????????? : " + cooltime + " ??????");
        player.sendMessage(ChatColor.GREEN + "????????? /respawn ????????????");
    }

    public static boolean isDeath(Player player) {
        return DEATH_TIME.get(player.getName()) != null;
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        player.setFlying(false);

        if (event.getNewGameMode() != GameMode.SPECTATOR) {
            return;
        }
        if (isDeath(player)) {
            showRespawnMessage(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR) {
            if (isDeath(player)) {
                showRespawnMessage(player);
            }
        }
    }

    public static int getDeathCount(String player) {
        return Bukkit.getScoreboardManager().getMainScoreboard().getObjective("Death").getScore(player).getScore();
    }

    public static int getRespawnCooltime(int deathCount) {
        int ret = 0;
        if (deathCount > 1) {
            ret = (deathCount - 1) * 2 - 1;
        } else if (deathCount >= 10) {
            ret = 20;
        }

        if (deathCount == 50) {
            ret = 0;
        } else if (deathCount >= 100) {
            ret = 60;
        }
        return ret;
    }
}
