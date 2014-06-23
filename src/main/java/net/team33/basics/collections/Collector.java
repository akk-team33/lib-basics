package net.team33.basics.collections;

import java.util.Collection;

@SuppressWarnings("ReturnOfThis")
public abstract class Collector<E, C extends Collector<E, C>> {

    /**
     * Supplies the mutable (!) {@link Collection} this instance is backed by. Of course not {@code null}.
     */
    protected abstract Collection<E> getBacking();

    public final C add(final E element) {
        Util.add(getBacking(), element);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C addAll(final Collection<? extends E> elements) {
        Util.addAll(getBacking(), elements);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C remove(final E element) {
        Util.remove(getBacking(), element);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C removeAll(final Collection<? extends E> elements) {
        Util.removeAll(getBacking(), elements);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C retainAll(final Collection<? extends E> elements) {
        Util.retainAll(getBacking(), elements);
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }

    public final C clear() {
        Util.clear(getBacking());
        // <this> must be an instance of <C> ...
        // noinspection unchecked
        return (C) this;
    }
}
