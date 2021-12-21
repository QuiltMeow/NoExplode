package ew.sr.x1c.quilt.meow.plugin.NoExplode.fix;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

// 一個相對簡單粗暴的 TPS 過低解決方案
public class NoAICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("quilt.no.ai")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "指令格式錯誤");
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "villager": {
                if (args.length >= 2 && args[1].equalsIgnoreCase("all")) {
                    List<Entity> entityList = Bukkit.getWorld("world").getEntities();
                    boolean resume = args.length >= 3 && args[2].equalsIgnoreCase("resume");
                    for (Entity entity : entityList) {
                        if (entity.getType() == EntityType.VILLAGER && entity instanceof LivingEntity) {
                            ((LivingEntity) entity).setAI(resume);
                        }
                    }
                    sender.sendMessage(ChatColor.GREEN + "已" + (resume ? "復原" : "移除") + "所有村民 AI");
                } else {
                    MobFreezer.ENABLE_VILLAGER = !MobFreezer.ENABLE_VILLAGER;
                    sender.sendMessage(ChatColor.GREEN + "已" + (MobFreezer.ENABLE_VILLAGER ? "開啟" : "關閉") + "移除村民 AI");
                }
                return true;
            }
            case "freeze": {
                List<Entity> entityList = Bukkit.getWorld("world").getEntities();
                boolean resume = args.length >= 2 && args[1].equalsIgnoreCase("resume");
                for (Entity entity : entityList) {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).setAI(resume);
                    }
                }
                sender.sendMessage(ChatColor.GREEN + "已" + (resume ? "復原" : "移除") + "所有實體 AI");
                return true;
            }
            default: {
                sender.sendMessage(ChatColor.RED + "指令無效");
                return false;
            }
        }
    }
}
