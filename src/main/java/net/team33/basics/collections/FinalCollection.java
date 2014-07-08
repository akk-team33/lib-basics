package net.team33.basics.collections;

import java.util.Collection;
import java.util.Iterator;

import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link java.util.List}.
 * Fails fast on any attempt to ...
 * <ul>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #addAll(Collection)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #removeAll(Collection)}</li>
 * <li>{@link #retainAll(Collection)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 * To create an instance you can use ... TODO
 * <ul>
 * <li>{@code #from(Object[])}</li>
 * <li>{@code #from(Collection)}</li>
 * <li>{@code #builder(Object[])}.[...].  {@code Builder#build() build()}</li>
 * <li>{@code #builder(Collection)}.[...].{@code Builder#build() build()}</li>
 * </ul>
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class FinalCollection<E, C extends FinalCollection.Core<E>> implements Collection<E> {

    @SuppressWarnings("ProtectedField")
    protected final C core;

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

    @Override
    public final boolean containsAll(final Collection<?> c) {
        return core.containsAll(c);
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

    @Override
    public final Iterator<E> iterator() {
        return core.iterator();
    }

    @Override
    public final Object[] toArray() {
        return core.toArray();
    }

    @Override
    public final <T> T[] toArray(final T[] a) {
        return core.toArray(a);
    }

    @Override
    public final int size() {
        return core.size();
    }

    @Override
    public final boolean isEmpty() {
        return 0 == core.size();
    }

    @Override
    public final boolean contains(final Object o) {
        return core.contains(o);
    }

    public interface Core<E> {

        /**
         * @see Collection#contains(Object)
         */
        boolean contains(Object o);

        /**
         * @see Collection#containsAll(Collection)
         */
        boolean containsAll(Collection<?> c);

        /**
         * @see Collection#iterator()
         */
        FinalIterator<E, ?> iterator();

        /**
         * @see Collection#size()
         */
        int size();

        /**
         * @see Collection#toArray()
         */
        Object[] toArray();

        /**
         * @see Collection#toArray(Object[])
         */
        <T> T[] toArray(T[] a);
    }
}
