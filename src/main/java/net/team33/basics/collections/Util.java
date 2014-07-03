package net.team33.basics.collections;

import com.google.common.base.Function;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;

/**
 * Convenience methods to deal with Collections in addition to {@link Collections}.
 */
@SuppressWarnings({"ProhibitedExceptionCaught", "StaticMethodOnlyUsedInOneClass"})
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
     * Removes an {@code element} from a given {@code subject}.
     * Respectively ensures the {@code subject} not to contain the {@code element}.
     * <p/>
     * If {@code subject} contains the {@code element} several times, each occurance will be removed!
     * <p/>
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which may be caused by
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
     * Avoids an unnecessary {@link ClassCastException} or {@link NullPointerException} which may be caused by
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
     * Indicates if a given {@code subject} contains specific {@code elements}.
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

    @SuppressWarnings({"RefusedBequest", "StandardVariableNames", "NewExceptionWithoutArguments"})
    private abstract static class FinalCollection<E, B extends Collection<E>> extends AbstractCollection<E> {
        @SuppressWarnings("ProtectedField")
        protected final B backing;

        private FinalCollection(final B backing) {
            this.backing = backing;
        }

        @Override
        public final Iterator<E> iterator() {
            return new FinalIterator<>(backing.iterator());
        }

        @Override
        public final int size() {
            return backing.size();
        }

        @Override
        public final boolean isEmpty() {
            return backing.isEmpty();
        }

        @Override
        public final boolean contains(final Object o) {
            return backing.contains(o);
        }

        @Override
        public final boolean containsAll(final Collection<?> c) {
            return backing.containsAll(c);
        }

        @Override
        public final boolean add(final E e) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean addAll(final Collection<? extends E> c) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean remove(final Object o) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean removeAll(final Collection<?> c) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean retainAll(final Collection<?> c) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final void clear() {
            // fast fail ...
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings({"RefusedBequest", "NewExceptionWithoutArguments", "StandardVariableNames"})
    private static class FinalList<E> extends FinalCollection<E, List<E>> implements List<E> {

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private final AbstractList<E> equals = new Equals();

        private FinalList(final List<E> origin) {
            super(new ArrayList<>(origin));
        }

        @Override
        public final ListIterator<E> listIterator() {
            return new FinalListIterator<>(backing.listIterator());
        }

        @Override
        public final ListIterator<E> listIterator(final int index) {
            return new FinalListIterator<>(backing.listIterator(index));
        }

        @Override
        public final List<E> subList(final int fromIndex, final int toIndex) {
            return new FinalList<>(backing.subList(fromIndex, toIndex));
        }

        @Override
        public final E get(final int index) {
            return backing.get(index);
        }

        @Override
        public final int indexOf(final Object o) {
            return backing.indexOf(o);
        }

        @Override
        public final int lastIndexOf(final Object o) {
            return backing.lastIndexOf(o);
        }

        @Override
        public final E set(final int index, final E element) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final void add(final int index, final E element) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final E remove(final int index) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean addAll(final int index, final Collection<? extends E> c) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final int hashCode() {
            return equals.hashCode();
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public final boolean equals(final Object obj) {
            return equals.equals(obj);
        }

        @SuppressWarnings({"InnerClassTooDeeplyNested", "NonStaticInnerClassInSecureContext"})
        private class Equals extends AbstractList<E> {
            @Override
            public final E get(final int index) {
                return backing.get(index);
            }

            @Override
            public final int size() {
                return backing.size();
            }
        }
    }

    @SuppressWarnings({"RefusedBequest", "StandardVariableNames", "NewExceptionWithoutArguments"})
    private abstract static class FinalSet<E, B extends Collection<E>>
            extends FinalCollection<E, B>
            implements Set<E> {

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private final AbstractSet<E> equals = new Equals();

        private FinalSet(final B backing) {
            super(backing);
        }

        @Override
        public final int hashCode() {
            return equals.hashCode();
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public final boolean equals(final Object obj) {
            return equals.equals(obj);
        }

        @SuppressWarnings({"InnerClassTooDeeplyNested", "NonStaticInnerClassInSecureContext"})
        private class Equals extends AbstractSet<E> {
            @Override
            public final Iterator<E> iterator() {
                return backing.iterator();
            }

            @Override
            public final int size() {
                return backing.size();
            }
        }
    }

    @SuppressWarnings({"RefusedBequest", "NewExceptionWithoutArguments", "StandardVariableNames"})
    private static class FinalPlainSet<E> extends FinalSet<E, ArrayList<E>> {
        private FinalPlainSet(final Set<E> origin) {
            super(new ArrayList<>(origin));
        }
    }

    @SuppressWarnings({"RefusedBequest", "NewExceptionWithoutArguments", "StandardVariableNames"})
    private static class FinalEnumSet<E extends Enum<E>> extends FinalSet<E, EnumSet<E>> {
        private FinalEnumSet(final EnumSet<E> origin) {
            super(EnumSet.copyOf(origin));
        }
    }

    @SuppressWarnings({"RefusedBequest", "NewExceptionWithoutArguments", "StandardVariableNames"})
    private static class FinalSortedSet<E> extends FinalSet<E, TreeSet<E>> implements SortedSet<E> {
        private FinalSortedSet(final SortedSet<E> origin) {
            super(new TreeSet<>(origin));
        }

        @Override
        public final Comparator<? super E> comparator() {
            return backing.comparator();
        }

        @Override
        public final SortedSet<E> subSet(final E fromElement, final E toElement) {
            return new FinalSortedSet<>(backing.subSet(fromElement, toElement));
        }

        @Override
        public final SortedSet<E> headSet(final E toElement) {
            return new FinalSortedSet<>(backing.headSet(toElement));
        }

        @Override
        public final SortedSet<E> tailSet(final E fromElement) {
            return new FinalSortedSet<>(backing.tailSet(fromElement));
        }

        @Override
        public final E first() {
            return backing.first();
        }

        @Override
        public final E last() {
            return backing.last();
        }
    }

    @SuppressWarnings("NewExceptionWithoutArguments")
    private static class FinalIteratorBase<E, B extends Iterator<E>> implements Iterator<E> {
        @SuppressWarnings("ProtectedField")
        protected final B backing;

        private FinalIteratorBase(final B backing) {
            this.backing = backing;
        }

        @Override
        public final boolean hasNext() {
            return backing.hasNext();
        }

        @Override
        public final E next() {
            return backing.next();
        }

        @Override
        public final void remove() {
            // fast fail ...
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("NewExceptionWithoutArguments")
    private static class FinalIterator<E> extends FinalIteratorBase<E, Iterator<E>> {
        private FinalIterator(final Iterator<E> backing) {
            super(backing);
        }
    }

    @SuppressWarnings("NewExceptionWithoutArguments")
    private static class FinalListIterator<E> extends FinalIteratorBase<E, ListIterator<E>> implements ListIterator<E> {
        private FinalListIterator(final ListIterator<E> backing) {
            super(backing);
        }

        @Override
        public final boolean hasPrevious() {
            return backing.hasPrevious();
        }

        @Override
        public final E previous() {
            return backing.previous();
        }

        @Override
        public final int nextIndex() {
            return backing.nextIndex();
        }

        @Override
        public final int previousIndex() {
            return backing.previousIndex();
        }

        @Override
        public final void set(final E e) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }

        @Override
        public final void add(final E e) {
            // fast fail ...
            throw new UnsupportedOperationException();
        }
    }
}
