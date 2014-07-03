package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@SuppressWarnings("ClassWithTooManyMethods")
public class UtilTest {

    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private static final List<String> THREE_STRINGS = asList(C, B, A);
    private static final List<String> TWO_STRINGS_AND_NULL = asList(A, null, C);
    private static final List<?> TWO_STRINGS_AND_OTHER = asList(A, 5, C);
    private static final List<String> NO_STRINGS = emptyList();
    private static final Collection<?> COLLECTION_NULL = null;

    @Test
    public final void testAltAdd() {
        Assert.assertEquals(
                new TreeSet<>(THREE_STRINGS),
                Util.Alt.add(new TreeSet<>(NO_STRINGS), A, B, C)
        );
    }

    @Test
    public final void testAltRemove() {
        Assert.assertEquals(
                new TreeSet<>(NO_STRINGS),
                Util.Alt.remove(new TreeSet<>(THREE_STRINGS), A, null, B, 5, C)
        );
    }

    @Test
    public final void testAltRetain() {
        Assert.assertEquals(
                new TreeSet<>(asList(A, C)),
                Util.Alt.retain(new TreeSet<>(THREE_STRINGS), A, null, 5, C)
        );
    }

    @Test
    public final void testAltContains_with_Null() {
        Assert.assertFalse(
                Util.Alt.contains(new TreeSet<>(THREE_STRINGS), A, null, C)
        );
    }

    @Test
    public final void testAltContains_with_Other() {
        Assert.assertFalse(
                Util.Alt.contains(new TreeSet<>(THREE_STRINGS), A, 5, C)
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
        Assert.assertEquals(
                new TreeSet<>(THREE_STRINGS),
                Util.remove(subject, null)
        );
    }

    @Test
    public final void testRemoveAll_null_indirect() {
        final Collection<String> subject = new ArrayList<>(TWO_STRINGS_AND_NULL);
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                singletonList(null),
                Util.removeAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public final void testRetainAll_null_indirect() {
        final Collection<String> subject = new ArrayList<>(TWO_STRINGS_AND_NULL);
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                asList(A, C),
                Util.retainAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public final void testRemoveAll_other_indirect() {
        final Collection<Object> subject = new ArrayList<Object>(asList(A, 5, C));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                singletonList(5),
                Util.removeAll(subject, new TreeSet<>(THREE_STRINGS))
        );
    }

    @Test
    public final void testRetainAll_other_indirect() {
        final Collection<Object> subject = new ArrayList<Object>(asList(A, 5, C));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        Assert.assertEquals(
                asList(A, C),
                Util.retainAll(subject, new TreeSet<>(THREE_STRINGS))
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
        Assert.assertFalse(
                Util.contains(subject, null)
        );
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_NULL_NULL() {
        Util.containsAll(COLLECTION_NULL, COLLECTION_NULL);
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_NULL_STUFF() {
        Util.containsAll(COLLECTION_NULL, THREE_STRINGS);
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_STUFF_NULL() {
        Util.containsAll(THREE_STRINGS, COLLECTION_NULL);
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAll_STUFF_STUFF_with_Null_direct() {
        (new TreeSet<>(THREE_STRINGS)).containsAll(TWO_STRINGS_AND_NULL);
    }

    @Test(expected = ClassCastException.class)
    public final void testContainsAll_STUFF_STUFF_with_Other_direct() {
        (new TreeSet<>(THREE_STRINGS)).containsAll(TWO_STRINGS_AND_OTHER);
    }

    @Test
    public final void testContainsAll_STUFF_STUFF_with_Null_indirect() {
        Assert.assertFalse(
                Util.containsAll(new TreeSet<>(THREE_STRINGS), TWO_STRINGS_AND_NULL)
        );
    }

    @Test
    public final void testContainsAll_STUFF_STUFF_with_Other_indirect() {
        Assert.assertFalse(
                Util.containsAll(new TreeSet<>(THREE_STRINGS), TWO_STRINGS_AND_OTHER)
        );
    }

    @Test
    public final void testClear() {
        Assert.assertEquals(
                new TreeSet<>(NO_STRINGS),
                Util.clear(new TreeSet<>(THREE_STRINGS))
        );
    }

    @SuppressWarnings("UnusedDeclaration")
    private enum AnEnum {
        ABC, DEF, GHI, JKL, MNO
    }
}
