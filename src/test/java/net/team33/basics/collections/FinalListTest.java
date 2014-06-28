package net.team33.basics.collections;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.fail;

@SuppressWarnings("JUnitTestMethodWithNoAssertions")
public class FinalListTest {

    private static final String SHOULD_FAIL_BUT_RETURNS = "Should fail but returns <%s>";
    private static final Collection<? extends CharSequence> ORIGIN_01
            = Arrays.asList("these", "are", "some", "strings");
    private static final String ANOTHER_STRING = "another string";
    private static final int NEGATIVE_INDEX = -278;

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
    public void testAdd1() {
        FinalList.from(ORIGIN_01).add(0, ANOTHER_STRING);
        fail(String.format(
                SHOULD_FAIL_BUT_RETURNS, ANOTHER_STRING
        ));
    }

    @Test
    public void testRemove() {

    }

    @Test
    public void testClear() {

    }

    @Test
    public void testAddAll() {

    }

    @Test
    public void testIterator() {

    }

    @Test
    public void testListIterator() {

    }

    @Test
    public void testListIterator1() {

    }

    @Test
    public void testIterator1() {

    }

    @Test
    public void testAdd2() {

    }

    @Test
    public void testRemove1() {

    }

    @Test
    public void testAddAll1() {

    }

    @Test
    public void testRemoveAll() {

    }

    @Test
    public void testRetainAll() {

    }

    @Test
    public void testClear1() {

    }
}
