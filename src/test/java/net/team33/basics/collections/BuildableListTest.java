package net.team33.basics.collections;

import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"JUnitTestMethodWithNoAssertions", "ClassWithTooManyMethods"})
public class BuildableListTest {

    private static final Collection<? extends CharSequence> ORIGIN_01
            = asList("these", "are", "some", "strings");

    @Test
    public final void testBuilder__empty() {
        assertEquals(
                emptyList(),
                BuildableList.builder().build()
        );
    }

    @Test
    public final void testBuilder__byArray() {
        assertEquals(
                asList(1, 2, 0, 1, 2, 4, 5, 6, 7, 8, 8, 9, 10, null),
                BuildableList.builder(1, 2, 3, 4)
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
                BuildableList.builder(ORIGIN_01).build()
        );
    }

    @Test
    public final void testFrom__byArray() {
        assertEquals(
                asList(1, 2, 3, 4, 5, 6),
                BuildableList.from(1, 2, 3, 4, 5, 6)
        );
    }

    @Test
    public final void testFrom__byCollection() {
        assertEquals(
                ORIGIN_01,
                BuildableList.from(ORIGIN_01)
        );
    }
}
