package ru.studyasm.structures;

public class SNumber implements Struct {

    private final int value;

    public SNumber(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "int(" + value + ")";
    }
}
