package net.team33.basics.collections;

import com.google.common.base.Supplier;

import java.util.LinkedHashSet;

public class LinkedHashSetBuilder<E> extends PlainSetBuilder<E, LinkedHashSet<E>, LinkedHashSetBuilder<E>> {

    private LinkedHashSetBuilder() {
        super(new SetSupplier<E>());
    }

    public static <E> LinkedHashSetBuilder<E> empty() {
        return new LinkedHashSetBuilder<>();
    }

    private static class SetSupplier<E> implements Supplier<LinkedHashSet<E>> {
        @Override
        public final LinkedHashSet<E> get() {
            return new LinkedHashSet<>(0);
        }
    }
}
