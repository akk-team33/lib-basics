package net.team33.basics.collections;

import java.util.Collection;
import java.util.Set;

/**
 * Specifies an unmodifiable {@link Collection} that (in particular) fails fast on any attempt to ...
 * <ul>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #addAll(Collection)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #removeAll(Collection)}</li>
 * <li>{@link #retainAll(Collection)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 */
public abstract class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {

    @Override
    public final int hashCode() {
        return Collecting.proxy(this).hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        return Collecting.proxy(this).equals(obj);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * An {@link UnmodifiableCollection} returns a {@link PureIterator}.
     */
    @Override
    public abstract PureIterator<E> iterator();
}
