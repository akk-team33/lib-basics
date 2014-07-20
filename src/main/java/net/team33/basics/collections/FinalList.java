package net.team33.basics.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

import static java.util.Arrays.asList;

/**
 * Implementation of an immutable {@link List}.
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class FinalList<E> extends UnmodifiableList<E> implements RandomAccess {

    private final Object[] elements;

    /**
     * Mentioned to support derivation.
     * Use {@link #from(Object[])} or {@link #from(Collection)} to directly retrieve an instance.
     */
    protected FinalList(final Collection<? extends E> origin) {
        elements = origin.toArray();
    }

    /**
     * Supplies a new instance of {@link FinalList} by given {@code elements}.
     */
    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E> FinalList<E> from(final E... elements) {
        return from(asList(elements));
    }

    /**
     * Supplies a {@link FinalList} as a copy of an original {@link Collection}.
     * <p/>
     * If the original already is a {@link FinalList} than the original itself will be returned
     * (no need for a copy).
     */
    public static <E> FinalList<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof FinalList) ? (FinalList<E>) origin : new FinalList<>(origin);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E> Builder<E> builder(final E... elements) {
        return new Builder<>(asList(elements));
    }

    public static <E> Builder<E> builder(final Collection<? extends E> elements) {
        return new Builder<>(elements);
    }

    @Override
    public final E get(final int index) {
        // There is no regular way to insert an element that is not an instance of <E> ...
        // noinspection unchecked
        return (E) elements[index];
    }

    @Override
    public final FinalList<E> subList(final int fromIndex, final int toIndex) {
        return from(Collecting.proxy(this).subList(fromIndex, toIndex));
    }

    @Override
    public final int size() {
        return elements.length;
    }

    @SuppressWarnings("ClassNameSameAsAncestorName")
    public static class Builder<E>
            extends Lister<E, List<E>, Builder<E>>
            implements net.team33.basics.Builder<FinalList<E>> {

        private Builder(final Collection<? extends E> origin) {
            super(new ArrayList<>(origin));
        }

        @Override
        public final FinalList<E> build() {
            return from(subject);
        }
    }
}
