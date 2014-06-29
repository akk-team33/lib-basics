package net.team33.basics.collections;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link Iterator} covering a common one.
 */
public class FinalIterator<E, I extends Iterator<E>> implements Iterator<E> {

    @SuppressWarnings("ProtectedField")
    protected final I core;

    /**
     * Intended to support derivation.
     * Use {@link #from(Iterator)} to create a straight new Instance.
     */
    protected FinalIterator(final I core) {
        this.core = requireNonNull(core);
    }

    public static <E> FinalIterator<E, ?> from(final Iterator<E> core) {
        return new FinalIterator<>(core);
    }

    @Override
    public final boolean hasNext() {
        return core.hasNext();
    }

    @Override
    public final E next() {
        return core.next();
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void remove() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }
}
