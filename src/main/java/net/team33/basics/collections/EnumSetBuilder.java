package net.team33.basics.collections;

import com.google.common.base.Supplier;

import java.util.EnumSet;

import static java.util.Objects.requireNonNull;

public class EnumSetBuilder<E extends Enum<E>> extends PlainSetBuilder<E, EnumSet<E>, EnumSetBuilder<E>> {

    private EnumSetBuilder(final Class<E> enumClass) {
        super(new SetSupplier<>(enumClass));
    }

    public static <E extends Enum<E>> EnumSetBuilder<E> empty(final Class<E> enumClass) {
        return new EnumSetBuilder<>(enumClass);
    }

    private static class SetSupplier<E extends Enum<E>> implements Supplier<EnumSet<E>> {
        private final Class<E> enumClass;

        private SetSupplier(final Class<E> enumClass) {
            this.enumClass = requireNonNull(enumClass);
        }

        @Override
        public final EnumSet<E> get() {
            return EnumSet.noneOf(enumClass);
        }
    }
}
