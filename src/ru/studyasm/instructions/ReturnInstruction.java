package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;

public class ReturnInstruction implements Instruction {

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[RET]\n");

        thread.IP.setValue(stack.getCurrentFrame().pop());
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 1)
            throw new ParseException("RET accepts 0 parameters.");
    }
}
