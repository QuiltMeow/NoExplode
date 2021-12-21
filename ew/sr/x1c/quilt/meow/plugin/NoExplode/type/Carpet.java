package ew.sr.x1c.quilt.meow.plugin.NoExplode.type;

import org.bukkit.Material;

public enum Carpet {
    BLACK_CARPET,
    BLUE_CARPET,
    BROWN_CARPET,
    CYAN_CARPET,
    GRAY_CARPET,
    GREEN_CARPET,
    LIGHT_BLUE_CARPET,
    LIGHT_GRAY_CARPET,
    LIME_CARPET,
    MAGENTA_CARPET,
    ORANGE_CARPET,
    PINK_CARPET,
    PURPLE_CARPET,
    RED_CARPET,
    WHITE_CARPET,
    YELLOW_CARPET;

    public Material getMaterial() {
        return Material.getMaterial(name());
    }
}
