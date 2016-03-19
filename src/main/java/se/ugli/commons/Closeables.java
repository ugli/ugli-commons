package se.ugli.commons;

import java.io.IOException;

public final class Closeables {

    public static void close(final AutoCloseable... closeables) {
        for (final AutoCloseable closeable : closeables)
            if (closeable != null)
                try {
                    closeable.close();
                }
                catch (final Exception e) {
                    throw new IoException(new IOException(e));
                }
    }

    private Closeables() {

    }

}
