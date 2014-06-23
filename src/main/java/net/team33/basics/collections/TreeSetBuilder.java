package net.team33.basics.collections;

import com.google.common.base.Supplier;

import java.util.Comparator;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;

public class TreeSetBuilder<E> extends SortedSetBuilder<E, TreeSet<E>, TreeSetBuilder<E>> {

    private TreeSetBuilder(final Comparator<? super E> order) {
        super(new SetSupplier<>(order));
    }

    public static <E> TreeSetBuilder<E> empty(final Comparator<? super E> order) {
        return new TreeSetBuilder<>(order);
    }

    private static class SetSupplier<E> implements Supplier<TreeSet<E>> {
        private final Comparator<? super E> order;

        private SetSupplier(final Comparator<? super E> order) {
            this.order = requireNonNull(order);
        }

        @Override
        public final TreeSet<E> get() {
            return new TreeSet<>(order);
        }
    }
}
