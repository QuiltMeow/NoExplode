package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ServerTotalTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try (FileInputStream fis = new FileInputStream("./eula.txt")) {
            try (InputStreamReader isr = new InputStreamReader(fis)) {
                try (BufferedReader br = new BufferedReader(isr)) {
                    br.readLine();
                    String time = br.readLine().substring(1);

                    DateFormat input = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                    Date inputDate = input.parse(time);

                    DateFormat output = new SimpleDateFormat("yyyy / MM / dd E a hh : mm : ss", Locale.TAIWAN);
                    String outputDate = output.format(inputDate);

                    long start = inputDate.getTime();
                    long current = System.currentTimeMillis();
                    String difference = UptimeCommand.getDifference(start, current);

                    sender.sendMessage("第一次開服時間 : " + ChatColor.GREEN + outputDate);
                    sender.sendMessage("經過時間 : " + ChatColor.GREEN + difference);
                }
            }
        } catch (Exception ex) {
            Main.getPlugin().getLogger().log(Level.SEVERE, "讀取檔案時發生例外狀況", ex);
            sender.sendMessage(ChatColor.RED + "讀取檔案時發生例外狀況");
        }
        return true;
    }
}
