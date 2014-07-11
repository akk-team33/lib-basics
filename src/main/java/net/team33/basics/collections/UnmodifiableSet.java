package net.team33.basics.collections;

import java.util.Set;

/**
 * Abstracts an unmodifiable {@link Set} that fails fast on any attempt to ...
 * <ul>
 * <li>{@link #add(Object)}</li>
 * <li>{@link #addAll(java.util.Collection)}</li>
 * <li>{@link #remove(Object)}</li>
 * <li>{@link #removeAll(java.util.Collection)}</li>
 * <li>{@link #retainAll(java.util.Collection)}</li>
 * <li>{@link #clear()}</li>
 * </ul>
 */
public abstract class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {

    @Override
    public final boolean equals(final Object obj) {
        return Collecting.equals(this, obj);
    }

    @Override
    public final int hashCode() {
        return Collecting.hashCode(this);
    }
}
