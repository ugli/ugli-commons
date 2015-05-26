package se.ugli.commons.collection.mutable;

import static java.util.Arrays.asList;
import static java.util.Collections.synchronizedSet;
import static java.util.Collections.synchronizedSortedSet;
import static se.ugli.commons.collection.mutable.Collections.toCollection;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetFactory {

	public static <E> NavigableSet<E> concurrentSkipListSet(final E... values) {
		return new ConcurrentSkipListSet<E>(asList(values));
	}

	public static <E> NavigableSet<E> concurrentSkipListSet(final Iterable<E> iterable) {
		return new ConcurrentSkipListSet<E>(toCollection(iterable));
	}

	public static <E> Set<E> copyOnWriteArraySet(final E... values) {
		return new CopyOnWriteArraySet<E>(asList(values));
	}

	public static <E> Set<E> copyOnWriteArraySet(final Iterable<E> iterable) {
		return new CopyOnWriteArraySet<E>(toCollection(iterable));
	}

	public static <E> Set<E> hashSet(final E... values) {
		return new HashSet<E>(asList(values));
	}

	public static <E> Set<E> hashSet(final Iterable<E> iterable) {
		return new HashSet<E>(toCollection(iterable));
	}

	public static <E> Set<E> linkedHashSet(final E... values) {
		return new LinkedHashSet<E>(asList(values));
	}

	public static <E> Set<E> linkedHashSet(final Iterable<E> iterable) {
		return new LinkedHashSet<E>(toCollection(iterable));
	}

	public static <E> Set<E> synchronizedHashSet(final E... values) {
		return synchronizedSet(new HashSet<E>(asList(values)));
	}

	public static <E> Set<E> synchronizedHashSet(final Iterable<E> iterable) {
		return synchronizedSet(new HashSet<E>(toCollection(iterable)));
	}

	public static <E> SortedSet<E> synchronizedTreeSet(final E... values) {
		return synchronizedSortedSet(new TreeSet<E>(asList(values)));
	}

	public static <E> SortedSet<E> synchronizedTreeSet(final Iterable<E> iterable) {
		return synchronizedSortedSet(new TreeSet<E>(toCollection(iterable)));
	}

	public static <E> NavigableSet<E> treeSet(final E... values) {
		return new TreeSet<E>(asList(values));
	}

	public static <E> NavigableSet<E> treeSet(final Iterable<E> iterable) {
		return new TreeSet<E>(toCollection(iterable));
	}

}
