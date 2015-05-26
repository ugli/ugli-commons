package se.ugli.commons.collection.immutable;

public interface List<E> extends Collection<E> {

	E get(int index);

	int indexOf(Object o);

	int lastIndexOf(Object o);

	List<E> subList(int fromIndex, int toIndex);
}
