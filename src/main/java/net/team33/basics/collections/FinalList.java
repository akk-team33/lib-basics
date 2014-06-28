package net.team33.basics.collections;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/**
 * Implementation of an immutable {@link List}.
 * <ul>
 * <li>To be instantiated as a copy of an original {@link Collection} (eg. via {@link #from(Collection)}).</li>
 * <li>Preserves the iteration order of the original {@link Collection} and makes each element index-accessible.</li>
 * <li>Fast-fails on any attempt of modification throwing an {@link UnsupportedOperationException}</li>
 * </ul>
 */
public class FinalList<E> extends AbstractList<E> implements RandomAccess {

    private final Object[] elements;

    private FinalList(final Collection<? extends E> origin) {
        this(origin.iterator(), origin.size());
    }

    private FinalList(final Iterator<? extends E> iterator, final int size) {
        elements = new Object[size];
        //noinspection ForLoopThatDoesntUseLoopVariable
        for (int index = 0; iterator.hasNext(); ++index) {
            elements[index] = iterator.next();
        }
    }

    /**
     * Supplies a new instance of {@link FinalList} as a copy of an original {@link Collection}.
     * If the original already is a {@link FinalList} than the original itself will be returned
     * (no need for a further copy).
     */
    public static <E> FinalList<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof FinalList) ? (FinalList<E>) origin : new FinalList<>(origin);
    }

    @Override
    public final E get(final int index) {
        //noinspection unchecked
        return (E) elements[index];
    }

    @Override
    public final int size() {
        return elements.length;
    }
}
