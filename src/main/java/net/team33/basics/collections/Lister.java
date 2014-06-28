package net.team33.basics.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Abstracts an instrument to initialize a {@link List} in a declarative style.
 *
 * @param <E> The element type of the {@link Collection} to be initialized.
 * @param <L> The type of the {@link List} to be initialized.
 * @param <R> The 'final' type of the {@link Lister} itself.
 */
@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
public class Lister<E, L extends List<E>, R extends Lister<E, L, R>> extends Collector<E, L, R> {

    /**
     * Initiates a new instance giving the {@code subject} to be built.
     *
     * @param subject a mutable {@link Collection} that is mentioned to be modified through the new instance.
     */
    protected Lister(final L subject) {
        super(subject);
    }

    public static <E, L extends List<E>>
    Lister<E, L, ?> pro(final L subject) {
        return new Simple<>(subject);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E>
    Lister<E, ArrayList<E>, ?> proArrayList(final E... elements) {
        return proArrayList(asList(elements));
    }

    public static <E>
    Lister<E, ArrayList<E>, ?> proArrayList(final Collection<? extends E> origin) {
        return pro(new ArrayList<>(origin));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    @SafeVarargs
    public static <E>
    Lister<E, LinkedList<E>, ?> proLinkedList(final E... elements) {
        return proLinkedList(asList(elements));
    }

    public static <E>
    Lister<E, LinkedList<E>, ?> proLinkedList(final Collection<? extends E> origin) {
        return pro(new LinkedList<>(origin));
    }

    /**
     * Substitutes {@link List#add(int, Object)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Lister} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link List#add(int, Object)} is not supported by the underlying
     *                                       {@link #getSubject() subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code element} prevents it from being added
     *                                       to the underlying {@link #getSubject() subject}.
     * @throws NullPointerException          if the specified {@code element} is {@code null} and the underlying
     *                                       {@link #getSubject() subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the underlying {@link #getSubject() subject}.
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt;= size()</tt>
     */
    public final R add(final int index, final E element) {
        getSubject().add(index, element);
        return cast(this);
    }

    /**
     * Substitutes {@link List#addAll(int, Collection)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Lister} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link List#addAll(int, Collection)} is not supported by the
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
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt;= size()</tt>
     */
    public final R addAll(final int index, final Collection<? extends E> elements) {
        getSubject().addAll(index, elements);
        return cast(this);
    }

    /**
     * Substitutes {@link List#remove(int)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Lister} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link List#remove(int)} is not supported by the underlying
     *                                       {@link #getSubject() subject}.
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt; size()</tt>
     */
    public final R remove(final int index) {
        getSubject().remove(index);
        return cast(this);
    }

    /**
     * Substitutes {@link List#set(int, Object)} for the underlying {@link #getSubject() subject}.
     *
     * @return The related {@code Lister} itself in its 'final' representation. Of course not {@code null}.
     * @throws UnsupportedOperationException (may occur only if used with an improper type of {@code subject})
     *                                       if {@link List#set(int, Object)} is not supported by the underlying
     *                                       {@link #getSubject() subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code element} prevents it from being added
     *                                       to the underlying {@link #getSubject() subject}.
     * @throws NullPointerException          if the specified {@code element} is {@code null} and the underlying
     *                                       {@link #getSubject() subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the underlying {@link #getSubject() subject}.
     * @throws IndexOutOfBoundsException     if the {@code index} is out of range: <tt>0 &lt;= index &lt; size()</tt>
     */
    public final R set(final int index, final E element) {
        getSubject().set(index, element);
        return cast(this);
    }

    private static class Simple<E, L extends List<E>> extends Lister<E, L, Simple<E, L>> {
        private Simple(final L subject) {
            super(subject);
        }
    }
}
