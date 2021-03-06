package net.team33.basics.collections;

import org.junit.Test;

import java.util.Collection;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

@SuppressWarnings({"JUnitTestMethodWithNoAssertions", "ClassWithTooManyMethods"})
public class FinalListTest {

    private static final String SHOULD_FAIL_BUT_RETURNS
            = "Should fail but returns <%s>";
    private static final Collection<? extends CharSequence> ORIGIN_01
            = asList("these", "are", "some", "strings");
    private static final String ANOTHER_STRING
            = "another string";
    private static final int NEGATIVE_INDEX
            = -278;

    @Test
    public final void testBuilder__byArray() {
        assertEquals(
                ORIGIN_01,
                FinalList.builder()
                        .addAlt(ORIGIN_01.toArray())
                        .build());
        assertEquals(
                ORIGIN_01,
                FinalList.builder(ORIGIN_01.toArray())
                        .build());
    }

    @Test
    public final void testBuilder__byCollection() {
        assertEquals(
                ORIGIN_01,
                FinalList.builder(ORIGIN_01)
                        .build());
    }

    @Test
    public final void testFrom__byArray() {
        assertEquals(
                asList(ORIGIN_01.toArray()),
                FinalList.from(ORIGIN_01.toArray()));
        assertEquals(
                FinalList.from(ORIGIN_01.toArray()),
                asList(ORIGIN_01.toArray()));
    }

    @Test
    public final void testFrom__byCollection() {
        assertEquals(
                ORIGIN_01,
                FinalList.from(ORIGIN_01));
        assertEquals(
                FinalList.from(ORIGIN_01),
                ORIGIN_01);
    }

    @Test
    public final void testFrom__byFinalList() {
        final FinalList<CharSequence> subject = FinalList.from(ORIGIN_01);
        assertSame(
                subject,
                FinalList.from(subject)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_E__00() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).add(null)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_E__01() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).add(ANOTHER_STRING)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__00() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(0, null)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__10() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(NEGATIVE_INDEX, null)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__01() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(0, ANOTHER_STRING)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_int_E__11() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).set(NEGATIVE_INDEX, ANOTHER_STRING)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd1() {
        FinalList.from(ORIGIN_01).add(0, ANOTHER_STRING);
        fail(format(
                SHOULD_FAIL_BUT_RETURNS, ANOTHER_STRING
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove__byIndex() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).remove(NEGATIVE_INDEX)
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove__byElement() {
        fail(format(
                SHOULD_FAIL_BUT_RETURNS,
                FinalList.from(ORIGIN_01).remove(ANOTHER_STRING)
        ));
    }
}
