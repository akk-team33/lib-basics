package net.team33.basics.collections;

import com.google.common.base.Function;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class CollectorTest {

    private static final int INT_277 = 277;
    private static final int INT_278 = 278;
    private static final int INT_279 = 279;
    private static final int INT_280 = 280;

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<AnEnum, String> FUNCTION = new Function<AnEnum, String>() {
        @SuppressWarnings("ReturnOfNull")
        @Override
        public String apply(final AnEnum input) {
            return (null == input) ? null : input.name();
        }
    };

    @Test
    public final void testAdd() {
        Assert.assertEquals(
                singleton(INT_278),
                Collector.apply(new HashSet<>(0))
                        .add(INT_278)
                        .getCore()
        );
    }

    @Test
    public final void testAddAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279, INT_280)),
                Collector.apply(new LinkedHashSet<>(0))
                        .addAll(asList(INT_278, INT_279, INT_280))
                        .getCore()
        );
    }

    @Test
    public final void testAddAll_Function() {
        Assert.assertEquals(
                new HashSet<>(asList(AnEnum.ABC.name(), AnEnum.DEF.name(), AnEnum.GHI.name())),
                Collector.apply(new LinkedHashSet<>(0))
                        .addAll(FUNCTION, asList(AnEnum.ABC, AnEnum.DEF, AnEnum.GHI))
                        .getCore()
        );
    }

    @Test
    public final void testAddAlt() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_277, INT_278, INT_279, INT_280)),
                Collector.apply(new TreeSet<>(singletonList(INT_277)))
                        .addAlt(INT_278, INT_279, INT_280)
                        .getCore()
        );
    }

    @Test
    public final void testRemove() {
        Assert.assertEquals(
                singleton(AnEnum.DEF),
                Collector.apply(EnumSet.of(AnEnum.DEF, AnEnum.JKL))
                        .remove(AnEnum.JKL)
                        .getCore()
        );
    }

    @Test
    public final void testRemoveAll() {
        Assert.assertEquals(
                singletonList(INT_280),
                Collector.apply(new LinkedList<>(asList(INT_278, INT_279, INT_280)))
                        .removeAll(asList(INT_278, INT_279))
                        .getCore()
        );
    }

    @Test
    public final void testRemoveAlt() {
        Assert.assertEquals(
                singletonList(INT_280),
                Collector.apply(new LinkedList<>(asList(INT_278, INT_279, INT_280)))
                        .removeAlt(INT_278, INT_279)
                        .getCore()
        );
    }

    @Test
    public final void testRetainAll() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279)),
                Collector.apply(new HashSet<>(asList(INT_278, INT_279, INT_280)))
                        .retainAll(asList(INT_278, INT_279))
                        .getCore()
        );
    }

    @Test
    public final void testRetainAlt() {
        Assert.assertEquals(
                new HashSet<>(asList(INT_278, INT_279)),
                Collector.apply(new TreeSet<>((Comparator<Integer>) null))
                        .addAlt(INT_278, INT_279, INT_280)
                        .retainAlt(INT_278, INT_279)
                        .getCore()
        );
    }

    @Test
    public final void testClear() {
        Assert.assertEquals(
                emptySet(),
                Collector.apply(EnumSet.allOf(AnEnum.class))
                        .clear()
                        .getCore()
        );
    }

    @SuppressWarnings("UnusedDeclaration")
    private enum AnEnum {ABC, DEF, GHI, JKL}
}
