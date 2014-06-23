package net.team33.basics.collections;

import com.google.common.base.Supplier;
import net.team33.basics.Builder;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("ReturnOfThis")
public class Collector<E, R extends Collection<E>, B extends Collector<E, R, B>> implements Builder<R> {

    private final Supplier<R> newResult;
    private final R backing;

    public Collector(final Supplier<R> newResult) {
        this.newResult = requireNonNull(newResult);
        backing = newResult.get();
    }

    public final B add(final E element) {
        Util.add(backing, element);
        // <this> must be an instance of <B> ...
        // noinspection unchecked
        return (B) this;
    }

    public final B addAll(final Collection<? extends E> elements) {
        Util.addAll(backing, elements);
        // <this> must be an instance of <B> ...
        // noinspection unchecked
        return (B) this;
    }

    public final B remove(final E element) {
        Util.remove(backing, element);
        // <this> must be an instance of <B> ...
        // noinspection unchecked
        return (B) this;
    }

    public final B removeAll(final Collection<? extends E> elements) {
        Util.removeAll(backing, elements);
        // <this> must be an instance of <B> ...
        // noinspection unchecked
        return (B) this;
    }

    public final B retainAll(final Collection<? extends E> elements) {
        Util.retainAll(backing, elements);
        // <this> must be an instance of <B> ...
        // noinspection unchecked
        return (B) this;
    }

    public final B clear() {
        Util.clear(backing);
        // <this> must be an instance of <B> ...
        // noinspection unchecked
        return (B) this;
    }

    @Override
    public final R build() {
        return Util.<E, R>addAll(newResult.get(), backing);
    }
}
