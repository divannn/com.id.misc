package com.id.misc.jscp;


public class ArrayType {
    public static void main(String[] args) {
        int[] arr1d = {};
        System.err.println(">>> " + (arr1d instanceof int[]));//true
        System.err.println(">>> " + (arr1d instanceof Object));//true

        int[][] arr2d = {{}};
        //System.err.println(">>> " + (arr2d instanceof int[]));//won't compile.
        System.err.println(">>> " + (arr2d instanceof int[][]));//true
        System.err.println(">>> " + (arr2d instanceof Object[]));//true
    }
}
