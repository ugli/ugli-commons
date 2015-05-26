package se.ugli.commons.collection.mutable;

import static java.util.Arrays.asList;
import static se.ugli.commons.collection.mutable.Collections.toCollection;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class QueueFactory {

	public static <E> BlockingQueue<E> arrayBlockingQueue(final E... values) {
		return arrayBlockingQueue(asList(values));
	}

	public static <E> BlockingQueue<E> arrayBlockingQueue(final Iterable<E> iterable) {
		final Collection<E> collection = toCollection(iterable);
		return new ArrayBlockingQueue<E>(collection.size(), false, collection);
	}

	public static <E> Deque<E> arrayDeque(final E... values) {
		return new ArrayDeque<E>(asList(values));
	}

	public static <E> Deque<E> arrayDeque(final Iterable<E> iterable) {
		return new ArrayDeque<E>(toCollection(iterable));
	}

	public static <E> Deque<E> concurrentLinkedDeque(final E... values) {
		return new ConcurrentLinkedDeque<E>(asList(values));
	}

	public static <E> Deque<E> concurrentLinkedDeque(final Iterable<E> iterable) {
		return new ConcurrentLinkedDeque<E>(toCollection(iterable));
	}

	public static <E> Queue<E> concurrentLinkedQueue(final E... values) {
		return new ConcurrentLinkedQueue<E>(asList(values));
	}

	public static <E> Queue<E> concurrentLinkedQueue(final Iterable<E> iterable) {
		return new ConcurrentLinkedQueue<E>(toCollection(iterable));
	}

	public static <E> BlockingQueue<E> linkedBlockingQueue(final E... values) {
		return new LinkedBlockingQueue<E>(asList(values));
	}

	public static <E> BlockingQueue<E> linkedBlockingQueue(final Iterable<E> iterable) {
		return new LinkedBlockingQueue<E>(toCollection(iterable));
	}

	public static <E> BlockingQueue<E> priorityBlockingQueue(final E... values) {
		return new PriorityBlockingQueue<E>(asList(values));
	}

	public static <E> BlockingQueue<E> priorityBlockingQueue(final Iterable<E> iterable) {
		return new PriorityBlockingQueue<E>(toCollection(iterable));
	}

	public static <E> BlockingQueue<E> synchronousQueue(final E... values) {
		return synchronousQueue(asList(values));
	}

	public static <E> BlockingQueue<E> synchronousQueue(final Iterable<E> iterable) {
		final SynchronousQueue<E> queue = new SynchronousQueue<E>();
		queue.addAll(toCollection(iterable));
		return queue;
	}

}
