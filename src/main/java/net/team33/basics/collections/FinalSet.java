package net.team33.basics.collections;

import com.google.common.base.Supplier;
import net.team33.basics.lazy.Initial;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import static net.team33.basics.collections.FinalIterator.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link Set}.
 * <ul>
 *     <li>To be instantiated as a copy of an original {@link Collection} (eg. via {@link #from(Collection)}).</li>
 *     <li>Preserves the iteration order of the original {@link Collection} (as far as compatible with a {@link Set}).</li>
 * </ul>
 * <p/>
 * NOTE (from documentation of {@link Set}):
 * Great care must be exercised if mutable objects are used as set elements. The behavior of a set is not specified if
 * the value of an object is changed in a manner that affects {@link Object#equals(Object) equals} comparisons while the
 * object is an element in the set.
 */
@SuppressWarnings("EqualsAndHashcode")
public class FinalSet<E> extends AbstractSet<E> implements Serializable {

    private static final String NO_SUCH_ELEMENT = "index(%d) >= size(%d) --> %s";
    private static final long serialVersionUID = 0x9a1a574810bace4eL;

    private final Object[] elements;
    private final int[] hashCodes;

    private transient Supplier<Integer> hashSupplier = new HashCode();
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
        final int otherCode = Objects.hashCode(o);
        for (int index = 0, limit = elements.length; index < limit; ++index) {
            if ((otherCode == hashCodes[index]) && Objects.equals(o, elements[index])) {
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
        return new InternalIterator();
    }

    @Override
    public final int size() {
        return elements.length;
    }

    @Override
    public final int hashCode() {
        //noinspection NonFinalFieldReferencedInHashCode
        return hashSupplier.get();
    }

    private class InternalIterator extends FinalIterator<E> {
        private int index = 0;

        @Override
        public final boolean hasNext() {
            return index < size();
        }

        @Override
        public final E next() {
            try {
                //noinspection unchecked,ValueOfIncrementOrDecrementUsed
                return (E) elements[index++];
            } catch (@SuppressWarnings("ProhibitedExceptionCaught") final ArrayIndexOutOfBoundsException caught) {
                throw new NoSuchElementException(String.format(String.format(NO_SUCH_ELEMENT, index, size(), caught), index, size(), caught));
            }
        }
    }

    private class HashCode extends Initial<Integer> {
        @Override
        protected final Integer getFinal() {
            return FinalSet.super.hashCode();
        }

        @Override
        protected final Supplier<Integer> getAnchor() {
            return hashSupplier;
        }

        @Override
        protected final void setAnchor(final Supplier<Integer> supplier) {
            hashSupplier = supplier;
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
