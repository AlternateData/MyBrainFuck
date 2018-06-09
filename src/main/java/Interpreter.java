package main.java;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


import java.util.logging.*;
import static main.java.Instruction.*;

public final class Interpreter {

    // TODO: Remove the logger ! and ship !


    private final static int MAX_MEMORY = 4096; // 4 kb memory

    private byte[] memory = new byte[MAX_MEMORY]; // this is the tape

    private final  Scanner scanner = new Scanner(System.in);




    public void clearMemory(){
        for(int i=0; i<MAX_MEMORY;i++){
            memory[i] = 0;
        }
    }


    // compute the distance from the bracket at program[start] to its closing bracket
    // if there is no bracket at program[start] an IllegalArgumentException is raised
    public int jumpLength(Instruction[] program, int start){
        int skip = 0;
        int len = 0;

        if(program.length < start) {
            throw new IndexOutOfBoundsException("Illegal index into progam");
        }
        if(program[start] != LOOP_START && program[start] != LOOP_END) {
            throw new IllegalArgumentException("Character at index start is not [ or ] but is " + program[start]);
        }

        int i = start + 1;
        int di = 1;
        Instruction seen = program[start];
        Instruction search = LOOP_END;


        if(seen == LOOP_END) {
            search = LOOP_START;
            di = -1;
            i = start - 1;
        }
        //System.out.println("\tSeen: " + seen + "\tdi: " + Integer.toString(di));

        Instruction ithInstruction;
        for(;0 <= i && i < program.length; i+=di){
            ithInstruction = program[i];
            if(ithInstruction == seen) {
                skip++;
            }else if(ithInstruction == search && skip > 0){
                skip--;
            }else if(ithInstruction == search){
                len = i - start;
                break;
            }
        }
        //System.out.println("\ti: "+ Integer.toString(i) + "\tStart: "  +   Integer.toString(start));
        if(skip != 0){
            throw new IllegalArgumentException();
        }
        return len;
    }

    public byte interpret(Instruction[] program) throws NullPointerException, IllegalArgumentException{
        if(program == null) {
            throw new NullPointerException("The Program-String may not be null");
        }

        clearMemory();

        int instructionPointer = 0;
        int pointer = 0;
        int delta;
        Instruction instruction;

        while(instructionPointer < program.length){

            delta = 1;
            instruction = program[instructionPointer];

            switch(instruction){
                case NOP:
                    break;
                case RSHIFT:
                    pointer++;
                    break;
                case LSHIFT:
                    pointer--;
                    break;
                case INCR:
                    memory[pointer]++;
                    break;
                case DECR:
                    memory[pointer]--;
                    break;
                case PRINT:
                    System.out.print((char) memory[pointer]);
                    break;
                case READ:
                    String input = scanner.next();
                    byte[] inputByte = input.getBytes(StandardCharsets.US_ASCII);
                    memory[pointer] = inputByte[0];
                    break;
                case LOOP_START:
                    if(memory[pointer] == 0) {
                        delta = jumpLength(program, instructionPointer) + 1;
                    }
                    break;
                case LOOP_END:
                    // always jump back to the closing [
                    delta = jumpLength(program, instructionPointer);
                    break;
                default:
                    throw new IllegalArgumentException("The Instruction: " + instruction + " is invalid");
            }

            if(pointer == -1) {
                pointer = MAX_MEMORY - 1;
            }else if(pointer == MAX_MEMORY + 1) {
                pointer = 0;
            }
            instructionPointer += delta;

        }

        return memory[pointer];
    }

}
