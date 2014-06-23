package net.team33.basics.collections;

import java.util.Collection;
import java.util.HashSet;

import static java.util.Arrays.asList;

/**
 * Convenience methods to deal with Collections in addition to {@link java.util.Collections}.
 */
@SuppressWarnings({"ProhibitedExceptionCaught", "OverloadedVarargsMethod", "PublicInnerClass"})
public final class Util {
    private Util() {
    }

    /**
     * Adds a single {@code element} to a given {@code subject}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#add(Object)} is not supported by the {@code subject}.
     * @throws ClassCastException            (may occur only if used raw or forced in a mismatched class context)
     *                                       if the class of the specified {@code element} prevents it from being added
     *                                       to the {@code subject}.
     * @throws NullPointerException          <ul>
     *                                       <li>if {@code subject} is {@code null}.</li>
     *                                       <li>if the specified {@code element} is {@code null}
     *                                       and the {@code subject} does not permit {@code null} elements.</li>
     *                                       </ul>
     * @throws IllegalArgumentException      if some property of the specified {@code element} prevents it from being
     *                                       added to the {@code subject}.
     * @throws IllegalStateException         if the {@code element} cannot be added at this time due to
     *                                       the {@code subject}'s insertion restrictions (if any).
     */
    public static <E, C extends Collection<E>> C add(final C subject, final E element) {
        subject.add(element);
        return subject;
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
     * @throws NullPointerException          <ul>
     *                                       <li>if {@code subject} or the {@link Collection} of {@code elements}
     *                                       is {@code null}</li>
     *                                       <li>if some of the specified {@code elements} are {@code null}
     *                                       and the {@code subject} does not permit {@code null} elements.</li>
     *                                       </ul>
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
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which may be caused by
     * {@link Collection#remove(Object)} when the {@code subject} does not support the requested {@code element}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#remove(Object)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null}.
     */
    public static <E, C extends Collection<E>> C remove(final C subject, final Object element) {
        try {
            //noinspection SuspiciousMethodCalls,StatementWithEmptyBody,ControlFlowStatementWithoutBraces
            while (subject.remove(element)) ;

        } catch (final NullPointerException | ClassCastException caught) {
            if (null == subject) {
                throw caught; // expected to be a NullPointerException
            }

            // --> <subject> can not contain <element>
            // --> <subject> simply does not contain <element>
            // --> Nothing to do.
        }
        return subject;
    }

    /**
     * Removes some {@code elements} from a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain any of the specified {@code elements}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which may be caused by
     * {@link Collection#removeAll(Collection)} when the {@code subject} does not support some of the requested
     * {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} or the {@link Collection} of {@code elements}
     *                                       is {@code null}.
     */
    public static <E, C extends Collection<E>> C removeAll(final C subject, final Collection<?> elements) {
        try {
            subject.removeAll(elements);

        } catch (final NullPointerException | ClassCastException caught) {
            if ((null == subject) || (null == elements)) {
                throw caught; // expected to be a NullPointerException

            } else {
                // --> <subject> can not contain <element>
                // --> <subject> simply does not contain <element>
                // --> May be incomplete, retry in a more secure way ...
                removeAll(subject, retainAll(new HashSet<>(elements), subject));
            }
        }
        return subject;
    }

    /**
     * Retains some {@code elements} in a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain any element, not also contained in {@code elements}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which may be caused by
     * {@link Collection#retainAll(Collection)} when the {@code subject} does not support some of the requested
     * {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} or the {@link Collection} of {@code elements}
     *                                       is {@code null}.
     */
    public static <E, C extends Collection<E>> C retainAll(final C subject, final Collection<?> elements) {
        try {
            subject.retainAll(elements);

        } catch (final NullPointerException | ClassCastException caught) {
            if ((null == subject) || (null == elements)) {
                throw caught; // expected to be a NullPointerException

            } else {
                // --> <subject> can not contain <element>
                // --> <subject> simply does not contain <element>
                // --> May be incomplete, retry in a more secure way ...
                retainAll(subject, retainAll(new HashSet<>(elements), subject));
            }
        }
        return subject;
    }

    /**
     * Indicates if a given {@code subject} contains a specific {@code element}.
     *
     * @throws NullPointerException if {@code subject} is {@code null}.
     */
    public static boolean contains(final Collection<?> subject, final Object element) {
        try {
            return subject.contains(element);

        } catch (final NullPointerException | ClassCastException caught) {
            if (null == subject) {
                throw caught; // expected to be a NullPointerException

            } else {
                // --> <subject> can not contain <element>
                // --> <subject> simply does not contain <element> ...
                return false;
            }
        }
    }

    /**
     * Indicates if a given {@code subject} contains specific {@code elements}.
     *
     * @throws NullPointerException if {@code subject} or the {@link Collection} of {@code elements}
     *                              is {@code null}.
     */
    public static boolean containsAll(final Collection<?> subject, final Collection<?> elements) {
        try {
            return subject.containsAll(elements);

        } catch (final NullPointerException | ClassCastException caught) {
            if ((null == subject) || (null == elements)) {
                throw caught; // expected to be a NullPointerException

            } else {
                // --> <subject> can not contain all <elements>
                // --> <subject> simply does not contain all <elements> ...
                return false;
            }
        }
    }

    /**
     * Variable arguments implementations of some Util-methods
     */
    public static final class Alt {

        private Alt() {
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
         * @throws NullPointerException          <ul>
         *                                       <li>if {@code subject} or the {@code array} of {@code elements}
         *                                       is {@code null}</li>
         *                                       <li>if some of the specified {@code elements} are {@code null}
         *                                       and the {@code subject} does not permit {@code null} elements.</li>
         *                                       </ul>
         * @throws IllegalArgumentException      if some property of some {@code elements} prevents them from being
         *                                       added to the {@code subject}.
         * @throws IllegalStateException         if the {@code elements} cannot be added at this time due to
         *                                       the {@code subject}'s insertion restrictions (if any).
         */
        public static <E, C extends Collection<E>> C add(final C subject, final E... elements) {
            if (1 == elements.length) {
                return Util.add(subject, elements[0]);
            } else if (1 < elements.length) {
                return addAll(subject, asList(elements));
            } else {
                return subject;
            }
        }

        /**
         * Removes some {@code elements} from a given {@code subject}.
         * Respectively ensures the {@code subject} not to contain any of the specified {@code elements}.
         * <p/>
         * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which may be caused by
         * {@link Collection#removeAll(Collection)} when the {@code subject} does not support some of the requested
         * {@code elements}.
         *
         * @return The {@code subject}.
         * @throws UnsupportedOperationException if {@link Collection#removeAll(Collection)} is not supported by the
         *                                       {@code subject}.
         * @throws NullPointerException          if {@code subject} or the {@link Collection} of {@code elements}
         *                                       is {@code null}.
         */
        public static <E, C extends Collection<E>> C remove(final C subject, final Object... elements) {
            if (1 == elements.length) {
                return Util.remove(subject, elements[0]);
            } else if (1 < elements.length) {
                return removeAll(subject, asList(elements));
            } else {
                return subject;
            }
        }

        /**
         * Retains some {@code elements} in a given {@code subject}.
         * Respectively ensures the {@code subject} not to contain any element, not also contained in {@code elements}.
         * <p/>
         * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which may be caused by
         * {@link Collection#retainAll(Collection)} when the {@code subject} does not support some of the requested
         * {@code elements}.
         *
         * @return The {@code subject}.
         * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
         *                                       {@code subject}.
         * @throws NullPointerException          if {@code subject} or the {@link Collection} of {@code elements}
         *                                       is {@code null}.
         */
        public static <E, C extends Collection<E>> C retain(final C subject, final Object... elements) {
            return retainAll(subject, asList(elements));
        }

        /**
         * Indicates if a given {@code subject} contains specific {@code elements}.
         *
         * @throws NullPointerException if {@code subject} or the {@link Collection} of {@code elements}
         *                              is {@code null}.
         */
        public static boolean contains(final Collection<?> subject, final Object... elements) {
            if (1 == elements.length) {
                return Util.contains(subject, elements[0]);
            } else {
                return containsAll(subject, asList(elements));
            }
        }
    }
}
