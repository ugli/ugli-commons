package se.ugli.java.util.stream;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ResourceStream<T> extends ResourceBase implements Stream<T> {

    private final Stream<T> stream;

    public ResourceStream(final Stream<T> stream, final boolean closeOnTerminalOperation,
            final AutoCloseable... resources) {
        super(stream, closeOnTerminalOperation, resources);
        this.stream = stream;
    }

    @Override
    public Iterator<T> iterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.iterator());
    }

    @Override
    public Spliterator<T> spliterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.spliterator());
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return new ResourceStream<>(stream.sequential(), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> parallel() {
        return new ResourceStream<>(stream.parallel(), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> unordered() {
        return new ResourceStream<>(stream.unordered(), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> onClose(final Runnable closeHandler) {
        return new ResourceStream<>(stream.onClose(closeHandler), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> filter(final Predicate<? super T> predicate) {
        return new ResourceStream<>(stream.filter(predicate), closeOnTerminalOperation, resources);
    }

    @Override
    public <R> Stream<R> map(final Function<? super T, ? extends R> mapper) {
        return new ResourceStream<>(stream.map(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream mapToInt(final ToIntFunction<? super T> mapper) {
        return new IntResourceStream(stream.mapToInt(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream mapToLong(final ToLongFunction<? super T> mapper) {
        return new LongResourceStream(stream.mapToLong(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream mapToDouble(final ToDoubleFunction<? super T> mapper) {
        return new DoubleResourceStream(stream.mapToDouble(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public <R> Stream<R> flatMap(final Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new ResourceStream<>(stream.flatMap(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream flatMapToInt(final Function<? super T, ? extends IntStream> mapper) {
        return new IntResourceStream(stream.flatMapToInt(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream flatMapToLong(final Function<? super T, ? extends LongStream> mapper) {
        return new LongResourceStream(stream.flatMapToLong(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream flatMapToDouble(final Function<? super T, ? extends DoubleStream> mapper) {
        return new DoubleResourceStream(stream.flatMapToDouble(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> distinct() {
        return new ResourceStream<>(stream.distinct(), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> sorted() {
        return new ResourceStream<>(stream.sorted(), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> sorted(final Comparator<? super T> comparator) {
        return new ResourceStream<>(stream.sorted(comparator), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> peek(final Consumer<? super T> action) {
        return new ResourceStream<>(stream.peek(action), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> limit(final long maxSize) {
        return new ResourceStream<>(stream.limit(maxSize), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<T> skip(final long n) {
        return new ResourceStream<>(stream.skip(n), closeOnTerminalOperation, resources);
    }

    @Override
    public void forEach(final Consumer<? super T> action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(final Consumer<? super T> action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEachOrdered(action));
    }

    @Override
    public Object[] toArray() {
        // This is a terminal operation
        return evalAndclose(() -> stream.toArray());
    }

    @Override
    public <A> A[] toArray(final IntFunction<A[]> generator) {
        // This is a terminal operation
        return evalAndclose(() -> stream.toArray(generator));
    }

    @Override
    public T reduce(final T identity, final BinaryOperator<T> accumulator) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(identity, accumulator));
    }

    @Override
    public Optional<T> reduce(final BinaryOperator<T> accumulator) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(accumulator));
    }

    @Override
    public <U> U reduce(final U identity, final BiFunction<U, ? super T, U> accumulator,
            final BinaryOperator<U> combiner) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(identity, accumulator, combiner));
    }

    @Override
    public <R> R collect(final Supplier<R> supplier, final BiConsumer<R, ? super T> accumulator,
            final BiConsumer<R, R> combiner) {
        // This is a terminal operation
        return evalAndclose(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public <R, A> R collect(final Collector<? super T, A, R> collector) {
        // This is a terminal operation
        return evalAndclose(() -> stream.collect(collector));
    }

    @Override
    public Optional<T> min(final Comparator<? super T> comparator) {
        // This is a terminal operation
        return evalAndclose(() -> stream.min(comparator));
    }

    @Override
    public Optional<T> max(final Comparator<? super T> comparator) {
        // This is a terminal operation
        return evalAndclose(() -> stream.max(comparator));
    }

    @Override
    public long count() {
        // This is a terminal operation
        return evalAndclose(() -> stream.count());
    }

    @Override
    public boolean anyMatch(final Predicate<? super T> predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(final Predicate<? super T> predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(final Predicate<? super T> predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.noneMatch(predicate));
    }

    @Override
    public Optional<T> findFirst() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findFirst());
    }

    @Override
    public Optional<T> findAny() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findAny());
    }

}
