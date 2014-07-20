package net.team33.basics.collections;

import java.util.Collection;

import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Specifies an unmodifiable {@link Collection} that (in particular) fails fast on any attempt to ...
 * <ul>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #addAll(Collection)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #removeAll(Collection)}</li>
 * <li>{@link #retainAll(Collection)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 */
public abstract class UnmodifiableCollection<E> implements Collection<E> {

    /**
     * {@inheritDoc}
     * <p/>
     * The basic implementation is 'straight forward'.
     * A derivative may override it to provide a more efficient implementation.
     */
    @SuppressWarnings("DesignForExtension")
    @Override
    public boolean contains(final Object o) {
        return Collecting.proxy(this).contains(o);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The basic implementation is 'straight forward' based on {@link #contains(Object)}.
     * A derivative may override it to provide a more efficient implementation.
     */
    @SuppressWarnings("DesignForExtension")
    @Override
    public boolean containsAll(final Collection<?> c) {
        return Collecting.proxy(this).containsAll(c);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * In particular returns {@code false} if {@link #size()} &gt; {@code 0}.
     */
    @Override
    public final boolean isEmpty() {
        return 1 > size();
    }

    @Override
    public final Object[] toArray() {
        return Collecting.proxy(this).toArray();
    }

    @Override
    public final <T> T[] toArray(final T[] a) {
        //noinspection SuspiciousToArrayCall
        return Collecting.proxy(this).toArray(a);
    }

    @Override
    public final String toString() {
        return Collecting.proxy(this).toString();
    }

    /**
     * {@inheritDoc}
     * <p/>
     * An {@link UnmodifiableCollection} returns a {@link PureIterator}.
     */
    @Override
    public abstract PureIterator<E> iterator();

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
}
