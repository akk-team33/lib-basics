package net.team33.basics.collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;

final class Package {
    static final String NOT_SUPPORTED = "Unsupported operation";

    private static final String CLASS_SEPARATOR = ".";

    private Package() {
    }

    static String simpleName(final Class<?> aClass) {
        final String result = aClass.getSimpleName();
        final Class<?> declaringClass = aClass.getDeclaringClass();
        return (null == declaringClass) ? result : (simpleName(declaringClass) + CLASS_SEPARATOR + result);
    }
}

abstract class ImmutableIndexIterator<E> implements ListIterator<E> {

    private int index;

    ImmutableIndexIterator(final int index) {
        this.index = index;
    }

    protected abstract int size();

    /**
     * @throws IndexOutOfBoundsException
     */
    protected abstract E get(int index);

    @Override
    public final boolean hasNext() {
        return index < size();
    }

    @Override
    public final int nextIndex() {
        return index;
    }

    @Override
    public final E next() {
        try {
            //noinspection ValueOfIncrementOrDecrementUsed
            return get(index++);
        } catch (final IndexOutOfBoundsException caught) {
            //noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
            throw new NoSuchElementException(caught.toString());
        }
    }

    @Override
    public final boolean hasPrevious() {
        return 0 < index;
    }

    @Override
    public final int previousIndex() {
        return index - 1;
    }

    @Override
    public final E previous() {
        try {
            //noinspection ValueOfIncrementOrDecrementUsed
            return get(--index);
        } catch (final IndexOutOfBoundsException caught) {
            //noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
            throw new NoSuchElementException(caught.toString());
        }
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException(Package.NOT_SUPPORTED);
    }

    @Override
    public final void set(final E e) {
        throw new UnsupportedOperationException(Package.NOT_SUPPORTED);
    }

    @Override
    public final void add(final E e) {
        throw new UnsupportedOperationException(Package.NOT_SUPPORTED);
    }
}