package net.team33.basics.collections;

import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SuppressWarnings({"JUnitTestMethodWithNoAssertions", "ClassWithTooManyMethods"})
public class FinalListTest {

    private static final String SHOULD_FAIL_BUT_RETURNS = "Should fail but returns <%s>";
    private static final Collection<? extends CharSequence> ORIGIN_01
            = asList("these", "are", "some", "strings");
    private static final String ANOTHER_STRING = "another string";
    private static final int NEGATIVE_INDEX = -278;

    @Test
    public final void testBuilder__empty() {
        assertEquals(
                emptyList(),
                FinalList.builder().build()
        );
    }

    @Test
    public final void testBuilder__byArray() {
        assertEquals(
                asList(1, 2, 0, 1, 2, 4, 5, 6, 7, 8, 8, 9, 10, null),
                FinalList.builder(1, 2, 3, 4)
                        .add(5)
                        .addAlt(6, 7, 8)
                        .add(0, 0)
                        .addAll(0, asList(1, 2, 3))
                        .addAll(asList(8, 9, 10, null))
                        .remove(Integer.valueOf(3))
                        .build()
        );
    }

    @Test
    public final void testBuilder__byCollection() {
        assertEquals(
                ORIGIN_01,
                FinalList.builder(ORIGIN_01).build()
        );
    }

    @Test
    public final void testFrom__byArray() {
        assertEquals(
                asList(1, 2, 3, 4, 5, 6),
                FinalList.from(1, 2, 3, 4, 5, 6)
        );
    }

    @Test
    public final void testFrom__byCollection() {
        assertEquals(
                ORIGIN_01,
                FinalList.from(ORIGIN_01)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_E__00() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).add(null)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_E__01() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).add(ANOTHER_STRING)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__00() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(0, null)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__10() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(NEGATIVE_INDEX, null)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__01() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(0, ANOTHER_STRING)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__11() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(NEGATIVE_INDEX, ANOTHER_STRING)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd1() {
        FinalList.from(ORIGIN_01).add(0, ANOTHER_STRING);
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS, ANOTHER_STRING
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove__byIndex() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).remove(NEGATIVE_INDEX)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove__byElement() {
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).remove(ANOTHER_STRING)
        ));
    }
}
