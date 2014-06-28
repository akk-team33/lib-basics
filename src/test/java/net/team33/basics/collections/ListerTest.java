package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class ListerTest {

    private static final int INT_277 = 277;
    private static final int INT_278 = 278;
    private static final int INT_279 = 279;
    private static final int INT_280 = 280;

    @Test
    public final void testAdd() {
        Assert.assertEquals(
                asList(INT_277, INT_278, INT_279, INT_280),
                Lister.proArrayList(INT_277, INT_280)
                        .add(1, INT_279)
                        .add(1, INT_278)
                        .getSubject()
        );
    }

    @Test
    public final void testAddAll() {
        Assert.assertEquals(
                asList(INT_277, INT_278, INT_279, INT_280, INT_278, INT_279),
                Lister.proLinkedList(INT_277, INT_280)
                        .addAll(1, asList(INT_278, INT_279))
                        .addAll(4, asList(INT_278, INT_279))
                        .getSubject()
        );
    }

    @Test
    public final void testRemove() {
        Assert.assertEquals(
                asList(INT_277, INT_279, INT_280),
                Lister.proLinkedList(INT_277, INT_278, INT_279, INT_280)
                        .remove(1)
                        .getSubject()
        );
    }

    @Test
    public final void testSet() {
        Assert.assertEquals(
                asList(INT_277, INT_279, INT_280),
                Lister.proArrayList(INT_278, INT_278, INT_280)
                        .set(0, INT_277)
                        .set(1, INT_279)
                        .getSubject()
        );
    }

    @Test
    public final void testRemoveAlt() {
        Assert.assertEquals(
                singletonList(INT_280),
                Lister.proLinkedList(INT_278, INT_279, INT_280)
                        .removeAlt(INT_278, INT_279)
                        .getSubject()
        );
    }
}
