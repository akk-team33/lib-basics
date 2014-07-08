package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class FinalIteratorTest {

    @SuppressWarnings("MagicNumber")
    static final List<Object> ORIGINAL = Arrays.asList(
            "a string", 278, 3.141592654, new Date(), new Object(), null,
            Arrays.asList("a string", 278, 3.141592654, new Date(), new Object(), null));

    static List<?> forwardCopy(final Iterator<?> iterator) {
        final List<Object> result = new ArrayList<>(0);
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    @Test
    public final void testNext_direct() {
        Assert.assertEquals(
                ORIGINAL,
                forwardCopy(ORIGINAL.iterator())
        );
    }

    @Test
    public final void testNext_byProxy() {
        Assert.assertEquals(
                ORIGINAL,
                forwardCopy(FinalIterator.proxy(ORIGINAL.iterator()))
        );
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNext_fail() {
        final FinalIterator<?, ?> subject = FinalIterator.proxy(ORIGINAL.iterator());
        while (subject.hasNext()) {
            subject.next();
        }
        Assert.fail(String.format(
                "Should fail but returns <%s>",
                subject.next()
        ));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_beforeNext() {
        FinalIterator.proxy(ORIGINAL.iterator()).remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_afterHashNext() {
        final FinalIterator<?, ?> subject = FinalIterator.proxy(ORIGINAL.iterator());
        subject.hasNext();
        subject.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_afterNext() {
        final FinalIterator<?, ?> subject = FinalIterator.proxy(ORIGINAL.iterator());
        subject.next();
        subject.remove();
    }
}