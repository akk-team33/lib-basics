package net.team33.basics.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Derivative of a {@link FinalList} that supports optimized searching for elements,
 * just like you probably would expect from a {@link Set}.
 * <p/>
 * This mainly affects ...
 * <ul>
 * <li>{@link #indexOf(Object)}</li>
 * <li>{@link #lastIndexOf(Object)}</li>
 * <li>{@link #contains(Object)}</li>
 * <li>{@link #containsAll(Collection)} (implicitly trough {@link #contains(Object)})</li>
 * </ul>
 * <p/>
 * According to a {@link Set}, regrettably it will show unspecific behavior,
 * if it contains mutable elements, which in the meantime are in deed modified
 * (in a manner that affects hashCode() or equals()).
 */
@SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
public class FinalIndexList<E> extends FinalList<E> {

    private static final Comparator<Entry> ORDER = new Order();

    private final Entry[] entries;

    /**
     * Mentioned to support derivation.
     * Use {@link #from(Object[])} or {@link #from(Collection)} to directly retrieve an instance.
     */
    protected FinalIndexList(final Collection<? extends E> origin) {
        super(origin);
        entries = newIndex(iterator(), size());
    }

    private static Entry[] newIndex(final Iterator<?> origin, final int size) {
        final Entry[] result = new Entry[size];
        for (int index = 0; (index < size) || origin.hasNext(); ++index) {
            result[index] = new Entry(Objects.hashCode(origin.next()), index);
        }
        Arrays.sort(result, ORDER);
        return result;
    }

    /**
     * Supplies a new instance of {@link FinalIndexList} by given {@code elements}.
     */
    @SuppressWarnings({"OverloadedVarargsMethod", "MethodOverridesStaticMethodOfSuperclass"})
    @SafeVarargs
    public static <E> FinalIndexList<E> from(final E... elements) {
        return from(asList(elements));
    }

    /**
     * Supplies a {@link FinalIndexList} as a copy of an original {@link Collection}.
     * <p/>
     * If the original already is a {@link FinalIndexList} than the original itself will be returned
     * (no need for a copy).
     */
    @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
    public static <E> FinalIndexList<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof FinalIndexList) ? (FinalIndexList<E>) origin : new FinalIndexList<>(origin);
    }

    @SuppressWarnings("ReturnOfNull")
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
                if (Objects.equals(other, get(entries[lower].index))) {
                    return entries[lower];

                } else {
                    lower = direction.next(lower);
                    lowerHash = ((0 <= lower) && (lower < entries.length)) ? entries[lower].hash : ~otherHash;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public final int indexOf(final Object o) {
        return indexOf(Direction.FORWARD, o);
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public final int lastIndexOf(final Object o) {
        return indexOf(Direction.BACKWARD, o);
    }

    private int indexOf(final Direction direction, final Object other) {
        final Entry entry = entry(direction, other);
        return (null == entry) ? -1 : entry.index;
    }

    @SuppressWarnings("RefusedBequest")
    @Override
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
