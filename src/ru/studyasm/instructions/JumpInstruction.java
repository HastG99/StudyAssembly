package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;

public class JumpInstruction implements Instruction {

    private int index;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[JMP] %d\n", index);
        thread.IP.setValue(index);
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("JMP accepts 1 parameters.");

        index = lexer.parseLabel(args[1]);
    }

}
