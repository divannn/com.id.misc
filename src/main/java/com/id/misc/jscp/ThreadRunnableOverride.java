package com.id.misc.com.id.misc.jscp;


public class ThreadRunnableOverride {
    public static void main(String[] args) {
        Threader t = new Threader();
        Thread h = new Pooler(t);
        h.start();//will print "In Pooler"
    }
}

class Threader extends Thread {
    public void run() {
        System.out.println("In Threader");
    }
}

class Pooler extends Thread {

    public Pooler(Runnable r) {
        super(r);
    }

    //this has a priority over passed runnable.
    public void run() {
        System.out.println("In Pooler");
    }
}
