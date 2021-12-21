package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import java.text.NumberFormat;
import org.bukkit.Bukkit;

public class TickUtil {

    public static double tickToSecond(int tick) {
        return (double) tick / 20;
    }

    public static int secondToTick(int second) {
        return second * 20;
    }

    public static int minuteToTick(int minute) {
        return secondToTick(minute * 60);
    }

    public static int hourToTick(int hour) {
        return secondToTick(hour * 60 * 60);
    }

    public static double getTPS() {
        double ret = Bukkit.getServer().getTPS()[0];
        if (ret > 20) {
            ret = 20;
        }
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(3);
        return Double.parseDouble(format.format(ret));
    }
}
