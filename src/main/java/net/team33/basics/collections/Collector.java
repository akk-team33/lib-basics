package net.team33.basics.collections;

import com.google.common.base.Function;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static net.team33.basics.collections.Package.simpleName;

/**
 * Abstracts an instrument to initialize a {@link Collection} in a declarative style.
 *
 * @param <E> The element type of the {@link Collection} to be initialized.
 * @param <C> The type of the {@link Collection} to be initialized.
 * @param <R> The 'final' type of the {@link Collector} itself.
 */
public class Collector<E, C extends Collection<E>, R extends Collector<E, C, R>> {

    private final C core;

    /**
     * Initiates a new instance giving the {@code core} to be initialized.
     * <p/>
     * Mentioned to be used by a derivative. Use {@link #apply(Collection)} to straightly create a new Instance.
     *
     * @param core a mutable {@link Collection} that is mentioned to be modified through the new instance.
     */
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter") // by intention
    protected Collector(final C core) {
        this.core = requireNonNull(core);
    }

    public static <E, C extends Collection<E>> Collector<E, C, ?> apply(final C subject) {
        return new Simple<>(subject);
    }

    @SuppressWarnings("unchecked")
    protected static <E, C extends Collection<E>, R extends Collector<E, C, R>>
    R cast(final Collector<E, C, R> collector) {
        return (R) collector;
    }

    /**
     * Supplies the underlying {@link Collection} in its original representation. Not {@code null}.
     */
    public final C getCore() {
        // Intended to supply the mutable instance as is ...
        // noinspection ReturnOfCollectionOrArrayField
        return core;
    }

    /**
     * Substitutes {@link Collection#add(Object)} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#add(Object)} is not supported by the underlying
     *                                       {@link #getCore() core}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code element} prevents it from being added
     *                                       to the underlying {@link #getCore() core}.
     * @throws NullPointerException          if the specified {@code element} is {@code null} and the underlying
     *                                       {@link #getCore() core} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the underlying {@link #getCore() core}.
     * @throws IllegalStateException         if the {@code element} cannot be added at this time due to
     *                                       the {@code core}'s insertion restrictions (if any).
     */
    public final R add(final E element) {
        core.add(element);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code core}.
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@code array} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #getCore() core} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #getCore() core}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #getCore() core}'s insertion restrictions (if any).
     */
    @SafeVarargs
    public final R addAlt(final E... elements) {
        return addAll(asList(elements));
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code core}.
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@link Collection} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #getCore() core} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #getCore() core}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #getCore() core}'s insertion restrictions (if any).
     */
    public final R addAll(final Collection<? extends E> elements) {
        core.addAll(elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#addAll(Collection)} with {@code conversion} for the underlying
     * {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#addAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code core}.
     * @throws NullPointerException          <ul>
     *                                       <li>if the {@link Collection} of {@code elements} is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null} and the
     *                                       underlying {@link #getCore() core} does not permit {@code null}
     *                                       elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the underlying {@link #getCore() core}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to the underlying
     *                                       {@link #getCore() core}'s insertion restrictions (if any).
     */
    public final <I> R addAll(final Function<I, ? extends E> conversion, final Iterable<? extends I> elements) {
        Collecting.addAll(core, conversion, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#remove(Object)} for the underlying {@link #getCore() core}.
     * <p/>
     * In contrast to that, not just the first but any occurrence of the requested {@code element} will be removed!
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#remove(Object)} is not supported by the underlying
     *                                       {@link #getCore() core}.
     */
    public final R remove(final E element) {
        Collecting.removeAlt(core, element);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#removeAll(Collection)} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     * @throws NullPointerException          if the {@code array} of {@code elements} is {@code null}.
     */
    @SafeVarargs
    public final R removeAlt(final E... element) {
        return removeAll(asList(element));
    }

    /**
     * Substitutes {@link Collection#removeAll(Collection)} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     * @throws NullPointerException          if the {@link Collection} of {@code elements} is {@code null}.
     */
    public final R removeAll(final Collection<? extends E> elements) {
        Collecting.removeAll(core, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#retainAll(Collection)} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     * @throws NullPointerException          if the {@code array} of {@code elements} is {@code null}.
     */
    @SafeVarargs
    public final R retainAlt(final E... elements) {
        return retainAll(asList(elements));
    }

    /**
     * Substitutes {@link Collection#retainAll(Collection)} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     * @throws NullPointerException          if the {@link Collection} of {@code elements} is {@code null}.
     */
    public final R retainAll(final Collection<? extends E> elements) {
        Collecting.retainAll(core, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link Collection#clear()} for the underlying {@link #getCore() core}.
     *
     * @return The related {@code Collector} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code core})
     *                                       if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       underlying {@link #getCore() core}.
     */
    public final R clear() {
        Collecting.clear(core);
        return cast(this);
    }

    @Override
    public final String toString() {
        return simpleName(getClass()) + core;
    }

    private static class Simple<E, C extends Collection<E>> extends Collector<E, C, Simple<E, C>> {
        private Simple(final C subject) {
            super(subject);
        }
    }
}
