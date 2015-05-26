package se.ugli.commons.collection.immutable;

import java.util.Iterator;

import se.ugli.commons.collection.mutable.ListFactory;

public class ImmutableCollection<E> implements Collection<E> {

	private static final long serialVersionUID = 3160744151592475804L;

	private final java.util.Collection<E> collectionImpl;

	public ImmutableCollection(final java.util.Collection<E> collectionImpl) {
		this.collectionImpl = collectionImpl;
	}

	@Override
	public boolean contains(final Object o) {
		return collectionImpl.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		if (c instanceof ImmutableCollection<?>)
			return collectionImpl.containsAll(((ImmutableCollection<?>) c).collectionImpl);
		return collectionImpl.containsAll(ListFactory.arrayList(c));
	}

	@Override
	public boolean containsAll(final java.util.Collection<?> c) {
		return collectionImpl.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return collectionImpl.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return collectionImpl.iterator();
	}

	@Override
	public int size() {
		return collectionImpl.size();
	}

	@Override
	public Object[] toArray() {
		return collectionImpl.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return collectionImpl.toArray(a);
	}
}
