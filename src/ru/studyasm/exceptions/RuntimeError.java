package ru.studyasm.exceptions;

public class RuntimeError extends Exception {

    private final int ip;

    public RuntimeError(int ip) {
        this.ip = ip;
    }

    public RuntimeError(String message, int ip) {
        super(message);
        this.ip = ip;
    }

    public int getIP() {
        return ip;
    }
}
