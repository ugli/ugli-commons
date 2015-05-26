package se.ugli.commons.collection.immutable;

public class ListFactory {

	public static <E> List<E> arrayList(final E... values) {
		return new ImmutableList<E>(se.ugli.commons.collection.mutable.ListFactory.arrayList(values));
	}

	public static <E> List<E> arrayList(final Iterable<E> iterable) {
		return new ImmutableList<E>(se.ugli.commons.collection.mutable.ListFactory.arrayList(iterable));
	}

	public static <E> List<E> linkedList(final E... values) {
		return new ImmutableList<E>(se.ugli.commons.collection.mutable.ListFactory.linkedList(values));
	}

	public static <E> List<E> linkedList(final Iterable<E> iterable) {
		return new ImmutableList<E>(se.ugli.commons.collection.mutable.ListFactory.linkedList(iterable));
	}

}
