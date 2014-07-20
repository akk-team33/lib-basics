package net.team33.basics.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class PureIteratorTest {

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
    public final void testForwardCopy() {
        assertEquals(
                ORIGINAL,
                forwardCopy(ORIGINAL.iterator())
        );
    }

    @Test
    public final void testNext_viaForwardCopy() {
        assertEquals(
                ORIGINAL,
                forwardCopy(PureIterator.proxy(ORIGINAL.iterator()))
        );
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNext_fail() {
        final PureIterator<?> subject = PureIterator.proxy(ORIGINAL.iterator());
        while (subject.hasNext()) {
            subject.next();
        }
        fail(String.format(
                "Should fail but returns <%s>",
                subject.next()
        ));
    }

    @Test(expected = IllegalStateException.class)
    public final void testRemove_fromMutable_beforeNext() {
        final Iterator<Object> subject = new ArrayList<>(ORIGINAL).iterator();
        subject.remove();
    }

    @Test(expected = IllegalStateException.class)
    public final void testRemove_fromOriginal_beforeNext() {
        ORIGINAL.iterator().remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_fromPureIterator_beforeNext() {
        PureIterator.proxy(ORIGINAL.iterator()).remove();
    }

    @Test(expected = IllegalStateException.class)
    public final void testRemove_fromMutable_afterHasNext() {
        final Iterator<Object> subject = new ArrayList<>(ORIGINAL).iterator();
        subject.hasNext();
        subject.remove();
    }

    @Test(expected = IllegalStateException.class)
    public final void testRemove_fromOriginal_afterHasNext() {
        final Iterator<Object> subject = ORIGINAL.iterator();
        subject.hasNext();
        subject.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_fromPureIterator_afterHasNext() {
        final PureIterator<?> subject = PureIterator.proxy(ORIGINAL.iterator());
        subject.hasNext();
        subject.remove();
    }

    @Test
    public final void testRemove_fromMutable_afterNext() {
        final Object first = ORIGINAL.get(0);
        final Collection<Object> original = new ArrayList<>(ORIGINAL);
        final Iterator<Object> subject = original.iterator();
        subject.next();
        subject.remove();
        assertFalse(original.contains(first));
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_fromOriginal_afterNext() {
        final Iterator<Object> subject = ORIGINAL.iterator();
        subject.next();
        subject.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_fromPureIterator_afterNext() {
        final PureIterator<?> subject = PureIterator.proxy(ORIGINAL.iterator());
        subject.next();
        subject.remove();
    }
}
