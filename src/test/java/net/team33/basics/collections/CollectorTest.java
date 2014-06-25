package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class CollectorTest {

    private static final int INT_278 = 278;
    private static final int INT_279 = 279;
    private static final int INT_280 = 280;

    @Test
    public final void testAdd() {
        Assert.assertEquals(
                singleton(INT_278),
                Collector.hashSet()
                        .add(INT_278)
                        .getSubject()
        );
    }

    @Test
    public final void testAddAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279, INT_280)),
                Collector.linkedHashSet()
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .getSubject()
        );
    }

    @Test
    public final void testAddAlt() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279, INT_280)),
                Collector.treeSet((Comparator<Integer>) null)
                        .alt.add(INT_278, INT_279, INT_280)
                        .getSubject()
        );
    }

    @Test
    public final void testRemove() {
        Assert.assertEquals(
                singleton(AnEnum.DEF),
                Collector.enumSet(EnumSet.of(AnEnum.DEF, AnEnum.JKL))
                        .remove(AnEnum.JKL)
                        .getSubject()
        );
    }

    @Test
    public final void testRemoveAll() {
        Assert.assertEquals(
                singletonList(INT_280),
                Collector.arrayList(asList(INT_278, INT_279, INT_280))
                        .removeAll(asList(INT_278, INT_279))
                        .getSubject()
        );
    }

    @Test
    public final void testRemoveAlt() {
        Assert.assertEquals(
                singletonList(INT_280),
                Collector.linkedList(asList(INT_278, INT_279, INT_280))
                        .alt.remove(INT_278, INT_279)
                        .getSubject()
        );
    }

    @Test
    public final void testRetainAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279)),
                Collector.hashSet(asList(INT_278, INT_279, INT_280))
                        .retainAll(asList(INT_278, INT_279))
                        .getSubject()
        );
    }

    @Test
    public final void testRetainAlt() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279)),
                Collector.treeSet((Comparator<Integer>) null)
                        .alt.add(INT_278, INT_279, INT_280)
                        .alt.retain(INT_278, INT_279)
                        .getSubject()
        );
    }

    @Test
    public final void testClear() {
        Assert.assertEquals(
                emptySet(),
                Collector.enumSet(asList(AnEnum.values()))
                        .clear()
                        .getSubject()
        );
    }

    private enum AnEnum {ABC, DEF, GHI, JKL}
}