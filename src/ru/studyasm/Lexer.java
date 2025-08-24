package ru.studyasm;

import ru.studyasm.exceptions.ParseException;
import ru.studyasm.storages.Memory;
import ru.studyasm.storages.MemoryReference;
import ru.studyasm.storages.Storage;
import ru.studyasm.structures.SNumber;
import ru.studyasm.structures.Struct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Lexer {
    private final List<String> input;

    private int lineCounter;
    private String currentLine;

    private boolean exhausted = false;

    private int instructionIndex = 0;
    private int memoryIndex = 0;

    private final List<String> errors = new ArrayList<>();

    private final InstructionManager instructionManager = new InstructionManager();
    private final RegisterManager registerManager;
    private final Memory memory;

    private final List<Instruction> instructions = new ArrayList<>();
    private final List<DataDeclaration> declarations = new ArrayList<>();
    private final Map<String, Integer> labels = new HashMap<>();
    private final Map<String, Section> sections = new HashMap<>();
    private final Map<String, LibraryFunction> functions = new HashMap<>();
    private final Map<String, MemoryReference> variables = new HashMap<>();

    private static final String DATA_SEC = "data";
    private static final String CODE_SEC = "code";

    private Section currentSection = null;
    private int global = -1;


    public Lexer(String filePath, RegisterManager registerManager, Memory memory) throws ParseException {
        this.registerManager = registerManager;
        this.memory = memory;

        try {
            input = Files.readAllLines(Paths.get(filePath));
        } catch (IOException ex) {
            exhausted = true;
            throw new ParseException("Could not read file: " + filePath, ex);
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void printErrors() {
        for (String error : errors)
            System.out.println(error);
    }

    public void lex() {
        findSections();
        findLabels();

        lexSection(DATA_SEC);
        lexSection(CODE_SEC);

        exhausted = true;
    }

    public void lexSection(String sectionName) {
        currentSection = sections.get(sectionName);

        if(currentSection == null) return;

        long start = currentSection.getStart();
        long end = currentSection.getEnd();

        for (String line : input) {
            lineCounter++;

            if(lineCounter <= start) continue;
            if(lineCounter >= end) break;

            currentLine = line.trim().replaceAll("[\\s]{2,}", " ");

            try {
                if(currentSection.getName().equals(CODE_SEC))
                    nextInstruction();
                else if(currentSection.getName().equals(DATA_SEC))
                    nextVariable();
            } catch (ParseException ex) {
                errors.add(ex.getMessage() + " :" + lineCounter);
            }
        }

        lineCounter = 0;
    }

    public void findLabels() {
        currentSection = sections.get(CODE_SEC);

        long start = currentSection.getStart();
        long end = currentSection.getEnd();

        for (String line : input) {
            lineCounter++;

            if(lineCounter <= start) continue;
            if(lineCounter >= end) break;

            currentLine = line.trim().replaceAll("[\\s]{2,}", " ");

            if(currentLine.isEmpty() ||
                    currentLine.startsWith(";") ||
                    currentLine.startsWith(".") ||
                    currentLine.startsWith("glob "))
                continue;

            if (!currentLine.startsWith("@")) {
                instructionIndex++;
                continue;
            }

            findLabel();
        }

        lineCounter = 0;
        instructionIndex = 0;
    }

    public void findLabel() {
        String labelName = currentLine.replaceAll(";.*", "").trim();

        if (!validateLabel(labelName))
            return;

        labels.put(labelName.substring(1), instructionIndex);
    }

    public boolean validateLabel(String labelName) {
        String[] args = labelName.split(" ");

        if(args.length > 1) {
            errors.add("Illegal Statement:" + lineCounter);
            return false;
        }

        return true;
    }

    public void findSections() {
        for (String line : input) {
            lineCounter++;
            currentLine = line.trim().replaceAll("[\\s]{2,}", " ");

            if(!currentLine.startsWith("."))
                continue;

            String secName = findSection();

            if(sections.containsKey(secName)) {
                errors.add(String.format("Duplicate Section '%s': %d", secName, lineCounter));
                continue;
            }

            if(currentSection != null && !currentSection.getName().equals(secName)) {
                currentSection.setEnd(lineCounter);
            }

            currentSection = new Section(secName, lineCounter);
            sections.put(secName, currentSection);
        }

        if(currentSection != null && currentSection.getEnd() == 0)
            currentSection.setEnd(++lineCounter);

        lineCounter = 0;
    }

    public String findSection() {
        String secName = currentLine.replaceAll(";.*", "").trim();

        if (!validateSection(secName))
            return null;

        return secName.substring(1);
    }

    public boolean validateSection(String secName) {
        String[] args = secName.split(" ");

        if(args.length > 1) {
            errors.add("Illegal Statement:" + lineCounter);
            return false;
        }

        return true;
    }

    public void nextInstruction() throws ParseException {
        String[] args = currentLine.replaceAll(";.*", "").split(" ");

        String firstToken = args[0];

        if(firstToken.isEmpty() || firstToken.startsWith(";") || firstToken.startsWith("@") || firstToken.startsWith("."))
            return;

        Token token = null;

        for (Token t : Token.values()) {
            if (t.match(firstToken, currentSection.getName())) {
                token = t;
                break;
            }
        }

        if(token == null)
            throw new ParseException("Illegal Instruction:");

        Instruction inst = instructionManager.getFactory(token).get();
        inst.loadParams(this, args);

        if(token == Token.GLOBAL) return;

        instructions.add(inst);

        instructionIndex++;
    }

    public void nextVariable() throws ParseException {
        String[] args = currentLine.replaceAll(";.*", "").split(" ");

        String variableName = args[0];

        if(variableName.isEmpty() || variableName.startsWith(";") || variableName.startsWith("@") || variableName.startsWith("."))
            return;

        Token token = null;

        for (Token t : Token.values()) {
            if (t.match(variableName, currentSection.getName())) {
                token = t;
                break;
            }
        }

        if(token != null)
            throw new ParseException("Illegal variable id. This identifier cannot be used.");

        variables.put(variableName, new MemoryReference(memory, memoryIndex));

        DataDeclaration declaration = new DataDeclaration(memoryIndex++);
        declaration.loadParams(this, args);

        declarations.add(declaration);
    }

    public Instruction[] getInstructions() {
        return instructions.toArray(new Instruction[0]);
    }

    public List<DataDeclaration> getDeclarations() {
        return declarations;
    }

    public Storage parseStorage(String str) throws ParseException {
        if(registerManager.has(str))
            return registerManager.get(str);

        if(variables.containsKey(str))
            return variables.get(str);

        throw new ParseException("Cannot resolve symbol: '" + str + "'");
    }

    public int parseLabel(String str) throws ParseException {
        if(!labels.containsKey(str))
            throw new ParseException("Label not defined: '" + str + "'");

        return labels.get(str);
    }

    public Struct parseStruct(String str) throws ParseException {
        char firstChar = str.charAt(0);

        if(Character.isDigit(firstChar))
            return parseNumber(str);

        return parseStorage(str);
    }


    public Struct parseNumber(String str) throws ParseException {
        String format = str.replaceAll("[0-9A-F]+|[-.]", "");

        int value;

        if(format.isEmpty())
            return parseDecimal(str);

        else {
            String num = str.substring(0, str.length() - 1);

            switch (format) {
                case "h":
                    value = Integer.parseInt(num, 16);

                    break;

                case "d":
                    value = Integer.parseInt(num);

                    break;

                case "b":
                    value = Integer.parseInt(num, 2);
                    break;

                default:
                    throw new ParseException("Not a statement.");
            }

            return new SNumber(value);

        }
    }

    private Struct parseDecimal(String str) throws ParseException {
        try {

            int value;

//            if(str.contains("."))
//                value = Double.parseDouble(str);
//            else

            value = Integer.parseInt(str);

            return new SNumber(value);

        } catch (NumberFormatException ex) {
            throw new ParseException("Not a number");
        }
    }

    public void addLibrary(Library library) {
        functions.putAll(library.getFunctions());
    }

    public LibraryFunction getFunction(String sig) {
        return functions.get(sig);
    }

    public void setGlobal(int global) {
        this.global = global;
    }

    public int getGlobal() {
        return global;
    }
}
