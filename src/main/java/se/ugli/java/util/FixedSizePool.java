package se.ugli.java.util;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

class FixedSizePool<T extends AutoCloseable> implements Pool<T> {

    private static final Logger logger = LoggerFactory.getLogger(FixedSizePool.class);
    private final BlockingQueue<T> queue;
    private final long checkoutTimeout;
    private final TimeUnit checkoutTimeoutUnit;

    FixedSizePool(final BlockingQueue<T> queue, final Supplier<T> objectFactory, final long checkoutTimeout,
            final TimeUnit checkoutTimeoutUnit) {
        this.queue = queue;
        this.checkoutTimeout = checkoutTimeout;
        this.checkoutTimeoutUnit = checkoutTimeoutUnit;
        while (queue.remainingCapacity() > 0)
            queue.offer(objectFactory.get());
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void close() {
        final List<T> list = new ArrayList<>(size());
        queue.drainTo(list);
        list.stream().forEach(object -> {
            try {
                object.close();
            }
            catch (final Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void borrow(final Consumer<T> lender) {
        try {
            final T loan = queue.poll(checkoutTimeout, checkoutTimeoutUnit);
            lender.accept(loan);
            queue.offer(loan);
        }
        catch (final InterruptedException e) {
            throw new PoolException(e);
        }
    }

    @Override
    public T borrow() {
        try {
            return proxy(queue.poll(checkoutTimeout, checkoutTimeoutUnit));
        }
        catch (final InterruptedException e) {
            throw new PoolException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private T proxy(final T object) {
        try {
            return (T) new ByteBuddy().subclass(object.getClass()).method(isDeclaredBy(object.getClass()))
                    .intercept(InvocationHandlerAdapter.of(new ProxyInvocationHandler(object))).make()
                    .load(object.getClass().getClassLoader()).getLoaded().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new PoolException(e);
        }

    }

    private class ProxyInvocationHandler implements InvocationHandler {

        private final T object;

        ProxyInvocationHandler(final T object) {
            this.object = object;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if ("close".equals(method.getName()) && method.getParameterTypes().length == 0) {
                queue.offer(object);
                return null;
            }
            return object.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(object, args);
        }

    }

}
