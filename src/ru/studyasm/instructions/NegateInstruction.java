package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;
import ru.studyasm.storages.Storage;

public class NegateInstruction implements Instruction {

    private Storage storage;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[NEG] %s\n", storage);

        storage.setValue(-storage.getValue());

        updateFlags(storage, thread);
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("NEG accepts 1 parameters.");

        storage = lexer.parseStorage(args[1]);
    }
}
