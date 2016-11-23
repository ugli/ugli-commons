package se.ugli.java.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferencePool<T extends AutoCloseable> implements Pool<T> {

    private static final Logger logger = LoggerFactory.getLogger(FixedSizePool.class);
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final BlockingQueue<Reference<T>> objectQueue;
    private final ReferenceQueue<T> referenceQueue = new ReferenceQueue<>();
    private final long checkoutTimeout;
    private final TimeUnit checkoutTimeoutUnit;

    public ReferencePool(final int capacity, final Supplier<T> objectFactory, final long checkoutTimeout,
            final TimeUnit checkoutTimeoutUnit) {
        objectQueue = new ArrayBlockingQueue<>(capacity);
        this.checkoutTimeout = checkoutTimeout;
        this.checkoutTimeoutUnit = checkoutTimeoutUnit;
        while (objectQueue.remainingCapacity() > 0)
            objectQueue.offer(new SoftReference<>(objectFactory.get(), referenceQueue));
        scheduler.scheduleWithFixedDelay(this::purgeReferences, 10, 10, TimeUnit.SECONDS);
    }

    private void purgeReferences() {
        Reference<? extends T> reference = referenceQueue.poll();
        while (reference != null) {
            final T object = reference.get();
            if (object != null)
                try {
                    object.close();
                }
                catch (final Exception e) {
                    logger.error(e.getMessage(), e);
                }
            reference = referenceQueue.poll();
        }
    }

    @Override
    public int size() {
        return objectQueue.size();
    }

    @Override
    public void close() {
        final List<Reference<T>> list = new ArrayList<>(size());
        objectQueue.drainTo(list);
        list.stream().map(Reference::get).filter(t -> t != null).forEach(object -> {
            try {
                object.close();
            }
            catch (final Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        purgeReferences();
    }

    @Override
    public void borrow(final Consumer<T> lender) {
        try {
            final Reference<T> ref = objectQueue.poll(checkoutTimeout, checkoutTimeoutUnit);
            final T loan = ref.get();
            if (loan == null)
                borrow(lender);
            else {
                lender.accept(loan);
                objectQueue.offer(ref);
            }
        }
        catch (final InterruptedException e) {
            throw new PoolException(e);
        }
    }

    @Override
    public T borrow() {
        // TODO Auto-generated method stub
        return null;
    }

}
