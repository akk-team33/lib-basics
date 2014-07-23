package net.team33.basics.collections.collecting;

import net.team33.basics.collections.Collecting;
import net.team33.basics.collections.Mapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;
import static net.team33.basics.collections.Collecting.proxy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link Collecting#proxy(Map)}
 */
public class MapProxyTest {

    private static final Map<Integer, String> BACKING = unmodifiableMap(
            Mapper.apply(new HashMap<Integer, String>(0))
                    .put(1, "1")
                    .put(2, "2")
                    .put(3, "3")
                    .put(4, "4")
                    .put(101, null)
                    .put(102, null)
                    .subject
    );

    private HashMap<Integer, String> backing;
    private Subject<Integer, String> subject;

    private static <K, V> void remove(final Map<K, V> backing, final V value) {
        remove(backing.entrySet().iterator(), value);
    }

    private static <K, V> void remove(final Iterator<Map.Entry<K, V>> iterator, final V value) {
        while (iterator.hasNext()) {
            final Map.Entry<K, V> next = iterator.next();
            if (Objects.equals(value, next.getValue())) {
                iterator.remove();
            }
        }
    }

    @Before
    public final void before() {
        backing = new HashMap<>(BACKING);
        subject = new Subject<>(backing);
    }

    @Test
    public final void testSize() {
        assertEquals(BACKING.size(), subject.size());
    }

    @Test
    public final void testIsEmpty() {
        assertFalse(subject.isEmpty());
        backing.clear();
        assertTrue(subject.isEmpty());
    }

    @Test
    public final void testContainsKey() {
        for (final int key : asList(2, 101, 102)) {
            assertTrue(subject.containsKey(key));
            backing.remove(key);
            assertFalse(subject.containsKey(key));
        }
    }

    @Test
    public final void testContainsValue() {
        for (final String value : asList("3", null)) {
            assertTrue(subject.containsValue(value));
            remove(backing, value);
            assertFalse(subject.containsValue(value));
        }
    }

    @Test
    public final void testGet() {
        for (final Integer key : asList(0, 1, 2, 3, 4, 5, null)) {
            assertEquals(BACKING.get(key), subject.get(key));
        }
    }

    @Test
    public final void testProxy() {

    }

    /**
     * Implements each Map specific method but {@link Map#entrySet()} by delegating to {@link Collecting#proxy(Map)}.
     */
    private static class Subject<K, V> implements Map<K, V> {
        private final Map<K, V> backing;

        private Subject(final Map<K, V> backing) {
            this.backing = backing;
        }

        @Override
        public final int size() {
            return proxy(this).size();
        }

        @Override
        public final boolean isEmpty() {
            return proxy(this).isEmpty();
        }

        @Override
        public final boolean containsKey(final Object key) {
            return proxy(this).containsKey(key);
        }

        @Override
        public final boolean containsValue(final Object value) {
            return proxy(this).containsValue(value);
        }

        @Override
        public final V get(final Object key) {
            return proxy(this).get(key);
        }

        @Override
        public final V put(final K key, final V value) {
            return proxy(this).put(key, value);
        }

        @Override
        public final V remove(final Object key) {
            return proxy(this).remove(key);
        }

        @Override
        public final void putAll(final Map<? extends K, ? extends V> m) {
            proxy(this).putAll(m);
        }

        @Override
        public final void clear() {
            proxy(this).clear();
        }

        @Override
        public final Set<K> keySet() {
            return proxy(this).keySet();
        }

        @Override
        public final Collection<V> values() {
            return proxy(this).values();
        }

        @Override
        public final Set<Entry<K, V>> entrySet() {
            return backing.entrySet();
        }
    }
}
