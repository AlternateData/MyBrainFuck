package main.java;

public enum Instruction {
    NOP,
    INCR('+'),
    DECR('-'),
    RSHIFT('>'),
    LSHIFT('<'),
    PRINT('.'),
    READ(','),
    LOOP_START('['),
    LOOP_END(']');

    private char identifier;

    Instruction(){

    }

    Instruction(char identifier){
        this.identifier = identifier;
    }
}
