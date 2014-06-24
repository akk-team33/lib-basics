package net.team33.basics.collections;

import java.util.EnumSet;

public class EnumSetBuilder<E extends Enum<E>> extends PlainSetBuilder<E, EnumSet<E>, EnumSetBuilder<E>> {

    private EnumSetBuilder(final Class<E> enumClass) {
        super(EnumSet.noneOf(enumClass));
    }

    public static <E extends Enum<E>> EnumSetBuilder<E> empty(final Class<E> enumClass) {
        return new EnumSetBuilder<>(enumClass);
    }
}
