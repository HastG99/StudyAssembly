package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;
import ru.studyasm.structures.Struct;

public class PushInstruction implements Instruction {

    private Struct data;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[PUSH] %s\n", data);
        stack.getCurrentFrame().push(data.getValue());
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("PUSH accepts 1 parameters.");

        data = lexer.parseStruct(args[1]);
    }
}
