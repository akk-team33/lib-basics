package net.team33.basics.collections;

import net.team33.basics.Builder;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Abstracts a {@link Builder} for {@link Collection}s.
 * <p/>
 * Must not be derived directly but through {@link Collector.Base}
 *
 * @param <E> The element type of the {@link Collection} to be built.
 * @param <R> The result type of the {@link Builder}.
 * @param <C> The 'final' type of the Collector implementation.
 */
@SuppressWarnings("ReturnOfThis")
public abstract class Collector<E, R extends Collection<E>, C extends Collector<E, R, C>>
        implements Builder<R> {

    /**
     * Provides alternative access methods
     */
    @SuppressWarnings("PublicField")
    public final Alt alt = new Alt();

    /**
     * Must not be derived directly but through {@link Base}
     */
    private Collector() {
    }

    public abstract C add(E element);

    public abstract C addAll(Collection<? extends E> elements);

    public abstract C remove(E element);

    public abstract C removeAll(Collection<? extends E> elements);

    public abstract C retainAll(Collection<? extends E> elements);

    public abstract C clear();

    /**
     * Basic {@link Collector} implementation.
     *
     * @param <E> The element type of the {@link Collection} to be built.
     * @param <B> The specific type of {@link Collection} the instance itself is backed by.
     * @param <R> The result type of the {@link Builder}.
     * @param <C> The 'final' type of the Collector implementation.
     */
    @SuppressWarnings({"ReturnOfThis", "PublicInnerClass"})
    public abstract static class Base<E, B extends Collection<E>, R extends Collection<E>, C extends Base<E, B, R, C>>
            extends Collector<E, R, C> {

        @SuppressWarnings("ProtectedField")
        protected final B backing;

        protected Base(final B backing) {
            // belongs to <this> and is intended to remain mutable ...
            // noinspection AssignmentToCollectionOrArrayFieldFromParameter
            this.backing = backing;
        }

        @Override
        public final C add(final E element) {
            Util.add(backing, element);
            // <this> is expected to be an instance of <C> ...
            // noinspection unchecked
            return (C) this;
        }

        @Override
        public final C addAll(final Collection<? extends E> elements) {
            Util.addAll(backing, elements);
            // <this> is expected to be an instance of <C> ...
            // noinspection unchecked
            return (C) this;
        }

        @Override
        public final C remove(final E element) {
            Util.remove(backing, element);
            // <this> is expected to be an instance of <C> ...
            // noinspection unchecked
            return (C) this;
        }

        @Override
        public final C removeAll(final Collection<? extends E> elements) {
            Util.removeAll(backing, elements);
            // <this> is expected to be an instance of <C> ...
            // noinspection unchecked
            return (C) this;
        }

        @Override
        public final C retainAll(final Collection<? extends E> elements) {
            Util.retainAll(backing, elements);
            // <this> is expected to be an instance of <C> ...
            // noinspection unchecked
            return (C) this;
        }

        @Override
        public final C clear() {
            Util.clear(backing);
            // <this> is expected to be an instance of <C> ...
            // noinspection unchecked
            return (C) this;
        }
    }

    /**
     * Provides alternative access methods
     */
    @SuppressWarnings({"PublicInnerClass", "NonStaticInnerClassInSecureContext"})
    public class Alt {
        private Alt() {
        }

        @SafeVarargs
        public final C add(final E... elements) {
            return addAll(asList(elements));
        }

        @SafeVarargs
        public final C remove(final E... elements) {
            return removeAll(asList(elements));
        }

        @SafeVarargs
        public final C retain(final E... elements) {
            return retainAll(asList(elements));
        }
    }
}
