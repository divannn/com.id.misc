package com.id.misc.assorted;


import java.util.Date;

public class PrintUtil {

    public static void main(String[] args) {
        printDate(new Date());
    }

    public static void printDate(Date d) {
        //YYYY:MM:DD HH:MM:SS
        System.err.printf("Date: %tY:%<tm:%<td Time: %<TT", d);
    }
}
