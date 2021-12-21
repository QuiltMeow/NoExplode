package ew.sr.x1c.quilt.meow.plugin.NoExplode.music;

import NoteSoundAPI.NoteSound;
import NoteSoundAPI.NoteSoundUpdateEvent;
import NoteSoundAPI.NoteState;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.file.FileHandler;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.file.HTTPDownloader;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

// 我好懶得重構他
// 這 API 還不提供文檔 只能反編譯 w
public class MusicPlayerCommand implements CommandExecutor, Listener {

    private static final Map<String, NoteSound> PLAYING = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerStateChange(NoteSoundUpdateEvent event) {
        if (event.getNoteState() == NoteState.STOP) {
            NoteSound sound = event.getNoteSound();
            List<Player> playerList = sound.getPlayers();
            for (Player player : playerList) {
                PLAYING.remove(player.getUniqueId().toString());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();
        NoteSound sound = PLAYING.get(uuid);
        if (sound != null) {
            sound.stop();
            PLAYING.remove(uuid);
        }
    }

    public static String getMusicPath(String fileName) {
        return FileHandler.MUSIC_DOWNLOAD_PATH + "/" + fileName + ".midi";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "該指令必須在遊戲內由玩家使用");
            return false;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "指令格式錯誤");
            return false;
        }
        Player player = (Player) sender;
        try {
            switch (args[0].toLowerCase()) {
                case "add": {
                    Player target = args.length <= 2 ? player : Bukkit.getPlayer(args[1]);
                    if (target != player && !sender.hasPermission("quilt.music.other")) {
                        sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
                        return true;
                    }
                    File file = new File(args.length <= 2 ? getMusicPath(args[1]) : getMusicPath(args[2]));
                    if (target == null || !file.exists()) {
                        sender.sendMessage(ChatColor.RED + "輸入參數無效");
                        return false;
                    }
                    String uuid = target.getUniqueId().toString();
                    if (PLAYING.get(uuid) != null) {
                        sender.sendMessage(ChatColor.RED + "目標玩家已正在播放中");
                        return true;
                    }
                    NoteSound sound = new NoteSound();
                    sound.addPlayer(target);
                    sound.setMidiFile(file);
                    sound.setTempo(1);

                    if (sound.play()) {
                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "正在播放音樂"));
                        sender.sendMessage(ChatColor.GREEN + "已開始播放音樂");
                        PLAYING.put(uuid, sound);
                    } else {
                        sender.sendMessage(ChatColor.RED + "無法播放該音樂");
                    }
                    break;
                }
                case "pause": {
                    Player target = args.length <= 1 ? player : Bukkit.getPlayer(args[1]);
                    if (target != player && !sender.hasPermission("quilt.music.other")) {
                        sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
                        return true;
                    }
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "找不到目標玩家");
                        return true;
                    }
                    String uuid = target.getUniqueId().toString();
                    NoteSound sound = PLAYING.get(uuid);
                    if (sound == null) {
                        sender.sendMessage(ChatColor.RED + "目標玩家尚未播放任何音樂");
                        return true;
                    }
                    sound.togglePause();
                    target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "已切換音樂暫停狀態"));
                    sender.sendMessage(ChatColor.GREEN + "已切換目標玩家音樂暫停狀態");
                    break;
                }
                case "stop": {
                    Player target = args.length <= 1 ? player : Bukkit.getPlayer(args[1]);
                    if (target != player && !sender.hasPermission("quilt.music.other")) {
                        sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
                        return true;
                    }
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "找不到目標玩家");
                        return true;
                    }
                    String uuid = target.getUniqueId().toString();
                    NoteSound sound = PLAYING.get(uuid);
                    if (sound == null) {
                        sender.sendMessage(ChatColor.RED + "目標玩家尚未播放任何音樂");
                        return true;
                    }
                    sound.stop();
                    target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "已停止音樂播放"));
                    PLAYING.remove(uuid);
                    sender.sendMessage(ChatColor.GREEN + "已停止目標玩家音樂播放");
                    break;
                }
                case "addall": {
                    if (!sender.hasPermission("quilt.music.all")) {
                        sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
                        return true;
                    }
                    File file = new File(getMusicPath(args[1]));
                    if (!file.exists()) {
                        sender.sendMessage(ChatColor.RED + "目標檔案不存在");
                        return false;
                    }
                    Collection<? extends Player> online = Bukkit.getOnlinePlayers();
                    for (Player target : online) {
                        String uuid = target.getUniqueId().toString();
                        if (PLAYING.get(uuid) != null) {
                            continue;
                        }
                        NoteSound sound = new NoteSound();
                        sound.addPlayer(target);
                        sound.setMidiFile(file);
                        sound.setTempo(1);

                        if (!sound.play()) {
                            sender.sendMessage(ChatColor.RED + "無法播放該音樂");
                            return true;
                        }
                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "正在播放音樂"));
                        PLAYING.put(uuid, sound);
                    }
                    sender.sendMessage(ChatColor.GREEN + "已對所有玩家播放指定音樂");
                    break;
                }
                case "pauseall": {
                    if (!sender.hasPermission("quilt.music.all")) {
                        sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
                        return true;
                    }
                    Collection<? extends Player> online = Bukkit.getOnlinePlayers();
                    for (Player target : online) {
                        String uuid = target.getUniqueId().toString();
                        NoteSound sound = PLAYING.get(uuid);
                        if (sound == null) {
                            continue;
                        }
                        sound.togglePause();
                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "已切換音樂暫停狀態"));
                    }
                    sender.sendMessage(ChatColor.GREEN + "已切換所有玩家音樂暫停狀態");
                    break;
                }
                case "stopall": {
                    if (!sender.hasPermission("quilt.music.all")) {
                        sender.sendMessage(ChatColor.RED + "權限不足 無法使用該指令");
                        return true;
                    }
                    Collection<? extends Player> online = Bukkit.getOnlinePlayers();
                    for (Player target : online) {
                        String uuid = target.getUniqueId().toString();
                        NoteSound sound = PLAYING.get(uuid);
                        if (sound == null) {
                            continue;
                        }
                        sound.stop();
                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "已停止音樂播放"));
                        PLAYING.remove(uuid);
                    }
                    sender.sendMessage(ChatColor.GREEN + "已停止所有玩家音樂播放");
                    break;
                }
                case "download": {
                    String url = args[1];
                    String[] splitURL = url.split("\\.");
                    String fileType = splitURL[splitURL.length - 1];
                    if (!FileHandler.isValidMusicFileType(fileType)) {
                        sender.sendMessage(ChatColor.RED + "不支援該檔案類型");
                        return true;
                    }
                    HTTPDownloader downloader = new HTTPDownloader(sender, url, FileHandler.MUSIC_DOWNLOAD_PATH + "/" + FileHandler.getRandomFileName("midi"), false);
                    new Thread(downloader).start();
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
