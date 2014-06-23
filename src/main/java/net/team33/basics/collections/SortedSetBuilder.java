package net.team33.basics.collections;

import com.google.common.base.Supplier;
import net.team33.basics.Builder;

import java.util.SortedSet;

import static java.util.Collections.unmodifiableSortedSet;
import static java.util.Objects.requireNonNull;

/**
 * Generic implementation of a {@link Builder} for an immutable {@link SortedSet} of a specific element type.
 * <p/>
 * Both the {@link Builder} itself as well as the {@link #build() final result} are backed by a {@link SortedSet}
 * of a specific type.
 *
 * @param <E> The element type.
 * @param <B> The specific type of {@link java.util.Set} the instance itself and the {@link #build() final result} is backed by.
 * @param <C> The type of the 'final' implementation.
 */
public class SortedSetBuilder<E, B extends SortedSet<E>, C extends SortedSetBuilder<E, B, C>>
        extends Collector<E, SortedSet<E>, C> {

    private final Supplier<B> newSet;
    private final B backing;

    public SortedSetBuilder(final Supplier<B> newSet) {
        this.newSet = requireNonNull(newSet);
        backing = newSet.get();
    }

    @Override
    public final SortedSet<E> build() {
        return unmodifiableSortedSet(Util.addAll(newSet.get(), backing));
    }

    @Override
    protected final B getBacking() {
        // intended to be mutable ...
        // noinspection ReturnOfCollectionOrArrayField
        return backing;
    }
}
