package se.ugli.java.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockingQueueLenderPool<T> implements LenderPool<T> {

    private final BlockingQueue<T> queue;
    private final long checkoutTimeout;
    private final TimeUnit checkoutTimeoutUnit;

    public BlockingQueueLenderPool(final BlockingQueue<T> queue, final Supplier<T> objectFactory,
            final long checkoutTimeout, final TimeUnit checkoutTimeoutUnit) {
        this.queue = queue;
        this.checkoutTimeout = checkoutTimeout;
        this.checkoutTimeoutUnit = checkoutTimeoutUnit;
        while (queue.remainingCapacity() > 0)
            queue.offer(objectFactory.get());
    }

    @Override
    public void borrow(final Consumer<T> borrower) {
        try {
            final T loan = queue.poll(checkoutTimeout, checkoutTimeoutUnit);
            borrower.accept(loan);
            queue.offer(loan);
        }
        catch (final InterruptedException e) {
            throw new PoolException(e);
        }
    }

    @Override
    public void close() {
        while (!queue.isEmpty()) {
            final T t = queue.poll();
            if (t instanceof AutoCloseable) {
                final AutoCloseable c = (AutoCloseable) t;
                try {
                    c.close();
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
