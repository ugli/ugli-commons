package se.ugli.commons.collection.mutable;

import static java.util.Arrays.asList;
import static java.util.Collections.synchronizedList;
import static se.ugli.commons.collection.mutable.Collections.toCollection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListFactory {

	public static <E> List<E> arrayList(final E... values) {
		return new ArrayList<E>(asList(values));
	}

	public static <E> List<E> arrayList(final Iterable<E> iterable) {
		return new ArrayList<E>(toCollection(iterable));
	}

	public static <E> List<E> copyOnWriteArrayList(final E... values) {
		return new CopyOnWriteArrayList<E>(asList(values));
	}

	public static <E> List<E> copyOnWriteArrayList(final Iterable<E> iterable) {
		return new CopyOnWriteArrayList<E>(toCollection(iterable));
	}

	public static <E> List<E> linkedList(final E... values) {
		return new LinkedList<E>(asList(values));
	}

	public static <E> List<E> linkedList(final Iterable<E> iterable) {
		return new LinkedList<E>(toCollection(iterable));
	}

	public static <E> Stack<E> stack(final E... values) {
		return stack(asList(values));
	}

	public static <E> Stack<E> stack(final Iterable<E> iterable) {
		final Stack<E> stack = new Stack<E>();
		stack.addAll(toCollection(iterable));
		return stack;
	}

	public static <E> List<E> synchronizedArrayList(final E... values) {
		return synchronizedList(new ArrayList<E>(asList(values)));
	}

	public static <E> List<E> synchronizedArrayList(final Iterable<E> iterable) {
		return synchronizedList(new ArrayList<E>(toCollection(iterable)));
	}

	public static <E> List<E> synchronizedLinkedList(final E... values) {
		return synchronizedList(new LinkedList<E>(asList(values)));
	}

	public static <E> List<E> synchronizedLinkedList(final Iterable<E> iterable) {
		return synchronizedList(new LinkedList<E>(toCollection(iterable)));
	}

}
