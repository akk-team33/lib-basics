package net.team33.basics.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Specifies an {@link Iterator} that is purely mentioned to iterate over an underlying collection
 * and strictly refuses to modify it, so fails fast on any attempt to {@link #remove()}.
 */
@SuppressWarnings("AbstractClassWithOnlyOneDirectInheritor")
public abstract class PureIterator<E> implements Iterator<E> {

    /**
     * Supplies a {@link PureIterator} as a proxy for an original {@link Iterator}.
     * <p/>
     * If the original already is a {@link PureIterator} than the original itself will be returned
     * (no need for a proxy).
     */
    public static <E> PureIterator<E> proxy(final Iterator<E> original) {
        return (original instanceof PureIterator) ? (PureIterator<E>) original : new Proxy<>(original);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    private static class Proxy<E> extends PureIterator<E> {
        private final Iterator<E> original;

        private Proxy(final Iterator<E> original) {
            this.original = requireNonNull(original);
        }

        @Override
        public final boolean hasNext() {
            return original.hasNext();
        }

        @Override
        public final E next() throws NoSuchElementException {
            return original.next();
        }
    }
}
