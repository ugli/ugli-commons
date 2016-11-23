package se.ugli.java.util;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

class BlockingQueueObjectPool<T> implements ObjectPool<T> {

    private final BlockingQueue<T> queue;
    private final long checkoutTimeout;
    private final TimeUnit checkoutTimeoutUnit;

    BlockingQueueObjectPool(final BlockingQueue<T> queue, final Supplier<T> objectFactory, final long checkoutTimeout,
            final TimeUnit checkoutTimeoutUnit) {
        this.queue = queue;
        this.checkoutTimeout = checkoutTimeout;
        this.checkoutTimeoutUnit = checkoutTimeoutUnit;
        while (queue.remainingCapacity() > 0)
            queue.offer(objectFactory.get());
    }

    @Override
    public void close() {
        final T pooledOject = queue.poll();
        while (pooledOject != null)
            if (pooledOject instanceof AutoCloseable) {
                final AutoCloseable closeable = (AutoCloseable) pooledOject;
                try {
                    closeable.close();
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
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
                    .intercept(InvocationHandlerAdapter.of(new PooledObjectInvocationHandler(object))).make()
                    .load(object.getClass().getClassLoader()).getLoaded().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new PoolException(e);
        }

    }

    private class PooledObjectInvocationHandler implements InvocationHandler {

        private final T pooledObject;

        public PooledObjectInvocationHandler(final T pooledObject) {
            this.pooledObject = pooledObject;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if (method.getName().equals("close") && method.getParameterTypes().length == 0) {
                queue.offer(pooledObject);
                return null;
            }
            return pooledObject.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(pooledObject,
                    args);
        }

    }

}
