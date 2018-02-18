package se.ugli.java.util.stream;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator.OfDouble;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class DoubleResourceStream extends ResourceBase implements DoubleStream {

    private final DoubleStream stream;

    public DoubleResourceStream(final DoubleStream stream, final boolean closeOnTerminalOperation,
            final AutoCloseable... resources) {
        super(stream, closeOnTerminalOperation, resources);
        this.stream = stream;
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public DoubleStream unordered() {
        return new DoubleResourceStream(stream.unordered(), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream onClose(final Runnable closeHandler) {
        return new DoubleResourceStream(stream.onClose(closeHandler), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream filter(final DoublePredicate predicate) {
        return new DoubleResourceStream(stream.filter(predicate), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream map(final DoubleUnaryOperator mapper) {
        return new DoubleResourceStream(stream.map(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public <U> Stream<U> mapToObj(final DoubleFunction<? extends U> mapper) {
        return new ResourceStream<>(stream.mapToObj(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public IntStream mapToInt(final DoubleToIntFunction mapper) {
        return new IntResourceStream(stream.mapToInt(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public LongStream mapToLong(final DoubleToLongFunction mapper) {
        return new LongResourceStream(stream.mapToLong(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream flatMap(final DoubleFunction<? extends DoubleStream> mapper) {
        return new DoubleResourceStream(stream.flatMap(mapper), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream distinct() {
        return new DoubleResourceStream(stream.distinct(), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream sorted() {
        return new DoubleResourceStream(stream.sorted(), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream peek(final DoubleConsumer action) {
        return new DoubleResourceStream(stream.peek(action), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream limit(final long maxSize) {
        return new DoubleResourceStream(stream.limit(maxSize), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream skip(final long n) {
        return new DoubleResourceStream(stream.skip(n), closeOnTerminalOperation, resources);
    }

    @Override
    public void forEach(final DoubleConsumer action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(final DoubleConsumer action) {
        // This is a terminal operation
        executeAndClose(() -> stream.forEachOrdered(action));
    }

    @Override
    public double[] toArray() {
        // This is a terminal operation
        return evalAndclose(() -> stream.toArray());
    }

    @Override
    public double reduce(final double identity, final DoubleBinaryOperator op) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(identity, op));
    }

    @Override
    public OptionalDouble reduce(final DoubleBinaryOperator op) {
        // This is a terminal operation
        return evalAndclose(() -> stream.reduce(op));
    }

    @Override
    public <R> R collect(final Supplier<R> supplier, final ObjDoubleConsumer<R> accumulator,
            final BiConsumer<R, R> combiner) {
        // This is a terminal operation
        return evalAndclose(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public double sum() {
        // This is a terminal operation
        return evalAndclose(() -> stream.sum());
    }

    @Override
    public OptionalDouble min() {
        // This is a terminal operation
        return evalAndclose(() -> stream.min());
    }

    @Override
    public OptionalDouble max() {
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
    public DoubleSummaryStatistics summaryStatistics() {
        // This is a terminal operation
        return evalAndclose(() -> stream.summaryStatistics());
    }

    @Override
    public boolean anyMatch(final DoublePredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(final DoublePredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(final DoublePredicate predicate) {
        // This is a terminal operation
        return evalAndclose(() -> stream.noneMatch(predicate));
    }

    @Override
    public OptionalDouble findFirst() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findFirst());
    }

    @Override
    public OptionalDouble findAny() {
        // This is a terminal operation
        return evalAndclose(() -> stream.findAny());
    }

    @Override
    public Stream<Double> boxed() {
        return new ResourceStream<>(stream.boxed(), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream sequential() {
        return new DoubleResourceStream(stream.sequential(), closeOnTerminalOperation, resources);
    }

    @Override
    public DoubleStream parallel() {
        return new DoubleResourceStream(stream.parallel(), closeOnTerminalOperation, resources);
    }

    @Override
    public OfDouble iterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.iterator());
    }

    @Override
    public java.util.Spliterator.OfDouble spliterator() {
        // This is a terminal operation
        return evalAndclose(() -> stream.spliterator());
    }

}
