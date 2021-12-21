package ew.sr.x1c.quilt.meow.plugin.NoExplode.score;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.TestModeCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.DeathListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoardHandler implements CommandExecutor {

    private final ReentrantLock lock;
    private final List<ScoreBoardData> deathScoreBoard;
    private int maxDeathIndex;

    public ScoreBoardHandler() {
        lock = new ReentrantLock();
        deathScoreBoard = new ArrayList<>();
        maxDeathIndex = -1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<ScoreBoardData> data = null;
        if (lock.tryLock()) {
            data = new ArrayList<>(deathScoreBoard);
            lock.unlock();
        }
        if (data == null) {
            sender.sendMessage(ChatColor.RED + "資源暫時無法取得 請稍後再試 ...");
            return true;
        }

        if (data.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "目前沒有任何資料");
            return true;
        }
        Collections.sort(data);

        int page = 1;
        if (args.length >= 1) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (Exception ex) {
            }
            if (page <= 0) {
                page = 1;
            }
        }
        sender.sendMessage(ChatColor.YELLOW + "母湯次數排行榜 資料總數 : " + data.size() + " 目前頁數 : " + page);
        int startIndex = (page - 1) * 10;
        for (int i = startIndex; i < Math.min(startIndex + 10, data.size()); ++i) {
            ScoreBoardData record = data.get(i);
            sender.sendMessage("[" + (i + 1) + "] 玩家 : " + record.getName() + " 母湯次數 : " + record.getScore());
        }
        return true;
    }

    private void updateDeathScoreBoard() {
        maxDeathIndex = -1;
        int max = 0, index = -1;
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective death = board.getObjective("Death");
        lock.lock();
        try {
            deathScoreBoard.clear();
            ImmutableSet<String> nameSet = ImmutableSet.copyOf(board.getEntries());
            for (String name : nameSet) {
                try {
                    int score = death.getScore(name).getScore();
                    if (score > 0) {
                        deathScoreBoard.add(new ScoreBoardData(name, score));
                        if (score > max) {
                            max = score;
                            index = deathScoreBoard.size() - 1;
                        }
                    }
                } catch (Exception ex) {
                    Main.getPlugin().getLogger().log(Level.WARNING, "取得母湯次數時發生例外狀況", ex);
                }
            }
        } catch (Exception ex) {
            Main.getPlugin().getLogger().log(Level.WARNING, "取得分數列表時發生例外狀況", ex);
        } finally {
            lock.unlock();
        }
        maxDeathIndex = index;
        ScoreBoardData king = deathScoreBoard.get(maxDeathIndex);

        try {
            ImmutableList<Player> playerList = ImmutableList.copyOf(Bukkit.getOnlinePlayers());
            playerList.forEach(player -> {
                try {
                    String name = player.getName();
                    int deathCount = death.getScore(name).getScore();
                    int cooltime = DeathListener.getRespawnCooltime(deathCount + 1);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            try {
                                Scoreboard display = manager.getNewScoreboard();
                                Objective output = display.registerNewObjective("deathRecord", "dummy", "§B母湯紀錄");
                                output.setDisplaySlot(DisplaySlot.SIDEBAR);

                                Score first = output.getScore(king.getName());
                                first.setScore(king.getScore());

                                Score count = output.getScore("§D母湯次數");
                                count.setScore(deathCount);

                                Score wait = output.getScore("§E復活等待");
                                wait.setScore(cooltime);

                                if (TestModeCommand.isTestMode()) {
                                    Score test = output.getScore("§A測試模式");
                                    test.setScore(1);
                                }
                                player.setScoreboard(display);
                            } catch (Exception ex) {
                                Main.getPlugin().getLogger().log(Level.WARNING, "更新計分板時發生例外狀況", ex);
                            }
                        }
                    }.runTask(Main.getPlugin());
                } catch (Exception ex) {
                    Main.getPlugin().getLogger().log(Level.WARNING, "取得計分板資料時發生例外狀況", ex);
                }
            });
        } catch (Exception ex) {
            Main.getPlugin().getLogger().log(Level.WARNING, "取得玩家列表時發生例外狀況", ex);
        }
    }

    public void registerScoreBoardTask() {
        new BukkitRunnable() {

            @Override
            public void run() {
                updateDeathScoreBoard();
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 20 * 60, 20 * 60);
    }
}
