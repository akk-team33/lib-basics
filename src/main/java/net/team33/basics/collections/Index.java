package net.team33.basics.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents an index, that provides relatively fast locating of elements within an associated {@link List}.
 * <p/>
 * Requires the {@link List} and its content not to be modified while being associated to the index
 * or otherwise that the index will be renewed.
 */
@SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "UnusedDeclaration"})
public class Index<E> {

    private static final Comparator<Entry> ORDER = new Order();

    private final List<E> subject;
    private final Entry[] entries;

    /**
     * Initiates a new instance for a specific {@link List subject} that must not be modified
     * while the new instance is in use.
     */
    public Index(final List<E> subject) {
        // For efficiency purpose ...
        // noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.subject = subject;
        this.entries = newIndex(subject.iterator(), subject.size());
    }

    private static Entry[] newIndex(final Iterator<?> origin, final int size) {
        final Entry[] result = new Entry[size];
        for (int index = 0; (index < size) || origin.hasNext(); ++index) {
            result[index] = new Entry(Objects.hashCode(origin.next()), index);
        }
        Arrays.sort(result, ORDER);
        return result;
    }

    @SuppressWarnings({"ReturnOfNull", "MethodWithMultipleLoops"})
    private Entry entry(final Direction direction, final Object other) {
        if (0 < entries.length) {
            final int otherHash = Objects.hashCode(other);

            int lower = direction.lower(entries.length);
            int lowerHash = entries[lower].hash;
            if (direction.isLess(lowerHash, otherHash)) {

                int higher = direction.higher(entries.length);
                int higherHash = entries[higher].hash;
                while (direction.isLess(lowerHash, otherHash) && direction.isLessOrEquals(otherHash, higherHash)) {
                    //noinspection NumericCastThatLosesPrecision,UnnecessaryExplicitNumericCast
                    final int middle = (int) (((long) lower + (long) higher) / 2L);
                    if (Math.min(lower, higher) == middle) {
                        lower = higher;
                        lowerHash = higherHash;

                    } else {
                        final int middleHash = entries[middle].hash;
                        if (direction.isLess(middleHash, otherHash)) {
                            lower = middle;
                            lowerHash = middleHash;
                        } else {
                            higher = middle;
                            higherHash = middleHash;
                        }
                    }
                }
            }

            while (lowerHash == otherHash) {
                if (Objects.equals(other, subject.get(entries[lower].index))) {
                    return entries[lower];

                } else {
                    lower = direction.next(lower);
                    lowerHash = ((0 <= lower) && (lower < entries.length)) ? entries[lower].hash : ~otherHash;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the index number of an element's first occurrence within the associated {@link List}.
     * <p/>
     * Returns {@code -1} if the supposed element is not in the list.
     */
    public final int first(final Object o) {
        return number(Direction.FORWARD, o);
    }

    /**
     * Retrieves the index number of an element's last occurrence within the associated {@link List}.
     * <p/>
     * Returns {@code -1} if the supposed element is not in the list.
     */
    public final int last(final Object o) {
        return number(Direction.BACKWARD, o);
    }

    private int number(final Direction direction, final Object other) {
        final Entry entry = entry(direction, other);
        return (null == entry) ? -1 : entry.index;
    }

    public final boolean contains(final Object o) {
        return null != entry(Direction.FORWARD, o);
    }

    private enum Direction {
        FORWARD {
            @Override
            final int lower(final int size) {
                return 0;
            }

            @Override
            final int higher(final int size) {
                return size - 1;
            }

            @Override
            final int next(final int lower) {
                return lower + 1;
            }

            @Override
            final boolean isLess(final int left, final int right) {
                return left < right;
            }
        },
        BACKWARD {
            @Override
            final int lower(final int size) {
                return FORWARD.higher(size);
            }

            @Override
            final int higher(final int size) {
                return FORWARD.lower(size);
            }

            @Override
            final int next(final int lower) {
                return lower - 1;
            }

            @Override
            final boolean isLess(final int left, final int right) {
                return FORWARD.isLess(right, left);
            }
        };

        abstract int lower(int size);

        abstract int higher(int size);

        abstract int next(int lower);

        abstract boolean isLess(int left, int right);

        final boolean isLessOrEquals(final int left, final int right) {
            return (left == right) || isLess(left, right);
        }
    }

    private static class Locator {
        private int lowerIndex;
        private int lowerHash;
        private int middleIndex;
        private int middleHash;
        private int higherIndex;
        private int higherHash;

        @SuppressWarnings({"NestedAssignment", "UnnecessaryParentheses", "NumericCastThatLosesPrecision", "UnnecessaryExplicitNumericCast"})
        private Locator(final Entry[] entries, final int lower, final int higher, final int otherHash) {
            lowerIndex = lower;
            lowerHash = entries[lowerIndex].hash;
            if (lowerHash > otherHash) {
                higherIndex = (middleIndex = lowerIndex);
                higherHash = (middleHash = lowerHash);

            } else {
                higherIndex = higher;
                higherHash = entries[higherIndex].hash;
                if (otherHash > higherHash) {
                    lowerIndex = (middleIndex = higherIndex);
                    lowerHash = (middleHash = higherHash);

                } else {
                    middleIndex = (int) (((long) lower + (long) higher) / 2L);
                    middleHash = entries[middleIndex].hash;

                    while ((lowerHash < otherHash) && (otherHash < higherHash)) {
                        ;
                    }
                }
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
