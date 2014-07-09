package net.team33.basics.collections;

import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Generic implementation of an immutable {@link Collection} that fails fast on any attempt to ...
 * <ul>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #addAll(Collection)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #removeAll(Collection)}</li>
 * <li>{@link #retainAll(Collection)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 * To create an instance you can use ... TODO ...
 * <ul>
 * <li>{@code #from(Object[])}</li>
 * <li>{@code #from(Collection)}</li>
 * <li>{@code #builder(Object[])}.[...].  {@code Builder#build() build()}</li>
 * <li>{@code #builder(Collection)}.[...].{@code Builder#build() build()}</li>
 * </ul>
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class FinalCollection<E, C extends Collection<E>> implements Collection<E> {

    @SuppressWarnings("ProtectedField")
    protected final C core;

    /**
     * Mentioned to support derivation. TODO: how to directly create an instance if possible?
     */
    protected FinalCollection(final C core) {
        this.core = requireNonNull(core);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean add(final E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean remove(final Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean addAll(final Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean removeAll(final Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean retainAll(final Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @see Collection#contains(Object)
     */
    @Override
    public final boolean contains(final Object o) {
        return core.contains(o);
    }

    /**
     * @see Collection#containsAll(Collection)
     */
    @Override
    public final boolean containsAll(final Collection<?> c) {
        return core.containsAll(c);
    }

    /**
     * @see Collection#size()
     */
    @Override
    public final int size() {
        return core.size();
    }

    @Override
    public final boolean isEmpty() {
        return core.isEmpty();
    }

    @Override
    public final FinalIterator<E, ?> iterator() {
        return FinalIterator.proxy(core.iterator());
    }

    /**
     * @see Collection#toArray()
     */
    @Override
    public final Object[] toArray() {
        return core.toArray();
    }

    /**
     * @see Collection#toArray(Object[])
     */
    @Override
    public final <T> T[] toArray(final T[] a) {
        return core.toArray(a);
    }

    /**
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public final String toString() {
        return core.toString();
    }
}
