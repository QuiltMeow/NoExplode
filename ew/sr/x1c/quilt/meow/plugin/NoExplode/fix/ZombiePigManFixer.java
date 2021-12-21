package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

// 殭屍豬人仇恨
public class ZombiePigManFixer implements CommandExecutor {

    public static void fixZombiePigMan() {
        // getEntitiesByClass 壞掉了
        List<Entity> entityList = Bukkit.getWorld("world_nether").getEntities();
        entityList.forEach(entity -> {
            if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN) { // 1.16
                ((PigZombie) entity).setAngry(false);
            }
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("quilt.pig.zombie.fix")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        switch (args.length) {
            case 0: {
                fixZombiePigMan();
                Bukkit.broadcastMessage(ChatColor.GREEN + "已修正殭屍豬人仇恨");
                break;
            }
            default: {
                if (args[0].equalsIgnoreCase("enable")) {
                    ZombiePigManListener.setEnable(true);
                    sender.sendMessage(ChatColor.GREEN + "已開啟殭屍豬人自動仇恨消除");
                } else if (args[0].equalsIgnoreCase("disable")) {
                    ZombiePigManListener.setEnable(false);
                    sender.sendMessage(ChatColor.YELLOW + "已關閉殭屍豬人自動仇恨消除");
                } else {
                    sender.sendMessage(ChatColor.RED + "指令格式錯誤");
                }
                break;
            }
        }
        return true;
    }
}
