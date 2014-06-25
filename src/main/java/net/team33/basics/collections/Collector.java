package net.team33.basics.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import static java.util.Arrays.asList;

/**
 * An instrument to initialize collections in a declarative or iterative way.
 * <p/>
 * May be extended to a {@link net.team33.basics.Builder}.
 *
 * @param <E> The element type of the {@link Collection} to be built.
 * @param <C> The type of the {@link Collection} to be built.
 * @param <R> The 'final' type of the Collector implementation.
 */
@SuppressWarnings({"ReturnOfThis", "UnusedDeclaration", "ClassReferencesSubclass", "StaticMethodOnlyUsedInOneClass"})
public class Collector<E, C extends Collection<E>, R extends Collector<E, C, R>> {

    /**
     * Provides alternative access methods
     */
    @SuppressWarnings("PublicField")
    public final Alt alt = new Alt();
    private final C subject;

    protected Collector(final C subject) {
        // belongs to <this> and is intended to remain mutable ...
        // noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.subject = subject;
    }

    public static <E> Set<E> hashSet() {
        return new Set<>(new HashSet<E>(0));
    }

    public static <E> Set<E> hashSet(final Collection<? extends E> origin) {
        return new Set<>(new HashSet<>(origin));
    }

    public static <E> Set<E> linkedHashSet() {
        return new Set<>(new LinkedHashSet<E>(0));
    }

    public static <E> Set<E> linkedHashSet(final Collection<? extends E> origin) {
        return new Set<>(new LinkedHashSet<>(origin));
    }

    public static <E> Set<E> treeSet(final Comparator<? super E> order) {
        return new Set<>(new TreeSet<>(order));
    }

    public static <E> Set<E> treeSet(final Collection<? extends E> origin) {
        return new Set<>(new TreeSet<>(origin));
    }

    public static <E extends Enum<E>> Set<E> enumSet(final Class<E> enumClass) {
        return new Set<>(EnumSet.noneOf(enumClass));
    }

    public static <E extends Enum<E>> Set<E> enumSet(final Collection<E> origin) {
        return new Set<>(EnumSet.copyOf(origin));
    }

    public static <E> List<E> arrayList() {
        return new List<>(new ArrayList<E>(0));
    }

    public static <E> List<E> arrayList(final Collection<? extends E> origin) {
        return new List<>(new ArrayList<>(origin));
    }

    public static <E> List<E> linkedList() {
        return new List<>(new LinkedList<E>());
    }

    public static <E> List<E> linkedList(final Collection<? extends E> origin) {
        return new List<>(new LinkedList<>(origin));
    }

    public final C getSubject() {
        // Intended to supply the mutable instance to deal with ...
        // noinspection ReturnOfCollectionOrArrayField
        return subject;
    }

    public final R add(final E element) {
        Util.add(subject, element);
        // <this> is expected to be an instance of <R> ...
        // noinspection unchecked
        return (R) this;
    }

    public final R addAll(final Collection<? extends E> elements) {
        Util.addAll(subject, elements);
        // <this> is expected to be an instance of <R> ...
        // noinspection unchecked
        return (R) this;
    }

    public final R remove(final E element) {
        Util.remove(subject, element);
        // <this> is expected to be an instance of <R> ...
        // noinspection unchecked
        return (R) this;
    }

    public final R removeAll(final Collection<? extends E> elements) {
        Util.removeAll(subject, elements);
        // <this> is expected to be an instance of <R> ...
        // noinspection unchecked
        return (R) this;
    }

    public final R retainAll(final Collection<? extends E> elements) {
        Util.retainAll(subject, elements);
        // <this> is expected to be an instance of <R> ...
        // noinspection unchecked
        return (R) this;
    }

    public final R clear() {
        Util.clear(subject);
        // <this> is expected to be an instance of <R> ...
        // noinspection unchecked
        return (R) this;
    }

    private static class HashCollector<E> extends Collector<E, HashSet<E>, HashCollector<E>> {
        private HashCollector(final Collection<? extends E> origin) {
            super(new HashSet<>(origin));
        }
    }

    private static class LinkedHashCollector<E> extends Collector<E, LinkedHashSet<E>, LinkedHashCollector<E>> {
        private LinkedHashCollector(final Collection<? extends E> origin) {
            super(new LinkedHashSet<>(origin));
        }
    }

    private static class TreeCollector<E> extends Collector<E, TreeSet<E>, TreeCollector<E>> {
        private TreeCollector(final Collection<? extends E> origin) {
            super(new TreeSet<>(origin));
        }

        private TreeCollector(final Comparator<? super E> order) {
            super(new TreeSet<>(order));
        }
    }

    private static class EnumCollector<E extends Enum<E>> extends Collector<E, EnumSet<E>, EnumCollector<E>> {
        private EnumCollector(final Collection<E> origin) {
            super(EnumSet.copyOf(origin));
        }

        private EnumCollector(final Class<E> enumClass) {
            super(EnumSet.noneOf(enumClass));
        }
    }

    private static class ArrayCollector<E> extends Collector<E, ArrayList<E>, ArrayCollector<E>> {
        private ArrayCollector(final Collection<? extends E> origin) {
            super(new ArrayList<>(origin));
        }
    }

    private static class LinkedCollector<E> extends Collector<E, LinkedList<E>, LinkedCollector<E>> {
        private LinkedCollector(final Collection<? extends E> origin) {
            super(new LinkedList<>(origin));
        }
    }

    @SuppressWarnings("PublicInnerClass")
    public static class Set<E> extends Collector<E, java.util.Set<E>, Set<E>> {
        protected Set(final java.util.Set<E> subject) {
            super(subject);
        }
    }

    @SuppressWarnings("PublicInnerClass")
    public static class List<E> extends Collector<E, java.util.List<E>, List<E>> {
        protected List(final java.util.List<E> subject) {
            super(subject);
        }
    }

    /**
     * Provides alternative access methods
     */
    @SuppressWarnings({"PublicInnerClass", "NonStaticInnerClassInSecureContext"})
    public class Alt {
        private Alt() {
        }

        @SafeVarargs
        public final R add(final E... elements) {
            return addAll(asList(elements));
        }

        @SafeVarargs
        public final R remove(final E... elements) {
            return removeAll(asList(elements));
        }

        @SafeVarargs
        public final R retain(final E... elements) {
            return retainAll(asList(elements));
        }
    }
}
