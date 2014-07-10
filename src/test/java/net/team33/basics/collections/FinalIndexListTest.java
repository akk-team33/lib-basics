package net.team33.basics.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
public class FinalIndexListTest {

    private static final int SIZE = 100000;
    private static final List<String> ELEMENTS = newList(SIZE);
    private static final char[] CHARS = {
            32, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '@', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static final List<Object> SAMPLES = Arrays.asList(
            0, -1, 94, -519, 5783, -15783, 784512, Integer.MAX_VALUE, Integer.MIN_VALUE,
            ELEMENTS.get(0).hashCode(), ELEMENTS.get(23).hashCode(), ELEMENTS.get(358).hashCode(),
            ELEMENTS.get(4352).hashCode(), ELEMENTS.get(54758).hashCode(), ELEMENTS.get(SIZE - 1).hashCode(),
            ELEMENTS.get(1), ELEMENTS.get(23), ELEMENTS.get(456), ELEMENTS.get(7890), ELEMENTS.get(12345),
            ELEMENTS.get(6), ELEMENTS.get(78), ELEMENTS.get(901), ELEMENTS.get(2345), ELEMENTS.get(6789),
            "another string", new Object(), new Date(), null);

    private static List<String> newList(final int size) {
        final Random random = new Random();
        final Collection<String> result = new ArrayList<>(size);
        while (size > result.size()) {
            result.add(newString(random));
        }
        return FinalList.from(result);
    }

    private static String newString(final Random random) {
        final int length = random.nextInt(18) + 6;
        final char[] chars = new char[length];
        for (int i = 0; i < length; ++i) {
            chars[i] = CHARS[random.nextInt(CHARS.length)];
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
        assertFalse("Empty set should not contain 5", FinalIndexList.from().contains(5));
        assertFalse("Empty set should not contain <null>", FinalIndexList.from().contains(null));
        assertFalse("Set of 3 should not contain 5", FinalIndexList.from(3).contains(5));
        assertFalse("Set of 0 should not contain <null>", FinalIndexList.from(0).contains(null));
        assertTrue("Set of 5 should contain 5", FinalIndexList.from(5).contains(5));
        assertFalse("Set of 5 should contain \"5\"", FinalIndexList.from(5).contains("5"));
        assertTrue("Set of <null> should contain <null>", FinalIndexList.from((Integer) null).contains(null));
        assertFalse("Set of <null> should not contain 0", FinalIndexList.from((Integer) null).contains(0));
    }

    @Test
    public final void testIndexOf() {
        final List<String> arrayList = new ArrayList<>(ELEMENTS);
        final FinalIndexList<String> finalIndexList = FinalIndexList.from(ELEMENTS);
        for (final Object sample : SAMPLES) {
            assertEquals(
                    String.format("for sample <%s>", sample),
                    arrayList.indexOf(sample),
                    finalIndexList.indexOf(sample)
            );
        }
    }

    @Test
    public final void testLastIndexOf() {
        final List<String> arrayList = new ArrayList<>(ELEMENTS);
        final FinalIndexList<String> finalIndexList = FinalIndexList.from(ELEMENTS);
        for (final Object sample : SAMPLES) {
            final int expected = arrayList.lastIndexOf(sample);
            final String message = String.format("for sample <%s> -> <%d>", sample, expected);
            //System.out.println(message);
            assertEquals(message, expected, finalIndexList.lastIndexOf(sample));
        }
    }

    @Test
    public final void testContains() {
        final Collection<String> hashSet = new HashSet<>(ELEMENTS);
        final Collection<Object> hashSetContains = new HashSet<>(0);
        final Collection<String> finalIndexList = FinalIndexList.from(ELEMENTS);
        final Collection<Object> finalIndexListContains = new HashSet<>(0);
        for (final Object sample : SAMPLES) {
            if (hashSet.contains(sample)) {
                hashSetContains.add(sample);
            }
            if (finalIndexList.contains(sample)) {
                finalIndexListContains.add(sample);
            }
        }
        assertEquals(
                hashSetContains,
                finalIndexListContains
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
    public final void testContains_byFinalIndexList() {
        assertEquals(0, timeContains(FinalIndexList.from(ELEMENTS)));
    }
}
