package net.team33.basics;

/**
 * Abstracts a type that is (mentioned to be) immutable by itself, but is able to supply a specific {@link Builder}
 * that 'inherits' the properties necessary to {@linkplain Builder#build() build} a copy of the original.
 * <p/>
 * Frankly the {@link Builder} may be modified to finally {@linkplain Builder#build() build} a 'modified copy'.
 */
public interface Branchable<E extends Branchable<E, B>, B extends Builder<E>> {

    /**
     * Supplies a specific {@link Builder} that 'inherits' the properties of the this instance and may be modified
     * as needed to finally {@linkplain Builder#build() build} a new instance of the original type.
     */
    B branch();
}
