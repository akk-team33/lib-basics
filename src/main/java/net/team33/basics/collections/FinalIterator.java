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

    public interface Core<E> {

        boolean hasNext();

        E next() throws NoSuchElementException;
    }

    protected static class Proxy<E, I extends Iterator<E>> implements Core<E> {

        @SuppressWarnings("ProtectedField")
        protected final I original;

        protected Proxy(final I original) {
            this.original = original;
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
