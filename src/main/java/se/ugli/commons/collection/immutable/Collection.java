package se.ugli.commons.collection.immutable;

import java.io.Serializable;

public interface Collection<E> extends Iterable<E>, Serializable {

	boolean contains(Object o);

	boolean containsAll(Collection<?> c);

	boolean containsAll(java.util.Collection<?> c);

	boolean isEmpty();

	int size();

	Object[] toArray();

	<T> T[] toArray(T[] a);

}
