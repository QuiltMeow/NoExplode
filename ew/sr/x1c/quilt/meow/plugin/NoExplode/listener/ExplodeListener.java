package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ExplodeListener implements Listener {

    private final boolean ALLOW_BOMB = true;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (ALLOW_BOMB) {
            switch (event.getEntityType()) {
                case PRIMED_TNT:
                case MINECART_TNT:
                case ENDER_CRYSTAL: {
                    // 避免當初計算錯誤造成方塊被炸掉
                    if (event.getLocation().getWorld().getName().equals("world")) {
                        List<Block> blockList = event.blockList();
                        Iterator<Block> iterator = blockList.iterator();
                        while (iterator.hasNext()) {
                            Block block = iterator.next();
                            int x = block.getLocation().getBlockX();
                            if (x >= -457 && x <= -456) {
                                iterator.remove();
                            }
                        }
                    }
                    return;
                }
            }
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDestroy(EntityChangeBlockEvent event) {
        switch (event.getEntityType()) {
            case WITHER:
            case ENDERMAN: {
                event.setCancelled(true);
                break;
            }
        }
    }

    // 別亂開老娘的箱子 OAO !
    @EventHandler
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getUniqueId().toString().equalsIgnoreCase("a85f56d3-c879-4e76-9634-70ad867a9b94")) {
            Block block = event.getClickedBlock();
            if (block != null) {
                Location location = block.getLocation();
                if (location.equals(new Location(Bukkit.getWorld("world"), -424, 63, 167))) {
                    player.getWorld().createExplosion(location, 1, false, false);
                    player.damage(999);
                    event.setCancelled(true);
                }
            }
        }
    }
}
