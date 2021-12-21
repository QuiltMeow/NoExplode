package ew.sr.x1c.quilt.meow.plugin.NoExplode.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import net.minecraft.server.v1_16_R3.TileEntityBeehive;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class BeehiveListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Material type = block.getType();
            if (type == Material.BEE_NEST || type == Material.BEEHIVE) {
                EquipmentSlot hand = event.getHand();
                if (hand == EquipmentSlot.HAND && event.hasItem()) {
                    return;
                } else if (hand == EquipmentSlot.OFF_HAND) {
                    return;
                }
                CraftWorld craftWorld = (CraftWorld) block.getWorld();
                TileEntityBeehive beehive = (TileEntityBeehive) craftWorld.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
                NBTTagCompound tileNBT = new NBTTagCompound();
                beehive.save(tileNBT);
                NBTTagList bee = tileNBT.getList("Bees", 10);
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§B蜂巢內蜜蜂數量 : " + bee.size()));
            }
        }
    }
}
