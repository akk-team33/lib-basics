package net.team33.basics.collections;

import com.google.common.base.Function;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.simpleName;

/**
 * Implements an instrument to initialize/modify a {@link Collection} in a declarative style.
 *
 * @param <E> The element type of the {@link Collection} to be initialized.
 * @param <C> The type of the {@link Collection} to be initialized.
 * @param <R> The finally intended type of the {@link Collector} itself.
 */
public class Collector<E, C extends Collection<E>, R extends Collector<E, C, R>> {

    /**
     * The subject to be initialized/modified.
     */
    @SuppressWarnings("PublicField")
    public final C subject;

    /**
     * Initiates a new instance giving the {@code subject} to be initialized.
     * <p/>
     * Mentioned to support derivation. Use {@link #apply(Collection)} to straightly create a new Instance.
     *
     * @param subject a mutable {@link Collection} that is mentioned to be modified through the new instance.
     */
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter") // by intention
    protected Collector(final C subject) {
        this.subject = requireNonNull(subject);
    }

    /**
     * Supplies a new instance to handle a specific {@link #subject}.
     */
    public static <E, C extends Collection<E>> Collector<E, C, ?> apply(final C subject) {
        return new Simple<>(subject);
    }

    /**
     * Supplies a {@code collector} in a finally intended representation.
     * Intended to {@code return this}.
     * May lead to a {@link ClassCastException} if used in an improper context.
     */
    @SuppressWarnings("unchecked")
    protected static <R extends Collector<?, ?, R>> R cast(final Collector<?, ?, R> collector) {
        return (R) collector;
    }

    /**
     * Substitutes {@link Collection#add(Object)} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#add(Object)} is not supported by the underlying
     *                                       {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws ClassCastException            if the class of the specified {@code element} prevents it from being added
     *                                       to the underlying {@link #subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
     * @throws NullPointerException          if the specified {@code element} is {@code null} and the underlying
     *                                       {@link #subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the underlying {@link #subject}.
     * @throws IllegalStateException         if the {@code element} cannot be added at this time due to
     *                                       the {@code subject}'s insertion restrictions (if any).
     */
    public final R add(final E element) {
        subject.add(element);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws ClassCastException            if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@code array} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #subject} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #subject}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #subject}'s insertion restrictions (if any).
     */
    @SafeVarargs
    public final R addAlt(final E... elements) {
        return addAll(asList(elements));
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws ClassCastException            if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@link Collection} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #subject} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #subject}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #subject}'s insertion restrictions (if any).
     */
    public final R addAll(final Collection<? extends E> elements) {
        subject.addAll(elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} with {@code conversion} for the underlying
     * {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws ClassCastException            if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@link Collection} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #subject} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #subject}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #subject}'s insertion restrictions (if any).
     */
    public final <I> R addAll(final Function<I, ? extends E> conversion, final Iterable<? extends I> elements) {
        Collecting.addAll(subject, conversion, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#remove(Object)} for the underlying {@link #subject}.
     * <p/>
     * In contrast to that, not just the first but any occurrence of the requested {@code element} will be removed!
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#remove(Object)} is not supported by the underlying
     *                                       {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     */
    public final R remove(final E element) {
        Collecting.removeAlt(subject, element);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#removeAll(Collection)} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws NullPointerException          if the {@code array} of {@code elements} is {@code null}.
     */
    @SafeVarargs
    public final R removeAlt(final E... element) {
        return removeAll(asList(element));
    }

    /**
     * Substitutes {@link Collection#removeAll(Collection)} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws NullPointerException          if the {@link Collection} of {@code elements} is {@code null}.
     */
    public final R removeAll(final Collection<? extends E> elements) {
        Collecting.removeAll(subject, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#retainAll(Collection)} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws NullPointerException          if the {@code array} of {@code elements} is {@code null}.
     */
    @SafeVarargs
    public final R retainAlt(final E... elements) {
        return retainAll(asList(elements));
    }

    /**
     * Substitutes {@link Collection#retainAll(Collection)} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws NullPointerException          if the {@link Collection} of {@code elements} is {@code null}.
     */
    public final R retainAll(final Collection<? extends E> elements) {
        Collecting.retainAll(subject, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#clear()} for the underlying {@link #subject}.
     *
     * @return The related {@code Collector} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     */
    public final R clear() {
        Collecting.clear(subject);
        return cast(this);
    }

    @Override
    public final String toString() {
        return simpleName(getClass()) + subject;
    }

    private static class Simple<E, C extends Collection<E>> extends Collector<E, C, Simple<E, C>> {
        private Simple(final C subject) {
            super(subject);
        }
    }
}
