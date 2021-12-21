package ew.sr.x1c.quilt.meow.plugin.NoExplode.api;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

// API 中轉用
public class RedstoneEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String signal;

    private final Location location;

    private boolean cancel;

    public RedstoneEvent(String signal, Location location) {
        this.signal = signal;
        this.location = location;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean set) {
        cancel = set;
    }

    public String getSignal() {
        return signal;
    }

    public Location getLocation() {
        return location;
    }
}
