package se.ugli.java.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface ImmutableCollection<E> extends Iterable<E>, Serializable {

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Object[] toArray();

    <T> T[] toArray(T[] a);

    Collection<E> toCollection();

    default boolean containsAll(final Iterable<?> c) {
        for (final Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(toArray(), 0);
    }

    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
