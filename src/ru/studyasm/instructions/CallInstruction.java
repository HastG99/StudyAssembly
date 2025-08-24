package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;

public class CallInstruction implements Instruction {

    private int index;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[CALL] %d\n", index);

        stack.getCurrentFrame().push(thread.IP.getValue());
        thread.IP.setValue(index);
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("CALL accepts 1 parameters.");

        index = lexer.parseLabel(args[1]);
    }
}
