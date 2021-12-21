package ew.sr.x1c.quilt.meow.plugin.NoExplode.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HTTPDownloader implements Runnable {

    private final CommandSender sender;
    private final String path;
    private final String link;

    private final boolean checkType;

    public HTTPDownloader(CommandSender sender, String link, String path) {
        this.sender = sender;
        this.path = path;
        this.link = link;
        checkType = true;
    }

    public HTTPDownloader(CommandSender sender, String link, String path, boolean checkType) {
        this.sender = sender;
        this.path = path;
        this.link = link;
        this.checkType = checkType;
    }

    @Override
    public void run() {
        String[] splitFileName = path.split("\\.");
        String[] splitLink = link.split("\\.");

        String fileType = splitFileName[splitFileName.length - 1];
        String linkType = splitLink[splitLink.length - 1];
        if (checkType) {
            if (!fileType.equalsIgnoreCase(linkType)) {
                if (sender != null) {
                    sender.sendMessage(ChatColor.RED + "檔案名稱與實際類型不符 ...");
                }
                return;
            }
        }

        if (FileHandler.checkFileExist(path)) {
            if (sender != null) {
                sender.sendMessage(ChatColor.RED + "檔案下載失敗 目標檔案名稱已存在 ...");
            }
            return;
        }
        try {
            URL url = new URL(link);
            try (ReadableByteChannel rbc = Channels.newChannel(url.openStream())) {
                try (FileOutputStream fos = new FileOutputStream(path)) {
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
            }
            if (sender != null) {
                sender.sendMessage(ChatColor.GREEN + "檔案下載成功 ! 檔案路徑 : " + path);
            }
        } catch (IOException ex) {
            if (sender != null) {
                sender.sendMessage(ChatColor.RED + "檔案下載失敗 請檢查網址與伺服器網路連線是否正常 ...");
            }
        }
    }
}
