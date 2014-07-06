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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SuspiciousMethodCalls")
public class FinalSetTest {

    private static final int SIZE = 100000;
    private static final List<String> ELEMENTS = newList(SIZE);
    private static final List<Object> SAMPLES = FinalList.from(
            0, -1, 94, -519, 5783, -15783, 784512, Integer.MAX_VALUE, Integer.MIN_VALUE,
            ELEMENTS.get(0).hashCode(), ELEMENTS.get(23).hashCode(), ELEMENTS.get(358).hashCode(),
            ELEMENTS.get(4352).hashCode(), ELEMENTS.get(54758).hashCode(), ELEMENTS.get(SIZE - 1).hashCode(),
            ELEMENTS.get(1), ELEMENTS.get(22), ELEMENTS.get(333), ELEMENTS.get(4444), ELEMENTS.get(55555),
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
    public final void testContains_specials() {
        assertFalse("Empty set should not contain 5", FinalSet.from().contains(5));
        assertFalse("Empty set should not contain <null>", FinalSet.from().contains(null));
        assertFalse("Set of 3 should not contain 5", FinalSet.from(3).contains(5));
        assertFalse("Set of 0 should not contain <null>", FinalSet.from(0).contains(null));
        assertTrue("Set of 5 should contain 5", FinalSet.from(5).contains(5));
        assertFalse("Set of 5 should contain \"5\"", FinalSet.from(5).contains("5"));
        assertTrue("Set of <null> should contain <null>", FinalSet.from((Integer) null).contains(null));
        assertFalse("Set of <null> should not contain 0", FinalSet.from((Integer) null).contains(0));
    }

    @Test
    public final void testContains() {
        final Collection<String> hashSet = new HashSet<>(ELEMENTS);
        final Collection<Object> hashSetContains = new HashSet<>(0);
        final Collection<String> finalSet = FinalSet.from(ELEMENTS);
        final Collection<Object> finalSetContains = new HashSet<>(0);
        for (final Object sample : SAMPLES) {
            if (hashSet.contains(sample)) {
                hashSetContains.add(sample);
            }
            if (finalSet.contains(sample)) {
                finalSetContains.add(sample);
            }
        }
        assertEquals(
                hashSetContains,
                finalSetContains
        );
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
