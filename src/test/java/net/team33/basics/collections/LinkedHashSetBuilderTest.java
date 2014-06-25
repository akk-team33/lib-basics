package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class LinkedHashSetBuilderTest {

    @Test
    public final void testEmpty() {
        Assert.assertEquals(
                Collections.emptySet(),
                Collector.linkedHashSet()
                        .getSubject()
        );
    }
}