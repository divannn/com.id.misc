package com.id.misc.com.id.misc.jscp;


public class ArrayNPE {
    public static void main(String[] args) throws Exception {
        int[] a = null;
        //NPE won't happen here as index calculation is going first. So "Another Exception" will happen.
        int i = a[m()];
    }

    public static int m() throws Exception {
        throw new Exception("Another Exception");
    }
}
