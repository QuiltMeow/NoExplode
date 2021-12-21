package ew.sr.x1c.quilt.meow.plugin.NoExplode.util.data;

public class TimerTask {

    private final int id;
    private final String time;
    private final Runnable task;

    public TimerTask(int id, String time, Runnable task) {
        this.id = id;
        this.time = time;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public Runnable getTask() {
        return task;
    }
}
