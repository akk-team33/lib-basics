package net.team33.basics.collections;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Arrays.asList;

/**
 * Represents an instrument to initialize a map in a declarative style.
 *
 * @param <K> The key type of the {@link java.util.Map} to be built.
 * @param <V> The value type of the {@link java.util.Map} to be built.
 * @param <M> The type of the {@link java.util.Map} to be built.
 */
@SuppressWarnings({"ReturnOfThis", "StaticMethodOnlyUsedInOneClass"})
public class Mapper<K, V, M extends Map<K, V>, R extends Mapper<K, V, M, R>> {

    private final M subject;

    public Mapper(final M subject) {
        // belongs to <this> and is intended to remain mutable ...
        // noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.subject = subject;
    }

    private static <K, V> Map<K, V> asMap(final Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return new AsMap<>(entries);
    }

    public static <K, V, M extends Map<K, V>> Mapper<K, V, M, ?> pro(final M subject) {
        return new Simple<>(subject);
    }

    public static <K, V> Mapper<K, V, HashMap<K, V>, ?> proHashMap() {
        return pro(new HashMap<K, V>(0));
    }

    public static <K, V> Mapper<K, V, LinkedHashMap<K, V>, ?> proLinkedHashMap() {
        return pro(new LinkedHashMap<K, V>(0));
    }

    public static <K, V> Mapper<K, V, TreeMap<K, V>, ?> proTreeMap(final Comparator<? super K> order) {
        return pro(new TreeMap<K, V>(order));
    }

    public static <K extends Enum<K>, V> Mapper<K, V, EnumMap<K, V>, ?> proEnumMap(final Class<K> keyClass) {
        return pro(new EnumMap<K, V>(keyClass));
    }

    @SuppressWarnings("unchecked")
    private static <K, V, M extends Map<K, V>, R extends Mapper<K, V, M, R>> R cast(final Mapper<K, V, M, R> mapper) {
        return (R) mapper;
    }

    public final M getSubject() {
        // Intended to supply the mutable instance to deal with ...
        // noinspection ReturnOfCollectionOrArrayField
        return subject;
    }

    public final R put(final K key, final V value) {
        subject.put(key, value);
        return cast(this);
    }

    public final R put(final Map.Entry<? extends K, ? extends V> entry) {
        subject.put(entry.getKey(), entry.getValue());
        return cast(this);
    }

    public final R putAll(final Map<? extends K, ? extends V> origin) {
        subject.putAll(origin);
        return cast(this);
    }

    public final R putAll(final Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return putAll(asMap(entries));
    }

    @SafeVarargs
    public final R putAlt(final Map.Entry<? extends K, ? extends V>... entries) {
        return putAll(asList(entries));
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

    private static class Simple<K, V, M extends Map<K, V>> extends Mapper<K, V, M, Simple<K, V, M>> {
        private Simple(final M subject) {
            super(subject);
        }
    }

    private static class AsMap<K, V> extends AbstractMap<K, V> {
        @SuppressWarnings("rawtypes")
        private final Set entries;

        private AsMap(final Collection<? extends Entry<? extends K, ? extends V>> entries) {
            this.entries = newEntries(entries);
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Set newEntries(final Collection entries) {
            return new HashSet(entries);
        }

        @Override
        public final Set<Entry<K, V>> entrySet() {
            //noinspection unchecked,ReturnOfCollectionOrArrayField
            return entries;
        }
    }
}
