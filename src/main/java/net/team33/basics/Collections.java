package net.team33.basics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Convenience methods dealing with Collections in addition to {@link java.util.Collections}.
 */
public final class Collections {
    private Collections() {
    }

    /**
     * Adds a single {@code element} to a given {@code subject}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#add(Object)} is not supported by the {@code subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code element} prevents it from being added
     *                                       to the {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null} or
     *                                       if the specified {@code element} is {@code null}
     *                                       and the {@code subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the {@code subject}.
     * @throws IllegalStateException         if the {@code element} cannot be added at this time due to
     *                                       the {@code subject}'s insertion restrictions (if any).
     */
    public static <E, C extends Collection<E>> C add(final C subject, E element) {
        subject.add(element);
        return subject;
    }

    /**
     * Adds some {@code elements} to a given {@code subject}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#add(Object)} is not supported by the {@code subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null} or
     *                                       if the specified array of {@code elements} is {@code null} or
     *                                       if some of the specified {@code elements} are {@code null}
     *                                       and the {@code subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the {@code subject}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to
     *                                       the {@code subject}'s insertion restrictions (if any).
     */
    @SafeVarargs
    public static <E, C extends Collection<E>> C addAll(final C subject, E... elements) {
        if (1 == elements.length) {
            return add(subject, elements[0]);
        } else if (1 < elements.length) {
            return addAll(subject, Arrays.asList(elements));
        } else {
            return subject;
        }
    }

    /**
     * Adds some {@code elements} to a given {@code subject}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#addAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null} or
     *                                       if the specified {@link Collection} of {@code elements} is {@code null} or
     *                                       if some of the specified {@code elements} are {@code null}
     *                                       and the {@code subject} does not permit {@code null} elements.
     * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
     *                                       added to the {@code subject}.
     * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to
     *                                       the {@code subject}'s insertion restrictions (if any).
     */
    public static <E, C extends Collection<E>> C addAll(final C subject, final Collection<? extends E> elements) {
        subject.addAll(elements);
        return subject;
    }

    /**
     * Removes an {@code element} from a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain the {@code element}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#remove(Object)} is not supported by the
     *                                       {@code subject}.
     */
    public static <E, C extends Collection<E>> C remove(final C subject, final Object element) {
        try {
            //noinspection SuspiciousMethodCalls,StatementWithEmptyBody
            while (subject.remove(element)) ;
        } catch (final NullPointerException | ClassCastException ignored) {
            // --> <subject> can not contain <element>
            // --> <subject> simply does not contain <element>
            // --> Nothing to do.
        }
        return subject;
    }

    /**
     * Removes some {@code elements} from a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain any of the specified {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null} or
     *                                       if the specified {@link Collection} of {@code elements} is {@code null}.
     */
    public static <E, C extends Collection<E>> C removeAll(final C subject, final Object... elements) {
        if (1 == elements.length) {
            return remove(subject, elements[0]);
        } else if (1 < elements.length) {
            return removeAll(subject, Arrays.asList(elements));
        } else {
            return subject;
        }
    }

    /**
     * Removes some {@code elements} from a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain any of the specified {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null} or
     *                                       if the specified {@link Collection} of {@code elements} is {@code null}.
     */
    public static <E, C extends Collection<E>> C removeAll(final C subject, final Collection<?> elements) {
        try {
            subject.removeAll(elements);
        } catch (final NullPointerException | ClassCastException ignored) {
            // --> <subject> can not contain <element>
            // --> <subject> simply does not contain <element>
            // --> May be incomplete, retry in a more secure way ...
            removeAll(subject, retainAll(new HashSet<>(elements), subject));
        }
        return subject;
    }

    /**
     * Retains some {@code elements} in a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain any element, not also contained in {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null} or
     *                                       if the specified {@link Collection} of {@code elements} is {@code null}.
     */
    public static <E, C extends Collection<E>> C retainAll(final C subject, final Object... elements) {
        return retainAll(subject, Arrays.asList(elements));
    }

    /**
     * Retains some {@code elements} in a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain any element, not also contained in {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null} or
     *                                       if the specified {@link Collection} of {@code elements} is {@code null}.
     */
    public static <E, C extends Collection<E>> C retainAll(final C subject, final Collection<?> elements) {
        try {
            subject.retainAll(elements);
        } catch (final NullPointerException | ClassCastException ignored) {
            // --> <subject> can not contain <element>
            // --> <subject> simply does not contain <element>
            // --> May be incomplete, retry in a more secure way ...
            retainAll(subject, retainAll(new HashSet<>(elements), subject));
        }
        return subject;
    }
}
