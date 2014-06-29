package net.team33.basics.collections;

import com.google.common.base.Supplier;
import net.team33.basics.lazy.Initial;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Implementation of an immutable {@link Set}.
 * <ul>
 * <li>To be instantiated as a copy of an original {@link Collection} (eg. via {@link #from(Collection)}).</li>
 * <li>Preserves the iteration order of the original {@link Collection} (as far as compatible with a {@link Set}).</li>
 * </ul>
 * <p/>
 * NOTE (from documentation of {@link Set}):
 * Great care must be exercised if mutable objects are used as set elements. The behavior of a set is not specified if
 * the value of an object is changed in a manner that affects {@link Object#equals(Object) equals} comparisons while the
 * object is an element in the set.
 */
@SuppressWarnings("EqualsAndHashcode")
public class FinalSet<E> extends AbstractSet<E> {

    private final FinalList<E> elements;

    private transient Supplier<Integer> hashSupplier = new HashCode();
    private transient Supplier<String> stringSupplier = new ToString();

    @SuppressWarnings("TypeMayBeWeakened")
    private FinalSet(final Set<? extends E> origin) {
        elements = FinalList.from(origin);
    }

    public static <E> FinalSet<E> from(final Collection<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof Set) ? from((Set<? extends E>) origin) : from(new LinkedHashSet<>(origin));
    }

    public static <E> FinalSet<E> from(final Set<? extends E> origin) {
        //noinspection unchecked
        return (origin instanceof FinalSet) ? (FinalSet<E>) origin : new FinalSet<>(origin);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean add(final E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean remove(final Object o) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @SuppressWarnings("RefusedBequest")
    @Override
    public final void clear() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public final boolean contains(final Object o) {
        // TODO? final int otherCode = Objects.hashCode(o);
        // noinspection ForLoopReplaceableByForEach
        for (int index = 0, limit = elements.size(); index < limit; ++index) {
            if (/* TODO? (otherCode == hashCodes[index]) && */ Objects.equals(o, elements.get(index))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final String toString() {
        return stringSupplier.get();
    }

    @Override
    public final Iterator<E> iterator() {
        return elements.iterator();
    }

    @Override
    public final int size() {
        return elements.size();
    }

    @Override
    public final int hashCode() {
        //noinspection NonFinalFieldReferencedInHashCode
        return hashSupplier.get();
    }

    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private class HashCode extends Initial<Integer> {
        @Override
        protected final Integer getFinal() {
            return FinalSet.super.hashCode();
        }

        @Override
        protected final Supplier<Integer> getAnchor() {
            return hashSupplier;
        }

        @Override
        protected final void setAnchor(final Supplier<Integer> supplier) {
            hashSupplier = supplier;
        }
    }

    @SuppressWarnings("NonStaticInnerClassInSecureContext")
    private class ToString extends Initial<String> {
        @Override
        protected final String getFinal() {
            return FinalSet.super.toString();
        }

        @Override
        protected final Supplier<String> getAnchor() {
            return stringSupplier;
        }

        @Override
        protected final void setAnchor(final Supplier<String> supplier) {
            stringSupplier = supplier;
        }
    }
}
