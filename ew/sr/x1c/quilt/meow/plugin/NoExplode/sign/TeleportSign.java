package ew.sr.x1c.quilt.meow.plugin.NoExplode.sign;

import org.bukkit.Location;

public class TeleportSign {

    private final String name;
    private final Location source, target;

    public TeleportSign(String name, Location source, Location target) {
        this.name = name;
        this.source = source;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public Location getSource() {
        return source;
    }

    public Location getTarget() {
        return target;
    }
}
