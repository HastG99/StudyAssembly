package ru.studyasm.storages;

public class MemoryReference implements Storage {

    private final Memory memory;
    private final int address;

    public MemoryReference(Memory memory, int address) {
        this.memory = memory;
        this.address = address;
    }

    @Override
    public void setValue(int value) {
        memory.setValue(address, value);
    }

    @Override
    public int getValue() {
        return memory.getValue(address);
    }

    public int getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return "ref(" + address + ")";
    }
}
