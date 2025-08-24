package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;
import ru.studyasm.storages.Storage;
import ru.studyasm.structures.Struct;

public class AddInstruction implements Instruction {

    private Storage storage;
    private Struct struct;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[ADD] %s, %s\n", storage, struct);

        storage.setValue(storage.getValue() + struct.getValue());

        updateFlags(storage, thread);
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 3)
            throw new ParseException("ADD accepts 2 parameters.");

        storage = lexer.parseStorage(args[1]);
        struct = lexer.parseStruct(args[2]);
    }
}
