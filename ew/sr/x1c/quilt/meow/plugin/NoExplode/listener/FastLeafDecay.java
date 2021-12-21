package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;

public class FastLeafDecay implements Listener {

    private static final long BREAK_DELAY = 5, DECAY_DELAY = 2;

    private static final byte PLAYER_PLACE_BIT = 4;
    private static final BlockFace[] NEIGHBOR = {
        BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.DOWN
    };

    private final Set<Block> scheduleBlock = new HashSet<>();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        onBlockRemove(event.getBlock(), BREAK_DELAY);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onLeaveDecay(LeavesDecayEvent event) {
        onBlockRemove(event.getBlock(), DECAY_DELAY);
    }

    private static boolean isWoodLog(Material material) {
        switch (material) {
            case ACACIA_LOG:
            case BIRCH_LOG:
            case DARK_OAK_LOG:
            case JUNGLE_LOG:
            case OAK_LOG:
            case SPRUCE_LOG: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    private static boolean isWoodLeaf(Material material) {
        switch (material) {
            case ACACIA_LEAVES:
            case BIRCH_LEAVES:
            case DARK_OAK_LEAVES:
            case JUNGLE_LEAVES:
            case OAK_LEAVES:
            case SPRUCE_LEAVES: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    private static boolean isPersistent(Block block) {
        return (block.getData() & PLAYER_PLACE_BIT) != 0;
    }

    private static int getDistance(Block block) {
        List<Block> pending = new ArrayList<>();
        pending.add(block);

        Set<Block> done = new HashSet<>();
        Map<Block, Integer> distanceMap = new HashMap<>();
        distanceMap.put(block, 0);
        while (!pending.isEmpty()) {
            Block current = pending.remove(0);
            done.add(current);
            int distance = distanceMap.get(current);
            if (distance >= 5) {
                return distance;
            }
            for (BlockFace face : new BlockFace[]{BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
                Block neighbor = current.getRelative(face);
                if (done.contains(neighbor)) {
                    continue;
                }
                if (isWoodLog(neighbor.getType())) {
                    return distance + 1;
                } else if (isWoodLeaf(neighbor.getType())) {
                    pending.add(neighbor);
                    distanceMap.put(neighbor, distance + 1);
                }
            }
        }
        return 5;
    }

    private void onBlockRemove(Block oldBlock, long delay) {
        if (!isWoodLog(oldBlock.getType()) && !isWoodLeaf(oldBlock.getType())) {
            return;
        }
        for (BlockFace neighborFace : NEIGHBOR) {
            Block block = oldBlock.getRelative(neighborFace);
            if (!isWoodLeaf(block.getType())) {
                continue;
            }
            if (isPersistent(block)) {
                continue;
            }
            if (scheduleBlock.contains(block)) {
                continue;
            }
            scheduleBlock.add(block);
            Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), () -> decay(block), delay);
        }
    }

    private void decay(Block block) {
        if (!scheduleBlock.remove(block)) {
            return;
        }
        if (!isWoodLeaf(block.getType())) {
            return;
        }
        if (isPersistent(block)) {
            return;
        }
        if (getDistance(block) <= 4) {
            return;
        }
        LeavesDecayEvent event = new LeavesDecayEvent(block);
        Main.getPlugin().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        block.breakNaturally();
    }
}
