package ru.studyasm;

import ru.studyasm.exceptions.RuntimeError;
import ru.studyasm.storages.Memory;
import ru.studyasm.storages.Register;
import ru.studyasm.storages.StackFrame;
import ru.studyasm.structures.SNumber;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;

public class VMThread {

    private final VMStack stack = new VMStack();

    private final Instruction[] instructions;
    private final RegisterManager registerManager;
    private final Memory memory;

    public final Register IP;

    public boolean ZF_FLAG = false;
    public boolean OF_FLAG = false;
    public boolean SF_FLAG = false;
    public boolean CF_FLAG = false;

    private final int global;

    private final Random random = new Random();


    public VMThread(RegisterManager registerManager, Memory memory, Instruction[] instructions, int global) {
        this.registerManager = registerManager;
        this.memory = memory;
        this.IP = registerManager.getIP();
        this.instructions = instructions;
        this.global = global;
    }

    public void loadMemory(List<DataDeclaration> declarations) {
        memory.allocate(declarations.size());

        for(DataDeclaration dec : declarations) {
            SNumber val = dec.getData();

            memory.setValue(dec.getAddress(),
                    val == null ? random.nextInt() : val.getValue());
        }
    }


    public void run() throws RuntimeError, EmptyStackException {
        IP.setValue(global);

        stack.push(new StackFrame());

        while (IP.getValue() < instructions.length) {
            Instruction inst = instructions[IP.inc()];

            inst.execute(this, stack);
        }
    }

    public void halt() {
        IP.setValue(instructions.length);
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public Memory getMemory() {
        return memory;
    }
}
