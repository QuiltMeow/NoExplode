package ew.sr.x1c.quilt.meow.plugin.NoExplode.api.console.hook;

import java.util.Date;
import org.apache.logging.log4j.Level;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ConsoleLogEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String message;
    private final Date date;
    private final Level level;

    public ConsoleLogEvent(Date date, Level level, String message) {
        this.date = date;
        this.level = level;
        this.message = message;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public String getMessage() {
        return message;
    }

    public Date getLogDate() {
        return date;
    }

    public Level getLogLevel() {
        return level;
    }
}
