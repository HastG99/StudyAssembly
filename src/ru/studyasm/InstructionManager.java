package ru.studyasm;

import ru.studyasm.instructions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class InstructionManager {
    private final Map<Token, Supplier<Instruction>> instructions = new HashMap<>();


    public InstructionManager() {
        loadInstructions();
    }

    public void loadInstructions() {
        instructions.put(Token.MOV, MovInstruction::new);
        instructions.put(Token.PUSH, PushInstruction::new);
        instructions.put(Token.POP, PopInstruction::new);
        instructions.put(Token.ADD, AddInstruction::new);
        instructions.put(Token.SUB, SubInstruction::new);
        instructions.put(Token.INC, IncInstruction::new);
        instructions.put(Token.DEC, DecInstruction::new);
        instructions.put(Token.MUL, MulInstruction::new);
        instructions.put(Token.DIV, DivInstruction::new);
        instructions.put(Token.AND, AndInstruction::new);
        instructions.put(Token.OR, OrInstruction::new);
        instructions.put(Token.XOR, XorInstruction::new);
        instructions.put(Token.NEG, NegateInstruction::new);
        instructions.put(Token.NOT, NotInstruction::new);

        instructions.put(Token.CMP, CompareInstruction::new);

        instructions.put(Token.JMP, JumpInstruction::new);
        instructions.put(Token.JE, JumpEqualInstruction::new);
        instructions.put(Token.JNE, JumpNotEqualInstruction::new);
        instructions.put(Token.JL, JumpLesserInstruction::new);
        instructions.put(Token.JG, JumpGreaterInstruction::new);

        instructions.put(Token.CALL, CallInstruction::new);
        instructions.put(Token.RET, ReturnInstruction::new);

        instructions.put(Token.HALT, HaltInstruction::new);

        instructions.put(Token.INVOKE, InvokeInstruction::new);

        instructions.put(Token.GLOBAL, GlobalDirective::new);
    }

    public Supplier<Instruction> getFactory(Token token) {
        return instructions.get(token);
    }


}
