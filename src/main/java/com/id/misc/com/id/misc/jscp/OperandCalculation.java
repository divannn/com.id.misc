package com.id.misc.com.id.misc.jscp;


public class OperandCalculation {

    public static void main(String[] args) {
        String s = "old";
        print(s, s = "new");//operands calculated from left to right. First op1 already calculated and stored when op2 being calculated.
    }

    static void print(String s1, String s2) {
        System.err.println(s1 + " " + s2);//will prin "old new"
    }
}
