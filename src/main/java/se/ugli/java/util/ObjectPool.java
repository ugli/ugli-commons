package se.ugli.java.util;

import java.io.Closeable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface ObjectPool<T> extends Closeable {

    T borrow();

    @Override
    void close();

    static <T extends AutoCloseable> ObjectPoolBuilder<T> builder(final Supplier<T> objectFactory) {
        return new ObjectPoolBuilder<>(objectFactory);
    }

    static class ObjectPoolBuilder<T extends AutoCloseable> {
        private final Supplier<T> objectFactory;
        private final long checkoutTimeout = 10;
        private final int capacity = 10;
        private final BlockingQueue<T> queue = new ArrayBlockingQueue<>(capacity);
        private final TimeUnit checkoutTimeoutUnit = TimeUnit.MILLISECONDS;

        private ObjectPoolBuilder(final Supplier<T> objectFactory) {
            this.objectFactory = objectFactory;
        }

        ObjectPool<T> build() {
            return new BlockingQueueObjectPool<>(queue, objectFactory, checkoutTimeout, checkoutTimeoutUnit);
        }
    }

}
