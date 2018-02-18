package se.ugli.java.lang;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoCloseables {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCloseables.class);

    public static void safeClose(final AutoCloseable... closeables) {
        if (closeables != null)
            Stream.of(closeables).forEach(c -> {
                try {
                    if (c != null)
                        c.close();
                }
                catch (final Exception e) {
                    LOG.warn(e.getMessage(), e);
                }

            });

    }

}
