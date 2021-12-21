package ew.sr.x1c.quilt.meow.plugin.NoExplode.api.console.hook;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.Date;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class LogAppender extends AbstractAppender {

    public LogAppender() {
        super("HookLogAppender", null, null);
        start();
    }

    @Override
    public void append(LogEvent event) {
        LogEvent log = event.toImmutable();

        Date date = new Date(event.getTimeMillis());
        Level level = event.getLevel();
        String message = log.getMessage().getFormattedMessage();

        try {
            new BukkitRunnable() {

                @Override
                public void run() {
                    Bukkit.getPluginManager().callEvent(new ConsoleLogEvent(date, level, message));
                }
            }.runTask(Main.getPlugin());
        } catch (Exception ex) {
        }
    }
}
