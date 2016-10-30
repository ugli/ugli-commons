package se.ugli.commons.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockingQueuePool<T> implements Pool<T> {

    private final BlockingQueue<T> queue;
    private final long checkoutTimeout;
    private final TimeUnit checkoutTimeoutUnit;

    private BlockingQueuePool(BlockingQueue<T> queue, Supplier<T> objectFactory, long checkoutTimeout, TimeUnit checkoutTimeoutUnit) {
        this.queue = queue;
        this.checkoutTimeout = checkoutTimeout;
        this.checkoutTimeoutUnit = checkoutTimeoutUnit;
        while (queue.remainingCapacity() > 0)
            queue.offer(objectFactory.get());
    }

    @Override
    public void borrow(Consumer<T> borrower) {
        try {
            T loan = queue.poll(checkoutTimeout, checkoutTimeoutUnit);
            borrower.accept(loan);
            queue.offer(loan);
        } catch (InterruptedException e) {
            throw new PoolException(e);
        }
    }

    @Override
    public void close() {
        while (!queue.isEmpty()) {
            T t = queue.poll();
            if (t instanceof AutoCloseable) {
                AutoCloseable c = (AutoCloseable) t;
                try {
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
