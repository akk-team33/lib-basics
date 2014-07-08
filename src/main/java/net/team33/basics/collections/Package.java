package net.team33.basics.collections;

final class Package {
    static final String NOT_SUPPORTED = "Unsupported operation";

    private static final String CLASS_SEPARATOR = ".";

    private Package() {
    }

    static String simpleName(final Class<?> aClass) {
        final String result = aClass.getSimpleName();
        final Class<?> declaringClass = aClass.getDeclaringClass();
        return (null == declaringClass) ? result : (simpleName(declaringClass) + CLASS_SEPARATOR + result);
    }
}