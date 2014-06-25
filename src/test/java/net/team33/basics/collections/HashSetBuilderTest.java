package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class HashSetBuilderTest {

    @Test
    public final void testEmpty() {
        Assert.assertEquals(
                Collections.emptySet(),
                Collector.hashSet().getSubject()
        );
    }
}