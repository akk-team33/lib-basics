package net.team33.basics.collections;

import com.google.common.base.Supplier;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

public class CollectorTest {

    private static final int INT_278 = 278;
    private static final int INT_279 = 279;
    private static final int INT_280 = 280;

    private static <E, R extends Collection<E>> Subject<E, R> subject(final Supplier<R> newResult) {
        return new Subject<>(newResult);
    }

    @Test
    public final void testAdd() {
        Assert.assertEquals(
                singleton(INT_278),
                subject(new SetSupplier<Integer>())
                        .add(INT_278)
                        .build()
        );
    }

    @Test
    public final void testAddAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279, INT_280)),
                subject(new SetSupplier<Integer>())
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .build()
        );
    }

    @Test
    public final void testRemove() {
        Assert.assertEquals(
                singleton(INT_279),
                subject(new SetSupplier<Integer>())
                        .addAll(asList(INT_278, INT_279))
                        .remove(INT_278)
                        .build()
        );
    }

    @Test
    public final void testRemoveAll() {
        Assert.assertEquals(
                singleton(INT_280),
                subject(new SetSupplier<Integer>())
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .removeAll(asList(INT_278, INT_279))
                        .build()
        );
    }

    @Test
    public final void testRetainAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279)),
                subject(new SetSupplier<Integer>())
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .retainAll(asList(INT_278, INT_279))
                        .build()
        );
    }

    @Test
    public final void testClear() {
        Assert.assertEquals(
                emptySet(),
                subject(new SetSupplier<Integer>())
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .clear()
                        .build()
        );
    }

    private static class SetSupplier<E> implements Supplier<Set<E>> {
        @Override
        public final Set<E> get() {
            return new HashSet<>(0);
        }
    }

    private static class Subject<E, R extends Collection<E>> extends Collector<E, R, Subject<E, R>> {
        private Subject(Supplier<R> newResult) {
            super(newResult);
        }
    }
}