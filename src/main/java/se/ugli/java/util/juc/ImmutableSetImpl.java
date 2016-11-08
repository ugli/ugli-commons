package se.ugli.java.util.juc;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import se.ugli.java.util.ImmutableSet;
import se.ugli.java.util.Iterables;

public class ImmutableSetImpl<E> implements ImmutableSet<E> {

    private static final long serialVersionUID = -7736014747220267958L;
    private final Set<E> set;

    public ImmutableSetImpl(final Set<E> set) {
        this.set = set;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return set.toArray(a);
    }

    @Override
    public Collection<E> toCollection() {
        return Collections.unmodifiableSet(set);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Iterable)
            return Arrays.equals(toArray(), Iterables.toCollection((Iterable<?>) obj).toArray());
        return false;
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public String toString() {
        return "Set(" + stream().map(e -> e == null ? "null" : e.toString()).collect(joining(",")) + ")";
    }

}