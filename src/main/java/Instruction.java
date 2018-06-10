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
    private byte opcode;

    Instruction(){
        identifier = 0;
        opcode = 0;
    }

    Instruction(char identifier){
        this.identifier = identifier;
        // causes problems when MAX_BYTE < identifier
        this.opcode = (byte) identifier;
    }

    public String toString(){
        return this.name() + " " + this.getIdentifier();
    }

    public char getIdentifier(){
        return identifier;
    }
    public byte getOpcode(){
        return opcode;
    }
}
