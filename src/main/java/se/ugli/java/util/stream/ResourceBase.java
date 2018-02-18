package se.ugli.java.util.stream;

import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ResourceBase implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceBase.class);
    private boolean closed = false;
    private final BaseStream<?, ?> stream;

    protected final boolean closeOnTerminalOperation;
    protected final AutoCloseable[] resources;

    ResourceBase(final BaseStream<?, ?> stream, final boolean closeOnTerminalOperation,
            final AutoCloseable... resources) {
        this.stream = stream;
        this.closeOnTerminalOperation = closeOnTerminalOperation;
        this.resources = resources;
    }

    protected <T> T evalAndclose(final Supplier<T> toEval) {
        final T result = toEval.get();
        if (closeOnTerminalOperation)
            close();
        return result;
    }

    protected void executeAndClose(final Runnable toExecute) {
        toExecute.run();
        if (closeOnTerminalOperation)
            close();

    }

    @Override
    public final void close() {
        if (!closed) {
            stream.close();
            Stream.of(resources).forEach(r -> {
                try {
                    r.close();
                }
                catch (final Exception e) {
                    LOG.warn(e.getMessage(), e);
                }
            });
            closed = true;
        }
    }

}
