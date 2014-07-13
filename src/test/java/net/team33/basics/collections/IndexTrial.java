package net.team33.basics.collections;

import org.junit.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"SuspiciousMethodCalls", "JUnitTestClassNamingConvention"})
public class IndexTrial {

    static final int SIZE;
    static final List<String> ELEMENTS;
    static final char[] CHARS;
    static final List<Object> SAMPLES;

    static {
        SIZE = 100000;
        CHARS = "123456789 -@*. abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        ELEMENTS = newList(SIZE);
        //noinspection PointlessArithmeticExpression
        SAMPLES = asList(
                0, Integer.MAX_VALUE, Integer.MIN_VALUE,

                ELEMENTS.get(0).hashCode(),
                ELEMENTS.get((SIZE * 1) / 7).hashCode(), ELEMENTS.get((SIZE * 2) / 7).hashCode(),
                ELEMENTS.get((SIZE * 3) / 7).hashCode(), ELEMENTS.get((SIZE * 4) / 7).hashCode(),
                ELEMENTS.get((SIZE * 5) / 7).hashCode(), ELEMENTS.get((SIZE * 6) / 7).hashCode(),
                ELEMENTS.get(SIZE - 1).hashCode(),

                ELEMENTS.get(0),
                ELEMENTS.get((SIZE * 1) / 9), ELEMENTS.get((SIZE * 2) / 9), ELEMENTS.get((SIZE * 3) / 9),
                ELEMENTS.get((SIZE * 4) / 9), ELEMENTS.get((SIZE * 5) / 9), ELEMENTS.get((SIZE * 6) / 9),
                ELEMENTS.get((SIZE * 7) / 9), ELEMENTS.get((SIZE * 8) / 9), ELEMENTS.get(SIZE - 1),

                "another string", new Object(), new Date(), null);
    }

    private static List<String> newList(final int size) {
        final Random random = new Random();
        final List<String> result = new ArrayList<>(size);
        result.add("");
        for (int len = 1, num = 5; num < size; ++len, num *= 5) {
            while (num > result.size()) {
                result.add(newString(random, len + random.nextInt(len)));
            }
        }
        while (size > result.size()) {
            result.add(result.get(random.nextInt(result.size())));
        }
        return result;
    }

    private static String newString(final Random random, final int length) {
        final char[] chars = new char[length];
        for (int i = 0; i < length; ++i) {
            chars[i] = CHARS[random.nextInt(CHARS.length)];
        }
        return new String(chars);
    }

    private static long timeContains(final Collection<?> subject) {
        //noinspection CallToSystemGC
        System.gc();
        final long time0 = System.nanoTime();
        for (final Object sample : SAMPLES) {
            Collecting.contains(subject, sample);
        }
        final long timeX = System.nanoTime();
        return timeX - time0;
    }

    @Test
    public final void testPerformance_00_byElements() {
        assertEquals("subject.size() == " + ELEMENTS.size(), 0, timeContains(ELEMENTS));
    }

    @Test
    public final void testPerformance_01_byArrayList() {
        final ArrayList<String> subject = new ArrayList<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testPerformance_02_byLinkedList() {
        final LinkedList<String> subject = new LinkedList<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testPerformance_03_byHashSet() {
        final HashSet<String> subject = new HashSet<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testPerformance_04_byLinkedHashSet() {
        final LinkedHashSet<String> subject = new LinkedHashSet<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testPerformance_05_byTreeSet() {
        final TreeSet<String> subject = new TreeSet<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testPerformance_06_byIndexList() {
        final IndexList<String> subject = IndexList.from(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "RefusedBequest"})
    static class IndexList<E> extends AbstractList<E> {

        private final List<E> backing;
        private final Index<E> index;

        IndexList(final List<E> backing) {
            this.backing = backing;
            this.index = new Index<>(backing);
        }

        @SuppressWarnings("OverloadedVarargsMethod")
        @SafeVarargs
        static <E> IndexList<E> from(final E... values) {
            return from(asList(values));
        }

        static <E> IndexList<E> from(final List<E> backing) {
            return new IndexList<>(backing);
        }

        @SuppressWarnings("ParameterHidesMemberVariable")
        @Override
        public final E get(final int index) {
            return backing.get(index);
        }

        @Override
        public final int indexOf(final Object o) {
            return index.first(o);
        }

        @Override
        public final int lastIndexOf(final Object o) {
            return index.last(o);
        }

        @Override
        public final boolean contains(final Object o) {
            return index.contains(o);
        }

        @Override
        public final int size() {
            return backing.size();
        }
    }
}
