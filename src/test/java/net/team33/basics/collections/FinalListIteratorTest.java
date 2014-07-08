package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static net.team33.basics.collections.FinalIteratorTest.ORIGINAL;
import static net.team33.basics.collections.FinalIteratorTest.forwardCopy;

@SuppressWarnings("ClassWithTooManyMethods")
public class FinalListIteratorTest {

    private static final String AN_ELEMENT = "an element";

    private static FinalListIterator<Object, ?> backwardProxy() {
        return FinalListIterator.proxy(ORIGINAL.listIterator(ORIGINAL.size()));
    }

    private static List<?> backwardCopy(final ListIterator<?> iterator) {
        final List<Object> result = new ArrayList<>(0);
        while (iterator.hasPrevious()) {
            result.add(0, iterator.previous());
        }
        return result;
    }

    private static FinalListIterator<Object, ?> forwardProxy() {
        return FinalListIterator.proxy(ORIGINAL.listIterator());
    }

    @Test
    public final void testPrevious_direct() {
        Assert.assertEquals(
                ORIGINAL,
                backwardCopy(ORIGINAL.listIterator(ORIGINAL.size()))
        );
    }

    @Test
    public final void testPrevious_byProxy() {
        Assert.assertEquals(
                ORIGINAL,
                backwardCopy(backwardProxy())
        );
    }

    @Test(expected = NoSuchElementException.class)
    public final void testPrevious_fail() {
        final FinalListIterator<?, ?> subject = forwardProxy();
        while (subject.hasPrevious()) {
            subject.previous();
        }
        Assert.fail(String.format(
                "Should fail but returns <%s>",
                subject.previous()
        ));
    }

    @Test
    public final void testNext_direct() {
        Assert.assertEquals(
                ORIGINAL,
                forwardCopy(ORIGINAL.listIterator())
        );
    }

    @Test
    public final void testNext_byProxy() {
        Assert.assertEquals(
                ORIGINAL,
                forwardCopy(forwardProxy())
        );
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNext_fail() {
        final FinalListIterator<?, ?> subject = forwardProxy();
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
        forwardProxy().remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_beforePrev() {
        backwardProxy().remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_afterHashNext() {
        final FinalListIterator<?, ?> subject = forwardProxy();
        subject.hasNext();
        subject.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_afterHashPrev() {
        final FinalListIterator<?, ?> subject = backwardProxy();
        subject.hasNext();
        subject.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_afterNext() {
        final FinalListIterator<?, ?> subject = forwardProxy();
        subject.next();
        subject.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemove_afterPrev() {
        final FinalListIterator<?, ?> subject = backwardProxy();
        subject.previous();
        subject.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_beforeNext() {
        forwardProxy().add(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_beforePrev() {
        backwardProxy().add(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_afterHasNext() {
        final FinalListIterator<Object, ?> subject = forwardProxy();
        subject.hasNext();
        subject.add(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_afterHasPrev() {
        final FinalListIterator<Object, ?> subject = backwardProxy();
        subject.hasPrevious();
        subject.add(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_afterNext() {
        final FinalListIterator<Object, ?> subject = forwardProxy();
        subject.next();
        subject.add(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAdd_afterPrev() {
        final FinalListIterator<Object, ?> subject = backwardProxy();
        subject.previous();
        subject.add(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_beforeNext() {
        forwardProxy().set(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_beforePrev() {
        backwardProxy().set(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_afterHasNext() {
        final FinalListIterator<Object, ?> subject = forwardProxy();
        subject.hasNext();
        subject.set(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_afterHasPrev() {
        final FinalListIterator<Object, ?> subject = backwardProxy();
        subject.hasPrevious();
        subject.set(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_afterNext() {
        final FinalListIterator<Object, ?> subject = forwardProxy();
        subject.next();
        subject.set(AN_ELEMENT);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSet_afterPrev() {
        final FinalListIterator<Object, ?> subject = backwardProxy();
        subject.previous();
        subject.set(AN_ELEMENT);
    }
}