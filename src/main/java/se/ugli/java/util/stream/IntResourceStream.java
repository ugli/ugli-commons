package se.ugli.java.util.stream;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator.OfInt;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class IntResourceStream extends ResourceBase implements IntStream {

    private final IntStream stream;

    public IntResourceStream(final IntStream stream, final boolean closeOnTerminalOperation,
            final AutoCloseable... resources) {
        super(stream, closeOnTerminalOperation, resources);
        this.stream = stream;
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public IntStream unordered() {
        return new IntResourceStream(stream.unordered(), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream onClose(final Runnable closeHandler) {
        return new IntResourceStream(stream.onClose(closeHandler), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream filter(final IntPredicate predicate) {
        return new IntResourceStream(stream.filter(predicate), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream map(final IntUnaryOperator mapper) {
        return new IntResourceStream(stream.map(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public <U> Stream<U> mapToObj(final IntFunction<? extends U> mapper) {
        return new ResourceStream<>(stream.mapToObj(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream mapToLong(final IntToLongFunction mapper) {
        return new LongResourceStream(stream.mapToLong(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream mapToDouble(final IntToDoubleFunction mapper) {
        return new DoubleResourceStream(stream.mapToDouble(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream flatMap(final IntFunction<? extends IntStream> mapper) {
        return new IntResourceStream(stream.flatMap(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream distinct() {
        return new IntResourceStream(stream.distinct(), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream sorted() {
        return new IntResourceStream(stream.sorted(), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream peek(final IntConsumer action) {
        return new IntResourceStream(stream.peek(action), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream limit(final long maxSize) {
        return new IntResourceStream(stream.limit(maxSize), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream skip(final long n) {
        return new IntResourceStream(stream.skip(n), closeOnTerminalOperation, resources);
    }

    @Override
    public void forEach(final IntConsumer action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(final IntConsumer action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEachOrdered(action));
    }

    @Override
    public int[] toArray() {
        // This is a terminal operation
        return evalAndclose(() -> stream.toArray());
    }

    @Override
    public int reduce(final int identity, final IntBinaryOperator op) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(identity, op));
    }

    @Override
    public OptionalInt reduce(final IntBinaryOperator op) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(op));
    }

    @Override
    public <R> R collect(final Supplier<R> supplier, final ObjIntConsumer<R> accumulator,
            final BiConsumer<R, R> combiner) {
        // This is a terminal operation
        return evalAndclose(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public int sum() {
        // This is a terminal operation
        return evalAndclose(() -> stream.sum());
    }

    @Override
    public OptionalInt min() {
        // This is a terminal operation
        return evalAndclose(() -> stream.min());
    }

    @Override
    public OptionalInt max() {
        // This is a terminal operation
        return evalAndclose(() -> stream.max());
    }

    @Override
    public long count() {
        // This is a terminal operation
        return evalAndclose(() -> stream.count());
    }

    @Override
    public OptionalDouble average() {
        // This is a terminal operation
        return evalAndclose(() -> stream.average());
    }

    @Override
    public IntSummaryStatistics summaryStatistics() {
        // This is a terminal operation
        return evalAndclose(() -> stream.summaryStatistics());
    }

    @Override
    public boolean anyMatch(final IntPredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(final IntPredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(final IntPredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.noneMatch(predicate));
    }

    @Override
    public OptionalInt findFirst() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findFirst());
    }

    @Override
    public OptionalInt findAny() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findAny());
    }

    @Override
    public LongStream asLongStream() {
        return new LongResourceStream(stream.asLongStream(), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream asDoubleStream() {
        return new DoubleResourceStream(stream.asDoubleStream(), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<Integer> boxed() {
        return new ResourceStream<>(stream.boxed(), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream sequential() {
        return new IntResourceStream(stream.sequential(), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream parallel() {
        return new IntResourceStream(stream.parallel(), closeOnTerminalOperation, resources);
    }

    @Override
    public OfInt iterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.iterator());
    }

    @Override
    public java.util.Spliterator.OfInt spliterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.spliterator());
    }

}
