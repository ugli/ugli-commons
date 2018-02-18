package se.ugli.java.util.stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.stream.Stream;

import org.junit.Test;
import org.mockito.Mockito;

public class ResourceStreamTest {

    @SuppressWarnings("resource")
    @Test
    public void shouldCloseResource() throws Exception {
        final AutoCloseable resource = Mockito.mock(AutoCloseable.class);

        final Stream<String> resourceStream = new ResourceStream<>(Stream.of("hej"), true, resource);
        resourceStream.count();

        verify(resource).close();
        verifyNoMoreInteractions(resource);
    }

    @SuppressWarnings("resource")
    @Test
    public void shouldNotCloseResource() throws Exception {
        final AutoCloseable resource = Mockito.mock(AutoCloseable.class);

        final Stream<String> resourceStream = new ResourceStream<>(Stream.of("hej"), false, resource);
        resourceStream.count();

        verifyNoMoreInteractions(resource);
    }

    @SuppressWarnings("resource")
    @Test
    public void shouldCloseResourceWithResource() throws Exception {
        final AutoCloseable resource = Mockito.mock(AutoCloseable.class);

        try (final Stream<String> resourceStream = new ResourceStream<>(Stream.of("hej"), false, resource)) {
            resourceStream.count();
        }

        verify(resource).close();
        verifyNoMoreInteractions(resource);
    }

    @SuppressWarnings("resource")
    @Test
    public void shouldCloseJustOnce() throws Exception {
        final AutoCloseable resource = Mockito.mock(AutoCloseable.class);

        try (final Stream<String> resourceStream = new ResourceStream<>(Stream.of("hej"), true, resource)) {
            resourceStream.count();
        }

        verify(resource).close();
        verifyNoMoreInteractions(resource);
    }

}
