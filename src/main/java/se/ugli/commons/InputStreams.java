package se.ugli.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public class InputStreams {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private final int bufferSize;

    private InputStreams(final int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public static InputStreams apply() {
        return new InputStreams(DEFAULT_BUFFER_SIZE);
    }

    public static InputStreams apply(final int bufferSize) {
        return new InputStreams(bufferSize);
    }

    public void copy(final InputStream in, final OutputStream out) {
        try {
            final byte[] buf = new byte[bufferSize];
            int n;
            while ((n = in.read(buf)) > 0)
                out.write(buf, 0, n);
        }
        catch (final IOException e) {
            throw new IoException(e);
        }
    }

    public void copy(final Reader in, final Charset charset, final OutputStream out) {
        copy(toInputStream(in, charset), out);
    }

    public void copy(final Reader in, final OutputStream out) {
        copy(in, Charset.defaultCharset(), out);
    }

    public byte[] copyToBytes(final InputStream in) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            copy(in, out);
            return out.toByteArray();
        }
        catch (final IOException e) {
            throw new IoException(e);
        }
    }

    public byte[] copyToBytes(final Reader in) {
        return copyToBytes(in, Charset.defaultCharset());
    }

    public byte[] copyToBytes(final Reader in, final Charset charset) {
        return copyToBytes(toInputStream(in, charset));
    }

    public String copyToString(final InputStream in) {
        return copyToString(in, Charset.defaultCharset());
    }

    public String copyToString(final InputStream in, final Charset charset) {
        return new String(copyToBytes(in), charset);
    }

    public String copyToString(final Reader in) {
        return copyToString(in, Charset.defaultCharset(), Charset.defaultCharset());
    }

    public String copyToString(final Reader in, final Charset inCharset, final Charset outCharset) {
        return copyToString(toInputStream(in, inCharset), outCharset);
    }

    private InputStream toInputStream(final Reader reader, final Charset charset) {
        try {
            final char[] charBuffer = new char[bufferSize];
            final StringBuilder builder = new StringBuilder();
            int numCharsRead;
            while ((numCharsRead = reader.read(charBuffer, 0, charBuffer.length)) != -1)
                builder.append(charBuffer, 0, numCharsRead);
            return new ByteArrayInputStream(builder.toString().getBytes(charset));
        }
        catch (final IOException e) {
            throw new IoException(e);
        }
    }

}
