package ew.sr.x1c.quilt.meow.plugin.NoExplode.map;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

public class MapListener implements Listener {

    @EventHandler
    public void onMapInitialize(MapInitializeEvent event) {
        MapView view = event.getMap();
        queryMapAsync(view.getId(), view);
    }

    // 啟用時載入
    public static void loadMap() {
        Connection con = DatabaseConnection.getConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM map_information")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int mapId = rs.getInt("map_id");
                    int type = rs.getInt("type");
                    String location = rs.getString("location");

                    MapView view = Bukkit.getServer().getMap(mapId);
                    if (view != null) {
                        ImageMap render = new ImageMap();
                        switch (type) {
                            case 0: {
                                try {
                                    render.loadResourceImage(ImageMap.getImagePath(Integer.parseInt(location)));
                                    view.getRenderers().clear();
                                    view.addRenderer(render);
                                } catch (NumberFormatException ex) {
                                    Main.getPlugin().getLogger().log(Level.WARNING, "發生資料格式錯誤例外狀況", ex);
                                }
                                break;
                            }
                            case 1: {
                                render.loadImage(location);
                                view.getRenderers().clear();
                                view.addRenderer(render);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Main.getPlugin().getLogger().log(Level.WARNING, "發生例外狀況", ex);
        }
    }

    public static void saveMapAsync(int mapId, int type, String location) {
        new Thread(() -> {
            Connection con = DatabaseConnection.getConnection();
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO map_information (map_id, type, location) VALUES (?, ?, ?)")) {
                ps.setInt(1, mapId);
                ps.setInt(2, type);
                ps.setString(3, location);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Main.getPlugin().getLogger().log(Level.WARNING, "發生資料庫例外狀況", ex);
            }
        }).start();
    }

    public static void queryMapAsync(int mapId, MapView view) {
        new Thread(() -> {
            Connection con = DatabaseConnection.getConnection();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM map_information WHERE map_id = ?")) {
                ps.setInt(1, mapId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int type = rs.getInt("type");
                        String location = rs.getString("location");
                        ImageMap render = new ImageMap();
                        switch (type) {
                            case 0: {
                                try {
                                    render.loadResourceImage(ImageMap.getImagePath(Integer.parseInt(location)));
                                    Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                                        view.getRenderers().clear();
                                        view.addRenderer(render);
                                    });
                                } catch (NumberFormatException ex) {
                                    Main.getPlugin().getLogger().log(Level.WARNING, "發生資料格式錯誤例外狀況", ex);
                                }
                                break;
                            }
                            case 1: {
                                render.loadImage(location);
                                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                                    view.getRenderers().clear();
                                    view.addRenderer(render);
                                });
                                break;
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Main.getPlugin().getLogger().log(Level.WARNING, "發生例外狀況", ex);
            }
        }).start();
    }
}
