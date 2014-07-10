package net.team33.basics.collections;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import static java.util.Arrays.asList;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link List}.
 * Fails fast on any attempt to ...
 * <ul>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #add(int, Object)}</li>
 * <li>{@link #addAll(Collection)}</li>
 * <li>{@link #addAll(int, Collection)}</li>
 * <li>{@link #set(int, Object)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #remove(int)}</li>
 * <li>{@link #removeAll(Collection)}</li>
 * <li>{@link #retainAll(Collection)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 * To create an instance you can use ...
 * <ul>
 * <li>{@link #from(Object[])}</li>
 * <li>{@link #from(Collection)}</li>
 * </ul>
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class FinalList<E> extends AbstractList<E> implements RandomAccess {

    private final Object[] elements;

    /**
     * Mentioned to support derivation.
     * Use {@link #from(Object[])} or {@link #from(Collection)} to directly retrieve an instance.
     */
    protected FinalList(final Collection<? extends E> origin) {
        elements = origin.toArray();
    }

    /**
     * Supplies a new instance of {@link FinalList} by given {@code elements}.
     */
    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E> FinalList<E> from(final E... elements) {
        return from(asList(elements));
    }

    /**
     * Supplies a {@link FinalList} as a copy of an original {@link Collection}.
     * <p/>
     * If the original already is a {@link FinalList} than the original itself will be returned
     * (no need for a copy).
     */
    public static <E> FinalList<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof FinalList) ? (FinalList<E>) origin : new FinalList<>(origin);
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

    @Override
    public final E get(final int index) {
        // There is no regular way to insert an element that is not an instance of <E> ...
        // noinspection unchecked
        return (E) elements[index];
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final E set(final int index, final E element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final void add(final int index, final E element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final E remove(final int index) throws UnsupportedOperationException {
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
     * Not supported.
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean addAll(final int index, final Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final Iterator<E> iterator() {
        return FinalIterator.proxy(super.iterator());
    }

    @Override
    public final ListIterator<E> listIterator() {
        return FinalListIterator.proxy(super.listIterator());
    }

    @Override
    public final ListIterator<E> listIterator(final int index) {
        return FinalListIterator.proxy(super.listIterator(index));
    }

    /**
     * Not supported (even this should fail fast).
     *
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    protected final void removeRange(final int fromIndex, final int toIndex) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final int size() {
        return elements.length;
    }
}
