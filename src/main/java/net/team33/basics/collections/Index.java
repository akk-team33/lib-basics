package net.team33.basics.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents an index, that provides accelerated locating of elements within an associated {@link List}.
 * <p/>
 * Requires the {@link List} and its content to remain unmodified while being associated to the index.
 * In other words, when the {@link List} or its content is modified, an associated index is required to be renewed.
 */
@SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "UnusedDeclaration"})
public class Index {

    private static final Comparator<Entry> ORDER = new Order();

    private final List<?> subject;
    private final int[] indexes;
    private final int[] hashes;

    /**
     * Initiates a new instance for a specific {@link List subject} that must not be modified
     * while the new instance is in use.
     */
    public Index(final List<?> subject) {
        final Entries entries1 = new Entries(subject.iterator(), subject.size());
        indexes = entries1.indexes;
        hashes = entries1.hashes;

        // For efficiency purpose ...
        // noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.subject = subject;
    }

    private static Entry[] newIndex_(final Iterator<?> origin, final int size) {
        final Set<Entry> result = new TreeSet<>(ORDER);
        for (int index = 0; (index < size) || origin.hasNext(); ++index) {
            result.add(new Entry(Objects.hashCode(origin.next()), index));
        }
        return result.toArray(new Entry[result.size()]);
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
    private int entry(final Direction direction, final Object other, final int otherHash) {
        final int size = hashes.length;
        if (0 < size) {
            int left = direction.maxLeft(size);
            int right = direction.maxRight(size);
            while (direction.isLeft(hashes[left], otherHash) && direction.isNotRight(otherHash, hashes[right])) {
                if (1 == Math.abs(left - right)) {
                    left = right;

                } else {
                    //noinspection NumericCastThatLosesPrecision,UnnecessaryExplicitNumericCast
                    final int middle = (int) (((long) left + (long) right) / 2L);
                    if (direction.isLeft(hashes[middle], otherHash)) {
                        left = middle;
                    } else {
                        right = middle;
                    }
                }
            }

            while ((left < size) && (hashes[left] == otherHash)) {
                if (Objects.equals(other, subject.get(indexes[left]))) {
                    return left;
                } else {
                    left = direction.nextIndex(left);
                }
            }
        }
        return -1;
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
        return number(Direction.REVERSE, o);
    }

    /**
     * Indicates weather or not the index contains an entry for a specific object.
     */
    public final boolean contains(final Object o) {
        final int hash = Objects.hashCode(o);
        return 0 <= entry((0 < hash) ? Direction.REVERSE : Direction.FORWARD, o, hash);
    }

    private int number(final Direction direction, final Object other) {
        final int entry = entry(direction, other, Objects.hashCode(other));
        return (0 > entry) ? -1 : indexes[entry];
    }

    @SuppressWarnings("ParameterHidesMemberVariable")
    private enum Direction {
        FORWARD(1) {
            @Override
            final int maxLeft(final int size) {
                return 0;
            }

            @Override
            final int maxRight(final int size) {
                return size - 1;
            }

            @Override
            final boolean isLeft(final int subject, final int other) {
                return subject < other;
            }
        },
        REVERSE(-1) {
            @Override
            final int maxLeft(final int size) {
                return FORWARD.maxRight(size);
            }

            @Override
            final int maxRight(final int size) {
                return FORWARD.maxLeft(size);
            }

            @Override
            final boolean isLeft(final int subject, final int other) {
                return FORWARD.isLeft(other, subject);
            }
        };

        private final int nextStep;

        Direction(final int nextStep) {
            this.nextStep = nextStep;
        }

        abstract int maxLeft(int size);

        abstract int maxRight(int size);

        abstract boolean isLeft(int subject, int other);

        final int nextIndex(final int index) {
            return index + nextStep;
        }

        final boolean isNotRight(final int subject, final int other) {
            return (subject == other) || isLeft(subject, other);
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

    private static class Entries {
        private final int[] hashes;
        private final int[] indexes;

        @SuppressWarnings("ProhibitedExceptionDeclared")
        private Entries(final Iterator<?> origin, final int size)
                throws NoSuchElementException, ArrayIndexOutOfBoundsException {

            final Entry[] entries = new Entry[size];
            for (int index = 0; (index < size) || origin.hasNext(); ++index) {
                entries[index] = new Entry(Objects.hashCode(origin.next()), index);
            }
            Arrays.sort(entries, ORDER);

            hashes = new int[size];
            indexes = new int[size];
            for (int index = 0; index < size; ++index) {
                indexes[index] = entries[index].index;
                hashes[index] = entries[index].hash;
            }
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
