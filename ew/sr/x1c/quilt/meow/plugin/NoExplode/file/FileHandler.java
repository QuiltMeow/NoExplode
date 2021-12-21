package ew.sr.x1c.quilt.meow.plugin.NoExplode.file;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.Randomizer;
import java.io.File;
import java.util.logging.Level;

public class FileHandler {

    public static final String IMAGE_DOWNLOAD_PATH = "plugins/Image";
    public static final String MUSIC_DOWNLOAD_PATH = "plugins/Music";
    public static final String SCRIPT_PATH = "plugins/Script";

    public static void init() {
        initPath(IMAGE_DOWNLOAD_PATH);
        initPath(MUSIC_DOWNLOAD_PATH);
        initPath(SCRIPT_PATH);
    }

    public static void initPath(String target) {
        File path = new File(target);
        if (!path.exists()) {
            Main.getPlugin().getLogger().log(Level.INFO, "指定位置 : {0} 不存在 正在建立 ...", target);
            try {
                path.mkdir();
                Main.getPlugin().getLogger().info("指定位置建立完成");
            } catch (SecurityException se) {
                Main.getPlugin().getLogger().warning("指定位置建立失敗 ...");
            }
        }
    }

    public static boolean checkFileExist(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }

    public static boolean isValidImageFileType(String type) {
        switch (type.toUpperCase()) {
            case "JPG":
            case "JPEG":
            case "PNG": {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidMusicFileType(String type) {
        switch (type.toUpperCase()) {
            case "MID":
            case "MIDI": {
                return true;
            }
        }
        return false;
    }

    public static String getRandomFileName(String type) {
        return Math.abs(Randomizer.nextInt()) + "-" + Math.abs(System.nanoTime()) + "." + type;
    }
}
