package ew.sr.x1c.quilt.meow.plugin.NoExplode.music;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.DataQueue;
import NoteSoundAPI.NoteSound;
import NoteSoundAPI.NoteSoundUpdateEvent;
import NoteSoundAPI.NoteState;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.file.FileHandler;
import java.io.File;
import java.util.Collection;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

// 已測試 未使用 全域音樂播放 一個沒用的 ㄎㄧㄤ 功能
public class GlobalMusicPlayerCommand implements CommandExecutor, Listener {

    private static final DataQueue<String> QUEUE = new DataQueue<>();
    private static NoteSound current;

    @EventHandler
    public void onPlayerStateChange(NoteSoundUpdateEvent event) {
        if (event.getNoteState() == NoteState.STOP) {
            tryPlayNext();
        }
    }

    private static void tryPlayNext() {
        for (int i = 0;; ++i) {
            if (i > 0 && QUEUE.isLock()) {
                QUEUE.remove();
            }
            try {
                NoteSound sound = nextTrack();
                if (sound != null) {
                    current = sound;
                    broadcastActionBar(ChatColor.AQUA + "正在播放音樂");
                    break;
                }
            } catch (RuntimeException ex) {
                current = null;
                broadcastActionBar(ChatColor.RED + "所有音樂已播放完畢");
                break;
            }
        }
    }

    private static NoteSound nextTrack() {
        String next = QUEUE.next();
        if (next == null) {
            throw new RuntimeException("佇列為空");
        }
        NoteSound sound = loadSound(next);
        return sound.play() ? sound : null;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (current != null) {
            current.removePlayer(event.getPlayer());
        }
    }

    public static String getMusicPath(String fileName) {
        return FileHandler.MUSIC_DOWNLOAD_PATH + "/" + fileName + ".midi";
    }

    public static NoteSound loadSound(String track) {
        NoteSound sound = new NoteSound();
        Collection<? extends Player> online = Bukkit.getOnlinePlayers();
        online.forEach(player -> {
            sound.addPlayer(player);
        });
        sound.setMidiFile(new File(track));
        sound.setTempo(1);
        return sound;
    }

    public static void broadcastActionBar(String message) {
        Collection<? extends Player> online = Bukkit.getOnlinePlayers();
        online.forEach(player -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("quilt.music.global")) {
            sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
            return true;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "指令格式錯誤");
            return false;
        }

        try {
            switch (args[0].toLowerCase()) {
                case "load": {
                    String path = getMusicPath(args[1]);
                    QUEUE.add(path);
                    if (current == null) {
                        tryPlayNext();
                    }
                    break;
                }
                case "pause": {
                    if (current == null) {
                        sender.sendMessage(ChatColor.RED + "目前尚未播放音樂");
                        return true;
                    }
                    current.togglePause();
                    broadcastActionBar(ChatColor.AQUA + "已切換音樂暫停狀態");
                    break;
                }
                case "stop": {
                    if (current == null) {
                        sender.sendMessage(ChatColor.RED + "目前尚未播放音樂");
                        return true;
                    }
                    current.stop();
                    current = null;
                    QUEUE.clear();
                    broadcastActionBar(ChatColor.AQUA + "已停止音樂播放");
                    break;
                }
                case "skip": {
                    if (current == null) {
                        sender.sendMessage(ChatColor.RED + "目前尚未播放音樂");
                        return true;
                    }
                    current.stop();
                    tryPlayNext();
                    break;
                }
                case "repeat": {
                    QUEUE.toggleLock();
                    broadcastActionBar(ChatColor.AQUA + "重複播放 : " + (QUEUE.isLock() ? "開啟" : "關閉"));
                    break;
                }
            }
            return true;
        } catch (Exception ex) {
            sender.sendMessage(ChatColor.RED + "指令執行時發生例外狀況");
            return false;
        }
    }
}
