package net.team33.basics.collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static net.team33.basics.collections.Package.NOT_SUPPORTED;

/**
 * Generic implementation of an immutable {@link ListIterator}.
 * Fails fast on any attempt to ...
 * <ul>
 *     <li>{@link #remove()}</li>
 *     <li>{@link #add(Object)}</li>
 *     <li>{@link #set(Object)}</li>
 * </ul>
 */
public class FinalListIterator<E, C extends FinalListIterator.Core<E>>
        extends FinalIterator<E, C>
        implements ListIterator<E> {

    public FinalListIterator(final C core) {
        super(core);
    }

    public static <E> FinalListIterator<E, ?> proxy(final ListIterator<E> original) {
        return new FinalListIterator<>(new Proxy<>(original));
    }

    @Override
    public final boolean hasPrevious() {
        return core.hasPrevious();
    }

    @Override
    public final int previousIndex() {
        return core.previousIndex();
    }

    @Override
    public final E previous() {
        return core.previous();
    }

    @Override
    public final int nextIndex() {
        return core.nextIndex();
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void set(final E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    /**
     * @throws UnsupportedOperationException on any attempt.
     */
    @Override
    public final void add(final E e) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @SuppressWarnings({"ClassNameSameAsAncestorName", "InterfaceWithOnlyOneDirectInheritor"})
    public interface Core<E> extends FinalIterator.Core<E> {

        boolean hasPrevious();

        int previousIndex();

        E previous() throws NoSuchElementException;

        int nextIndex();
    }

    @SuppressWarnings("ClassNameSameAsAncestorName")
    protected static class Proxy<E, I extends ListIterator<E>> extends FinalIterator.Proxy<E, I> implements Core<E> {

        protected Proxy(final I original) {
            super(original);
        }

        @Override
        public final boolean hasPrevious() {
            return original.hasPrevious();
        }

        @Override
        public final int previousIndex() {
            return original.previousIndex();
        }

        @Override
        public final E previous() throws NoSuchElementException {
            return original.previous();
        }

        @Override
        public final int nextIndex() {
            return 0;
        }
    }
}
