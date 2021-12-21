package ew.sr.x1c.quilt.meow.plugin.NoExplode.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataQueue<T> implements Serializable {

    private boolean lock;
    private final Queue<T> QUEUE = new ConcurrentLinkedQueue<>();

    public boolean add(T data) {
        try {
            return QUEUE.add(data);
        } catch (Exception ex) {
            return false;
        }
    }

    public T next() {
        if (lock) {
            return QUEUE.peek();
        }
        return QUEUE.poll();
    }

    public void shuffle() {
        List<T> data = new ArrayList<>(QUEUE);
        Collections.shuffle(data);
        QUEUE.clear();
        QUEUE.addAll(data);
    }

    public void clear() {
        QUEUE.clear();
    }

    public void remove() {
        QUEUE.poll();
    }

    public void setLock(boolean value) {
        lock = value;
    }

    public void toggleLock() {
        lock = !lock;
    }

    public boolean isLock() {
        return lock;
    }
}
