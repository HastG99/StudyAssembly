package ru.studyasm.storages;

import java.util.Stack;

public class StackFrame {

    private final Stack<Integer> operandStack = new Stack<>();

    public Integer pop() {
        return operandStack.pop();
    }

    public void push(Integer value) {
        operandStack.push(value);
    }


}
