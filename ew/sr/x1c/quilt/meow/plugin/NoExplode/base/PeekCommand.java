package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PeekCommand implements CommandExecutor, Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onShulkerPeek(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (view.getTitle().equalsIgnoreCase("道具箱預覽")) {
            event.setCancelled(true);
        }
    }

    public static Inventory createPreviewChest() {
        return Bukkit.createInventory(null, 27, "道具箱預覽");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        Material type = item.getType();
        if (type.name().contains("SHULKER_BOX")) {
            BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
            ShulkerBox shulker = (ShulkerBox) meta.getBlockState();
            Inventory inventory = createPreviewChest();
            inventory.setContents(shulker.getInventory().getContents());
            player.openInventory(inventory);
        } else if (type == Material.ENDER_CHEST) {
            Inventory inventory = createPreviewChest();
            inventory.setContents(player.getEnderChest().getContents());
            player.openInventory(inventory);
        } else {
            sender.sendMessage(ChatColor.RED + "目標道具無法預覽");
        }
        return true;
    }
}
