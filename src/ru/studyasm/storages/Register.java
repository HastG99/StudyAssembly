package ru.studyasm.storages;

public class Register implements Storage {

    protected static int idCounter = 0;

    private final String name;
    private final int address;

    private int value;

    public Register(String name) {
        this.name = name;
        this.address = idCounter++;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    public int inc() {
        return value++;
    }

    public int dec() {
        return value--;
    }

    @Override
    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "reg(" + name + ", " + value + ')';
    }

}
