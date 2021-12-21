package ew.sr.x1c.quilt.meow.plugin.NoExplode.sky;

public enum SkyPacketType {

    FADE_VALUE(7),
    FADE_TIME(8);

    private final int value;

    SkyPacketType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
