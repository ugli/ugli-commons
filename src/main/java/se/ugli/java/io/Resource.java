package se.ugli.java.io;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import se.ugli.java.lang.ValueObject;

public class Resource extends ValueObject<String> {

    private static final long serialVersionUID = 5702359943213375547L;

    public Resource(final String value) {
        super(value);
    }

    public static Resource apply(final String value) {
        return new Resource(value);
    }

    public boolean exists() {
        return getClass().getResource(value) != null;
    }

    public boolean isDirectory() {
        return asFile().isDirectory();
    }

    public byte[] asBytes() {
        return InputStreams.apply().copyToBytes(asInputStream());
    }

    public char[] asChars() {
        return asChars(Charset.defaultCharset());
    }

    public char[] asChars(final Charset charset) {
        return InputStreams.apply().copyToString(asInputStream(), charset).toCharArray();
    }

    public File asFile() {
        return new File(asUrl().getFile());
    }

    public Path asPath() {
        return asFile().toPath();
    }

    public Reader asReader() {
        return asReader(Charset.defaultCharset());
    }

    public Reader asReader(final Charset charset) {
        return new InputStreamReader(asInputStream(), charset);
    }

    public Source asSource() {
        return new StreamSource(asInputStream(), value);
    }

    public InputStream asInputStream() {
        final InputStream stream = getClass().getResourceAsStream(value);
        if (stream == null)
            throw new IoException(toString() + " not found.");
        return stream;
    }

    public String asString() {
        return asString(Charset.defaultCharset());
    }

    public String asString(final Charset charset) {
        return InputStreams.apply().copyToString(asInputStream(), charset);
    }

    public URL asUrl() {
        final URL url = getClass().getResource(value);
        if (url == null)
            throw new IoException(toString() + " not found.");
        return url;
    }

}
