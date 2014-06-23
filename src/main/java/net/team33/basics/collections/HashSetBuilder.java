package net.team33.basics.collections;

import com.google.common.base.Supplier;

import java.util.HashSet;

public class HashSetBuilder<E> extends PlainSetBuilder<E, HashSet<E>, HashSetBuilder<E>> {

    private HashSetBuilder() {
        super(new SetSupplier<E>());
    }

    public static <E> HashSetBuilder<E> empty() {
        return new HashSetBuilder<>();
    }

    private static class SetSupplier<E> implements Supplier<HashSet<E>> {
        @Override
        public final HashSet<E> get() {
            return new HashSet<>(0);
        }
    }
}
