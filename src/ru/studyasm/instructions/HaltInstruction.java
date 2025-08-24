package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;

public class HaltInstruction implements Instruction {

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[HALT]\n");

        thread.halt();
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 1)
            throw new ParseException("HALT accepts 0 parameters.");
    }
}
