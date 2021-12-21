package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class LineUtil {

    public static List<Location> getLinePoint(Location source, Location target, double space) {
        if (source.getWorld() != target.getWorld()) {
            throw new RuntimeException("兩點必須位於同一世界");
        }

        List<Location> ret = new ArrayList<>();
        double distance = source.distance(target);
        Vector sourceVector = source.toVector();
        Vector targetVector = target.toVector();
        Vector direction = targetVector.subtract(sourceVector).normalize().multiply(space);
        for (double length = 0; length <= distance; length += space) {
            ret.add(new Location(source.getWorld(), sourceVector.getX(), sourceVector.getY(), sourceVector.getZ()));
            sourceVector.add(direction);
        }
        return ret;
    }

    public static void spawnEffect(Particle effect, Location source, Location target, int space, int amount) {
        List<Location> point = getLinePoint(source, target, space);
        point.forEach(location -> {
            source.getWorld().spawnParticle(effect, location, amount);
        });
    }
}
