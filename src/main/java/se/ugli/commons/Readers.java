package se.ugli.commons;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public class Readers {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private final int bufferSize;
    private final InputStreams inputStreams;

    private Readers(final int bufferSize) {
        inputStreams = InputStreams.apply(bufferSize);
        this.bufferSize = bufferSize;
    }

    public static Readers apply() {
        return new Readers(DEFAULT_BUFFER_SIZE);
    }

    public static Readers apply(final int bufferSize) {
        return new Readers(bufferSize);
    }

    public void copy(final Reader in, final Charset charset, final OutputStream out) {
        inputStreams.copy(toInputStream(in, charset), out);
    }

    public void copy(final Reader in, final OutputStream out) {
        copy(in, Charset.defaultCharset(), out);
    }

    public byte[] copyToBytes(final Reader in) {
        return copyToBytes(in, Charset.defaultCharset());
    }

    public byte[] copyToBytes(final Reader in, final Charset charset) {
        return inputStreams.copyToBytes(toInputStream(in, charset));
    }

    public String copyToString(final Reader in) {
        return copyToString(in, Charset.defaultCharset(), Charset.defaultCharset());
    }

    public String copyToString(final Reader in, final Charset inCharset, final Charset outCharset) {
        return inputStreams.copyToString(toInputStream(in, inCharset), outCharset);
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
