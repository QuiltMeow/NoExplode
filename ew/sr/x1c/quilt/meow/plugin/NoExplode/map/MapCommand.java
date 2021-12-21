package ew.sr.x1c.quilt.meow.plugin.NoExplode.map;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MapCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        Player player = (Player) sender;
        ItemStack item = new ItemStack(Material.FILLED_MAP, 1);
        switch (args.length) {
            case 0:
            case 1: {
                int data = 0;
                try {
                    data = args.length == 0 ? 0 : Integer.parseInt(args[0]);
                } catch (NumberFormatException ex) {
                }
                ImageMap render = new ImageMap();
                render.renderResourceImageAsync(player, data, item);
                break;
            }
            default: {
                if (args[0].equals("download")) {
                    String url = ArgumentUtil.joinStringFrom(args, 1);
                    ImageMap render = new ImageMap();
                    render.renderRemoteImageAsync(player, url, item);
                } else {
                    sender.sendMessage(ChatColor.RED + "指令格式錯誤");
                }
                break;
            }
        }
        return true;
    }

    // Someone wanted it o_o
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<String>() {
                {
                    add("download");
                }
            };
        }
        return null;
    }
}
