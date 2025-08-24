package ru.studyasm;

public class VMLogger {

    private static boolean debug = false;

    public static void debug(String format, Object... args) {
        if(!debug) return;

        System.out.printf(format, args);
    }

    public static void setDebug(boolean debug) {
        VMLogger.debug = debug;
    }
}
