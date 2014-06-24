package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.EnumSet.allOf;

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
    private static final String HERE = "here";
    private static final String ARE = "are";
    private static final String SOME = "some";
    private static final String SHORT = "short";
    private static final String STRINGS = "strings";
    private static final String WITH = "with";
    private static final String DUPLICATE = "duplicate";
    private static final List<String> ORIGINAL_LIST
            = asList(HERE, ARE, SOME, SHORT, STRINGS, WITH, SOME, DUPLICATE, STRINGS);
    private static final String SHOULD_FAIL_BUT_RETURNS = "should fail but returns <%s>";

    private static <E> void testFinalCopy(final List<E> originalList) {
        testFinalCopy(originalList, Util.finalCopy(originalList));
    }

    private static <E extends Enum<E>> void testFinalCopy(final EnumSet<E> originalSet) {
        testFinalCopy(originalSet, Util.finalCopy(originalSet));
    }

    private static <E> void testFinalCopy(final Set<E> originalSet) {
        testFinalCopy(originalSet, Util.finalCopy(originalSet));
    }

    private static <E> void testFinalList(final Collection<E> original) {
        testFinalCopy(new ArrayList<>(original), Util.finalList(original));
    }

    private static <E> void testFinalSet(final Collection<E> original) {
        testFinalCopy(new LinkedHashSet<>(original), Util.finalSet(original));
    }

    private static <E, C extends Collection<E>> void testFinalCopy(final C original, final C finalCopy) {
        Assert.assertEquals(
                original,
                finalCopy
        );
        Assert.assertEquals(
                finalCopy,
                original
        );
        Assert.assertEquals(
                new ArrayList<>(original),
                new ArrayList<>(finalCopy)
        );
    }

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

    @Test(expected = UnsupportedOperationException.class)
    public final void testFinalCopy_Empty_removeAll() {
        Assert.fail(
                String.format(
                        SHOULD_FAIL_BUT_RETURNS,
                        Util.finalCopy(Collections.emptySet()).removeAll(THREE_STRINGS))
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testFinalCopy_notEmpty_removeAll() {
        Assert.fail(
                String.format(
                        SHOULD_FAIL_BUT_RETURNS,
                        Util.finalCopy(new TreeSet<Object>(THREE_STRINGS)).removeAll(THREE_STRINGS))
        );
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public final void testFinalCopy() {
        testFinalCopy(new HashSet<>(ORIGINAL_LIST));
        testFinalCopy(new LinkedHashSet<>(ORIGINAL_LIST));
        testFinalCopy(new TreeSet<>(ORIGINAL_LIST));
        testFinalCopy(allOf(AnEnum.class));
        testFinalCopy(asList(A, B, null, C, B, C));
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public final void testFinalList() {
        testFinalList(new HashSet<>(ORIGINAL_LIST));
        testFinalList(new LinkedHashSet<>(ORIGINAL_LIST));
        testFinalList(new TreeSet<>(ORIGINAL_LIST));
        testFinalList(allOf(AnEnum.class));
        testFinalList(asList(A, B, null, C, B, C));
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @Test
    public final void testFinalSet() {
        testFinalSet(new HashSet<>(ORIGINAL_LIST));
        testFinalSet(new LinkedHashSet<>(ORIGINAL_LIST));
        testFinalSet(new TreeSet<>(ORIGINAL_LIST));
        testFinalSet(allOf(AnEnum.class));
        testFinalSet(asList(A, B, null, C, B, C));
    }

    @SuppressWarnings("UnusedDeclaration")
    private enum AnEnum {
        ABC, DEF, GHI, JKL, MNO
    }
}
