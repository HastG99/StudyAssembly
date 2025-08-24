package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;
import ru.studyasm.storages.Storage;
import ru.studyasm.structures.Struct;

public class CompareInstruction implements Instruction {

    private Struct dst;
    private Struct src;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[CMP] %s, %s\n", dst, src);

        if(dst.getValue() == src.getValue()) {
            thread.CF_FLAG = false;
            thread.ZF_FLAG = true;
        }

        else if(dst.getValue() < src.getValue()) {
            thread.CF_FLAG = true;
            thread.ZF_FLAG = false;
        }

        else if(dst.getValue() > src.getValue()) {
            thread.CF_FLAG = false;
            thread.ZF_FLAG = false;
        }

        else {
            thread.CF_FLAG = true;
            thread.ZF_FLAG = true;
        }
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 3)
            throw new ParseException("CMP accepts 2 parameters.");

        dst = lexer.parseStorage(args[1]);
        src = lexer.parseStruct(args[2]);
    }

}
