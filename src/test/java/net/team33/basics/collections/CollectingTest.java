package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableMap;
import static net.team33.basics.collections.Mapper.apply;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@SuppressWarnings({"ClassWithTooManyMethods", "ProhibitedExceptionCaught", "ConstantNamingConvention", "AssertEqualsBetweenInconvertibleTypes"})
public class CollectingTest {

    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private static final List<String> THREE_STRINGS = asList(C, B, A);
    private static final List<String> TWO_STRINGS_AND_NULL = asList(A, null, C);
    private static final List<?> TWO_STRINGS_AND_OTHER = asList(A, 5, C);
    private static final String D = "D";
    private static final List<String> NO_STRINGS = emptyList();
    private static final Collection<?> COLLECTION_NULL = null;
    private static final Map<String, String> THREE_MAPPINGS = unmodifiableMap(
            apply(new HashMap<String, String>(3))
                    .put(A, B)
                    .put(B, C)
                    .put(C, A)
                    .getCore()
    );
    private static final String NULL = null;

    @Test
    public final void testGet() {
        final Map<?, ?> subject = new TreeMap<>(THREE_MAPPINGS);
        for (final String key : asList(A, B, C, D)) {
            assertEquals(
                    subject.get(key),
                    Collecting.get(subject, key)
            );
        }

        try {
            fail("should fail but returns <"
                    + subject.get(null)
                    + ">");
        } catch (final NullPointerException ignored) {
            assertNull(
                    Collecting.get(subject, null)
            );
        }

        try {
            fail("should fail but returns <"
                    + subject.get(5)
                    + ">");
        } catch (final ClassCastException ignored) {
            assertNull(
                    Collecting.get(subject, 5)
            );
        }
    }

    @Test
    public final void testContainsKey() {
        final Map<?, ?> subject = new TreeMap<>(THREE_MAPPINGS);
        for (final String key : asList(A, B, C, D)) {
            assertEquals(
                    subject.containsKey(key),
                    Collecting.containsKey(subject, key)
            );
        }

        try {
            fail("should fail but returns <"
                    + subject.containsKey(null)
                    + ">");
        } catch (final NullPointerException ignored) {
            assertFalse(
                    Collecting.containsKey(subject, null)
            );
        }

        try {
            fail("should fail but returns <"
                    + subject.containsKey(5)
                    + ">");
        } catch (final ClassCastException ignored) {
            assertFalse(
                    Collecting.containsKey(subject, 5)
            );
        }
    }

    @Test
    public final void testContainsValue() {
        final Map<?, ?> subject = new SpecialMap(THREE_MAPPINGS);
        for (final String value : asList(A, B, C, D)) {
            assertEquals(
                    subject.containsValue(value),
                    Collecting.containsValue(subject, value)
            );
        }

        try {
            fail("should fail but returns <"
                    + subject.containsValue(null)
                    + ">");
        } catch (final NullPointerException ignored) {
            assertFalse(
                    Collecting.containsValue(subject, null)
            );
        }

        try {
            fail("should fail but returns <"
                    + subject.containsValue(5)
                    + ">");
        } catch (final ClassCastException ignored) {
            assertFalse(
                    Collecting.containsValue(subject, 5)
            );
        }
    }

    @Test
    public final void testClear_Map() {
        assertEquals(
                Collections.emptyMap(),
                Collecting.clear(new HashMap<>(THREE_MAPPINGS))
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testClear_Map__Unsupported() {
        fail(
                "Should fail but returns "
                        + Collecting.clear(THREE_MAPPINGS)
        );
    }

    @Test
    public final void testAltAdd() {
        assertEquals(
                new TreeSet<>(THREE_STRINGS),
                Collecting.add(new TreeSet<>(NO_STRINGS), A, B, C)
        );
    }

    @Test
    public final void testAltRemove() {
        assertEquals(
                new TreeSet<>(NO_STRINGS),
                Collecting.removeAlt(new TreeSet<>(THREE_STRINGS), A, null, B, 5, C)
        );
    }

    @Test
    public final void testAltRetain() {
        assertEquals(
                new TreeSet<>(asList(A, C)),
                Collecting.retainAlt(new TreeSet<>(THREE_STRINGS), A, null, 5, C)
        );
    }

    @Test(expected = NullPointerException.class)
    public final void testRemove_null_direct() {
        final Collection<String> subject = new TreeSet<>(THREE_STRINGS);
        subject.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public final void testRemoveAll_null_direct() {
        final Collection<String> subject = new ArrayList<>(TWO_STRINGS_AND_NULL);
        subject.removeAll(new TreeSet<>(THREE_STRINGS));
    }

    @Test(expected = NullPointerException.class)
    public final void testRetainAll_null_direct() {
        final Collection<String> subject = new ArrayList<>(TWO_STRINGS_AND_NULL);
        subject.retainAll(new TreeSet<>(THREE_STRINGS));
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test(expected = ClassCastException.class)
    public final void testRemove_other_direct() {
        final Collection<String> subject = new TreeSet<>(THREE_STRINGS);
        subject.remove(5);
    }

    @Test(expected = ClassCastException.class)
    public final void testRemoveAll_other_direct() {
        final Collection<Object> subject = new ArrayList<Object>(asList(A, 5, C));
        subject.removeAll(new TreeSet<>(THREE_STRINGS));
    }

    @Test(expected = ClassCastException.class)
    public final void testRetainAll_other_direct() {
        final Collection<Object> subject = new ArrayList<Object>(asList(A, 5, C));
        subject.retainAll(new TreeSet<>(THREE_STRINGS));
    }

    @Test
    public final void testRemove_null_indirect() {
        final TreeSet<String> subject = new TreeSet<>(THREE_STRINGS);
        assertEquals(
                new TreeSet<>(THREE_STRINGS),
                Collecting.removeAlt(subject, NULL)
        );
    }

    @Test
    public final void testRemoveAll_null_indirect() {
        final Collection<String> subject = new ArrayList<>(TWO_STRINGS_AND_NULL);
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(
                singletonList(null),
                Collecting.removeAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public final void testRetainAll_null_indirect() {
        final Collection<String> subject = new ArrayList<>(TWO_STRINGS_AND_NULL);
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(
                asList(A, C),
                Collecting.retainAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public final void testRemoveAll_other_indirect() {
        final Collection<Object> subject = new ArrayList<Object>(asList(A, 5, C));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(
                singletonList(5),
                Collecting.removeAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public final void testRetainAll_other_indirect() {
        final Collection<Object> subject = new ArrayList<Object>(asList(A, 5, C));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(
                asList(A, C),
                Collecting.retainAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test(expected = NullPointerException.class)
    public final void testContains_null_direct() {
        final Collection<String> subject = new TreeSet<>(THREE_STRINGS);
        Assert.assertTrue(
                subject.contains(null)
        );
    }

    @Test
    public final void testContains_null_indirect() {
        final Collection<String> subject = new TreeSet<>(THREE_STRINGS);
        assertFalse(
                Collecting.contains(subject, null)
        );
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_NULL_NULL() {
        Collecting.containsAll(COLLECTION_NULL, COLLECTION_NULL);
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_NULL_STUFF() {
        Collecting.containsAll(COLLECTION_NULL, THREE_STRINGS);
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_STUFF_NULL() {
        Collecting.containsAll(THREE_STRINGS, COLLECTION_NULL);
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_STUFF_STUFF_with_Null_direct() {
        new TreeSet<>(THREE_STRINGS).containsAll(TWO_STRINGS_AND_NULL);
    }

    @Test(expected = ClassCastException.class)
    public final void testContainsAll_STUFF_STUFF_with_Other_direct() {
        new TreeSet<>(THREE_STRINGS).containsAll(TWO_STRINGS_AND_OTHER);
    }

    @Test
    public final void testContainsAll_STUFF_STUFF_with_Null_indirect() {
        assertFalse(
                Collecting.containsAlt(new TreeSet<>(THREE_STRINGS), A, null, C)
        );
    }

    @Test
    public final void testContainsAll_STUFF_STUFF_with_Other_indirect() {
        assertFalse(
                Collecting.containsAlt(new TreeSet<>(THREE_STRINGS), A, 5, C)
        );
    }

    @Test
    public final void testClear_Collection() {
        assertEquals(
                new TreeSet<>(NO_STRINGS),
                Collecting.clear(new TreeSet<>(THREE_STRINGS))
        );
    }

    @SuppressWarnings("ProhibitedExceptionThrown")
    private static class SpecialMap extends AbstractMap<String, String> {
        private final Map<String, String> backing;

        private SpecialMap(final Map<String, String> origin) {
            backing = new HashMap<>(origin);
        }

        @Override
        public final boolean containsValue(final Object value) {
            return super.containsValue(String.class.cast(Objects.requireNonNull(value)));
        }

        @Override
        public final Set<Entry<String, String>> entrySet() {
            return backing.entrySet();
        }
    }
}
