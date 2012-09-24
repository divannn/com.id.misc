package com.id.misc.memmeasure;

public class Lk {

    public static void main(String[] args) {
        System.out.println(Sub.ID);
    }

}

class Super {
    static String ID = "ID1";

    static {
        System.out.print("In Super");
    }
}

class Sub extends Super {
    //static String ID = "ID2";
    static {
        System.out.print("In Sub");
    }
}