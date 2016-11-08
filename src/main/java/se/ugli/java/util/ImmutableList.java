package se.ugli.java.util;

import java.util.Spliterator;
import java.util.Spliterators;

public interface ImmutableList<E> extends ImmutableCollection<E> {

    E get(int index);

    int indexOf(Object o);

    int lastIndexOf(Object o);

    ImmutableList<E> subList(int fromIndex, int toIndex);

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.toArray(), Spliterator.ORDERED);
    }
}
