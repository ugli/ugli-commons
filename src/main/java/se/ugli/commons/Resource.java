package se.ugli.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class Resource extends ValueObject<String> {

    private static final long serialVersionUID = 5702359943213375547L;

    public static Resource apply(final String value) {
        return new Resource(value);
    }

    public static Resource applyClassName(final String className) {
        return new Resource("/" + className.replace('.', '/') + ".class");
    }

    public static boolean classExists(final String className) {
        return applyClassName(className).exists();
    }

    public static Iterable<Resource> applyDir(final String resourceDir) {
        return applyDir(resourceDir, null);
    }

    public static Iterable<Resource> applyDir(final String resourceDir, final String resourcePattern) {
        final Resource resource = apply(resourceDir);
        final URL url = resource.asUrl();
        if ("file".equals(url.getProtocol()))
            return applyFileDir(resource, resourcePattern);
        return applyJarDir(url, resourcePattern);
    }

    private static Iterable<Resource> applyFileDir(final Resource resource, final String resourcePattern) {
        if (resource.isDirectory()) {
            final List<Resource> result = new LinkedList<>();
            for (final String childFileName : resource.asFile().list()) {
                final StringBuilder childPathBuilder = new StringBuilder();
                childPathBuilder.append(resource.value);
                if (!resource.value.endsWith("/"))
                    childPathBuilder.append("/");
                childPathBuilder.append(childFileName);
                final String childPath = childPathBuilder.toString();
                if (resourcePattern == null || childPath.matches(resourcePattern))
                    result.add(Resource.apply(childPath));
            }
            return result;
        }
        throw new IllegalStateException("This resource isn't a directory: " + resource.value);
    }

    private static Iterable<Resource> applyJarDir(final URL jarUrl, final String resourcePattern) {
        try (JarFile jarFile = getJarFile(jarUrl)) {
            final Set<Resource> result = new LinkedHashSet<>();
            final String jarUrlPath = jarUrl.getPath();
            final String resourcePath = jarUrlPath.substring(jarUrlPath.indexOf("!") + 1);
            final Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                final JarEntry jarEntry = jarEntries.nextElement();
                final String childPath = "/" + jarEntry.getName();
                if (childPath.startsWith(resourcePath) && !childPath.endsWith("/")
                        && (resourcePattern == null || childPath.matches(resourcePattern)))
                    result.add(Resource.apply(childPath));
            }
            return result;
        }
        catch (final IOException e) {
            throw new IoException(e);
        }
    }

    private static JarFile getJarFile(final URL jarURL) throws IOException {
        final String jarUrlPath = jarURL.getPath();
        final File jarFile = new File(jarUrlPath.substring("file:".length(), jarUrlPath.indexOf("!")));
        if (jarFile.exists())
            return new JarFile(jarFile);
        throw new FileNotFoundException(jarFile.getAbsolutePath());
    }

    private Resource(final String value) {
        super(value);
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
            throw new IoException("Resource '" + value + "' not found.");
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
        if (url != null)
            return url;
        throw new IoException("Resource '" + value + "' not found.");
    }

    @Override
    public String toString() {
        return value;
    }

}
