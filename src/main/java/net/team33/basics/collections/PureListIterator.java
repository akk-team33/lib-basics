package net.team33.basics.collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Specifies a {@link ListIterator} that is purely mentioned to iterate over an underlying list
 * and strictly refuses to modify it, so fails fast on any attempt to ...
 * <ul>
 * <li>{@link #remove()}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #set(Object)}</li>
 * </ul>
 */
@SuppressWarnings("AbstractClassWithOnlyOneDirectInheritor")
public abstract class PureListIterator<E> extends PureIterator<E> implements ListIterator<E> {

    /**
     * Supplies a {@link PureListIterator} as a proxy for an original {@link ListIterator}.
     * <p/>
     * If the original already is a {@link PureListIterator} than the original itself will be returned
     * (no need for a proxy).
     */
    public static <E> PureListIterator<E> proxy(final ListIterator<E> original) {
        return (original instanceof PureListIterator) ? (PureListIterator<E>) original : new Proxy<>(original);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void set(final E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void add(final E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    private static class Proxy<E> extends PureListIterator<E> {
        private final ListIterator<E> original;

        private Proxy(final ListIterator<E> original) {
            this.original = requireNonNull(original);
        }

        @Override
        public final boolean hasNext() {
            return original.hasNext();
        }

        @Override
        public final int nextIndex() {
            return original.nextIndex();
        }

        @Override
        public final E next() throws NoSuchElementException {
            return original.next();
        }

        @Override
        public final boolean hasPrevious() {
            return original.hasPrevious();
        }

        @Override
        public final int previousIndex() {
            return original.previousIndex();
        }

        @Override
        public final E previous() throws NoSuchElementException {
            return original.previous();
        }
    }
}
