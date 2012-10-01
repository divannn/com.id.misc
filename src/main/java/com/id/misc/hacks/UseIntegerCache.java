package com.id.misc.hacks;

import java.lang.reflect.Field;

//Don't forget to enable assertion (-ea option for JVM).
public class UseIntegerCache {
    static {
        //Write some code here to pass assertion.
        hack();
    }

    private static void hack() {
        ///use class Integer.IntegerCache and its memeber 'cache'
        try {
            Class<?>[] declaredClasses = Integer.class.getDeclaredClasses();
            Field cacheField = declaredClasses[0].getDeclaredField("cache");
            cacheField.setAccessible(true);
            ((Integer[]) cacheField.get(null))[20 + 128] = 30;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Integer a = 20;
        Integer b = 20;
        assert (a + b == 60);
    }
}
