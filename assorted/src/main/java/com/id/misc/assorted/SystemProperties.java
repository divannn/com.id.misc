package com.id.misc.assorted;

public class SystemProperties {
    public static void main(String[] args) {
        System.err.println(">> system arch: " + getArch());

		System.err.println("lib path: " + System.getProperty("java.library.path"));
		System.err.println("boot lib path: " + System.getProperty("sun.boot.library.path"));
		System.err.println("boot cp: " + System.getProperty("sun.boot.class.path"));
		System.err.println("ext cp: " + System.getProperty("java.ext.dirs"));
		System.err.println("app cp: " + System.getProperty("java.class.path"));
    }

    /**
     * Possible result:32,64,unknown
     *
     * @return OS architecture
     */
    private static String getArch() {
        return System.getProperty("sun.arch.model.model");
    }
}
