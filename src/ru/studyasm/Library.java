package ru.studyasm;

import java.util.HashMap;
import java.util.Map;

public abstract class Library {

    protected final Map<String, LibraryFunction> functions = new HashMap<>();

    public void register(String signature, LibraryFunction function) {
        functions.put(signature, function);
    }

    public Map<String, LibraryFunction> getFunctions() {
        return functions;
    }
}
