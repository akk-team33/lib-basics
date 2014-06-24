package net.team33.basics.collections;

import java.util.HashSet;

public class HashSetBuilder<E> extends PlainSetBuilder<E, HashSet<E>, HashSetBuilder<E>> {

    private HashSetBuilder() {
        super(new HashSet<E>(0));
    }

    public static <E> HashSetBuilder<E> empty() {
        return new HashSetBuilder<>();
    }
}
