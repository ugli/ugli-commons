package se.ugli.commons;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class Option<T> implements Serializable {

	private static final long serialVersionUID = 5493989609477736170L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Option<T> apply(final T value) {
		return new Option(value);
	}

	public static <T> Option<T> none() {
		return apply(null);
	}

	private final T value;

	private Option(final T value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final Option<?> that = (Option<?>) obj;
		return this.value == null && that.value == null || this.value != null && this.value.equals(that.value);
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

	@Override
	public int hashCode() {
		return 31 * 1 + (value == null ? 0 : value.hashCode());
	}

	public boolean isDefined() {
		return value != null;
	}

	@Override
	public String toString() {
		return "Option [value=" + value + "]";
	}

}
