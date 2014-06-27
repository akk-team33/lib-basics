package net.team33.basics.collections;

import java.util.Iterator;

abstract class FinalIterator<E> implements Iterator<E> {

    static final String NOT_SUPPORTED = "Unsupported operation";

    @Override
    public final void remove() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }
}
