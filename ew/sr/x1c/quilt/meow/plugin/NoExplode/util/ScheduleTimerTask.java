package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.data.TimerTask;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

// 定時排程器
// 自動備份功能已交由 CI 處理
public class ScheduleTimerTask {

    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");
    private static final List<TimerTask> TIMER_TASK = Collections.synchronizedList(new ArrayList<>());

    private static int counter;
    private static BukkitTask scheduleTask;

    public static void startTimerTask() {
        if (scheduleTask != null) {
            return;
        }
        scheduleTask = new BukkitRunnable() {

            @Override
            public void run() {
                String now = TIME_FORMAT.format(new Date());
                runTimerTask(now);
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 0, 20);
    }

    public static void stopTimerTask() {
        if (scheduleTask != null) {
            scheduleTask.cancel();
        }
    }

    public static int registerTask(String time, Runnable task) {
        int id = counter++;
        TIMER_TASK.add(new TimerTask(id, time, task));
        return id;
    }

    public static int registerAutomaticShutdownTask() {
        return registerTask("030000", () -> {
            Bukkit.shutdown();
        });
    }

    public static boolean removeTask(int id) {
        Iterator<TimerTask> iterator = TIMER_TASK.iterator();
        while (iterator.hasNext()) {
            TimerTask task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static void runTimerTask(String time) {
        for (TimerTask task : TIMER_TASK) {
            if (task.getTime().equals(time)) {
                new Thread(task.getTask()).start();
            }
        }
    }
}
