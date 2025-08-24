package ru.studyasm;

import ru.studyasm.exceptions.ParseException;
import ru.studyasm.exceptions.RuntimeError;
import ru.studyasm.structures.Struct;

import java.util.EmptyStackException;

public interface Instruction {

    void execute(VMThread thread, VMStack stack) throws RuntimeError, EmptyStackException;

    void loadParams(Lexer lexer, String[] args) throws ParseException;

    default void updateFlags(Struct struct, VMThread thread) {
        thread.ZF_FLAG = struct.getValue() == 0;

        thread.SF_FLAG = false;
        thread.CF_FLAG = false;
        thread.OF_FLAG = false;
    }

}
