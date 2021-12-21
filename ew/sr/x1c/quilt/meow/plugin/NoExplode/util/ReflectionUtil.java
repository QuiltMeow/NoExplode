package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

// 用於操作底層方法反射類別
public final class ReflectionUtil {

    private static final Map<String, Class<?>> NMS_CLASS = new HashMap<>();
    private static final Map<String, Class<?>> OCB_CLASS = new HashMap<>();

    private static final Map<Class<?>, Map<String, Method>> CACHE_METHOD = new HashMap<>();

    private static String version;
    private static int major;
    private static int minor;

    public static String getVersion() {
        if (version == null) {
            String declaration = Bukkit.getServer().getClass().getPackage().getName();
            version = declaration.substring(declaration.lastIndexOf('.') + 1) + ".";
            String[] split = version.substring(1).split("_");
            major = Integer.parseInt(split[0]);
            minor = Integer.parseInt(split[1]);
        }
        return version;
    }

    public static int getMajor() {
        if (version == null) {
            getVersion();
        }
        return major;
    }

    public static int getMinor() {
        if (version == null) {
            getVersion();
        }
        return minor;
    }

    public static Class<?> getNMSClass(String localPackage) {
        if (NMS_CLASS.containsKey(localPackage)) {
            return NMS_CLASS.get(localPackage);
        }

        String declaration = "net.minecraft.server." + getVersion() + localPackage;
        Class<?> clazz;
        try {
            clazz = Class.forName(declaration);
        } catch (Exception ex) {
            ex.printStackTrace();
            return NMS_CLASS.put(localPackage, null);
        }
        NMS_CLASS.put(localPackage, clazz);
        return clazz;
    }

    public static Class<?> getOCBClass(String localPackage) {
        if (OCB_CLASS.containsKey(localPackage)) {
            return OCB_CLASS.get(localPackage);
        }

        String declaration = "org.bukkit.craftbukkit." + getVersion() + localPackage;
        Class<?> clazz;
        try {
            clazz = Class.forName(declaration);
        } catch (Exception ex) {
            ex.printStackTrace();
            return OCB_CLASS.put(localPackage, null);
        }
        OCB_CLASS.put(localPackage, clazz);
        return clazz;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameter) {
        if (!CACHE_METHOD.containsKey(clazz)) {
            CACHE_METHOD.put(clazz, new HashMap<>());
        }

        Map<String, Method> classMethod = CACHE_METHOD.get(clazz);
        if (classMethod.containsKey(methodName)) {
            return classMethod.get(methodName);
        }

        try {
            Method method = clazz.getMethod(methodName, parameter);
            classMethod.put(methodName, method);
            CACHE_METHOD.put(clazz, classMethod);
            return method;
        } catch (Exception ex) {
            ex.printStackTrace();
            classMethod.put(methodName, null);
            CACHE_METHOD.put(clazz, classMethod);
            return null;
        }
    }

    public static Object getNMSPlayer(Player player) {
        try {
            return player.getClass().getMethod("getHandle").invoke(player);
        } catch (Exception ex) {
            return null;
        }
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = getNMSPlayer(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
