package net.team33.basics.collections;

import com.google.common.base.Supplier;
import net.team33.basics.lazy.Initial;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of an immutable {@link Set}.
 * <p/>
 * NOTE (from documentation of {@link Set}):
 * Great care must be exercised if mutable objects are used as set elements. The behavior of a set is not specified if
 * the value of an object is changed in a manner that affects {@link Object#equals(Object) equals} comparisons while the
 * object is an element in the set.
 */
public class FinalSet<E> extends AbstractSet<E> {

    private static final String NOT_SUPPORTED = "Not supported";

    private final Object[] elements;
    private final int[] hashCodes;

    private transient Supplier<Integer> hashCodeSupplier = new HashCode();
    private transient Supplier<String> stringSupplier = new ToString();

    private FinalSet(final Set<? extends E> origin) {
        this(origin.iterator(), origin.size());
    }

    private FinalSet(final Iterator<? extends E> origin, final int size) {
        elements = new Object[size];
        hashCodes = new int[size];
        for (int index = 0; origin.hasNext(); ++index) {
            final Object next = origin.next();
            elements[index] = next;
            hashCodes[index] = Objects.hashCode(next);
        }
    }

    public static <E> FinalSet<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof Set) ? from((Set<? extends E>) origin) : from(new LinkedHashSet<>(origin));
    }

    public static <E> FinalSet<E> from(final Set<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof FinalSet) ? (FinalSet<E>) origin : new FinalSet<>(origin);
    }

    @Override
    public final boolean removeAll(final Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final boolean add(final E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final boolean remove(final Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final boolean addAll(final Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final boolean retainAll(final Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public final boolean contains(final Object o) {
        for (int index = 0, limit = elements.length; index < limit; ++index) {
            if ((hashCodes[index] == Objects.hashCode(o)) && Objects.equals(elements[index], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final String toString() {
        return stringSupplier.get();
    }

    @Override
    public final Iterator<E> iterator() {
        return new ITERATOR();
    }

    @Override
    public final int size() {
        return elements.length;
    }

    @Override
    public final int hashCode() {
        return hashCodeSupplier.get();
    }

    private class ITERATOR implements Iterator<E> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public E next() {
            try {
                //noinspection unchecked
                return (E) elements[index++];
            } catch (final ArrayIndexOutOfBoundsException caught) {
                throw new NoSuchElementException(String.format("(index(%d) >= size(%d)) --> %s", index, size(), caught));
            }
        }

        @Override
        public final void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException(NOT_SUPPORTED);
        }
    }

    private class HashCode extends Initial<Integer> {
        @Override
        protected final Integer getFinal() {
            return FinalSet.super.hashCode();
        }

        @Override
        protected final Supplier<Integer> getAnchor() {
            return hashCodeSupplier;
        }

        @Override
        protected final void setAnchor(final Supplier<Integer> supplier) {
            hashCodeSupplier = supplier;
        }
    }

    private class ToString extends Initial<String> {
        @Override
        protected final String getFinal() {
            return FinalSet.super.toString();
        }

        @Override
        protected final Supplier<String> getAnchor() {
            return stringSupplier;
        }

        @Override
        protected final void setAnchor(final Supplier<String> supplier) {
            stringSupplier = supplier;
        }
    }
}
