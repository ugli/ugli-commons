package se.ugli.commons.collection.mutable;

import java.util.Collection;
import java.util.LinkedList;

class Collections {

	static <E> Collection<E> toCollection(final Iterable<E> iterable) {
		if (iterable instanceof Collection<?>)
			return (Collection<E>) iterable;
		final Collection<E> result = new LinkedList<E>();
		for (final E e : iterable)
			result.add(e);
		return result;
	}

}
