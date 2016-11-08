package se.ugli.java.util.juc;

import static java.util.Collections.unmodifiableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import se.ugli.java.util.ImmutableList;
import se.ugli.java.util.Iterables;

public class ImmutableListImpl<E> implements ImmutableList<E> {

    private static final long serialVersionUID = 1051540856993996441L;

    private final List<E> list;

    public ImmutableListImpl(final List<E> list) {
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public E get(final int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(final Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ImmutableList<E> subList(final int fromIndex, final int toIndex) {
        return new ImmutableListImpl<>(list.subList(fromIndex, toIndex));
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return list.toArray(a);
    }

    @Override
    public Collection<E> toCollection() {
        return unmodifiableList(list);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Iterable)
            return Arrays.equals(toArray(), Iterables.toCollection((Iterable<?>) obj).toArray());
        return false;
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}