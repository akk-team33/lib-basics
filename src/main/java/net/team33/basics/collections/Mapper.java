package net.team33.basics.collections;

import java.util.Collection;
import java.util.Map;

import static java.util.Arrays.asList;
import static net.team33.basics.collections.Package.simpleName;

/**
 * Represents an instrument to initialize a map in a declarative style.
 *
 * @param <K> The key type of the {@link Map} to be built.
 * @param <V> The value type of the {@link Map} to be built.
 * @param <M> The type of the {@link Map} to be built.
 * @param <R> The 'final' type of the {@link Mapper} itself.
 */
@SuppressWarnings({"ReturnOfThis", "StaticMethodOnlyUsedInOneClass"})
public class Mapper<K, V, M extends Map<K, V>, R extends Mapper<K, V, M, R>> {

    private final M core;

    /**
     * Initiates a new instance giving the {@code core} to be initialized.
     * <p/>
     * Mentioned to be used by a derivative. Use {@link #apply(Map)} to straightly create a new Instance.
     *
     * @param core a mutable {@link Collection} that is mentioned to be modified through the new instance.
     */
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter") // by intention
    protected Mapper(final M core) {
        this.core = core;
    }

    public static <K, V, M extends Map<K, V>> Mapper<K, V, M, ?> apply(final M subject) {
        return new Simple<>(subject);
    }

    @SuppressWarnings("unchecked")
    private static <K, V, M extends Map<K, V>, R extends Mapper<K, V, M, R>> R cast(final Mapper<K, V, M, R> mapper) {
        return (R) mapper;
    }

    public final M getCore() {
        // Intended to supply the mutable instance to deal with ...
        // noinspection ReturnOfCollectionOrArrayField
        return core;
    }

    public final R put(final K key, final V value) {
        core.put(key, value);
        return cast(this);
    }

    public final R put(final Map.Entry<? extends K, ? extends V> entry) {
        return put(entry.getKey(), entry.getValue());
    }

    public final R putAll(final Map<? extends K, ? extends V> origin) {
        core.putAll(origin);
        return cast(this);
    }

    public final R remove(final K key) {
        core.remove(key);
        return cast(this);
    }

    public final R removeAll(final Collection<? extends K> keys) {
        core.keySet().removeAll(keys);
        return cast(this);
    }

    @SafeVarargs
    public final R removeAlt(final K... keys) {
        return removeAll(asList(keys));
    }

    public final R retainAll(final Collection<? extends K> keys) {
        core.keySet().retainAll(keys);
        return cast(this);
    }

    @SafeVarargs
    public final R retainAlt(final K... keys) {
        return retainAll(asList(keys));
    }

    public final R clear() {
        core.clear();
        return cast(this);
    }

    @Override
    public final String toString() {
        return simpleName(getClass()) + core;
    }

    private static class Simple<K, V, M extends Map<K, V>> extends Mapper<K, V, M, Simple<K, V, M>> {
        private Simple(final M subject) {
            super(subject);
        }
    }
}
