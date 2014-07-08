package net.team33.basics.collections;

import java.util.Collection;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.simpleName;

/**
 * Implements an instrument to initialize a {@link Map} in a declarative style.
 *
 * @param <K> The key type of the {@link Map} to be built.
 * @param <V> The value type of the {@link Map} to be built.
 * @param <M> The type of the {@link Map} to be built.
 * @param <R> The finally intended type of the {@link Mapper} itself.
 */
@SuppressWarnings({"ReturnOfThis", "StaticMethodOnlyUsedInOneClass"})
public class Mapper<K, V, M extends Map<K, V>, R extends Mapper<K, V, M, R>> {

    /**
     * The subject to be initialized/modified.
     */
    @SuppressWarnings("PublicField")
    public final M subject;

    /**
     * Initiates a new instance giving the {@code subject} to be initialized.
     * <p/>
     * Mentioned to be used by a derivative. Use {@link #apply(Map)} to straightly create a new Instance.
     *
     * @param subject a mutable {@link Map} that is mentioned to be modified through the new instance.
     */
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter") // by intention
    protected Mapper(final M subject) {
        this.subject = requireNonNull(subject);
    }

    public static <K, V, M extends Map<K, V>> Mapper<K, V, M, ?> apply(final M subject) {
        return new Simple<>(subject);
    }

    /**
     * Supplies a {@code mapper} in a finally intended representation.
     * Intended to {@code return this}.
     * May lead to a {@link ClassCastException} if used in an improper context.
     */
    @SuppressWarnings("unchecked")
    private static <R extends Mapper<?, ?, ?, R>> R cast(final Mapper<?, ?, ?, R> mapper) {
        return (R) mapper;
    }

    public final R put(final K key, final V value) {
        subject.put(key, value);
        return cast(this);
    }

    public final R put(final Map.Entry<? extends K, ? extends V> entry) {
        return put(entry.getKey(), entry.getValue());
    }

    public final R putAll(final Map<? extends K, ? extends V> origin) {
        subject.putAll(origin);
        return cast(this);
    }

    public final R remove(final K key) {
        subject.remove(key);
        return cast(this);
    }

    public final R removeAll(final Collection<? extends K> keys) {
        subject.keySet().removeAll(keys);
        return cast(this);
    }

    @SafeVarargs
    public final R removeAlt(final K... keys) {
        return removeAll(asList(keys));
    }

    public final R retainAll(final Collection<? extends K> keys) {
        subject.keySet().retainAll(keys);
        return cast(this);
    }

    @SafeVarargs
    public final R retainAlt(final K... keys) {
        return retainAll(asList(keys));
    }

    public final R clear() {
        subject.clear();
        return cast(this);
    }

    @Override
    public final String toString() {
        return simpleName(getClass()) + subject;
    }

    private static class Simple<K, V, M extends Map<K, V>> extends Mapper<K, V, M, Simple<K, V, M>> {
        private Simple(final M subject) {
            super(subject);
        }
    }
}
