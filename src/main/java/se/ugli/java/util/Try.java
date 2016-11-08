package se.ugli.java.util;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import se.ugli.java.util.function.ThrowableSupplier;

public class Try<T> {

    public final Optional<T> value;
    public final Optional<Throwable> failure;

    private Try(final Optional<T> value, final Optional<Throwable> failure) {
        this.value = value;
        this.failure = failure;
    }

    public boolean isSuccess() {
        return !failure.isPresent();
    }

    public boolean isFailure() {
        return failure.isPresent();
    }

    public T orElse(final T other) {
        return value.isPresent() ? value.get() : other;
    }

    public T orNull() {
        return value.isPresent() ? value.get() : null;
    }

    public T orElseGet(final Supplier<? extends T> other) {
        return value.isPresent() ? value.get() : other.get();
    }

    public T get() {
        return value.get();
    }

    public <U> Try<U> map(final Function<? super T, ? extends U> mapper) {
        requireNonNull(mapper);
        if (value.isPresent())
            return new Try<>(Optional.ofNullable(mapper.apply(value.get())), failure);
        return new Try<>(Optional.empty(), failure);
    }

    public Optional<T> toOptional() {
        return value;
    }

    @Override
    public String toString() {
        return "Try [value=" + value + ", failure=" + failure + "]";
    }

    public static <T> Try<T> apply(final ThrowableSupplier<T> s) {
        try {
            return new Try<>(Optional.ofNullable(s.get()), Optional.empty());
        }
        catch (final Exception t) {
            return new Try<>(Optional.empty(), Optional.of(t));
        }
    }

    public static <T> T runtime(final ThrowableSupplier<T> s,
            final Function<Exception, RuntimeException> failureFactory) {
        try {
            return s.get();
        }
        catch (final Exception t) {
            throw failureFactory.apply(t);
        }
    }

    public static <T> T runtime(final ThrowableSupplier<T> s) {
        return runtime(s, Failure::new);
    }

    public static class Failure extends RuntimeException {

        private static final long serialVersionUID = -6606078570127139612L;

        public Failure(final Exception t) {
            super(t);
        }
    }

}