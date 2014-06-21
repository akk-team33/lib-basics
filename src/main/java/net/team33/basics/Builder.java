package net.team33.basics;

/**
 * Abstracts a kind of Supplier which carries all information necessary to build a new instance of a specific type.
 * <p/>
 * An implementation typically will be modifiable to explicitly set some of those information to desired values before
 * building an instance of the desired type.
 */
public interface Builder<T> {

    /**
     * Creates a new instance of the specified type.
     *
     * @return Not {@code null}.
     */
    T build();
}
