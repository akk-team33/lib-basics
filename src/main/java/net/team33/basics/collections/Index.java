package net.team33.basics.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.String.format;

/**
 * Represents an index, that provides accelerated locating of elements within an associated {@link List}.
 * <p/>
 * Requires the {@link List} and its content to remain unmodified while being associated to the index.
 * In other words, when the {@link List} or its content is modified, an associated index is required to be renewed.
 */
public class Index {

    private static final String ILLEGAL_SUBJECT = "Illegal <subject> of type <%s> with inconsistent size() and iterator()";

    private final List<?> subject;
    private final int[] indexes;
    private final int[] hashes;

    /**
     * Initiates a new instance for a specific {@link List subject} that must not be modified (nor its content)
     * while the new instance is in use.
     */
    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "ProhibitedExceptionCaught"})
    public Index(final List<?> subject) {
        try {
            final Entries entries = new Entries(subject.iterator(), subject.size());
            indexes = entries.indexes;
            hashes = entries.hashes;

            // Intended to associate the underlying list itself (and not to instantiate anything new) ...
            // noinspection AssignmentToCollectionOrArrayFieldFromParameter
            this.subject = subject;

        } catch (final NoSuchElementException | ArrayIndexOutOfBoundsException caught) {
            throw new IllegalArgumentException(format(ILLEGAL_SUBJECT, subject.getClass().getName()), caught);
        }
    }

    @SuppressWarnings({"ReturnOfNull", "MethodWithMultipleLoops"})
    private int entry(final Direction direction, final Object other, final int otherHash) {
        final int size = hashes.length;
        if (0 < size) {
            int left = direction.leftmost(size);
            int right = direction.rightmost(size);
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
                    left = direction.next(left);
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
            final int leftmost(final int size) {
                return 0;
            }

            @Override
            final int rightmost(final int size) {
                return size - 1;
            }

            @Override
            final boolean isLeft(final int subject, final int other) {
                return subject < other;
            }
        },
        REVERSE(-1) {
            @Override
            final int leftmost(final int size) {
                return FORWARD.rightmost(size);
            }

            @Override
            final int rightmost(final int size) {
                return FORWARD.leftmost(size);
            }

            @Override
            final boolean isLeft(final int subject, final int other) {
                return FORWARD.isLeft(other, subject);
            }
        };

        private final int deltaNext;

        Direction(final int deltaNext) {
            this.deltaNext = deltaNext;
        }

        abstract int leftmost(int size);

        abstract int rightmost(int size);

        abstract boolean isLeft(int subject, int other);

        final int next(final int index) {
            return index + deltaNext;
        }

        final boolean isNotRight(final int subject, final int other) {
            return (subject == other) || isLeft(subject, other);
        }
    }

    private static class Entries {
        private final int[] hashes;
        private final int[] indexes;

        @SuppressWarnings("ProhibitedExceptionDeclared")
        private Entries(final Iterator<?> origin, final int size)
                throws NoSuchElementException, ArrayIndexOutOfBoundsException {

            final long[] entries = newEntries(origin, size);
            hashes = new int[size];
            indexes = new int[size];
            for (int index = 0; index < size; ++index) {
                indexes[index] = toIndex(entries[index]);
                hashes[index] = toHash(entries[index]);
            }
        }

        private static long[] newEntries(final Iterator<?> origin, final int size) {
            final long[] entries = new long[size];
            for (int index = 0; (index < size) || origin.hasNext(); ++index) {
                entries[index] = toLong(Objects.hashCode(origin.next()), index);
            }
            Arrays.sort(entries);
            return entries;
        }

        private static int toHash(final long entry) {
            //noinspection NumericCastThatLosesPrecision
            return (int) (entry >> Integer.SIZE);
        }

        private static int toIndex(final long entry) {
            //noinspection NumericCastThatLosesPrecision
            return (int) entry;
        }

        private static long toLong(final int hashCode, final int index) {
            return ((long) hashCode << Integer.SIZE) | index;
        }
    }
}
