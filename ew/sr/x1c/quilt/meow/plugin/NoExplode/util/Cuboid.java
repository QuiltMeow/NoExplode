package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

// 長方體 用於處理兩點所圍繞空間 類似小木斧 //pos1 //pos2
public class Cuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable {

    protected final String worldName;
    protected final int x1, y1, z1;
    protected final int x2, y2, z2;

    public Cuboid(Location left, Location right) {
        if (!left.getWorld().equals(right.getWorld())) {
            throw new IllegalArgumentException("兩點必須位於同一世界");
        }
        worldName = left.getWorld().getName();
        x1 = Math.min(left.getBlockX(), right.getBlockX());
        y1 = Math.min(left.getBlockY(), right.getBlockY());

        x2 = Math.max(left.getBlockX(), right.getBlockX());
        y2 = Math.max(left.getBlockY(), right.getBlockY());

        z1 = Math.min(left.getBlockZ(), right.getBlockZ());
        z2 = Math.max(left.getBlockZ(), right.getBlockZ());
    }

    public Cuboid(Location location) {
        this(location, location);
    }

    public Cuboid(Cuboid copy) {
        this(copy.getWorld().getName(), copy.x1, copy.y1, copy.z1, copy.x2, copy.y2, copy.z2);
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        worldName = world.getName();
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);

        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);

        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    private Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);

        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);

        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    public Cuboid(Map<String, Object> map) {
        worldName = (String) map.get("worldName");
        x1 = (int) map.get("x1");
        x2 = (int) map.get("x2");

        y1 = (int) map.get("y1");
        y2 = (int) map.get("y2");

        z1 = (int) map.get("z1");
        z2 = (int) map.get("z2");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("worldName", worldName);
        map.put("x1", x1);
        map.put("y1", y1);
        map.put("z1", z1);

        map.put("x2", x2);
        map.put("y2", y2);
        map.put("z2", z2);
        return map;
    }

    public Location getLowerNorthEast() {
        return new Location(getWorld(), x1, y1, z1);
    }

    public Location getUpperSouthWest() {
        return new Location(getWorld(), x2, y2, z2);
    }

    public List<Block> getBlockList() {
        Iterator<Block> blockIterator = iterator();
        List<Block> ret = new ArrayList<>();
        while (blockIterator.hasNext()) {
            ret.add(blockIterator.next());
        }
        return ret;
    }

    public Location getCenter() {
        int upperX = x2 + 1;
        int upperY = y2 + 1;
        int upperZ = z2 + 1;
        return new Location(getWorld(), x1 + (upperX - x1) / 2D, y1 + (upperY - y1) / 2D, z1 + (upperZ - z1) / 2D);
    }

    public World getWorld() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("世界 " + worldName + " 尚未載入");
        }
        return world;
    }

    public int getSizeX() {
        return x2 - x1 + 1;
    }

    public int getSizeY() {
        return y2 - y1 + 1;
    }

    public int getSizeZ() {
        return z2 - z1 + 1;
    }

    public int getLowerX() {
        return x1;
    }

    public int getLowerY() {
        return y1;
    }

    public int getLowerZ() {
        return z1;
    }

    public int getUpperX() {
        return x2;
    }

    public int getUpperY() {
        return y2;
    }

    public int getUpperZ() {
        return z2;
    }

    public Block[] corner() {
        Block[] ret = new Block[8];
        World world = getWorld();
        ret[0] = world.getBlockAt(x1, y1, z1);
        ret[1] = world.getBlockAt(x1, y1, z2);

        ret[2] = world.getBlockAt(x1, y2, z1);
        ret[3] = world.getBlockAt(x1, y2, z2);

        ret[4] = world.getBlockAt(x2, y1, z1);
        ret[5] = world.getBlockAt(x2, y1, z2);

        ret[6] = world.getBlockAt(x2, y2, z1);
        ret[7] = world.getBlockAt(x2, y2, z2);
        return ret;
    }

    public Cuboid expand(CuboidDirection direction, int amount) {
        switch (direction) {
            case NORTH: {
                return new Cuboid(worldName, x1 - amount, y1, z1, x2, y2, z2);
            }
            case SOUTH: {
                return new Cuboid(worldName, x1, y1, z1, x2 + amount, y2, z2);
            }
            case EAST: {
                return new Cuboid(worldName, x1, y1, z1 - amount, x2, y2, z2);
            }
            case WEST: {
                return new Cuboid(worldName, x1, y1, z1, x2, y2, z2 + amount);
            }
            case DOWN: {
                return new Cuboid(worldName, x1, y1 - amount, z1, x2, y2, z2);
            }
            case UP: {
                return new Cuboid(worldName, x1, y1, z1, x2, y2 + amount, z2);
            }
            default: {
                throw new IllegalArgumentException("無效的方位 " + direction);
            }
        }
    }

    public Cuboid shift(CuboidDirection direction, int amount) {
        return expand(direction, amount).expand(direction.opposite(), -amount);
    }

    public Cuboid outset(CuboidDirection direction, int amount) {
        Cuboid ret;
        switch (direction) {
            case HORIZONTAL: {
                ret = expand(CuboidDirection.NORTH, amount).expand(CuboidDirection.SOUTH, amount).expand(CuboidDirection.EAST, amount).expand(CuboidDirection.WEST, amount);
                break;
            }
            case VERTICAL: {
                ret = expand(CuboidDirection.DOWN, amount).expand(CuboidDirection.UP, amount);
                break;
            }
            case BOTH: {
                ret = outset(CuboidDirection.HORIZONTAL, amount).outset(CuboidDirection.VERTICAL, amount);
                break;
            }
            default: {
                throw new IllegalArgumentException("無效的方位 " + direction);
            }
        }
        return ret;
    }

    public Cuboid inset(CuboidDirection direction, int amount) {
        return outset(direction, -amount);
    }

    public boolean contain(int x, int y, int z) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public boolean contain(Block block) {
        return contain(block.getLocation());
    }

    public boolean contain(Location location) {
        if (!worldName.equals(location.getWorld().getName())) {
            return false;
        }
        return contain(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    public byte getAverageLightLevel() {
        long total = 0;
        int count = 0;
        for (Block block : this) {
            if (block.isEmpty()) {
                total += block.getLightLevel();
                ++count;
            }
        }
        return count > 0 ? (byte) (total / count) : 0;
    }

    public Cuboid contract() {
        return contract(CuboidDirection.DOWN).contract(CuboidDirection.SOUTH).contract(CuboidDirection.EAST).contract(CuboidDirection.UP).contract(CuboidDirection.NORTH).contract(CuboidDirection.WEST);
    }

    public Cuboid contract(CuboidDirection direction) {
        Cuboid face = getFace(direction.opposite());
        switch (direction) {
            case DOWN: {
                while (face.containOnly(Material.AIR) && face.getLowerY() > y1) {
                    face = face.shift(CuboidDirection.DOWN, 1);
                }
                return new Cuboid(worldName, x1, y1, z1, x2, face.getUpperY(), z2);
            }
            case UP: {
                while (face.containOnly(Material.AIR) && face.getUpperY() < y2) {
                    face = face.shift(CuboidDirection.UP, 1);
                }
                return new Cuboid(worldName, x1, face.getLowerY(), z1, x2, y2, z2);
            }
            case NORTH: {
                while (face.containOnly(Material.AIR) && face.getLowerX() > x1) {
                    face = face.shift(CuboidDirection.NORTH, 1);
                }
                return new Cuboid(worldName, x1, y1, z1, face.getUpperX(), y2, z2);
            }
            case SOUTH: {
                while (face.containOnly(Material.AIR) && face.getUpperX() < x2) {
                    face = face.shift(CuboidDirection.SOUTH, 1);
                }
                return new Cuboid(worldName, face.getLowerX(), y1, z1, x2, y2, z2);
            }
            case EAST: {
                while (face.containOnly(Material.AIR) && face.getLowerZ() > z1) {
                    face = face.shift(CuboidDirection.EAST, 1);
                }
                return new Cuboid(worldName, x1, y1, z1, x2, y2, face.getUpperZ());
            }
            case WEST: {
                while (face.containOnly(Material.AIR) && face.getUpperZ() < z2) {
                    face = face.shift(CuboidDirection.WEST, 1);
                }
                return new Cuboid(worldName, x1, y1, face.getLowerZ(), x2, y2, z2);
            }
            default: {
                throw new IllegalArgumentException("無效的方位 " + direction);
            }
        }
    }

    public Cuboid getFace(CuboidDirection direction) {
        switch (direction) {
            case DOWN: {
                return new Cuboid(worldName, x1, y1, z1, x2, y1, z2);
            }
            case UP: {
                return new Cuboid(worldName, x1, y2, z1, x2, y2, z2);
            }
            case NORTH: {
                return new Cuboid(worldName, x1, y1, z1, x1, y2, z2);
            }
            case SOUTH: {
                return new Cuboid(worldName, x2, y1, z1, x2, y2, z2);
            }
            case EAST: {
                return new Cuboid(worldName, x1, y1, z1, x2, y2, z1);
            }
            case WEST: {
                return new Cuboid(worldName, x1, y1, z2, x2, y2, z2);
            }
            default: {
                throw new IllegalArgumentException("無效的方位 " + direction);
            }
        }
    }

    public boolean containOnly(Material type) {
        for (Block block : this) {
            if (block.getType() != type) {
                return false;
            }
        }
        return true;
    }

    public Cuboid getBoundCuboid(Cuboid other) {
        if (other == null) {
            return this;
        }

        int xMin = Math.min(x1, other.getLowerX());
        int yMin = Math.min(y1, other.getLowerY());
        int zMin = Math.min(z1, other.getLowerZ());

        int xMax = Math.max(x2, other.getUpperX());
        int yMax = Math.max(y2, other.getUpperY());
        int zMax = Math.max(z2, other.getUpperZ());
        return new Cuboid(worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public Block getRelativeBlock(int x, int y, int z) {
        return getWorld().getBlockAt(x1 + x, y1 + y, z1 + z);
    }

    public Block getRelativeBlock(World world, int x, int y, int z) {
        return world.getBlockAt(x1 + x, y1 + y, z1 + z);
    }

    public List<Chunk> getChunkList() {
        List<Chunk> ret = new ArrayList<>();

        World world = getWorld();
        int lowerX = x1 & ~0xF;
        int upperX = x2 & ~0xF;
        int lowerZ = z1 & ~0xF;
        int upperZ = z2 & ~0xF;
        for (int x = lowerX; x <= upperX; x += 16) {
            for (int z = lowerZ; z <= upperZ; z += 16) {
                ret.add(world.getChunkAt(x >> 4, z >> 4));
            }
        }
        return ret;
    }

    public int getChunkCount() {
        int ret = 0;

        int lowerX = x1 & ~0xF;
        int upperX = x2 & ~0xF;
        int lowerZ = z1 & ~0xF;
        int upperZ = z2 & ~0xF;
        for (int x = lowerX; x <= upperX; x += 16) {
            for (int z = lowerZ; z <= upperZ; z += 16) {
                ++ret;
            }
        }
        return ret;
    }

    @Override
    public Iterator<Block> iterator() {
        return new CuboidIterator(getWorld(), x1, y1, z1, x2, y2, z2);
    }

    @Override
    public Cuboid clone() {
        return new Cuboid(this);
    }

    @Override
    public String toString() {
        return "長方體 : " + worldName + " (" + x1 + ", " + y1 + ", " + z1 + ") => (" + x2 + ", " + y2 + ", " + z2 + ")";
    }

    public class CuboidIterator implements Iterator<Block> {

        private final World world;
        private final int baseX, baseY, baseZ;
        private int x, y, z;
        private final int sizeX, sizeY, sizeZ;

        public CuboidIterator(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.world = world;
            baseX = x1;
            baseY = y1;
            baseZ = z1;
            sizeX = Math.abs(x2 - x1) + 1;
            sizeY = Math.abs(y2 - y1) + 1;
            sizeZ = Math.abs(z2 - z1) + 1;
            x = y = z = 0;
        }

        @Override
        public boolean hasNext() {
            return x < sizeX && y < sizeY && z < sizeZ;
        }

        @Override
        public Block next() {
            Block block = world.getBlockAt(baseX + x, baseY + y, baseZ + z);
            if (++x >= sizeX) {
                x = 0;
                if (++y >= sizeY) {
                    y = 0;
                    ++z;
                }
            }
            return block;
        }

        @Override
        public void remove() {
        }
    }

    public enum CuboidDirection {

        NORTH, EAST, SOUTH, WEST, UP, DOWN, HORIZONTAL, VERTICAL, BOTH, UNKNOWN;

        public CuboidDirection opposite() {
            switch (this) {
                case NORTH: {
                    return SOUTH;
                }
                case EAST: {
                    return WEST;
                }
                case SOUTH: {
                    return NORTH;
                }
                case WEST: {
                    return EAST;
                }
                case HORIZONTAL: {
                    return VERTICAL;
                }
                case VERTICAL: {
                    return HORIZONTAL;
                }
                case UP: {
                    return DOWN;
                }
                case DOWN: {
                    return UP;
                }
                case BOTH: {
                    return BOTH;
                }
                default: {
                    return UNKNOWN;
                }
            }
        }
    }
}
