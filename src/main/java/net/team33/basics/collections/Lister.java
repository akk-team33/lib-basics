package net.team33.basics.collections;

import java.util.Collection;
import java.util.List;

/**
 * Abstracts an instrument to initialize a {@link List} in a declarative style.
 *
 * @param <E> The element type of the {@link Collection} to be initialized.
 * @param <L> The type of the {@link List} to be initialized.
 * @param <R> The finally intended type of the {@link Lister} itself.
 */
@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
public class Lister<E, L extends List<E>, R extends Lister<E, L, R>> extends Collector<E, L, R> {

    /**
     * Initiates a new instance giving the {@code subject} to be initialized.
     * <p/>
     * Mentioned to be used by a derivative. Use {@link #apply(List)} to straightly create a new Instance.
     *
     * @param subject a mutable {@link List} that is mentioned to be modified through the new instance.
     */
    protected Lister(final L subject) {
        super(subject);
    }

    /**
     * Supplies a new instance to handle a specific {@link #subject}.
     */
    public static <E, L extends List<E>> Lister<E, L, ?> apply(final L subject) {
        return new Simple<>(subject);
    }

    /**
     * Substitutes {@link List#add(int, Object)} for the underlying {@link #subject}.
     *
     * @return The related {@code Lister} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link List#add(int, Object)} is not supported by the underlying
     *                                       {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws ClassCastException            if the class of the specified {@code element} prevents it from being added
     *                                       to the underlying {@link #subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
     * @throws NullPointerException          if the specified {@code element} is {@code null} and the underlying
     *                                       {@link #subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the underlying {@link #subject}.
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt;= size()</tt>
     */
    public final R add(final int index, final E element) {
        subject.add(index, element);
        return cast(this);
    }

    /**
     * Substitutes {@link List#addAll(int, Collection)} for the underlying {@link #subject}.
     *
     * @return The related {@code Lister} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link List#addAll(int, Collection)} is not supported by the
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
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt;= size()</tt>
     */
    public final R addAll(final int index, final Collection<? extends E> elements) {
        subject.addAll(index, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link List#remove(int)} for the underlying {@link #subject}.
     *
     * @return The related {@code Lister} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link List#remove(int)} is not supported by the underlying
     *                                       {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt; size()</tt>
     */
    public final R remove(final int index) {
        subject.remove(index);
        return cast(this);
    }

    /**
     * Substitutes {@link List#set(int, Object)} for the underlying {@link #subject}.
     *
     * @return The related {@code Lister} itself in its finally intended representation. Of course not {@code null}.
     * @throws UnsupportedOperationException if {@link List#set(int, Object)} is not supported by the underlying
     *                                       {@link #subject}
     *                                       (may occur only if used with an improper type of {@code subject}).
     * @throws ClassCastException            if the class of the specified {@code element} prevents it from being added
     *                                       to the underlying {@link #subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
     * @throws NullPointerException          if the specified {@code element} is {@code null} and the underlying
     *                                       {@link #subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the underlying {@link #subject}.
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt; size()</tt>
     */
    public final R set(final int index, final E element) {
        subject.set(index, element);
        return cast(this);
    }

    private static class Simple<E, L extends List<E>> extends Lister<E, L, Simple<E, L>> {
        private Simple(final L subject) {
            super(subject);
        }
    }
}
