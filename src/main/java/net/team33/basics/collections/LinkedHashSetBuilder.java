package net.team33.basics.collections;

import java.util.LinkedHashSet;

public class LinkedHashSetBuilder<E> extends PlainSetBuilder<E, LinkedHashSet<E>, LinkedHashSetBuilder<E>> {

    private LinkedHashSetBuilder() {
        super(new LinkedHashSet<E>(0));
    }

    public static <E> LinkedHashSetBuilder<E> empty() {
        return new LinkedHashSetBuilder<>();
    }
}
