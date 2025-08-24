package ru.studyasm;

import ru.studyasm.exceptions.ParseException;
import ru.studyasm.exceptions.RuntimeError;
import ru.studyasm.storages.Memory;

import java.util.EmptyStackException;

public class AssemblyMain {

    public static void main(String[] args) throws ParseException {
        if(args.length < 1) {
            System.out.println("Usage: java -jar studyasm.jar ");
            return;
        }

        String fileName = args[0];

        if(args.length > 1 && args[1].equals("debug"))
            VMLogger.setDebug(true);

        Memory memory = new Memory();
        RegisterManager registerManager = new RegisterManager();

        Lexer lexer = new Lexer(fileName, registerManager, memory);
        lexer.addLibrary(new StandardLibrary());
        lexer.lex();


        if(lexer.hasErrors()) {
            lexer.printErrors();
            return;
        }

        if(lexer.getGlobal() == -1)
            throw new ParseException("Global not defined");

        VMThread thread = new VMThread(registerManager, memory, lexer.getInstructions(), lexer.getGlobal());
        thread.loadMemory(lexer.getDeclarations());

        try {
            thread.run();
        } catch (RuntimeError e) {
            System.err.printf("An exception was thrown. IP=%d\n", e.getIP());
            e.printStackTrace();
        }
        catch (EmptyStackException e) {
            System.err.printf("EmptyStack was thrown. IP=%d\n", thread.IP.getValue());
            e.printStackTrace();
        }

    }

}
