package net.team33.basics.collections;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Arrays.asList;
import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link Set}.
 * <p/>
 * NOTE (from documentation of {@link Set}):
 * Great care must be exercised if mutable objects are used as set elements. The behavior of a set is not specified if
 * the value of an object is changed in a manner that affects {@link Object#equals(Object) equals} comparisons while the
 * object is an element in the set.
 */
@SuppressWarnings("EqualsAndHashcode")
public class FinalSet<E> extends AbstractSet<E> {

    private static final Comparator<Entry> ORDER = new Order();
    private final List<E> elements;
    private final Entry[] entries;

    @SuppressWarnings("TypeMayBeWeakened")
    private FinalSet(final Set<? extends E> origin) {
        elements = FinalList.from(origin);
        entries = newEntries(elements).toArray(new Entry[elements.size()]);
    }

    private static Collection<Entry> newEntries(final List<?> elements) {
        final List<Entry> result = new ArrayList<>(elements.size());
        for (int index = 0; index < elements.size(); ++index) {
            result.add(new Entry(Objects.hashCode(elements.get(index)), index));
        }
        Collections.sort(result, ORDER);
        return result;
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E> FinalSet<E> from(final E... elements) {
        return from(asList(elements));
    }

    public static <E> FinalSet<E> from(final Collection<? extends E> origin) {
        //noinspection ChainOfInstanceofChecks
        if (origin instanceof FinalSet) {
            //noinspection unchecked
            return (FinalSet<E>) origin;

        } else if (origin instanceof Set) {
            //noinspection unchecked
            return new FinalSet<>((Set<? extends E>) origin);

        } else {
            return new FinalSet<>(new LinkedHashSet<>(origin));
        }
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
    public final boolean addAll(final Collection<? extends E> c) {
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

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final void clear() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @SuppressWarnings({"RefusedBequest", "AccessingNonPublicFieldOfAnotherObject"})
    @Override
    public final boolean contains(final Object other) {
        if (elements.isEmpty()) {
            return false;
        } // else ...

        if (1 == elements.size()) {
            return Objects.equals(elements.get(0), other);
        } // else ...

        final int otherHash = Objects.hashCode(other);
        int lower = 0;
        int lowerHash = entries[lower].hash;
//        if (lowerHash > otherHash) {
//            return false;
//        } // else ...

        int higher = entries.length - 1;
        int higherHash = entries[higher].hash;
//        if (higherHash < otherHash) {
//            return false;
//        } // else ...

        while ((lowerHash < otherHash) && (otherHash <= higherHash)) {
            //noinspection NumericCastThatLosesPrecision,UnnecessaryExplicitNumericCast
            final int index = (int) (((long) lower + (long) higher) / 2L);
            if (lower == index) {
                lower = higher;
                lowerHash = higherHash;

            } else {
                final int nextHash = entries[index].hash;
                if (otherHash > nextHash) {
                    lower = index;
                    lowerHash = nextHash;
                } else {
                    higher = index;
                    higherHash = nextHash;
                }
            }
        }

        while (lowerHash == otherHash) {
            if (Objects.equals(other, elements.get(entries[lower].index))) {
                return true;
            } else {
                lower += 1;
                lowerHash = entries[lower].hash;
            }
        }
        return false;
    }

    @Override
    public final Iterator<E> iterator() {
        return elements.iterator();
    }

    @Override
    public final int size() {
        return elements.size();
    }

    private static class Entry {
        private final int hash;
        private final int index;

        private Entry(final int hash, final int index) {
            this.hash = hash;
            this.index = index;
        }
    }

    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "ComparatorNotSerializable"})
    private static class Order implements Comparator<Entry> {
        @Override
        public final int compare(final Entry o1, final Entry o2) {
            final int result = Integer.compare(o1.hash, o2.hash);
            return (0 == result) ? Integer.compare(o1.index, o2.index) : result;
        }
    }
}
