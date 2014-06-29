package net.team33.basics.collections;

import java.util.ListIterator;

import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link ListIterator} covering a common one.
 */
public class FinalListIterator<E, I extends ListIterator<E>> extends FinalIterator<E, I> implements ListIterator<E> {

    /**
     * Intended to support derivation.
     * Use {@link #from(ListIterator)} to directly create a new Instance.
     */
    protected FinalListIterator(final I core) {
        super(core);
    }

    public static <E> FinalListIterator<E, ?> from(final ListIterator<E> core) {
        return new FinalListIterator<>(core);
    }

    @Override
    public final boolean hasPrevious() {
        return core.hasPrevious();
    }

    @Override
    public final int previousIndex() {
        return core.previousIndex();
    }

    @Override
    public final E previous() {
        return core.previous();
    }

    @Override
    public final int nextIndex() {
        return core.nextIndex();
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void set(final E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void add(final E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }
}
