package net.team33.basics.collections;

import net.team33.basics.Builder;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class CollectorTest {

    private static final int INT_278 = 278;
    private static final int INT_279 = 279;
    private static final int INT_280 = 280;

    private static <E> Subject<E> subject() {
        return new Subject<>();
    }

    @Test
    public final void testAdd() {
        Assert.assertEquals(
                singleton(INT_278),
                subject()
                        .add(INT_278)
                        .build()
        );
    }

    @Test
    public final void testAddAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279, INT_280)),
                subject()
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .build()
        );
    }

    @Test
    public final void testRemove() {
        Assert.assertEquals(
                singleton(INT_279),
                subject()
                        .addAll(asList(INT_278, INT_279))
                        .remove(INT_278)
                        .build()
        );
    }

    @Test
    public final void testRemoveAll() {
        Assert.assertEquals(
                singleton(INT_280),
                subject()
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .removeAll(asList(INT_278, INT_279))
                        .build()
        );
    }

    @Test
    public final void testRetainAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279)),
                subject()
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .retainAll(asList(INT_278, INT_279))
                        .build()
        );
    }

    @Test
    public final void testClear() {
        Assert.assertEquals(
                emptySet(),
                subject()
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .clear()
                        .build()
        );
    }

    private static class Subject<E> extends Collector<E, Subject<E>> implements Builder<Set<E>> {
        private final Set<E> backing = new HashSet<>(0);

        @Override
        protected final Set<E> getBacking() {
            // intended to be mutable ...
            // noinspection ReturnOfCollectionOrArrayField
            return backing;
        }

        @Override
        public final Set<E> build() {
            return new HashSet<>(backing);
        }
    }
}