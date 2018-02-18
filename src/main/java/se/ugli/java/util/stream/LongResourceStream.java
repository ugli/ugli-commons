package se.ugli.java.util.stream;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator.OfLong;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LongResourceStream extends ResourceBase implements LongStream {

    private final LongStream stream;

    public LongResourceStream(final LongStream stream, final boolean closeOnTerminalOperation,
            final AutoCloseable... resources) {
        super(stream, closeOnTerminalOperation, resources);
        this.stream = stream;
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public LongStream unordered() {
        return new LongResourceStream(stream.unordered(), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream onClose(final Runnable closeHandler) {
        return new LongResourceStream(stream.onClose(closeHandler), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream filter(final LongPredicate predicate) {
        return new LongResourceStream(stream.filter(predicate), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream map(final LongUnaryOperator mapper) {
        return new LongResourceStream(stream.map(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public <U> Stream<U> mapToObj(final LongFunction<? extends U> mapper) {
        return new ResourceStream<>(stream.mapToObj(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream mapToInt(final LongToIntFunction mapper) {
        return new IntResourceStream(stream.mapToInt(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream mapToDouble(final LongToDoubleFunction mapper) {
        return new DoubleResourceStream(stream.mapToDouble(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream flatMap(final LongFunction<? extends LongStream> mapper) {
        return new LongResourceStream(stream.flatMap(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream distinct() {
        return new LongResourceStream(stream.distinct(), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream sorted() {
        return new LongResourceStream(stream.sorted(), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream peek(final LongConsumer action) {
        return new LongResourceStream(stream.peek(action), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream limit(final long maxSize) {
        return new LongResourceStream(stream.limit(maxSize), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream skip(final long n) {
        return new LongResourceStream(stream.skip(n), closeOnTerminalOperation, resources);
    }

    @Override
    public void forEach(final LongConsumer action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(final LongConsumer action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEachOrdered(action));
    }

    @Override
    public long[] toArray() {
        // This is a terminal operation
        return evalAndclose(() -> stream.toArray());
    }

    @Override
    public long reduce(final long identity, final LongBinaryOperator op) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(identity, op));
    }

    @Override
    public OptionalLong reduce(final LongBinaryOperator op) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(op));
    }

    @Override
    public <R> R collect(final Supplier<R> supplier, final ObjLongConsumer<R> accumulator,
            final BiConsumer<R, R> combiner) {
        // This is a terminal operation
        return evalAndclose(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public long sum() {
        // This is a terminal operation
        return evalAndclose(() -> stream.sum());
    }

    @Override
    public OptionalLong min() {
        // This is a terminal operation
        return evalAndclose(() -> stream.min());
    }

    @Override
    public OptionalLong max() {
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
    public LongSummaryStatistics summaryStatistics() {
        // This is a terminal operation
        return evalAndclose(() -> stream.summaryStatistics());
    }

    @Override
    public boolean anyMatch(final LongPredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(final LongPredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(final LongPredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.noneMatch(predicate));
    }

    @Override
    public OptionalLong findFirst() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findFirst());
    }

    @Override
    public OptionalLong findAny() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findAny());
    }

    @Override
    public DoubleStream asDoubleStream() {
        return new DoubleResourceStream(stream.asDoubleStream(), closeOnTerminalOperation, resources);
    }

    @Override
    public Stream<Long> boxed() {
        return new ResourceStream<>(stream.boxed(), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream sequential() {
        return new LongResourceStream(stream.sequential(), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream parallel() {
        return new LongResourceStream(stream.parallel(), closeOnTerminalOperation, resources);
    }

    @Override
    public OfLong iterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.iterator());
    }

    @Override
    public java.util.Spliterator.OfLong spliterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.spliterator());
    }

}
