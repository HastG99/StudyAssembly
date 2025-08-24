package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;
import ru.studyasm.exceptions.RuntimeError;

public class GlobalDirective implements Instruction {


    @Override
    public void execute(VMThread thread, VMStack stack) throws RuntimeError {
        throw new RuntimeError("Global cannot be executed", thread.IP.getValue());
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("GLOBAL accepts 1 parameters.");

        if(lexer.getGlobal() != -1)
            throw new ParseException("Duplicate global declaration.");

        int global = lexer.parseLabel(args[1]);
        lexer.setGlobal(global);

        VMLogger.debug("GLOBAL: %d\n", global);
    }
}
