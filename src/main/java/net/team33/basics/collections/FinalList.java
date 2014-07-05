package net.team33.basics.collections;

import net.team33.basics.Rebuildable;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

import static java.util.Arrays.asList;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link List}. To create an instance you can use ...
 * <ul>
 * <li>{@link #from(Object[])}</li>
 * <li>{@link #from(Collection)}</li>
 * <li>{@link #builder(Object[])}.[...].{@link Builder#build() build()}</li>
 * <li>{@link #builder(Collection)}.[...].{@link Builder#build() build()}</li>
 * </ul>
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class FinalList<E> extends AbstractList<E>
        implements RandomAccess, Rebuildable<FinalList<E>, FinalList.Builder<E>> {

    private static final String ILLEGAL_ORIGIN
            = "inconsistent iterator (%s) and size (%d)";

    private final Object[] elements;

    private FinalList(final Collection<? extends E> origin) {
        this(origin.iterator(), origin.size());
    }

    private FinalList(final Iterator<? extends E> iterator, final int size) {
        try {
            elements = new Object[size];
            for (int index = 0; (index < size) || iterator.hasNext(); ++index) {
                elements[index] = iterator.next();
            }
        } catch (ArrayIndexOutOfBoundsException | NoSuchElementException caught) {
            throw new IllegalArgumentException(
                    String.format(ILLEGAL_ORIGIN, iterator, size),
                    caught);
        }
    }

    /**
     * Supplies a new {@link Builder}, initialized by given {@code elements}.
     */
    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E> Builder<E> builder(final E... elements) {
        return builder(asList(elements));
    }

    /**
     * Supplies a new {@link Builder}, initialized by an original {@link Collection}.
     */
    public static <E> Builder<E> builder(final Collection<? extends E> origin) {
        return new Builder<>(origin);
    }

    /**
     * Supplies a new instance of {@link FinalList} as a copy of an original {@link Collection}.
     * If the original already is a {@link FinalList} than the original itself will be returned
     * (no need for a further copy).
     */
    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E> FinalList<E> from(final E... elements) {
        return from(asList(elements));
    }

    /**
     * Supplies a {@link FinalList} as a copy of an original {@link Collection}.
     * If the original already is a {@link FinalList} than the original itself will be returned
     * (no need for a copy).
     */
    public static <E> FinalList<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof FinalList) ? (FinalList<E>) origin : new FinalList<>(origin);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean add(final E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean remove(final Object o) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final E get(final int index) {
        // There is no regular way to insert an element that is not an instance of <E> ...
        // noinspection unchecked
        return (E) elements[index];
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final E set(final int index, final E element) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final void add(final int index, final E element) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final E remove(final int index) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final void clear() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean addAll(final int index, final Collection<? extends E> c) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final Iterator<E> iterator() {
        return FinalIterator.from(super.iterator());
    }

    @Override
    public final ListIterator<E> listIterator() {
        return FinalListIterator.from(super.listIterator());
    }

    @Override
    public final ListIterator<E> listIterator(final int index) {
        return FinalListIterator.from(super.listIterator(index));
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    protected final void removeRange(final int fromIndex, final int toIndex) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final int size() {
        return elements.length;
    }

    @Override
    public final Builder<E> rebuilder() {
        return new Builder<>(this);
    }

    @SuppressWarnings({"PublicInnerClass", "ClassNameSameAsAncestorName"})
    public static class Builder<E>
            extends Lister<E, ArrayList<E>, Builder<E>>
            implements net.team33.basics.Builder<FinalList<E>> {

        private Builder(final Collection<? extends E> origin) {
            super(new ArrayList<>(origin));
        }

        @Override
        public final FinalList<E> build() {
            return from(getCore());
        }
    }
}
