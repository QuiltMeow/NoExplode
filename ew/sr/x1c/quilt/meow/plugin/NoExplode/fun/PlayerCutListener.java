package ew.sr.x1c.quilt.meow.plugin.NoExplode.fun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

// 剪貓奴纓的毛 !
public class PlayerCutListener implements Listener {

    private static long lastCut;

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        long current = System.currentTimeMillis();
        if (lastCut + 5 * 60 * 1000 <= current) {
            Entity click = event.getRightClicked();
            if (click.getType() == EntityType.PLAYER) {
                ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
                if (item.getType() == Material.SHEARS) {
                    Player target = (Player) click;
                    if (target.getUniqueId().toString().equalsIgnoreCase("a85f56d3-c879-4e76-9634-70ad867a9b94")) {
                        World world = target.getWorld();
                        Location location = target.getLocation();
                        world.playSound(location, Sound.ENTITY_SHEEP_SHEAR, 1, 1);

                        ItemStack drop = new ItemStack(Material.WHITE_WOOL);
                        ItemMeta meta = drop.getItemMeta();
                        meta.setDisplayName("§E貓奴纓的狐狸毛");

                        List<String> lore = new ArrayList<>(Arrays.asList(new String[]{"§E從貓奴纓身上剪下來的狐狸毛"}));
                        meta.setLore(lore);

                        drop.setItemMeta(meta);
                        world.dropItemNaturally(location, drop);

                        Damageable shear = (Damageable) item.getItemMeta();
                        shear.setDamage(shear.getDamage() + 1);
                        item.setItemMeta((ItemMeta) shear);
                        lastCut = current;
                    }
                }
            }
        }
    }
}
