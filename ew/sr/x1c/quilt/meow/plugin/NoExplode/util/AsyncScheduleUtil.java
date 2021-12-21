package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncScheduleUtil {

    private static final Map<String, Queue<Runnable>> TASK_QUEUE = new LinkedHashMap<>();

    public static void runTaskAsynchronouslySequence(Runnable task, String sequenceKey) {
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.containsKey(sequenceKey)) {
                Queue<Runnable> queue = TASK_QUEUE.get(sequenceKey);
                synchronized (queue) {
                    queue.add(task);
                }
            } else {
                Queue<Runnable> queue = new LinkedList<>();
                queue.add(task);
                TASK_QUEUE.put(sequenceKey, queue);

                Runnable runnable = () -> {
                    processTaskQueue(sequenceKey);
                };
                new Thread(runnable).start();
            }
        }
    }

    public static void runBukkitTaskAsynchronouslySequence(BukkitRunnable task, String sequenceKey) {
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.containsKey(sequenceKey)) {
                Queue<Runnable> queue = TASK_QUEUE.get(sequenceKey);
                synchronized (queue) {
                    queue.add(task);
                }
            } else {
                Queue<Runnable> queue = new LinkedList<>();
                queue.add(task);
                TASK_QUEUE.put(sequenceKey, queue);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        processTaskQueue(sequenceKey);
                    }
                }.runTaskAsynchronously(Main.getPlugin());
            }
        }
    }

    private static void processTaskQueue(String sequenceKey) {
        Queue<Runnable> queue;
        synchronized (sequenceKey) {
            if (!TASK_QUEUE.containsKey(sequenceKey)) {
                return;
            }
            queue = TASK_QUEUE.get(sequenceKey);
        }

        while (true) {
            Runnable task;
            synchronized (TASK_QUEUE) {
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        TASK_QUEUE.remove(sequenceKey);
                        return;
                    }
                    task = queue.poll();
                }
            }
            task.run();
        }
    }
}
