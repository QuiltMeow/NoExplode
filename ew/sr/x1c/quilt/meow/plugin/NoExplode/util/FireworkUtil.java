package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtil {

    private static final FireworkEffect.Type[] FIREWORK_TYPE = FireworkEffect.Type.values();
    private static final Color[] COLOR_TYPE = new Color[]{Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW};

    public static void addRandomFireworkEffect(Firework firework) {
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect.Type type = getRandomFireworkType();
        Color color = getFullRandomColor();
        Color fadeColor = getFullRandomColor();

        FireworkEffect effect = FireworkEffect.builder().flicker(Randomizer.nextBoolean()).withColor(color).withFade(fadeColor).with(type).trail(Randomizer.nextBoolean()).build();
        meta.addEffect(effect);

        meta.setPower(1);
        firework.setFireworkMeta(meta);
    }

    public static FireworkEffect.Type getRandomFireworkType() {
        return FIREWORK_TYPE[Randomizer.nextInt(FIREWORK_TYPE.length)];
    }

    public static Color getRandomColor() {
        return COLOR_TYPE[Randomizer.nextInt(COLOR_TYPE.length)];
    }

    public static Color getFullRandomColor() {
        return Color.fromRGB(Randomizer.nextInt(256), Randomizer.nextInt(256), Randomizer.nextInt(256));
    }
}
