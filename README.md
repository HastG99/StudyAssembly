# StudyAssembly 🖥️

Educational virtual machine and assembler with a simplified syntax, implemented in Java.

## 📋 Overview

**StudyAssembly** is a stack-based virtual machine with a custom assembly language. It includes an assembler (lexer), VM, standard library, and examples to help learn low-level programming concepts in a fun and interactive way!

## ✨ Features

- **🧠 Simplified Assembly Syntax**: Easy-to-learn instruction set
- **⚡ Virtual Machine**: Stack-based execution with registers and memory
- **📚 Standard Library**: Built-in functions for I/O (e.g., `print`, `get`)
- **📂 Examples**: Ready-to-run programs (calculator, Fibonacci, etc.)
- **🔍 Debug Mode**: Logs VM execution details

## 🏗️ Project Structure

```
StudyAssembly/
├── src/                 # Java source code
├── examples/            # Assembly code examples
│   ├── calc.sasm       # Calculator program
│   ├── echo.sasm       # Input/output example
│   ├── fibonacci.sasm  # Fibonacci sequence
│   └── pow.sasm        # Exponentiation example
└── out/                 # Compiled artifacts
```

## 🚀 Quick Start

### Prerequisites
- Java JDK 8 or higher

### Build the project
```bash
javac -d out/production/StudyAssembly src/ru/studyasm/*.java src/ru/studyasm/**/*.java
jar cfm out/artifacts/StudyAssembly_jar/StudyAssembly.jar src/META-INF/MANIFEST.MF -C out/production/StudyAssembly .
```

### Run an example
```bash
java -jar out/artifacts/StudyAssembly_jar/StudyAssembly.jar examples/calc.sasm
```

### Debug mode
```bash
java -jar out/artifacts/StudyAssembly_jar/StudyAssembly.jar examples/calc.sasm debug
```

## 📊 Examples

### Calculator (`calc.sasm`) 🧮
```sasm
; Simple calculator
; Supported operations:
; 1 - add, 2 - sub, 3 - mul, 4 - div
```

### Fibonacci (`fibonacci.sasm`) 🔢
```sasm
; Calculates Nth Fibonacci number
; using iterative approach
```

## 🧩 Language Syntax

### Sections
```sasm
.data  ; Data section (variables)
.code  ; Code section (instructions)
```

### Variables
```sasm
myvar ?  ; Uninitialized variable
status 0 ; Initialized to 0
```

### Instructions
```sasm
mov A 5     ; Move value 5 to register A
add A B     ; A = A + B
push A      ; Push value to stack
inv println ; Call standard library function
```

### Labels
```sasm
@start      ; Label declaration
jmp start   ; Jump to label
```

## 📚 Documentation

Full documentation is available in:
- [**DOCUMENTATION.MD**](DOCUMENTATION.MD): Complete instruction set reference, memory model explanation, standard library functions, and debugging guide
- [**CONTRIBUTE.MD**](CONTRIBUTE.MD): Developer contribution guidelines and technical documentation

## 🤝 Contributing

Contributions are welcome! Feel free to:
1. 🍴 Fork the project
2. 🌿 Create a feature branch
3. 💻 Make your changes
4. 📤 Submit a pull request

For detailed contribution guidelines, please see [CONTRIBUTE.MD](CONTRIBUTE.MD).

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

**Happy coding!** 💻✨

</div>

---

*Note: This project is intended for educational purposes to understand assembly language concepts and virtual machine implementation.*
