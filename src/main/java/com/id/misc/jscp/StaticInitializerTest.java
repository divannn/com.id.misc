package com.id.misc.com.id.misc.jscp;

class Super {
    static String ID = "QBANK";
}

class Sub extends Super {
    static {
        System.out.print("In Sub");
    }
}

public class StaticInitializerTest {
    public static void main(String[] args) {
        System.out.println(Sub.ID);//will print "QBANK" as Sub class is not initialized.
    }
}