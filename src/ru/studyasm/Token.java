package ru.studyasm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {

    // Инструкции

    // Инструкции по перемещению данных.
    MOV("mov"),
    PUSH("push"),
    POP("pop"),


    // Арифметические и Логические Инструкции.
    ADD("add"),
    SUB("sub"),
    INC("inc"),
    DEC("dec"),

    MUL("mul"),
    DIV("div"),

    AND("and"),
    OR("or"),
    XOR("xor"),

    NOT("not"),
    NEG("neg"),


    // Инструкции потока управления. (Control Flow)
    JMP("jmp"),

    JE("je"),
    JNE("jne"),
    JG("jg"),
    JL("jl"),

    CMP("cmp"),

    CALL("call"),
    RET("ret"),

    HALT("halt"),

    INVOKE("inv"),

    GLOBAL("glob", "code"),

    LABEL("^@[A-Za-z0-9]+$"),

    SECTION("^\\.[A-Za-z0-9]+$");;


    private final Pattern pattern;
    private final String section;

    Token(String regex) {
        pattern = Pattern.compile(regex);
        section = null;
    }

    Token(String regex, String section) {
        pattern = Pattern.compile(regex);
        this.section = section;
    }

    public boolean match(String str, String sectionName) {
        str = str.toLowerCase();

        Matcher matcher = pattern.matcher(str);

        return matcher.find() && (this.section == null || this.section.equals(sectionName));
    }


}
