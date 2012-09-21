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
        LinkedHashMap<Integer, String> lhm = new LinkedHashMap<Integer, String>();
        //Object a = new A();
        Object a = new LinkedHashMap<Integer,String>();
        ObjectGraphMeasurer.Footprint fp = ObjectGraphMeasurer.measure(a);
        long size = MemoryMeasurer.measureBytes(a);
        System.err.println(">>> " + fp + "   size: " + size);
        System.err.println("   >>> approx size:" + countSizeApprox(fp, true));
    }

    /** My own version.
     * @param footprint
     * @param is32bitVM
     * @return size in bytes
     */
    private static long countSizeApprox(ObjectGraphMeasurer.Footprint footprint, boolean is32bitVM) {
        int pointerSize = is32bitVM ? 4 : 8;
        int headerSize = 8;
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