package text.repository;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A poor man's circular buffer. There are plenty of third party libraries with this, but since we don't need to support poll(), just add(), it's easy
 * to implement. Uses a {@link Queue} so that we can't evict the oldest element when full
 *
 * @author antonio
 */
public class AddOnlyCircularBuffer<E> {

    private final int maxElements;
    private final Queue<E> queue;
    private int currentElements;

    public AddOnlyCircularBuffer(int maxElements) {
        if (maxElements <= 0) {
            throw new IllegalArgumentException("Max elements must be positive, given input was: " + maxElements);
        }
        this.maxElements = maxElements;
        this.queue = new LinkedList<>();
        this.currentElements = 0;
    }

    public void add(E e) {
        queue.add(e);
        if (++currentElements > maxElements) {
            queue.poll();
        }
    }

    public Queue<E> getAll() {
        return queue;
    }
}
