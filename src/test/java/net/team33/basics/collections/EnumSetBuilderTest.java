package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.EnumSet;

public class EnumSetBuilderTest {

    @Test
    public final void testEmpty() {
        Assert.assertEquals(
                EnumSet.of(Dummy.ABC, Dummy.DEF),
                Collector.enumSet(Dummy.class)
                        .add(Dummy.ABC)
                        .addAll(EnumSet.of(Dummy.DEF, Dummy.GHI))
                        .remove(Dummy.GHI)
                        .getSubject()
        );
    }

    private enum Dummy {
        ABC, DEF, GHI
    }
}