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
        identifier = 0;
    }

    Instruction(char identifier){
        this.identifier = identifier;
    }

    public String toString(){
        return this.name() + " " + this.getIdentifier();
    }

    public char getIdentifier(){
        return identifier;
    }
}
