package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ConcreteListener implements Listener {

    public static List<Block> getNearByBlock(Location location) {
        List<Block> ret = new ArrayList<>();
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        ret.add(world.getBlockAt(location));
        ret.add(world.getBlockAt(x + 1, y, z));
        ret.add(world.getBlockAt(x - 1, y, z));
        ret.add(world.getBlockAt(x, y + 1, z));
        ret.add(world.getBlockAt(x, y - 1, z));
        ret.add(world.getBlockAt(x, y, z + 1));
        ret.add(world.getBlockAt(x, y, z - 1));
        return ret;
    }

    private static void changeConcrete(List<Block> blockList) {
        blockList.forEach(block -> {
            changeConcrete(block);
        });
    }

    private static void changeConcrete(Block block) {
        String blockTypeName = block.getType().name();
        if (blockTypeName.contains("_CONCRETE_POWDER")) {
            String color = blockTypeName.split("_")[0];
            Material changeType = Material.getMaterial(color + "_CONCRETE");
            if (changeType != null) {
                block.setType(changeType);
            }
        }
    }

    public static boolean containBlockType(List<Block> blockList, Material type) {
        for (Block block : blockList) {
            if (block.getType() == type) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.LAVA) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    changeConcrete(getNearByBlock(event.getToBlock().getLocation()));
                }
            }.runTask(Main.getPlugin());
        }
    }

    @EventHandler
    public void onBlockFallDown(EntityChangeBlockEvent event) {
        if (event.getEntity().getType() == EntityType.FALLING_BLOCK) {
            Block solidBlock = event.getBlock();
            if (containBlockType(getNearByBlock(solidBlock.getLocation()), Material.LAVA)) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        changeConcrete(solidBlock);
                    }
                }.runTask(Main.getPlugin());
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (containBlockType(getNearByBlock(block.getLocation()), Material.LAVA)) {
            changeConcrete(block);
        }
    }
}
