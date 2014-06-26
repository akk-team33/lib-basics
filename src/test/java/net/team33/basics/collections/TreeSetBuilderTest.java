package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

public class TreeSetBuilderTest {

    private static final Comparator<Object> DUMMY = new Dummy();

    @Test
    public final void testEmpty() {
        Assert.assertEquals(
                Collections.emptySet(),
                Collector.byTreeSet((Comparator<String>) null).getSubject()
        );
    }

    private static class Dummy implements Comparator<Object>, Serializable {
        @Override
        public final int compare(final Object o1, final Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }
}