package ru.studyasm.instructions;

import ru.studyasm.Instruction;
import ru.studyasm.Lexer;
import ru.studyasm.VMThread;
import ru.studyasm.exceptions.ParseException;
import ru.studyasm.VMStack;

public class ShiftLeftInstruction implements Instruction {


    @Override
    public void execute(VMThread thread, VMStack stack) {

    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {

    }
}
