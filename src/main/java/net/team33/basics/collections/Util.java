package net.team33.basics.collections;

import com.google.common.base.Function;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Convenience methods to deal with Collections in addition to {@link Collections}.
 */
@SuppressWarnings("ProhibitedExceptionCaught")
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
     * @see Collection#add(Object)
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
     * @throws ClassCastException            if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
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
     * @see Collection#addAll(Collection)
     */
    public static <E, C extends Collection<E>> C addAll(final C subject, final Collection<? extends E> elements) {
        subject.addAll(elements);
        return subject;
    }

    /**
     * Adds some {@code elements} after {@code conversion} to a given {@code subject}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#addAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws ClassCastException            if the class of the specified {@code elements} prevents them from being
     *                                       added to the {@code subject}
     *                                       (may occur only if used raw or forced in a mismatched class context).
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
    public static <I, E, C extends Collection<E>> C addAll(
            final C subject, final Iterable<? extends I> elements, final Function<? super I, ? extends E> conversion) {
        for (final I input : elements) {
            add(subject, conversion.apply(input));
        }
        return subject;
    }

    /**
     * Removes any element from a given {@code subject}.
     *
     * @return The {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#clear()} is not supported by the {@code subject}.
     * @see Collection#clear()
     */
    public static <E, C extends Collection<E>> C clear(final C subject) {
        subject.clear();
        return subject;
    }

    /**
     * Removes all entries from a given {@code subject}.
     *
     * @return The {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null}.
     * @throws UnsupportedOperationException if {@link Collection#clear()} is not supported by the {@code subject}.
     * @see Map#clear()
     */
    public static <K, V, M extends Map<K, V>> M clear(final M subject) {
        subject.clear();
        return subject;
    }

    /**
     * Removes an {@code element} from a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain the {@code element}.
     * <p/>
     * If {@code subject} contains the {@code element} several times, each occurrence will be removed!
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Collection#remove(Object)} when the {@code subject} does not support the requested {@code element}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#remove(Object)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null}.
     * @see Collection#remove(Object)
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
            // --> Nothing else to do.
        }
        return subject;
    }

    /**
     * Removes an entry by a specific {@code key} from a given {@code subject}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Map#remove(Object)} when the {@code subject} does not support the requested {@code key}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Map#remove(Object)} is not supported by the {@code subject}.
     * @throws NullPointerException          if {@code subject} is {@code null}.
     * @see Map#remove(Object)
     */
    public static <K, V, M extends Map<K, V>> M remove(final M subject, final Object key) {
        try {
            //noinspection SuspiciousMethodCalls
            subject.remove(key);

        } catch (final NullPointerException | ClassCastException caught) {
            if (null == subject) {
                throw caught; // expected to be a NullPointerException
            }

            // --> <subject> can not contain <key>
            // --> <subject> simply does not contain <key>
            // --> Nothing else to do.
        }
        return subject;
    }

    /**
     * Removes some {@code elements} from a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain any of the specified {@code elements}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Collection#removeAll(Collection)} when the {@code subject} does not support some of the requested
     * {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#removeAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} or the {@link Collection} of {@code elements}
     *                                       is {@code null}.
     * @see Collection#removeAll(Collection)
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
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Collection#retainAll(Collection)} when the {@code subject} does not support some of the requested
     * {@code elements}.
     *
     * @return The {@code subject}.
     * @throws UnsupportedOperationException if {@link Collection#retainAll(Collection)} is not supported by the
     *                                       {@code subject}.
     * @throws NullPointerException          if {@code subject} or the {@link Collection} of {@code elements}
     *                                       is {@code null}.
     * @see Collection#retainAll(Collection)
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
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Collection#contains(Object)} when the {@code subject} does not support the requested {@code element}.
     *
     * @throws NullPointerException if {@code subject} is {@code null}.
     * @see Collection#contains(Object)
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
     * Indicates if a given {@code subject} contains an entry for a specific {@code key}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Map#containsKey(Object)} when the {@code subject} does not support the requested {@code key}.
     *
     * @throws NullPointerException if {@code subject} is {@code null}.
     * @see Map#containsKey(Object)
     */
    public static boolean containsKey(final Map<?, ?> subject, final Object key) {
        try {
            return subject.containsKey(key);

        } catch (final NullPointerException | ClassCastException caught) {
            if (null == subject) {
                throw caught; // expected to be a NullPointerException

            } else {
                // --> <subject> can not contain <key>
                // --> <subject> simply does not contain <key> ...
                return false;
            }
        }
    }

    /**
     * Indicates if a given {@code subject} contains an entry with a specific {@code value}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Map#containsValue(Object)} when the {@code subject} does not support the requested {@code value}.
     *
     * @throws NullPointerException if {@code subject} is {@code null}.
     * @see Map#containsValue(Object)
     */
    public static boolean containsValue(final Map<?, ?> subject, final Object value) {
        try {
            return subject.containsValue(value);

        } catch (final NullPointerException | ClassCastException caught) {
            if (null == subject) {
                throw caught; // expected to be a NullPointerException

            } else {
                // --> <subject> can not contain <value>
                // --> <subject> simply does not contain <value> ...
                return false;
            }
        }
    }

    /**
     * Indicates if a given {@code subject} contains specific {@code elements}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Collection#containsAll(Collection)} when the {@code subject} does not support some of the requested
     * {@code elements}.
     *
     * @throws NullPointerException if {@code subject} or the {@link Collection} of {@code elements}
     *                              is {@code null}.
     * @see Collection#containsAll(Collection)
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
     * Retrieves the value for a specific {@code key} from a given {@code subject}.
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
     * {@link Map#containsKey(Object)} when the {@code subject} does not support the requested {@code key}.
     *
     * @return The value or {@code null} if the {@code subject} doesn't contain (an entry for) the {@code key}.
     *
     * @throws NullPointerException if {@code subject} is {@code null}.
     * @see Map#get(Object)
     */
    public static <V> V get(final Map<?, V> subject, final Object key) {
        try {
            return subject.get(key);

        } catch (final NullPointerException | ClassCastException caught) {
            if (null == subject) {
                throw caught; // expected to be a NullPointerException

            } else {
                // --> <subject> can not contain <key>
                // --> <subject> simply does not contain <key>
                // --> as specified for Map ...
                // noinspection ReturnOfNull
                return null;
            }
        }
    }

    /**
     * Variable arguments implementations of some Util-methods
     */
    @SuppressWarnings("PublicInnerClass")
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
        @SafeVarargs
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
         * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
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
         * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which might be caused by
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
