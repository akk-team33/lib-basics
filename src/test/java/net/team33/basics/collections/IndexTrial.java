package net.team33.basics.collections;

import com.google.common.base.Function;
import org.junit.Test;

import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SuppressWarnings({"SuspiciousMethodCalls", "JUnitTestClassNamingConvention",
        "OverloadedMethodsWithSameNumberOfParameters", "ClassWithTooManyFields", "ClassWithTooManyMethods"})
public class IndexTrial {

    static final char[] CHARS;
    static final List<String> ELEMENTS;
    static final Set<Object> SAMPLES;

    static {
        //noinspection SpellCheckingInspection
        CHARS = "123456789 -@*. abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        final Random random = new Random();
        ELEMENTS = newElements(random, 200000);
        SAMPLES = newSamples(random, ELEMENTS, 200);
    }

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_00
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return input;
        }
    };

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_01
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return new ArrayList<>(input);
        }
    };

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_02
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return new LinkedList<>(input);
        }
    };

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_03
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return IndexList.from(input);
        }
    };

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_04
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return IndexSet.from(input);
        }
    };

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_05
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return new HashSet<>(input);
        }
    };

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_06
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return new LinkedHashSet<>(input);
        }
    };

    @SuppressWarnings("AnonymousInnerClass")
    private static final Function<List<String>, Collection<String>> CREATE_07
            = new Function<List<String>, Collection<String>>() {
        @Override
        public Collection<String> apply(final List<String> input) {
            return new TreeSet<>(input);
        }
    };

    private static Set<Object> newSamples(final Random random, final List<String> elements, final int size) {
        final Set<Object> result = new HashSet<>(size);
        result.addAll(asList(0, -1, Integer.MAX_VALUE, Integer.MIN_VALUE));
        result.addAll(asList("another string", new Object(), new Date(), null));
        while (size > result.size()) {
            final String element = elements.get(random.nextInt(elements.size()));
            result.add(Objects.hashCode(element));
            result.add(~Objects.hashCode(element));
            result.add(element);
        }
        return result;
    }

    private static List<String> newElements(final Random random, final int size) {
        final List<String> result = new ArrayList<>(size);
        result.add("");
        final int halfSize = size / 2;
        while (halfSize > result.size()) {
            for (int len = 1; 100 > len; ++len) {
                result.add(newString(random, 1 + random.nextInt(len)));
            }
        }
        while (size > result.size()) {
            result.add(result.get(random.nextInt(result.size())));
        }
        return result;
    }

    private static String newString(final Random random, final int length) {
        final char[] chars = new char[length];
        for (int i = 0; i < length; ++i) {
            chars[i] = CHARS[random.nextInt(CHARS.length)];
        }
        return new String(chars);
    }

    private static long timeContains(final Collection<?> subject) {
        final long time0 = System.nanoTime();
        for (final Object sample : SAMPLES) {
            Collecting.contains(subject, sample);
        }
        final long timeX = System.nanoTime();
        return timeX - time0;
    }

    private static int count(final List<String> elements, final Object sample) {
        final int index = elements.indexOf(sample);
        if (0 > index) {
            return 0;
        } else {
            return 1 + count(elements.subList(index + 1, elements.size()), sample);
        }
    }

    private static int count(final Iterable<String> elements, final int hashCode) {
        int result = 0;
        for (final String element : elements) {
            if (hashCode == Objects.hashCode(element)) {
                result += 1;
            }
        }
        return result;
    }

    private static <E> long timeCreate(
            final List<E> elements, final Function<List<E>, Collection<E>> create) {
        final long time0 = System.nanoTime();
        create.apply(elements);
        final long timeX = System.nanoTime();
        return timeX - time0;
    }

    @SuppressWarnings({"resource", "UseOfSystemOutOrSystemErr"})
    @Test
    public final void statistics() {
        final StringBuilder lines = new StringBuilder(0);
        lines.append(String.format(
                "SAMPLES.size() = %d%n",
                SAMPLES.size()));
        for (final Object sample : SAMPLES) {
            final int hashCode = Objects.hashCode(sample);
            lines.append(String.format(
                    "sample <%s>, hash <%d>, value count <%d>, hash count <%d>%n",
                    sample, hashCode, count(ELEMENTS, sample), count(ELEMENTS, hashCode)));
        }
        fail(lines.toString());
    }

    @Test
    public final void testCreate_00_byElements() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_00)
        );
    }

    @Test
    public final void testContains_00_byElements() {
        assertEquals("subject.size() == " + ELEMENTS.size(), 0, timeContains(ELEMENTS));
    }

    @Test
    public final void testCreate_01_byArrayList() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_01)
        );
    }

    @Test
    public final void testContains_01_byArrayList() {
        final ArrayList<String> subject = new ArrayList<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testCreate_02_byLinkedList() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_02)
        );
    }

    @Test
    public final void testContains_02_byLinkedList() {
        final LinkedList<String> subject = new LinkedList<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testCreate_03_byIndexList() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_03)
        );
    }

    @Test
    public final void testContains_03_byIndexList() {
        final IndexList<String> subject = IndexList.from(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testCreate_04_byIndexSet() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_04)
        );
    }

    @Test
    public final void testContains_04_byIndexSet() {
        final IndexSet<String> subject = IndexSet.from(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testCreate_05_byHashSet() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_05)
        );
    }

    @Test
    public final void testContains_05_byHashSet() {
        final HashSet<String> subject = new HashSet<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testCreate_06_byLinkedHashSet() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_06)
        );
    }

    @Test
    public final void testContains_06_byLinkedHashSet() {
        final HashSet<String> subject = new LinkedHashSet<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @Test
    public final void testCreate_07_byTreeSet() {
        assertEquals(
                "subject.size() == " + ELEMENTS.size(),
                0L, timeCreate(ELEMENTS, CREATE_07)
        );
    }

    @Test
    public final void testContains_07_byTreeSet() {
        final TreeSet<String> subject = new TreeSet<>(ELEMENTS);
        assertEquals("subject.size() == " + subject.size(), 0, timeContains(subject));
    }

    @SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "RefusedBequest"})
    static class IndexList<E> extends AbstractList<E> {

        private final List<E> backing;
        private final Index index;

        private IndexList(final List<E> backing) {
            this.backing = backing;
            this.index = new Index(backing);
        }

        @SuppressWarnings("OverloadedVarargsMethod")
        @SafeVarargs
        static <E> IndexList<E> from(final E... values) {
            return from(asList(values));
        }

        static <E> IndexList<E> from(final List<E> backing) {
            return new IndexList<>(backing);
        }

        @SuppressWarnings("ParameterHidesMemberVariable")
        @Override
        public final E get(final int index) {
            return backing.get(index);
        }

        @Override
        public final int indexOf(final Object o) {
            return index.first(o);
        }

        @Override
        public final int lastIndexOf(final Object o) {
            return index.last(o);
        }

        @Override
        public final boolean contains(final Object o) {
            return index.contains(o);
        }

        @Override
        public final int size() {
            return backing.size();
        }
    }

    @SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "RefusedBequest"})
    static class IndexSet<E> extends AbstractSet<E> {

        private final List<E> backing;
        private final Index index;

        private IndexSet(final Set<E> origin) {
            backing = new ArrayList<>(origin);
            index = new Index(backing);
        }

        static <E> IndexSet<E> from(final Collection<? extends E> origin) {
            return new IndexSet<>(new LinkedHashSet<>(origin));
        }

        @Override
        public final boolean contains(final Object o) {
            return index.contains(o);
        }

        @Override
        public final Iterator<E> iterator() {
            return backing.iterator();
        }

        @Override
        public final int size() {
            return backing.size();
        }
    }
}
