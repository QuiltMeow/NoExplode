package ew.sr.x1c.quilt.meow.plugin.NoExplode.sky;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ReflectionUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

// 已測試 未使用 : 非 0 數值將造成下雨 (即變更天空顏色)
public class SkyPacket {

    public static boolean changeSky(Player player, float number) {
        return changeSky(player, SkyPacketType.FADE_VALUE, number);
    }

    public static boolean changeCombineSky(Player player, float value, float time) {
        return changeSky(player, SkyPacketType.FADE_VALUE, value) && changeSky(player, SkyPacketType.FADE_TIME, time);
    }

    public static boolean changeSky(Player player, SkyPacketType packet, float number) {
        return sendPacket(player, packet.getValue(), number);
    }

    public static Object getConnection(Player player) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        Class<?> ocbPlayer = ReflectionUtil.getOCBClass("entity.CraftPlayer");
        Method getHandleMethod = ReflectionUtil.getMethod(ocbPlayer, "getHandle");
        Object nmsPlayer = getHandleMethod.invoke(player);
        Field connectionField = nmsPlayer.getClass().getField("playerConnection");
        return connectionField.get(nmsPlayer);
    }

    public static boolean sendPacket(Player player, int packetNumber, float number) {
        try {
            Class<?> packetClass = ReflectionUtil.getNMSClass("PacketPlayOutGameStateChange");
            Constructor<?> packetConstructor = packetClass.getConstructor(int.class, float.class);
            Object packet = packetConstructor.newInstance(packetNumber, number);
            Method sendPacketMethod = ReflectionUtil.getNMSClass("PlayerConnection").getMethod("sendPacket", ReflectionUtil.getNMSClass("Packet"));
            sendPacketMethod.invoke(getConnection(player), packet);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
