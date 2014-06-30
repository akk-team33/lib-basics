package net.team33.basics.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/**
 * Abstracts an instrument to initialize a {@link Collection} in a declarative style.
 *
 * @param <E> The element type of the {@link Collection} to be initialized.
 * @param <C> The type of the {@link Collection} to be initialized.
 * @param <R> The 'final' type of the {@link Collector} itself.
 */
@SuppressWarnings({"ClassWithTooManyMethods", "StaticMethodOnlyUsedInOneClass"})
public class Collector<E, C extends Collection<E>, R extends Collector<E, C, R>> {

    private final C subject;

    /**
     * Initiates a new instance giving the {@code subject} to be built.
     *
     * @param subject a mutable {@link Collection} that is mentioned to be modified through the new instance.
     */
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter") // by intention
    protected Collector(final C subject) {
        this.subject = requireNonNull(subject);
    }

    public static <E, C extends Collection<E>> Collector<E, C, ?> pro(final C subject) {
        return new Simple<>(subject);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E>
    Collector<E, HashSet<E>, ?> proHashSet(final E... elements) {
        return proHashSet(asList(elements));
    }

    public static <E>
    Collector<E, HashSet<E>, ?> proHashSet(final Collection<? extends E> origin) {
        return pro(new HashSet<>(origin));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E>
    Collector<E, LinkedHashSet<E>, ?> proLinkedHashSet(final E... elements) {
        return proLinkedHashSet(asList(elements));
    }

    public static <E>
    Collector<E, LinkedHashSet<E>, ?> proLinkedHashSet(final Collection<? extends E> origin) {
        return pro(new LinkedHashSet<>(origin));
    }

    public static <E>
    Collector<E, TreeSet<E>, ?> proTreeSet(final Comparator<? super E> order) {
        return pro(new TreeSet<>(order));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E extends Comparable<E>>
    Collector<E, TreeSet<E>, ?> proTreeSet(final E... elements) {
        return proTreeSet(asList(elements));
    }

    public static <E extends Comparable<E>>
    Collector<E, TreeSet<E>, ?> proTreeSet(final Collection<? extends E> origin) {
        return pro(new TreeSet<>(origin));
    }

    public static <E extends Enum<E>>
    Collector<E, EnumSet<E>, ?> proEnumSet(final Class<E> enumClass) {
        return pro(EnumSet.noneOf(enumClass));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E extends Enum<E>>
    Collector<E, EnumSet<E>, ?> proEnumSet(final E... elements) {
        return proEnumSet(asList(elements));
    }

    public static <E extends Enum<E>>
    Collector<E, EnumSet<E>, ?> proEnumSet(final Collection<E> origin) {
        return pro(EnumSet.copyOf(origin));
    }

    @SuppressWarnings("unchecked")
    protected static <E, C extends Collection<E>, R extends Collector<E, C, R>>
    R cast(final Collector<E, C, R> collector) {
        return (R) collector;
    }

    /**
     * Supplies the underlying {@link Collection} in its original representation. Not {@code null}.
     */
    public final C getSubject() {
        // Intended to supply the mutable instance as is ...
        // noinspection ReturnOfCollectionOrArrayField
        return subject;
    }

    /**
     * Substitutes {@link Collection#add(Object)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#add(Object)} is not supported by the underlying
     *                                       {@link #getSubject() subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code element} prevents it from being added
     *                                       to the underlying {@link #getSubject() subject}.
     * @throws NullPointerException          if the specified {@code element} is {@code null} and the underlying
     *                                       {@link #getSubject() subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the underlying {@link #getSubject() subject}.
     * @throws IllegalStateException         if the {@code element} cannot be added at this time due to
     *                                       the {@code subject}'s insertion restrictions (if any).
     */
    public final R add(final E element) {
        Util.add(subject, element);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #getSubject() subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}.
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@code array} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #getSubject() subject} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #getSubject() subject}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #getSubject() subject}'s insertion restrictions (if any).
     */
    @SafeVarargs
    public final R addAlt(final E... elements) {
        return addAll(asList(elements));
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #getSubject() subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}.
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@link Collection} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #getSubject() subject} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #getSubject() subject}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #getSubject() subject}'s insertion restrictions (if any).
     */
    public final R addAll(final Collection<? extends E> elements) {
        Util.addAll(subject, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#remove(Object)} for the underlying {@link #getSubject() subject}.
     * <p/>
     * In contrast to that, not just the first but any occurrence of the requested {@code element} will be removed!
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#remove(Object)} is not supported by the underlying
     *                                       {@link #getSubject() subject}.
     */
    public final R remove(final E element) {
        Util.remove(subject, element);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#removeAll(Collection)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       underlying {@link #getSubject() subject}.
     * @throws NullPointerException          if the {@code array} of {@code elements} is {@code null}.
     */
    @SafeVarargs
    public final R removeAlt(final E... element) {
        return removeAll(asList(element));
    }

    /**
     * Substitutes {@link Collection#removeAll(Collection)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       underlying {@link #getSubject() subject}.
     * @throws NullPointerException          if the {@link Collection} of {@code elements} is {@code null}.
     */
    public final R removeAll(final Collection<? extends E> elements) {
        Util.removeAll(subject, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#retainAll(Collection)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #getSubject() subject}.
     * @throws NullPointerException          if the {@code array} of {@code elements} is {@code null}.
     */
    @SafeVarargs
    public final R retainAlt(final E... elements) {
        return retainAll(asList(elements));
    }

    /**
     * Substitutes {@link Collection#retainAll(Collection)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #getSubject() subject}.
     * @throws NullPointerException          if the {@link Collection} of {@code elements} is {@code null}.
     */
    public final R retainAll(final Collection<? extends E> elements) {
        Util.retainAll(subject, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#clear()} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #getSubject() subject}.
     */
    public final R clear() {
        Util.clear(subject);
        return cast(this);
    }

    private static class Simple<E, C extends Collection<E>> extends Collector<E, C, Simple<E, C>> {
        private Simple(final C subject) {
            super(subject);
        }
    }
}
