package com.id.misc.memmeasure;

import com.google.common.collect.ImmutableMultiset;
import objectexplorer.MemoryMeasurer;
import objectexplorer.ObjectGraphMeasurer;

import java.util.LinkedHashMap;

/**
 * To use MemoryMeasurer object-explorer.jar must be specified as javaagent.
 */
public class MemoryMeasureTest {
    public static void main(String[] args) {
        Object a = new A();
        ObjectGraphMeasurer.Footprint fp = ObjectGraphMeasurer.measure(a);
        long size = MemoryMeasurer.measureBytes(a);
        System.err.println(">>> " + fp + "   size: " + size);
        System.err.println("   >>> approx size:" + countSizeApprox(fp, true));

        testMultiDimArray();
    }

    // arrays hold same data space (1,000,000 integers) but take different size in memory in 1.5 times.
    // cool!!!
    private static void testMultiDimArray() {
        Object arr1 = new int[1000][100][10];
        Object arr2 = new int[10][100][1000];
        ObjectGraphMeasurer.Footprint fp1 = ObjectGraphMeasurer.measure(arr1);
        ObjectGraphMeasurer.Footprint fp2 = ObjectGraphMeasurer.measure(arr2);
        System.err.println("arr2 " + fp1 + "   size: " + MemoryMeasurer.measureBytes(arr1));
        System.err.println("arr2 " + fp2 + "   size: " + MemoryMeasurer.measureBytes(arr2));
    }


    /** My own version.
     * @param footprint
     * @param is32bitVM
     * @return size in bytes
     */
    //TODO: check for arrays, they have additional system fields (for storing size, for example).
    private static long countSizeApprox(ObjectGraphMeasurer.Footprint footprint, boolean is32bitVM) {
        int pointerSize = is32bitVM ? 4 : 8;
        int headerSize = is32bitVM ? 8 : 16;
        ImmutableMultiset primitives = footprint.getPrimitives();
        long memory = footprint.getObjects() * headerSize + footprint.getReferences() * pointerSize +
                primitives.count(byte.class) * 1 +
                primitives.count(short.class) * 2 +
                primitives.count(int.class) * 4 +
                primitives.count(long.class) * 8 +
                primitives.count(float.class) * 4 +
                primitives.count(double.class) * 8 +
                primitives.count(char.class) * 2 +
                primitives.count(boolean.class) * 4;
        //align to 16.
        //memory = (memory + 15) & (~0xF); //the last part zeros the lowest 4 bits
        return memory;

    }
}

class A {
    //A a1;
    //A a2;
    private int a;
}