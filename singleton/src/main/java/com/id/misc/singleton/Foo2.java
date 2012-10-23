package com.id.misc.singleton;

/**
 * Singleton via inner class.
 * Thread safe.
 *
 * @author idanilov
 */
/*Since the line private static final Foo2 INSTANCE = new Foo2(); is only executed 
 * when the class FooLoader is actually used, this takes care of the lazy instantiation, 
 * and is it guaranteed to be thread safe.*/
public final class Foo2 {

    private static class FooLoader {

        private static final Foo2 INSTANCE = new Foo2();
    }

    private Foo2() {
        if (FooLoader.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static Foo2 getInstance() {
        return FooLoader.INSTANCE;
    }

}
