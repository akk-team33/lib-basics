package net.team33.basics.collections;

import com.google.common.base.Supplier;
import net.team33.basics.Builder;

import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

/**
 * Generic implementation of a {@link Builder} for an immutable {@link Set} of a specific element type.
 * <p/>
 * Both the {@link Builder} itself as well as the {@link #build() final result} are backed by a {@link Set}
 * of a specific type.
 *
 * @param <E> The element type.
 * @param <B> The specific type of {@link Set} the instance itself and the {@link #build() final result} is backed by.
 * @param <C> The type of the 'final' implementation.
 */
public class PlainSetBuilder<E, B extends Set<E>, C extends PlainSetBuilder<E, B, C>>
        extends Collector<E, Set<E>, C> {

    private final Supplier<B> newSet;
    private final B backing;

    public PlainSetBuilder(final Supplier<B> newSet) {
        this.newSet = requireNonNull(newSet);
        backing = newSet.get();
    }

    @Override
    protected final B getBacking() {
        // intended to be mutable ...
        // noinspection ReturnOfCollectionOrArrayField
        return backing;
    }

    @Override
    public final Set<E> build() {
        return unmodifiableSet(Util.addAll(newSet.get(), backing));
    }
}
