package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;

public class JumpGreaterEqualInstruction implements Instruction {

    private int index;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[JG] %d\n", index);

        if(thread.OF_FLAG == thread.SF_FLAG || !thread.ZF_FLAG)
            thread.IP.setValue(index);
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("JG accepts 1 parameters.");

        index = lexer.parseLabel(args[1]);
    }

}
