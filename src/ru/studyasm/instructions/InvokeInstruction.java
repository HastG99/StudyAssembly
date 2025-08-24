package ru.studyasm.instructions;

import ru.studyasm.*;
import ru.studyasm.exceptions.ParseException;

public class InvokeInstruction implements Instruction {

    private String signature;
    private LibraryFunction function;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        VMLogger.debug("[INVOKE] %s\n", signature);

        function.execute(thread, stack);
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("INVOKE accepts 1 parameters.");

        this.signature = args[1];
        this.function = lexer.getFunction(signature);

        if(function == null)
            throw new ParseException("Cannot resolve signature: '" + signature + "'");
    }
}
