package net.team33.basics.collections;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Collections.emptyMap;

@SuppressWarnings({"MapReplaceableByEnumMap", "AssertEqualsBetweenInconvertibleTypes"})
public class MapperTest {

    private static final String VALUE_01 = "value 01";
    private static final String VALUE_02 = "value 02";
    private static final String VALUE_03 = "value 03";
    private static final String VALUE_11 = "value 11";
    private static final String VALUE_12 = "value 12";
    private static final String VALUE_13 = "value 13";
    private static final Map<Key, String> ORIGIN_A = new HashMap<>(0);
    private static final Map<Key, String> ORIGIN_B = new TreeMap<>();
    private static final Map<Key, String> ORIGIN_C = new LinkedHashMap<>(0);
    private static final List<Map.Entry<Key, String>> ENTRIES_A = new LinkedList<>();
    private static final Comparator<Key> NATURAL_ORDER = null;

    static {
        ORIGIN_A.put(Key.KEY_01, VALUE_01);
        ORIGIN_A.put(Key.KEY_02, VALUE_02);
        ORIGIN_A.put(Key.KEY_03, VALUE_03);

        ENTRIES_A.addAll(ORIGIN_A.entrySet());

        ORIGIN_B.put(Key.KEY_11, VALUE_11);
        ORIGIN_B.put(Key.KEY_12, VALUE_12);
        ORIGIN_B.put(Key.KEY_13, VALUE_13);

        ORIGIN_C.putAll(ORIGIN_A);
        ORIGIN_C.putAll(ORIGIN_B);
    }

    @Test
    public final void testPut() {
        Assert.assertEquals(
                ORIGIN_A,
                Mapper.apply(new EnumMap<>(Key.class))
                        .put(Key.KEY_01, VALUE_01)
                        .put(Key.KEY_02, VALUE_02)
                        .put(Key.KEY_03, VALUE_03)
                        .getCore()
        );
    }

    @Test
    public final void testPut_Entry() {
        Assert.assertEquals(
                ORIGIN_A,
                Mapper.apply(new TreeMap<Key, String>())
                        .put(ENTRIES_A.get(0))
                        .put(ENTRIES_A.get(1))
                        .put(ENTRIES_A.get(2))
                        .getCore()
        );
    }

    @Test
    public final void testPutAll() {
        Assert.assertEquals(
                ORIGIN_A,
                Mapper.apply(new TreeMap<Key, String>())
                        .putAll(ORIGIN_A)
                        .getCore()
        );
    }

    @Test
    public final void testRemove() {
        Assert.assertEquals(
                emptyMap(),
                Mapper.apply(new LinkedHashMap<>(ORIGIN_A))
                        .remove(Key.KEY_01)
                        .remove(Key.KEY_02)
                        .remove(Key.KEY_03)
                        .getCore()
        );
    }

    @Test
    public final void testRemoveAll() {
        Assert.assertEquals(
                ORIGIN_B,
                Mapper.apply(new EnumMap<>(Key.class))
                        .putAll(ORIGIN_C)
                        .removeAll(ORIGIN_A.keySet())
                        .getCore()
        );
    }

    @Test
    public final void testAltRemove() {
        Assert.assertEquals(
                ORIGIN_B,
                Mapper.apply(new HashMap<>(0))
                        .putAll(ORIGIN_C)
                        .removeAlt(Key.KEY_01, Key.KEY_02, Key.KEY_03)
                        .getCore()
        );
    }

    @Test
    public final void testRetainAll() {
        Assert.assertEquals(
                ORIGIN_A,
                Mapper.apply(new HashMap<>(ORIGIN_C))
                        .retainAll(ORIGIN_A.keySet())
                        .getCore()
        );
    }

    @Test
    public final void testAltRetain() {
        Assert.assertEquals(
                ORIGIN_A,
                Mapper.apply(new HashMap<>(ORIGIN_C))
                        .retainAlt(Key.KEY_01, Key.KEY_02, Key.KEY_03)
                        .getCore()
        );
    }

    @Test
    public final void testClear() {
        Assert.assertEquals(
                emptyMap(),
                Mapper.apply(new LinkedHashMap<>(0))
                        .putAll(ORIGIN_A)
                        .clear()
                        .getCore()
        );
    }

    private enum Key {
        KEY_01,
        KEY_02,
        KEY_03,
        KEY_11,
        KEY_12,
        KEY_13
    }
}