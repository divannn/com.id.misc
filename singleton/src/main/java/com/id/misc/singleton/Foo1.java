package com.id.misc.singleton;

/**
 * Simpliest way with no lazy initialization.
 * 1) final class to forbid extention.
 * 2) Throwing an exception from the constructor prevents users to use reflection to create a second instance.
 * 3) Thread safe.
 *
 * @author idanilov
 */
/*When you have a very large object or heavy construction code AND also have other accessible 
 * static methods or fields that might be used before an instance is needed, then and only 
 * then you need to use lazy initialization.*/
public final class Foo1 {

    private static final Foo1 INSTANCE = new Foo1();

    private Foo1() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static Foo1 getInstance() {
        return INSTANCE;
    }


}
