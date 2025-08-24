package ru.studyasm.storages;

public class Memory {

    private int[] memory;

    public void allocate(int size) {
        memory = new int[size];
    }

    public void setValue(int address, int value){
        memory[address] = value;
    }

    public int getValue(int address){
        return memory[address];
    }
}
