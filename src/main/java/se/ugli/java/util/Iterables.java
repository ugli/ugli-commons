package se.ugli.java.util;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.stream.StreamSupport;

public class Iterables {

    private Iterables() {

    }

    public static <E> Collection<E> toCollection(final Iterable<E> iterable) {
        if (iterable instanceof Collection)
            return (Collection<E>) iterable;
        else if (iterable instanceof ImmutableCollection)
            return ((ImmutableCollection<E>) iterable).toCollection();
        else
            return StreamSupport.stream(iterable.spliterator(), false).collect(toList());
    }

}
