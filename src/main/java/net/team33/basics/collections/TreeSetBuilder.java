package net.team33.basics.collections;

import java.util.Comparator;
import java.util.TreeSet;

public class TreeSetBuilder<E> extends PlainSetBuilder<E, TreeSet<E>, TreeSetBuilder<E>> {

    private TreeSetBuilder(final Comparator<? super E> order) {
        super(new TreeSet<E>(order));
    }

    public static <E> TreeSetBuilder<E> empty(final Comparator<? super E> order) {
        return new TreeSetBuilder<>(order);
    }
}
