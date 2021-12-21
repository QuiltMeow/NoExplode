package ew.sr.x1c.quilt.meow.plugin.NoExplode.sign;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Block;

public class TeleportSignListener implements Listener {

    private final List<TeleportSign> teleportSignList;

    public TeleportSignListener() {
        teleportSignList = new ArrayList<>();

        // 這裡包含自訂世界 請確保該世界存在後再進行載入
        initSign();
    }

    private void initSign() {
        teleportSignList.add(new TeleportSign("new_end", new Location(Bukkit.getWorld("world"), -967, 46, 1142), Bukkit.getWorld("new_end").getSpawnLocation()));
        teleportSignList.add(new TeleportSign("world_to_quilt", new Location(Bukkit.getWorld("world"), -432, 64, 172), Bukkit.getWorld("quilt").getSpawnLocation()));
        teleportSignList.add(new TeleportSign("quilt_to_world", new Location(Bukkit.getWorld("quilt"), -116, 64, -168), Bukkit.getWorld("world").getSpawnLocation()));
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Material type = block.getType();
            switch (type) {
                case SPRUCE_WALL_SIGN:
                case SPRUCE_SIGN: {
                    Location location = block.getLocation();
                    for (TeleportSign sign : teleportSignList) {
                        if (location.equals(sign.getSource())) {
                            event.getPlayer().teleport(sign.getTarget());
                            break;
                        }
                    }
                }
            }
        }
    }
}
