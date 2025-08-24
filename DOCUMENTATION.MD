# StudyAssembly Documentation 📚

## 🎯 Introduction

**StudyAssembly** is an educational virtual machine and assembler designed to help developers understand low-level programming concepts through a simplified assembly language. This documentation covers everything you need to know to start programming with StudyAssembly.

## 📋 Table of Contents

1. [Assembly Syntax](#-assembly-syntax)
2. [Registers](#-registers)
3. [Instruction Set](#-instruction-set)
4. [Standard Library](#-standard-library)
5. [Memory Model](#-memory-model)
6. [Examples](#-examples)
7. [Debugging](#-debugging)
8. [Error Handling](#-error-handling)

## 🧩 Assembly Syntax

### File Structure
StudyAssembly programs consist of two main sections:

```sasm
.data  ; Data section for variable declarations
; variable declarations here

.code  ; Code section for instructions
glob main_label ; Entry point declaration

@main_label
; Your program instructions here
```

### Data Section
The `.data` section is used for variable declarations:

```sasm
.data
variable_name initial_value
```

**Examples:**
```sasm
status 0      ; Initialize to 0
counter ?     ; Uninitialized variable
max_value 100 ; Constant value
```

**Number formats:**
- Decimal: `10`
- Hexadecimal: `Ah` (suffix with 'h')
- Binary: `1010b` (suffix with 'b')

### Code Section
The `.code` section contains program instructions:

```sasm
.code
glob start    ; Declare entry point

@start        ; Label definition
; Instructions here
```

### Comments
```sasm
; This is a single-line comment
mov A 5       ; Comment after instruction
```

## 🧮 Registers

StudyAssembly provides several registers for data manipulation:

| Register | Purpose | Access |
|----------|---------|--------|
| A, B, C, D | General-purpose registers | Read/Write |
| IP | Instruction Pointer (program counter) | Read-only |

**Usage examples:**
```sasm
mov A 10    ; Load value 10 into register A
add A B     ; Add register B to register A
```

## 🎮 Instruction Set

### Data Movement Instructions

| Instruction | Syntax | Description | Flags Affected |
|-------------|--------|-------------|----------------|
| `mov` | `mov dest, src` | Move value to destination | None |
| `push` | `push value` | Push value onto stack | None |
| `pop` | `pop dest` | Pop value from stack | None |

**Examples:**
```sasm
mov A 5       ; A = 5
push A        ; Push A to stack
pop B         ; Pop value to B
```

### Arithmetic Instructions

| Instruction | Syntax | Description | Flags Affected |
|-------------|--------|-------------|----------------|
| `add` | `add dest, src` | Addition | ZF, SF, CF, OF |
| `sub` | `sub dest, src` | Subtraction | ZF, SF, CF, OF |
| `mul` | `mul dest, src` | Multiplication | ZF, SF |
| `div` | `div dest, src` | Division | ZF, SF |
| `inc` | `inc dest` | Increment | ZF, SF |
| `dec` | `dec dest` | Decrement | ZF, SF |

**Examples:**
```sasm
add A B     ; A = A + B
sub C 5     ; C = C - 5
inc D       ; D = D + 1
```

### Logical Instructions

| Instruction | Syntax | Description | Flags Affected |
|-------------|--------|-------------|----------------|
| `and` | `and dest, src` | Bitwise AND | ZF, SF |
| `or` | `or dest, src` | Bitwise OR | ZF, SF |
| `xor` | `xor dest, src` | Bitwise XOR | ZF, SF |
| `not` | `not dest` | Bitwise NOT | ZF, SF |
| `neg` | `neg dest` | Arithmetic negation | ZF, SF |

**Examples:**
```sasm
and A B     ; A = A & B
xor C 0xFF  ; C = C ^ 0xFF
not D       ; D = ~D
```

### Control Flow Instructions

| Instruction | Syntax | Description | Flags Affected |
|-------------|--------|-------------|----------------|
| `jmp` | `jmp label` | Unconditional jump | None |
| `je` | `je label` | Jump if equal (ZF=1) | None |
| `jne` | `jne label` | Jump if not equal (ZF=0) | None |
| `jl` | `jl label` | Jump if less (CF=1) | None |
| `jg` | `jg label` | Jump if greater (CF=0 and ZF=0) | None |
| `call` | `call label` | Call subroutine | None |
| `ret` | `ret` | Return from subroutine | None |
| `halt` | `halt` | Stop execution | None |
| `cmp` | `cmp op1, op2` | Compare two values | ZF, CF |

**Examples:**
```sasm
@start
cmp A B
je equal     ; Jump if A == B
jl less      ; Jump if A < B
jg greater   ; Jump if A > B

@equal
; A equals B
jmp end

@less
; A less than B
jmp end

@greater
; A greater than B

@end
halt
```

### Other Instructions

| Instruction | Syntax | Description |
|-------------|--------|-------------|
| `inv` | `inv function` | Call standard library function |

## 📚 Standard Library

StudyAssembly provides a standard library for common operations:

### I/O Functions

| Function | Description | Stack Effect |
|----------|-------------|--------------|
| `println` | Print value with newline | (value -- ) |
| `print` | Print value without newline | (value -- ) |
| `get` | Read integer from input | ( -- value) |

**Usage examples:**
```sasm
inv get        ; Read integer from input
inv println    ; Print it with newline

mov A 42
push A
inv print      ; Print without newline
```

## 🧠 Memory Model

StudyAssembly uses a simple memory model:

### Memory Types
1. **Register Memory**: Fast storage in CPU registers (A, B, C, D)
2. **Static Memory**: Variables declared in `.data` section
3. **Stack Memory**: LIFO structure for temporary storage

### Memory Access
```sasm
.data
value 10

.code
glob start

@start
mov A value    ; Load from static memory
mov value B    ; Store to static memory
push A         ; Push to stack
pop B          ; Pop from stack
```

## 🧪 Examples

### Simple Calculator
```sasm
.data
status 0

.code
glob start

@start
; Get first number
inv get
pop A

; Get second number
inv get
pop B

; Get operation (1-4)
inv get
pop C

; Perform operation
cmp C 1
je add
cmp C 2
je sub
cmp C 3
je mul
cmp C 4
je div

; Invalid operation
mov status 2
jmp exit

@add
add A B
jmp exit

@sub
sub A B
jmp exit

@mul
mul A B
jmp exit

@div
cmp B 0
je div_zero
div A B
jmp exit

@div_zero
mov status 1

@exit
push A
inv println
push status
inv println
halt
```

### Fibonacci Sequence
```sasm
.data
previous ?
current ?

.code
glob start

@start
; Get N
inv get
pop B

call fib

push A
inv println

halt

@fib
push B
push C
push D
mov A 1

; Reset variables
mov previous 0
mov current 0

; Reset counter
xor C C

@loop
add A previous
mov D current
mov previous D
mov current A

inc C
cmp C B
jl loop

pop D
pop C
pop B
ret
```

## 🔍 Debugging

### Enable Debug Mode
Run your program with the `debug` argument:
```bash
java -jar StudyAssembly.jar program.sasm debug
```

### Debug Output
Debug mode shows:
- Executed instructions with operands
- Register values after each instruction
- Stack operations
- Memory accesses

**Example debug output:**
```
[MOV] reg(A, 0), int(5)
[A = 5, B = 0, C = 0, D = 0, ZF = false]
[ADD] reg(A, 5), reg(B, 0)
[A = 5, B = 0, C = 0, D = 0, ZF = false]
```

## ⚠️ Error Handling

### Common Errors

1. **Parse Errors**
   - Syntax errors in assembly code
   - Undefined labels or variables
   - Incorrect number of operands

2. **Runtime Errors**
   - Division by zero
   - Stack overflow/underflow
   - Invalid memory access

3. **Execution Errors**
   - Infinite loops
   - Incorrect control flow

### Error Messages
StudyAssembly provides descriptive error messages:
```
Error: Division by zero at IP=24
Error: Undefined label 'nonexistent' at line 15
Error: Expected 2 operands for ADD at line 32
```

## 🚀 Performance Tips

1. Use registers for frequently accessed values
2. Minimize stack operations in performance-critical code
3. Use appropriate jump instructions for conditionals
4. Precompute values where possible

## 📝 Best Practices

1. **Code Organization**
   ```sasm
   ; Use comments liberally
   ; Group related instructions
   ; Use descriptive label names
   ```

2. **Memory Management**
   ```sasm
   ; Initialize variables in data section
   ; Clean up stack after function calls
   ; Avoid memory leaks
   ```

3. **Error Handling**
   ```sasm
   ; Check for division by zero
   ; Validate input values
   ; Use status codes for error reporting
   ```

---

<div align="center">

**Happy coding with StudyAssembly!** 💻✨

</div>
