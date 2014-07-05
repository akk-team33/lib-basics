package net.team33.basics.collections;

import org.junit.Test;

import static net.team33.basics.collections.Package.simpleName;
import static org.junit.Assert.assertEquals;

public class PackageTest {

    @Test
    public final void testSimpleName() {
        assertEquals("PackageTest", PackageTest.class.getSimpleName());
        assertEquals("Inner", Inner.class.getSimpleName());
        assertEquals("More", Inner.More.class.getSimpleName());

        assertEquals("PackageTest", simpleName(PackageTest.class));
        assertEquals("PackageTest.Inner", simpleName(Inner.class));
        assertEquals("PackageTest.Inner.More", simpleName(Inner.More.class));
    }

    private static class Inner {
        private static class More {
        }
    }
}
