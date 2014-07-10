package net.team33.basics.collections;

import net.team33.basics.Rebuildable;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Derivative of a {@link FinalList} that supports
 * {@linkplain BuildableList.Builder building} and {@linkplain Rebuildable re-building}.
 * <p/>
 * To create an instance you can use ...
 * <ul>
 * <li>{@link #from(Object[])}</li>
 * <li>{@link #from(java.util.Collection)}</li>
 * <li>{@link #builder(Object[])}.[...].{@link Builder#build() build()}</li>
 * <li>{@link #builder(java.util.Collection)}.[...].{@link Builder#build() build()}</li>
 * </ul>
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class BuildableList<E> extends FinalList<E> implements Rebuildable<BuildableList<E>, BuildableList.Builder<E>> {

    private BuildableList(final Collection<? extends E> origin) {
        super(origin);
    }

    /**
     * Supplies a new {@link Builder}, pre-initialized by given {@code elements}.
     */
    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E> Builder<E> builder(final E... elements) {
        return builder(asList(elements));
    }

    /**
     * Supplies a new {@link Builder}, pre-initialized by an original {@link java.util.Collection}.
     */
    public static <E> Builder<E> builder(final Collection<? extends E> origin) {
        return new Builder<>(origin);
    }

    /**
     * Supplies a new instance of {@link BuildableList} by given {@code elements}.
     */
    @SuppressWarnings({"OverloadedVarargsMethod", "MethodOverridesStaticMethodOfSuperclass"})
    @SafeVarargs
    public static <E> BuildableList<E> from(final E... elements) {
        return from(asList(elements));
    }

    /**
     * Supplies a {@link BuildableList} as a copy of an original {@link java.util.Collection}.
     * <p/>
     * If the original already is a {@link BuildableList} than the original itself will be returned
     * (no need for a copy).
     */
    @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
    public static <E> BuildableList<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof BuildableList) ? (BuildableList<E>) origin : new BuildableList<>(origin);
    }

    @Override
    public final Builder<E> rebuilder() {
        return new Builder<>(this);
    }

    @SuppressWarnings({"PublicInnerClass", "ClassNameSameAsAncestorName"})
    public static class Builder<E>
            extends Lister<E, ArrayList<E>, Builder<E>>
            implements net.team33.basics.Builder<BuildableList<E>> {

        private Builder(final Collection<? extends E> origin) {
            super(new ArrayList<>(origin));
        }

        @Override
        public final BuildableList<E> build() {
            return from(subject);
        }
    }
}
