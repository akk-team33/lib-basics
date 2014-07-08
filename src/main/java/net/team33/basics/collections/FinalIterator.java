package net.team33.basics.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Generic implementation of an immutable {@link Iterator}.
 * Fails fast on any attempt to {@link #remove()}.
 */
public class FinalIterator<E, C extends FinalIterator.Core<E>> implements Iterator<E> {

    @SuppressWarnings("ProtectedField")
    protected final C core;

    public FinalIterator(final C core) {
        this.core = requireNonNull(core);
    }

    public static <E> FinalIterator<E, ?> proxy(final Iterator<E> original) {
        return new FinalIterator<>(new Proxy<>(original));
    }

    @Override
    public final boolean hasNext() {
        return core.hasNext();
    }

    @Override
    public final E next() throws NoSuchElementException {
        return core.next();
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

    /**
     * Abstracts the core functionality of an immutable {@link Iterator}.
     */
    public interface Core<E> {

        /**
         * @see Iterator#hasNext()
         */
        boolean hasNext();

        /**
         * @see Iterator#next()
         */
        E next() throws NoSuchElementException;
    }

    /**
     * Implements the core functionality proxying an immutable {@link Iterator}.
     */
    static class Proxy<E, I extends Iterator<E>> implements Core<E> {

        @SuppressWarnings("PackageVisibleField")
        final I original;

        Proxy(final I original) {
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
