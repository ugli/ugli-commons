package se.ugli.java.util;

import java.util.Spliterator;
import java.util.Spliterators;

public interface ImmutableSet<E> extends ImmutableCollection<E> {

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.toArray(), Spliterator.DISTINCT);
    }
}
