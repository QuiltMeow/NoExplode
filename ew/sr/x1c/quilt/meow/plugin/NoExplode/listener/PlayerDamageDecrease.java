package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import java.util.HashMap;
import java.util.Map;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamageDecrease implements Listener {

    public static final int MAX_ONE_TIME_DAMAGE = 15;
    public static final int MAX_FIRE_TICK = 15 * 20;

    private static final Map<String, Long> LAST_HIT_TIME = new HashMap<>();

    public static Map<String, Long> getLastHit() {
        return LAST_HIT_TIME;
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            LAST_HIT_TIME.put(event.getEntity().getName(), System.currentTimeMillis());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }
        DamageCause cause = event.getCause();
        switch (cause) {
            case ENTITY_EXPLOSION: {
                if (event.getDamage() > MAX_ONE_TIME_DAMAGE) {
                    event.setDamage(MAX_ONE_TIME_DAMAGE);
                }
                break;
            }
            case FALL: {
                if (Randomizer.isSuccess(10)) {
                    ((Player) entity).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§B觸發跌落免疫效果"));
                    event.setCancelled(true);
                } else if (event.getDamage() > MAX_ONE_TIME_DAMAGE) {
                    Long lastHit = LAST_HIT_TIME.get(entity.getName());
                    if (lastHit == null || lastHit + 5000 <= System.currentTimeMillis()) {
                        event.setDamage(MAX_ONE_TIME_DAMAGE);
                    }
                }
                break;
            }
            case FIRE_TICK: {
                if (entity.getFireTicks() > MAX_FIRE_TICK) {
                    entity.setFireTicks(MAX_FIRE_TICK);
                }
                break;
            }
        }
    }
}
