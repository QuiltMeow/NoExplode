package ew.sr.x1c.quilt.meow.plugin.NoExplode.log;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerEventLogger {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy - MM - dd HH : mm : ss");
    private static final Object LOCK = new Object();

    private static FileWriter writer;
    private static String lastIP;

    static {
        try {
            writer = new FileWriter("./logs/event.log");
            initIPData();
        } catch (IOException ex) {
            Main.getPlugin().getLogger().log(Level.WARNING, "服務端事件記錄檔案初始化失敗", ex);
        }
    }

    private static void initIPData() throws IOException {
        lastIP = getExternalIP();
        writeLog("服務端啟動 IP : " + lastIP);
    }

    public static String getExternalIP() throws IOException {
        URL ipQuery = new URL("http://checkip.amazonaws.com/");
        try (InputStreamReader inputStreamReader = new InputStreamReader(ipQuery.openStream())) {
            try (BufferedReader bufferReader = new BufferedReader(inputStreamReader)) {
                return bufferReader.readLine();
            }
        }
    }

    public static void writeLog(String log) {
        new Thread(() -> {
            synchronized (LOCK) {
                try {
                    writer.write("[" + DATE_FORMAT.format(new Date()) + "] " + log + System.lineSeparator());
                    writer.flush();
                } catch (IOException ex) {
                    Main.getPlugin().getLogger().log(Level.WARNING, "輸出至紀錄檔案時發生例外狀況", ex);
                }
            }
        }).start();
    }

    public static void registerServerStatusLogger() {
        new BukkitRunnable() {

            @Override
            public void run() {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        Logger logger = Main.getPlugin().getLogger();
                        logger.log(Level.INFO, PlaceholderAPI.setPlaceholders(null, "線上人數 : %server_online% / %server_max_players%"));
                        logger.log(Level.INFO, PlaceholderAPI.setPlaceholders(null, "TPS : %spark_tps%"));
                        logger.log(Level.INFO, PlaceholderAPI.setPlaceholders(null, "CPU 使用率 系統 : %spark_cpu_system% 伺服器 : %spark_cpu_process%"));
                        logger.log(Level.INFO, PlaceholderAPI.setPlaceholders(null, "記憶體 已使用 : %server_ram_used% MB 剩餘 : %server_ram_free% MB 總計 : %server_ram_total% MB 最大 : %server_ram_max% MB"));
                        logger.log(Level.INFO, PlaceholderAPI.setPlaceholders(null, "載入區塊數 : %server_total_chunks% 生物實體數 : %server_total_living_entities% 總實體數 : %server_total_entities%"));
                    }
                }.runTask(Main.getPlugin());
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 3 * 60 * 20, 3 * 60 * 20);
    }

    public static void registerIPChangeLog() {
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    String ip = getExternalIP();
                    if (!ip.equals(lastIP)) {
                        lastIP = ip;
                        writeLog("服務端 IP 位址已變更 目前 IP : " + ip);
                    }
                } catch (IOException ex) {
                    Main.getPlugin().getLogger().log(Level.WARNING, "取得外部 IP 時發生例外狀況", ex);
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 3 * 60 * 20, 3 * 60 * 20);
    }

    public static void registerShutdownLog() {
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                writeLog("服務端已關閉");
            }
        });
    }
}
