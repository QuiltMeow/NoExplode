package ew.sr.x1c.quilt.meow.plugin.NoExplode.map;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.file.FileHandler;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ImageMap extends MapRenderer {

    private final Set<UUID> playerUUID = new HashSet<>();
    private BufferedImage image;

    private static final String[] IMAGE_PATH = new String[]{
        "/ew/sr/x1c/quilt/meow/plugin/NoExplode/map/Quilt.png",
        "/ew/sr/x1c/quilt/meow/plugin/NoExplode/map/SmallQuilt.png",
        "/ew/sr/x1c/quilt/meow/plugin/NoExplode/map/Yucohanhan.png",
        // 因為很可愛所以就放進來了 w
        "/ew/sr/x1c/quilt/meow/plugin/NoExplode/map/SX.png",
        "/ew/sr/x1c/quilt/meow/plugin/NoExplode/map/Amoeba.png"
    };

    public static String getImagePath(int index) {
        if (index < 0 || index >= IMAGE_PATH.length) {
            return null;
        }
        return IMAGE_PATH[index];
    }

    public boolean loadImage(String path) {
        try {
            File imageFile = new File(path);
            image = ImageIO.read(imageFile);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean loadResourceImage(String path) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static Dimension getScaleDimension(Dimension imageSize, Dimension bound) {
        int originWidth = imageSize.width;
        int originHeight = imageSize.height;
        int boundWidth = bound.width;
        int boundHeight = bound.height;
        int newWidth = originWidth;
        int newHeight = originHeight;
        if (originWidth > boundWidth) {
            newWidth = boundWidth;
            newHeight = (newWidth * originHeight) / originWidth;
        }
        if (newHeight > boundHeight) {
            newHeight = boundHeight;
            newWidth = (newHeight * originWidth) / originHeight;
        }
        return new Dimension(newWidth, newHeight);
    }

    public static Image resizeImage(Image origin, Dimension bound) {
        Dimension imageSize = new Dimension(origin.getWidth(null), origin.getHeight(null));
        Dimension scale = getScaleDimension(imageSize, bound);
        return origin.getScaledInstance((int) scale.getWidth(), (int) scale.getHeight(), Image.SCALE_SMOOTH);
    }

    public static BufferedImage toBufferImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage ret = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = ret.createGraphics();
        graphic.drawImage(image, 0, 0, null);
        graphic.dispose();
        return ret;
    }

    public void renderResourceImageAsync(Player player, int data, ItemStack item) {
        new Thread(() -> {
            String path = null;
            try {
                path = getImagePath(data);
            } catch (NumberFormatException ex) {
            }
            if (path == null) {
                path = IMAGE_PATH[0];
            }
            loadResourceImage(path);
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                MapView view = Bukkit.createMap(player.getWorld());
                view.getRenderers().clear();
                view.addRenderer(this);

                MapMeta meta = (MapMeta) item.getItemMeta();
                int id = view.getId();
                meta.setMapId(id);
                item.setItemMeta(meta);
                player.getInventory().addItem(item);
                MapListener.saveMapAsync(id, 0, String.valueOf(data));
            });
        }).start();
    }

    public void renderRemoteImageAsync(Player player, String path, ItemStack item) {
        new Thread(() -> {
            try {
                URL url = new URL(path);
                Image origin = ImageIO.read(url);
                if (origin == null) {
                    throw new IOException("無法下載指定網址圖片");
                }
                image = toBufferImage(resizeImage(origin, new Dimension(128, 128)));
                String name = FileHandler.IMAGE_DOWNLOAD_PATH + "/" + FileHandler.getRandomFileName("png");
                File save = new File(name);
                ImageIO.write(image, "png", save);

                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                    MapView view = Bukkit.createMap(player.getWorld());
                    view.getRenderers().clear();
                    view.addRenderer(this);

                    MapMeta meta = (MapMeta) item.getItemMeta();
                    int id = view.getId();
                    meta.setMapId(id);
                    item.setItemMeta(meta);
                    player.getInventory().addItem(item);
                    MapListener.saveMapAsync(id, 1, name);
                });
            } catch (IOException ex) {
                player.sendMessage(ChatColor.RED + "圖片渲染失敗");
            }
        }).start();
    }

    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        UUID uuid = player.getUniqueId();
        if (playerUUID.contains(uuid)) {
            return;
        }
        if (image != null) {
            canvas.drawImage(0, 0, image);
            playerUUID.add(uuid);
        }
    }
}
