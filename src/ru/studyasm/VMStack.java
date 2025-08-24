package ru.studyasm;

import ru.studyasm.storages.StackFrame;

import java.util.Stack;

public class VMStack {

    private final Stack<StackFrame> stack = new Stack<>();


    public StackFrame getCurrentFrame() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void push(StackFrame frame) {
        stack.add(frame);
    }

    public void removeFrame() {
        stack.pop();
    }

}
