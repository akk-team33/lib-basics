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
 * Represents an instrument to build maps in a declarative style.
 *
 * @param <K> The key type of the {@link java.util.Map} to be built.
 * @param <V> The value type of the {@link java.util.Map} to be built.
 * @param <M> The type of the {@link java.util.Map} to be built.
 */
@SuppressWarnings("ReturnOfThis")
public class Mapper<K, V, M extends Map<K, V>> {

    /**
     * Provides alternative access methods
     */
    @SuppressWarnings("PublicField")
    public final Alt alt = new Alt();
    private final Map<K, V> subject;

    public Mapper(final Map<K, V> subject) {
        // belongs to <this> and is intended to remain mutable ...
        // noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.subject = subject;
    }

    private static <K, V> Map<K, V> asMap(final Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return new AsMap<>(entries);
    }

    public static <K, V, M extends Map<K, V>> Mapper<K, V, M> by(final M subject) {
        return new Mapper<>(subject);
    }

    public static <K, V> Mapper<K, V, HashMap<K, V>> byHashMap() {
        return by(new HashMap<K, V>(0));
    }

    public static <K, V> Mapper<K, V, LinkedHashMap<K, V>> byLinkedHashMap() {
        return by(new LinkedHashMap<K, V>(0));
    }

    public static <K, V> Mapper<K, V, TreeMap<K, V>> byTreeMap(final Comparator<? super K> order) {
        return by(new TreeMap<K, V>(order));
    }

    public static <K extends Enum<K>, V> Mapper<K, V, EnumMap<K, V>> byEnumMap(final Class<K> keyClass) {
        return by(new EnumMap<K, V>(keyClass));
    }

    public final Map<K, V> getSubject() {
        // Intended to supply the mutable instance to deal with ...
        // noinspection ReturnOfCollectionOrArrayField
        return subject;
    }

    public final Mapper<K, V, M> put(final K key, final V value) {
        subject.put(key, value);
        return this;
    }

    public final Mapper<K, V, M> put(final Map.Entry<? extends K, ? extends V> entry) {
        subject.put(entry.getKey(), entry.getValue());
        return this;
    }

    public final Mapper<K, V, M> putAll(final Map<? extends K, ? extends V> origin) {
        subject.putAll(origin);
        return this;
    }

    public final Mapper<K, V, M> putAll(final Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return putAll(asMap(entries));
    }

    public final Mapper<K, V, M> remove(final K key) {
        subject.remove(key);
        return this;
    }

    public final Mapper<K, V, M> removeAll(final Collection<? extends K> keys) {
        subject.keySet().removeAll(keys);
        return this;
    }

    public final Mapper<K, V, M> retainAll(final Collection<? extends K> keys) {
        subject.keySet().retainAll(keys);
        return this;
    }

    public final Mapper<K, V, M> clear() {
        subject.clear();
        return this;
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

    /**
     * Provides alternative access methods
     */
    @SuppressWarnings({"PublicInnerClass", "NonStaticInnerClassInSecureContext"})
    public class Alt {

        @SafeVarargs
        public final Mapper<K, V, M> put(final Map.Entry<? extends K, ? extends V>... entries) {
            return putAll(asList(entries));
        }

        @SafeVarargs
        public final Mapper<K, V, M> remove(final K... key) {
            return removeAll(asList(key));
        }

        @SafeVarargs
        public final Mapper<K, V, M> retain(final K... key) {
            return retainAll(asList(key));
        }
    }
}
