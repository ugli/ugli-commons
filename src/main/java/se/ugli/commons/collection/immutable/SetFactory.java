package se.ugli.commons.collection.immutable;

public class SetFactory {

	public static <E> Set<E> hashSet(final E... values) {
		return new ImmutableSet<E>(se.ugli.commons.collection.mutable.SetFactory.hashSet(values));
	}

	public static <E> Set<E> hashSet(final Iterable<E> iterable) {
		return new ImmutableSet<E>(se.ugli.commons.collection.mutable.SetFactory.hashSet(iterable));
	}

	public static <E> Set<E> linkedHashSet(final E... values) {
		return new ImmutableSet<E>(se.ugli.commons.collection.mutable.SetFactory.linkedHashSet(values));
	}

	public static <E> Set<E> linkedHashSet(final Iterable<E> iterable) {
		return new ImmutableSet<E>(se.ugli.commons.collection.mutable.SetFactory.linkedHashSet(iterable));
	}

}
