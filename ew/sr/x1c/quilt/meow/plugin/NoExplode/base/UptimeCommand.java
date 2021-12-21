package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import java.lang.management.ManagementFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UptimeCommand implements CommandExecutor {

    public static String getDifference(long startDate, long endDate) {
        long different = endDate - startDate;

        long secondMilli = 1000;
        long minuteMilli = secondMilli * 60;
        long hourMilli = minuteMilli * 60;
        long dayMilli = hourMilli * 24;

        long elapseDay = different / dayMilli;
        different = different % dayMilli;

        long elapseHour = different / hourMilli;
        different = different % hourMilli;

        long elapseMinute = different / minuteMilli;
        different = different % minuteMilli;

        long elapseSecond = different / secondMilli;
        return new StringBuilder().append(elapseDay).append(" 天 ").append(elapseHour).append(" 小時 ").append(elapseMinute).append(" 分 ").append(elapseSecond).append(" 秒").toString();
    }

    public static String getUpTime() {
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        long current = System.currentTimeMillis();
        return getDifference(startTime, current);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("伺服器運行時間 : " + getUpTime());
        return true;
    }
}
