package net.team33.basics.lazy;

import com.google.common.base.Supplier;

public abstract class Initial<T> implements Supplier<T> {
    @Override
    public final synchronized T get() {
        if (getAnchor() == this) {
            setAnchor(new Final<>(getFinal()));
        }
        return getAnchor().get();
    }

    protected abstract T getFinal();

    protected abstract Supplier<T> getAnchor();

    protected abstract void setAnchor(Supplier<T> supplier);
}
