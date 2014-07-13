package net.team33.basics.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static net.team33.basics.collections.IndexTrial.ELEMENTS;
import static net.team33.basics.collections.IndexTrial.IndexList;
import static net.team33.basics.collections.IndexTrial.SAMPLES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SuspiciousMethodCalls")
public class IndexTest {

    @Test
    public final void testContains_specials() {
        assertFalse("Empty set should not contain 5", IndexList.from().contains(5));
        assertFalse("Empty set should not contain <null>", IndexList.from().contains(null));
        assertFalse("Set of 3 should not contain 5", IndexList.from(3).contains(5));
        assertFalse("Set of 0 should not contain <null>", IndexList.from(0).contains(null));
        assertTrue("Set of 5 should contain 5", IndexList.from(5).contains(5));
        assertFalse("Set of 5 should contain \"5\"", IndexList.from(5).contains("5"));
        assertTrue("Set of <null> should contain <null>", IndexList.from((Integer) null).contains(null));
        assertFalse("Set of <null> should not contain 0", IndexList.from((Integer) null).contains(0));
    }

    @Test
    public final void testIndexOf() {
        final List<String> arrayList = new ArrayList<>(ELEMENTS);
        final IndexList<String> indexList = IndexList.from(ELEMENTS);
        for (final Object sample : SAMPLES) {
            assertEquals(
                    String.format("for sample <%s>", sample),
                    arrayList.indexOf(sample),
                    indexList.indexOf(sample)
            );
        }
    }

    @Test
    public final void testLastIndexOf() {
        final List<String> arrayList = new ArrayList<>(ELEMENTS);
        final IndexList<String> indexList = IndexList.from(ELEMENTS);
        for (final Object sample : SAMPLES) {
            final int expected = arrayList.lastIndexOf(sample);
            final String message = String.format("for sample <%s> -> <%d>", sample, expected);
            //System.out.println(message);
            assertEquals(message, expected, indexList.lastIndexOf(sample));
        }
    }

    @Test
    public final void testContains() {
        final Collection<String> hashSet = new HashSet<>(ELEMENTS);
        final Collection<Object> hashSetContains = new HashSet<>(0);
        final Collection<String> indexList = IndexList.from(ELEMENTS);
        final Collection<Object> indexListContains = new HashSet<>(0);
        for (final Object sample : SAMPLES) {
            if (hashSet.contains(sample)) {
                hashSetContains.add(sample);
            }
            if (indexList.contains(sample)) {
                indexListContains.add(sample);
            }
        }
        assertEquals(
                hashSetContains,
                indexListContains
        );
    }
}
