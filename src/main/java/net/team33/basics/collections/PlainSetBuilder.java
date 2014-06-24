package net.team33.basics.collections;

import net.team33.basics.Builder;

import java.util.Set;

/**
 * Generic implementation of a {@link Builder} for an immutable {@link Set} of a specific element type.
 * <p/>
 * Both the {@link Builder} itself as well as the {@link #build() final result} are backed by a {@link Set}
 * of a specific type.
 *
 * @param <E> The element type.
 * @param <B> The specific type of {@link Set} the instance itself is backed by.
 * @param <C> The type of the 'final' implementation.
 */
public class PlainSetBuilder<E, B extends Set<E>, C extends PlainSetBuilder<E, B, C>>
        extends Collector<E, B, Set<E>, C> {

    protected PlainSetBuilder(final B backing) {
        super(backing);
    }

    @Override
    public final Set<E> build() {
        return Util.finalCopy(backing);
    }
}
