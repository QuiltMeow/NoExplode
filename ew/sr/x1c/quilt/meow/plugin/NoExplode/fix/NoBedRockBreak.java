package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

// 已測試 未套用 (這會讓許多好玩特性失效)
public class NoBedRockBreak implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block.getType() == Material.BEDROCK && !player.hasPermission("quilt.break.bed.rock")) {
            event.setCancelled(true);
        }
    }
}
