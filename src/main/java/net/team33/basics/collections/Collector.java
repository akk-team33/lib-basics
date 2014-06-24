package net.team33.basics.collections;

import net.team33.basics.Builder;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Abstracts a {@link Builder} for {@link Collection}s.
 *
 * @param <E> The element type of the {@link Collection} to be built.
 * @param <B> The specific type of {@link Collection} the instance itself is backed by.
 * @param <R> The result type of the {@link Builder}.
 * @param <C> The 'final' type of the Collector implementation.
 */
@SuppressWarnings("ReturnOfThis")
public abstract class Collector<E, B extends Collection<E>, R extends Collection<E>, C extends Collector<E, B, R, C>>
        implements Builder<R> {

    @SuppressWarnings("PublicField")
    public final Alt alt = new Alt();

    @SuppressWarnings("ProtectedField")
    protected final B backing;

    protected Collector(final B backing) {
        // belongs to <this> and is intended to remain mutable ... 
        // noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.backing = backing;
    }

    public final C add(final E element) {
        Util.add(backing, element);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C addAll(final Collection<? extends E> elements) {
        Util.addAll(backing, elements);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C remove(final E element) {
        Util.remove(backing, element);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C removeAll(final Collection<? extends E> elements) {
        Util.removeAll(backing, elements);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C retainAll(final Collection<? extends E> elements) {
        Util.retainAll(backing, elements);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C clear() {
        Util.clear(backing);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

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
