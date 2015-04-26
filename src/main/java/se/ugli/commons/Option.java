package se.ugli.commons;

import java.util.NoSuchElementException;

public class Option<T> extends ValueObject<T> {

	private static final long serialVersionUID = 5493989609477736170L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Option<T> apply(final T value) {
		return new Option(value);
	}

	public static <T> Option<T> none() {
		return apply(null);
	}

	private Option(final T value) {
		super(value);
	}

	public T get() {
		if (isDefined())
			return value;
		throw new NoSuchElementException();
	}

	public T getOrElse(final T defaultValue) {
		if (isDefined())
			return value;
		return defaultValue;
	}

	public T getOrNull() {
		return getOrElse(null);
	}

	public boolean isDefined() {
		return value != null;
	}

}
