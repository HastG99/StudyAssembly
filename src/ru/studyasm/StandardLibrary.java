package ru.studyasm;

import java.util.Scanner;

public class StandardLibrary extends Library {

    private final Scanner scanner = new Scanner(System.in);

    public StandardLibrary() {

        register("println", new LibraryFunction() {
            @Override
            public void execute(VMThread thread, VMStack stack) {
                System.out.println(stack.getCurrentFrame().pop());
            }
        });

        register("print", new LibraryFunction() {
            @Override
            public void execute(VMThread thread, VMStack stack) {
                System.out.print(stack.getCurrentFrame().pop());
            }
        });

        register("get", new LibraryFunction() {
            @Override
            public void execute(VMThread thread, VMStack stack) {
                int n = scanner.nextInt();
                stack.getCurrentFrame().push(n);
            }
        });



    }
}
