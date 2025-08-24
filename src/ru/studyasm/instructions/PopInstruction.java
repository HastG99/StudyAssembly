package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;
import ru.studyasm.storages.Storage;

public class PopInstruction implements Instruction {

    private Storage storage;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[POP] %s\n", storage);

        storage.setValue(stack.getCurrentFrame().pop());
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("POP accepts 1 parameters.");

        storage = lexer.parseStorage(args[1]);
    }

}
