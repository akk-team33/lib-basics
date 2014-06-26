package net.team33.basics.lazy;

import com.google.common.base.Supplier;

public class Final<T> implements Supplier<T> {
    private final T value;

    public Final(final T value) {
        this.value = value;
    }

    @Override
    public final T get() {
        return value;
    }
}
