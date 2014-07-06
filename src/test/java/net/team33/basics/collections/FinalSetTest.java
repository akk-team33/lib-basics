package net.team33.basics.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SuspiciousMethodCalls")
public class FinalSetTest {

    private static final List<String> ELEMENTS = newList(40000);
    private static final List<Object> SAMPLES = FinalList.from(
            0, -1, 94, -519, 5783, -15783, 784512,
            ELEMENTS.get(0).hashCode(), ELEMENTS.get(33).hashCode(), ELEMENTS.get(555).hashCode(),
            ELEMENTS.get(1), ELEMENTS.get(22), ELEMENTS.get(333), ELEMENTS.get(4444),
            "a string", new Object(), new Date(), null);

    private static List<String> newList(final int size) {
        final Random random = new Random();
        final Collection<String> result = new ArrayList<>(size);
        while (size > result.size()) {
            result.add(newString(random));
        }
        return FinalList.from(result);
    }

    private static String newString(final Random random) {
        final int length = random.nextInt(24);
        final char[] chars = new char[length];
        for (int i = 0; i < length; ++i) {
            //noinspection NumericCastThatLosesPrecision
            chars[i] = (char) (random.nextInt(96) + 32);
        }
        return new String(chars);
    }

    private static long timeContains(final Collection<?> subject) {
        final long time0 = System.nanoTime();
        for (final Object sample : SAMPLES) {
            Collecting.contains(subject, sample);
        }
        final long timeX = System.nanoTime();
        return timeX - time0;
    }

    @Test
    public final void testContains() {
        final HashSet<String> hashSet = new HashSet<>(ELEMENTS);
        final FinalSet<String> finalSet = FinalSet.from(ELEMENTS);
        for (final Object sample : SAMPLES) {
            assertEquals(
                    hashSet.contains(sample),
                    finalSet.contains(sample)
            );
        }
    }

    @Test
    public final void testContains_byArrayList() {
        assertEquals(0, timeContains(new ArrayList<>(ELEMENTS)));
    }

    @Test
    public final void testContains_byLinkedList() {
        assertEquals(0, timeContains(new LinkedList<>(ELEMENTS)));
    }

    @Test
    public final void testContains_byLinkedHashSet() {
        assertEquals(0, timeContains(new LinkedHashSet<>(ELEMENTS)));
    }

    @Test
    public final void testContains_byHashSet() {
        assertEquals(0, timeContains(new HashSet<>(ELEMENTS)));
    }

    @Test
    public final void testContains_byTreeSet() {
        assertEquals(0, timeContains(new TreeSet<>(ELEMENTS)));
    }

    @Test
    public final void testContains_byFinalSet() {
        assertEquals(0, timeContains(FinalSet.from(ELEMENTS)));
    }
}
