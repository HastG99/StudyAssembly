package ru.studyasm;

import ru.studyasm.exceptions.ParseException;
import ru.studyasm.structures.SNumber;

public class DataDeclaration {

    private SNumber data;
    private final int address;

    public DataDeclaration(int address) {
        this.address = address;
    }



    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 2)
            throw new ParseException("Data is not specified.");

        if(args[1].equals("?")) {
            data = null;
            VMLogger.debug("STATIC(%d) = ?\n", address);

            return;
        }

        data = (SNumber) lexer.parseNumber(args[1]);

        VMLogger.debug("STATIC(%d) = %s\n", address, data);
    }

    public SNumber getData() {
        return data;
    }

    public int getAddress() {
        return address;
    }
}
