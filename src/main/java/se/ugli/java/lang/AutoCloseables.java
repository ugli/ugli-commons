package se.ugli.java.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoCloseables {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCloseables.class);

    public static void safeClose(final AutoCloseable closeable) {
        try {
            closeable.close();
        }
        catch (final Exception e) {
            LOG.warn(e.getMessage(), e);
        }
    }

}
