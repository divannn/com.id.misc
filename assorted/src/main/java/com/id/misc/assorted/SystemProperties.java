package com.id.misc.assorted;

public class SystemProperties {
    public static void main(String[] args) {
        System.err.println(">> system arch: " + getArch());
    }

    /**
     * Possible result:32,64,unknown
     *
     * @return OS architecture
     */
    private static String getArch() {
        return System.getProperty("sun.arch.data.model");
    }
}
