package com.id.misc.singleton;

/**
 * Via double checked locking.
 * Thread safe in Java 5+ (volatile has strong memaning since Java5).
 *
 * @author idanilov
 */
public class Foo4 {

    private static volatile Foo4 INSTANCE;

    public static Foo4 getInstance() {
        if (INSTANCE == null) {
            synchronized (Foo4.class) {
                if (INSTANCE == null)
                    INSTANCE = new Foo4();
            }
        }
        return INSTANCE;
    }
}