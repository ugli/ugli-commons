package se.ugli.java.util.juc;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import se.ugli.java.util.ImmutableSet;

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

}