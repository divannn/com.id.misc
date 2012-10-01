package com.id.misc.jscp;

/** All integer wrappers has a cash of values in range [-128;127].
 * This cashe is used when autoboxing happens.
 * Tricky feature - very confusing!
 */
public class IntegerCache {

    public static void main(String[] args) {
        checkInts(100, 100);//true  - cache is used, so same Integer objects.
        checkInts(200, 200);//false - new Integer objects.
    }

    private static boolean checkInts(Integer i1, Integer i2) {
        boolean result = i1 == i2;
        System.err.println(i1 + " equals to " + i2 + " = " + result);
        return result;
    }
}
