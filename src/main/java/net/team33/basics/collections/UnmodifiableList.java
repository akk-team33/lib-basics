package net.team33.basics.collections;

import java.util.Collection;
import java.util.List;

import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Specifies an unmodifiable {@link Collection} that (in particular) fails fast on any attempt to ...
 * <ul>
 * <li>{@link #set(int, Object)}</li>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #add(int, Object)}</li>
 * <li>{@link #addAll(Collection)}</li>
 * <li>{@link #addAll(int, Collection)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #remove(int)}</li>
 * <li>{@link #removeAll(Collection)}</li>
 * <li>{@link #retainAll(Collection)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 */
@SuppressWarnings("AbstractClassWithOnlyOneDirectInheritor")
public abstract class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void add(final int index, final E element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final boolean addAll(final int index, final Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final E set(final int index, final E element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final E remove(final int index) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The basic implementation is 'straight forward'.
     * A derivative may override it to provide a more efficient implementation.
     */
    @SuppressWarnings("DesignForExtension")
    @Override
    public int indexOf(final Object o) {
        return Collecting.proxy(this).indexOf(o);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The basic implementation is 'straight forward'.
     * A derivative may override it to provide a more efficient implementation.
     */
    @SuppressWarnings("DesignForExtension")
    @Override
    public int lastIndexOf(final Object o) {
        return Collecting.proxy(this).lastIndexOf(o);
    }

    @Override
    public final PureIterator<E> iterator() {
        return PureIterator.proxy(Collecting.proxy(this).iterator());
    }

    @Override
    public final PureListIterator<E> listIterator() {
        return PureListIterator.proxy(Collecting.proxy(this).listIterator());
    }

    @Override
    public final PureListIterator<E> listIterator(final int index) {
        return PureListIterator.proxy(Collecting.proxy(this).listIterator(index));
    }

    /**
     * {@inheritDoc}
     * <p/>
     * An {@link UnmodifiableList} returns an {@link UnmodifiableList}.
     */
    @Override
    public abstract UnmodifiableList<E> subList(int fromIndex, int toIndex);

    @Override
    public final int hashCode() {
        return Collecting.proxy(this).hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        return Collecting.proxy(this).equals(obj);
    }
}
