package ru.studyasm;

import ru.studyasm.storages.Register;

import java.util.HashMap;
import java.util.Map;

public class RegisterManager {
    private final Map<String, Register> registers = new HashMap<>();

    private Register instPointer;

    public RegisterManager() {
        loadRegisters();
    }

    public void loadRegisters() {
        registers.put("A", new Register("A"));
        registers.put("B", new Register("B"));
        registers.put("C", new Register("C"));
        registers.put("D", new Register("D"));


        instPointer = new Register("IP");
        registers.put("IP", instPointer);
    }

    public Register get(String name) {
        return registers.get(name);
    }

    public boolean has(String name) {
        return registers.containsKey(name);
    }

    public Map<String, Register> getRegisters() {
        return registers;
    }

    public Register getIP() {
        return instPointer;
    }
}
