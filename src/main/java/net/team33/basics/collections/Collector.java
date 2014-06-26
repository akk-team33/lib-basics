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
 * Represents an instrument to initialize collections in a declarative style.
 *
 * @param <E> The element type of the {@link Collection} to be built.
 * @param <C> The type of the {@link Collection} to be built.
 */
@SuppressWarnings({"ReturnOfThis", "UnusedDeclaration", "ClassReferencesSubclass", "StaticMethodOnlyUsedInOneClass"})
public class Collector<E, C extends Collection<E>> {

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

    public static <E, C extends Collection<E>> Collector<E, C> by(final C subject) {
        return new Collector<>(subject);
    }

    public static <E> Collector<E, HashSet<E>> byHashSet() {
        return by(new HashSet<E>(0));
    }

    public static <E> Collector<E, HashSet<E>> byHashSet(final Collection<? extends E> origin) {
        return by(new HashSet<>(origin));
    }

    public static <E> Collector<E, LinkedHashSet<E>> byLinkedHashSet() {
        return by(new LinkedHashSet<E>(0));
    }

    public static <E> Collector<E, LinkedHashSet<E>> byLinkedHashSet(final Collection<? extends E> origin) {
        return by(new LinkedHashSet<>(origin));
    }

    public static <E> Collector<E, TreeSet<E>> byTreeSet(final Comparator<? super E> order) {
        return by(new TreeSet<>(order));
    }

    public static <E> Collector<E, TreeSet<E>> byTreeSet(final Collection<? extends E> origin) {
        return by(new TreeSet<>(origin));
    }

    public static <E extends Enum<E>> Collector<E, EnumSet<E>> byEnumSet(final Class<E> enumClass) {
        return by(EnumSet.noneOf(enumClass));
    }

    public static <E extends Enum<E>> Collector<E, EnumSet<E>> byEnumSet(final Collection<E> origin) {
        return by(EnumSet.copyOf(origin));
    }

    public static <E> Collector<E, ArrayList<E>> byArrayList() {
        return by(new ArrayList<E>(0));
    }

    public static <E> Collector<E, ArrayList<E>> byArrayList(final Collection<? extends E> origin) {
        return by(new ArrayList<>(origin));
    }

    public static <E> Collector<E, LinkedList<E>> byLinkedList() {
        return by(new LinkedList<E>());
    }

    public static <E> Collector<E, LinkedList<E>> byLinkedList(final Collection<? extends E> origin) {
        return by(new LinkedList<>(origin));
    }

    public final C getSubject() {
        // Intended to supply the mutable instance to deal with ...
        // noinspection ReturnOfCollectionOrArrayField
        return subject;
    }

    public final Collector<E, C> add(final E element) {
        Util.add(subject, element);
        return this;
    }

    public final Collector<E, C> addAll(final Collection<? extends E> elements) {
        Util.addAll(subject, elements);
        return this;
    }

    public final Collector<E, C> remove(final E element) {
        Util.remove(subject, element);
        return this;
    }

    public final Collector<E, C> removeAll(final Collection<? extends E> elements) {
        Util.removeAll(subject, elements);
        return this;
    }

    public final Collector<E, C> retainAll(final Collection<? extends E> elements) {
        Util.retainAll(subject, elements);
        return this;
    }

    public final Collector<E, C> clear() {
        Util.clear(subject);
        return this;
    }

    /**
     * Provides alternative access methods
     */
    @SuppressWarnings({"PublicInnerClass", "NonStaticInnerClassInSecureContext"})
    public class Alt {
        private Alt() {
        }

        @SafeVarargs
        public final Collector<E, C> add(final E... elements) {
            return addAll(asList(elements));
        }

        @SafeVarargs
        public final Collector<E, C> remove(final E... elements) {
            return removeAll(asList(elements));
        }

        @SafeVarargs
        public final Collector<E, C> retain(final E... elements) {
            return retainAll(asList(elements));
        }
    }
}
