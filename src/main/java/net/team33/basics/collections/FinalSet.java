package net.team33.basics.collections;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Implementation of an immutable {@link Set}.
 * <p/>
 * NOTE (from documentation of {@link Set}):
 * Great care must be exercised if mutable objects are used as set elements. The behavior of a set is not specified if
 * the value of an object is changed in a manner that affects {@link Object#equals(Object) equals} comparisons while the
 * object is an element in the set.
 */
@SuppressWarnings("EqualsAndHashcode")
public class FinalSet<E> extends FinalCollection<E, FinalSet.Core<E>> implements Set<E> {

    private static final Comparator<Entry> ORDER = new Order();

    @SuppressWarnings("TypeMayBeWeakened")
    private FinalSet(final Set<? extends E> origin) {
        super(new Core<>(origin));
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

    @SuppressWarnings("ClassNameSameAsAncestorName")
    static class Core<E> extends AbstractSet<E> implements FinalCollection.Core<E> {
        private final Object[] elements;
        private final Entry[] entries;

        @SuppressWarnings("TypeMayBeWeakened")
        private Core(final Set<? extends E> origin) {
            elements = origin.toArray();
            entries = newIndex(elements);
        }

        private static Entry[] newIndex(final Object[] elements) {
            final int size = elements.length;
            final Entry[] result = new Entry[size];
            for (int index = 0; index < size; ++index) {
                result[index] = new Entry(Objects.hashCode(elements[index]), index);
            }
            Arrays.sort(result, ORDER);
            return result;
        }

        @SuppressWarnings({"RefusedBequest", "AccessingNonPublicFieldOfAnotherObject"})
        @Override
        public final boolean contains(final Object other) {
            if (0 == elements.length) {
                return false;
            } // else ...

            if (1 == elements.length) {
                return Objects.equals(elements[0], other);
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
                final int middle = (int) (((long) lower + (long) higher) / 2L);
                if (lower == middle) {
                    lower = higher;
                    lowerHash = higherHash;

                } else {
                    final int middleHash = entries[middle].hash;
                    if (middleHash < otherHash) {
                        lower = middle;
                        lowerHash = middleHash;
                    } else {
                        higher = middle;
                        higherHash = middleHash;
                    }
                }
            }

            while (lowerHash == otherHash) {
                if (Objects.equals(other, elements[entries[lower].index])) {
                    return true;
                } else {
                    lower += 1;
                    lowerHash = entries[lower].hash;
                }
            }
            return false;
        }

        @Override
        public final FinalIterator<E, ?> iterator() {
            return new FinalIterator<>(new IteratorCore());
        }

        @Override
        public final int size() {
            return elements.length;
        }

        private class IteratorCore implements FinalIterator.Core<E> {
            private int index = 0;

            @Override
            public final boolean hasNext() {
                return index < elements.length;
            }

            @Override
            public final E next() throws NoSuchElementException {
                //noinspection unchecked,ValueOfIncrementOrDecrementUsed
                return (E) elements[index++];
            }
        }
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
