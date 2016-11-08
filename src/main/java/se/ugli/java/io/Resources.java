package se.ugli.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Resources {

    private Resources() {
    }

    public static Resource applyClassName(final String className) {
        return Resource.apply("/" + className.replace('.', '/') + ".class");
    }

    public static boolean classExists(final String className) {
        return applyClassName(className).exists();
    }

    public static Iterable<Resource> applyDir(final String resourceDir) {
        return applyDir(resourceDir, null);
    }

    public static Iterable<Resource> applyDir(final String resourceDir, final String resourcePattern) {
        final Resource resource = Resource.apply(resourceDir);
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
        throw new IoException(resource.toString() + " isn't a directory.");
    }

    private static Iterable<Resource> applyJarDir(final URL jarUrl, final String resourcePattern) {
        try (JarFile jarFile = getJarFile(jarUrl)) {
            final Set<Resource> result = new LinkedHashSet<>();
            final String jarUrlPath = jarUrl.getPath();
            final String resourcePath = jarUrlPath.substring(jarUrlPath.indexOf('!') + 1);
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
        final File jarFile = new File(jarUrlPath.substring("file:".length(), jarUrlPath.indexOf('!')));
        if (jarFile.exists())
            return new JarFile(jarFile);
        throw new FileNotFoundException(jarFile.getAbsolutePath());
    }

}
