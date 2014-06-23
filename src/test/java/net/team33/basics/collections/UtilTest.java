package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;

public class UtilTest {

    private static final List<String> THREE_STRINGS = asList("c", "b", "a");
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final String[] SINGLE_STRING_ARRAY = new String[]{"a"};

    @Test
    public void testAdd_Array_Multiple() {
        ArrayList<String> subject = new ArrayList<>(0);
        Assert.assertSame(
                subject,
                Util.addAll(subject, "a", "b", "c")
        );
    }

    @Test
    public void testAdd_Array_Single() {
        ArrayList<String> subject = new ArrayList<>(0);
        Assert.assertSame(
                subject,
                Util.addAll(subject, SINGLE_STRING_ARRAY)
        );
    }

    @Test
    public void testAdd_Array_None() {
        ArrayList<String> subject = new ArrayList<>(0);
        Assert.assertSame(
                subject,
                Util.addAll(subject, EMPTY_STRING_ARRAY)
        );
    }

    @Test(expected = NullPointerException.class)
    public void testRemove_null_direct() {
        final TreeSet<String> subject = new TreeSet<>(THREE_STRINGS);
        subject.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveAll_null_direct() {
        final Collection<String> subject = new ArrayList<>(asList("a", null, "c"));
        subject.removeAll(new TreeSet<>(THREE_STRINGS));
    }

    @Test(expected = NullPointerException.class)
    public void testRetainAll_null_direct() {
        final Collection<String> subject = new ArrayList<>(asList("a", null, "c"));
        subject.retainAll(new TreeSet<>(THREE_STRINGS));
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test(expected = ClassCastException.class)
    public void testRemove_other_direct() {
        final TreeSet<String> subject = new TreeSet<>(THREE_STRINGS);
        subject.remove(5);
    }

    @Test(expected = ClassCastException.class)
    public void testRemoveAll_other_direct() {
        final Collection<Object> subject = new ArrayList<Object>(asList("a", 5, "c"));
        subject.removeAll(new TreeSet<>(THREE_STRINGS));
    }

    @Test(expected = ClassCastException.class)
    public void testRetainAll_other_direct() {
        final Collection<Object> subject = new ArrayList<Object>(asList("a", 5, "c"));
        subject.retainAll(new TreeSet<>(THREE_STRINGS));
    }

    @Test
    public void testRemove_null_indirect() {
        final TreeSet<String> subject = new TreeSet<>(THREE_STRINGS);
        Assert.assertEquals(
                new TreeSet<>(THREE_STRINGS),
                Util.remove(subject, null)
        );
    }

    @Test
    public void testRemoveAll_null_indirect() {
        final Collection<String> subject = new ArrayList<>(asList("a", null, "c"));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                java.util.Collections.singletonList(null),
                Util.removeAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public void testRemove_other_indirect() {
        final TreeSet<String> subject = new TreeSet<>(THREE_STRINGS);
        Assert.assertEquals(
                new TreeSet<>(THREE_STRINGS),
                Util.removeAll(subject, 5)
        );
    }

    @Test
    public void testRetainAll_null_indirect() {
        final Collection<String> subject = new ArrayList<>(asList("a", null, "c"));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                asList("a", "c"),
                Util.retainAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public void testRemoveAll_other_indirect() {
        final Collection<Object> subject = new ArrayList<Object>(asList("a", 5, "c"));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                java.util.Collections.singletonList(5),
                Util.removeAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public void testRetainAll_other_indirect() {
        final Collection<Object> subject = new ArrayList<Object>(asList("a", 5, "c"));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                asList("a", "c"),
                Util.retainAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public void testRetainAll_Array_indirect() {
        final Collection<Object> subject = new ArrayList<Object>(asList("a", 5, "c"));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                asList("a", "c"),
                Util.retainAll(subject, "c", "b", "a")
        );
    }

    @Test(expected = NullPointerException.class)
    public void testContains_null_direct() {
        final Set<String> subject = new TreeSet<>(THREE_STRINGS);
        Assert.assertTrue(
                subject.contains(null)
        );
    }

    @Test
    public void testContains_null_indirect() {
        final Set<String> subject = new TreeSet<>(THREE_STRINGS);
        Assert.assertFalse(
                Util.contains(subject, null)
        );
    }
}
