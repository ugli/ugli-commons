package se.ugli.java.util;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Pool<T extends AutoCloseable> extends Closeable {

    T borrow();

    void borrow(Consumer<T> lender);

    @Override
    void close();

    int size();

    public static <T extends AutoCloseable> PoolBuilder<T> builder(final Supplier<T> objectFactory) {
        return new PoolBuilder<>(objectFactory);
    }

    public static class PoolBuilder<T extends AutoCloseable> {
        private final Supplier<T> objectFactory;
        private final long checkoutTimeout = 10;
        private final int capacity = 10;
        private final TimeUnit checkoutTimeoutUnit = MILLISECONDS;

        private PoolBuilder(final Supplier<T> objectFactory) {
            this.objectFactory = objectFactory;
        }

        public Pool<T> build() {
            return new FixedSizePool<>(capacity, objectFactory, checkoutTimeout, checkoutTimeoutUnit);
        }
    }

}
