package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class EntityTickUtil {

    public static void disableEntityTick(World world, Entity entity) {
        WorldServer ws = ((CraftWorld) world).getHandle();
        ws.entitiesById.remove(entity.getEntityId());
    }

    public static void enableEntityTick(World world, Entity entity) {
        WorldServer ws = ((CraftWorld) world).getHandle();
        ws.entitiesById.put(entity.getEntityId(), ((CraftEntity) entity).getHandle());
    }
}
