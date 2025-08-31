# CONTRIBUTE.MD

## Table of Contents
1. [Introduction](#introduction)
2. [Project Structure](#project-structure)
3. [Core Components](#core-components)
4. [Adding New Instructions](#adding-new-instructions)
5. [Extending Standard Library](#extending-standard-library)
6. [Memory Management](#memory-management)
7. [Debugging](#debugging)
8. [Error Handling](#error-handling)
9. [Building and Testing](#building-and-testing)
10. [Contribution Guidelines](#contribution-guidelines)

## Introduction

StudyAssembly is an educational virtual machine and assembler designed to help developers understand low-level programming concepts. This document provides technical documentation for developers who want to contribute to the project.

## Project Structure

```
StudyAssembly/
├── src/ru/studyasm/
│   ├── exceptions/          # Custom exceptions
│   ├── instructions/        # Instruction implementations
│   ├── storages/           # Memory and storage components
│   └── structures/         # Data structures
├── examples/               # Sample assembly programs
└── out/                    # Compiled artifacts
```

## Core Components

### Lexer (`Lexer.java`)
Responsible for parsing assembly code and converting it into executable instructions.

Key methods:
```java
public void lex() // Main parsing method
public Storage parseStorage(String str) // Resolves symbols to storage objects
public Struct parseStruct(String str) // Parses numbers or storage references
```

### Instruction Set (`instructions/`)
Each instruction implements the `Instruction` interface:

```java
public interface Instruction {
    void execute(VMThread thread, VMStack stack);
    void loadParams(Lexer lexer, String[] args);
}
```

### Virtual Machine (`VMThread.java`)
Executes compiled instructions and manages program state.

Key components:
- Register management (A, B, C, D, IP)
- Flag management (ZF, OF, SF, CF)
- Memory access
- Stack operations

### Memory Model (`storages/`)
Three types of storage:
1. Registers (`Register.java`) - Fast CPU storage
2. Static Memory (`Memory.java`) - Variables from .data section
3. Stack Memory (`StackFrame.java`) - Temporary storage

## Adding New Instructions

1. Create a new class in `instructions/` package implementing `Instruction`
2. Implement execute() and loadParams() methods
3. Register the instruction in `InstructionManager.loadInstructions()`

Example for a new `shl` (shift left) instruction:

```java
public class ShiftLeftInstruction implements Instruction {
    private Storage storage;
    private Struct shiftAmount;

    @Override
    public void execute(VMThread thread, VMStack stack) {
        storage.setValue(storage.getValue() << shiftAmount.getValue());
        updateFlags(storage, thread);
    }

    @Override
    public void loadParams(Lexer lexer, String[] args) throws ParseException {
        if(args.length != 3) throw new ParseException("SHL requires 2 parameters");
        storage = lexer.parseStorage(args[1]);
        shiftAmount = lexer.parseStruct(args[2]);
    }
}
```

Register in InstructionManager:
```java
instructions.put(Token.SHL, ShiftLeftInstruction::new);
```

## Extending Standard Library

1. Add new function in `StandardLibrary.java`
2. Implement `LibraryFunction` interface

Example for a random number generator:

```java
register("rand", new LibraryFunction() {
    private final Random random = new Random();
    
    @Override
    public void execute(VMThread thread, VMStack stack) {
        stack.getCurrentFrame().push(random.nextInt());
    }
});
```

Usage in assembly:
```sasm
inv rand
pop A
```

## Memory Management

### Accessing Memory
```java
// From instruction implementation
memory.setValue(address, value);
int value = memory.getValue(address);

// Through memory reference
MemoryReference ref = new MemoryReference(memory, address);
ref.setValue(42);
```

### Creating New Storage Types
Implement the `Storage` interface:

```java
public class CustomStorage implements Storage {
    private int value;
    
    @Override
    public void setValue(int value) { this.value = value; }
    
    @Override
    public int getValue() { return value; }
    
    @Override
    public int getAddress() { return -1; } // Or actual address
}
```

## Debugging

Enable debug mode by passing "debug" as second argument:
```bash
java -jar StudyAssembly.jar program.sasm debug
```

Add debug output in instructions:
```java
VMLogger.debug("[SHL] %s, %s\n", storage, shiftAmount);
```

## Error Handling

### Custom Exceptions
- `ParseException` - For parsing errors
- `RuntimeError` - For execution errors

### Adding New Error Checks
```java
// In instruction execute method
if(shiftAmount.getValue() < 0) {
    throw new RuntimeError("Negative shift amount", thread.IP.getValue());
}
```

## Building and Testing

### Build Process
```bash
# Compile
javac -d out/production/StudyAssembly src/ru/studyasm/*.java src/ru/studyasm/**/*.java

# Create JAR
jar cfm out/artifacts/StudyAssembly_jar/StudyAssembly.jar \
    src/META-INF/MANIFEST.MF -C out/production/StudyAssembly .
```

### Testing New Instructions
1. Create test assembly file in `examples/`
2. Run with debug mode to verify execution
3. Test edge cases and error conditions

Example test:
```sasm
; test_shl.sasm
.code
glob start

@start
mov A 5      ; A = 5
mov B 2      ; B = 2
shl A B      ; A = A << B (should be 20)

push A
inv println
halt
```

## Contribution Guidelines

1. Follow existing code style and patterns
2. Add appropriate error handling
3. Include debug logging for new features
4. Update documentation (DOCUMENTATION.MD)
5. Add test examples in examples/ folder
6. Test both success and error cases

### Code Style
- Use 4 spaces for indentation
- Follow Java naming conventions
- Add comments for complex logic
- Use VMLogger.debug() for execution logging

### Pull Request Process
1. Fork the repository
2. Create feature branch
3. Make changes with tests
4. Update documentation
5. Submit pull request with description

### Testing Requirements
- Test with various input values
- Verify error conditions are handled
- Test in both normal and debug modes
- Verify stack integrity after operations

For questions about implementation, examine existing instructions in the `instructions/` package as reference implementations.
