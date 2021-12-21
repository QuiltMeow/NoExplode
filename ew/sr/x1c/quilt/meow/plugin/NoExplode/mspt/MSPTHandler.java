package ew.sr.x1c.quilt.meow.plugin.NoExplode.mspt;

import java.text.DecimalFormat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.AsyncScheduleUtil;
import java.util.ArrayList;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MSPTHandler implements Listener {

    public static enum MSPTQueryType {
        SELF,
        SPARK
    }

    private static final MSPTQueryType QUERY = MSPTQueryType.SPARK;

    private long count;
    private double[] current;

    private List<Double> oneSecond;
    private List<Double> tenSecond;
    private List<Double> oneMinute;

    public MSPTHandler() {
        init();
    }

    private void init() {
        if (QUERY == MSPTQueryType.SELF) {
            Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
            current = new double[]{0, 0, 0};

            oneSecond = new ArrayList<>();
            tenSecond = new ArrayList<>();
            oneMinute = new ArrayList<>();
        }
    }

    // 這還是會有極小的性能開銷
    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerTick(ServerTickEndEvent event) {
        double duration = event.getTickDuration();
        AsyncScheduleUtil.runTaskAsynchronouslySequence(() -> {
            ++count;

            oneSecond.add(duration);
            tenSecond.add(duration);
            oneMinute.add(duration);

            if (count % 20 == 0) {
                current[0] = oneSecond.stream().mapToDouble(value -> value).average().orElse(0);
                oneSecond.clear();
            }

            if (count % 200 == 0) {
                current[1] = tenSecond.stream().mapToDouble(value -> value).average().orElse(0);
                tenSecond.clear();
            }

            if (count % 1200 == 0) {
                current[2] = oneMinute.stream().mapToDouble(value -> value).average().orElse(0);
                oneMinute.clear();
            }
        }, "mspt_task");
    }

    public static ChatColor getMSPTColor(double mspt) {
        if (mspt <= 55) {
            return ChatColor.GREEN;
        } else if (mspt > 55 && mspt <= 60) {
            return ChatColor.YELLOW;
        }
        return ChatColor.RED;
    }

    public void requestMSPT(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "MSPT 取樣資料 (1 秒, 10 秒, 1 分鐘) :");
        sender.sendMessage(getMSPTPlaceHolder());
    }

    private static double getAverageSparkMSPT(String data) {
        return Double.parseDouble(ChatColor.stripColor(data.split("/")[0]).trim());
    }

    public String getMSPTPlaceHolder() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        switch (QUERY) {
            case SELF: {
                return getMSPTColor(current[0]) + decimalFormat.format(current[0]) + ChatColor.WHITE + ", " + getMSPTColor(current[1]) + decimalFormat.format(current[1]) + ChatColor.WHITE + ", " + getMSPTColor(current[2]) + decimalFormat.format(current[2]);
            }
            case SPARK: {
                try {
                    double sparkFiveSecond = getAverageSparkMSPT(PlaceholderAPI.setPlaceholders(null, "%spark_tickduration%"));
                    double sparkTenSecond = getAverageSparkMSPT(PlaceholderAPI.setPlaceholders(null, "%spark_tickduration_10s%"));
                    double sparkOneMinute = getAverageSparkMSPT(PlaceholderAPI.setPlaceholders(null, "%spark_tickduration_1m%"));
                    return getMSPTColor(sparkFiveSecond) + decimalFormat.format(sparkFiveSecond) + ChatColor.WHITE + ", " + getMSPTColor(sparkTenSecond) + decimalFormat.format(sparkTenSecond) + ChatColor.WHITE + ", " + getMSPTColor(sparkOneMinute) + decimalFormat.format(sparkOneMinute);
                } catch (Exception ex) {
                    return ChatColor.RED + "無法取得";
                }
            }
        }
        throw new RuntimeException("無效的查詢方式");
    }

    public double[] getRawMSPT() {
        return current;
    }

    public void reset() {
        count = 0;
        current = new double[]{0, 0, 0};

        oneSecond.clear();
        tenSecond.clear();
        oneMinute.clear();
    }
}
