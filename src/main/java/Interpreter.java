package main.java;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


import java.util.logging.*;
import static main.java.Instruction.*;

public final class Interpreter {

    // TODO: retire the programm array and instead load the program directly into memory/tape array

    private final static char[] VALID_INSTRUCTIONS = {'>', '<', '+', '-', '.', ',', '[', ']'};

    private final static int MAX_MEMORY = 4096; // 4 kb memory
    private final static int MAX_PROGRAM_MEMORY = 1024;

    private byte[] memory = new byte[MAX_MEMORY]; // this is the tape

    private final  Scanner scanner = new Scanner(System.in);

    private static Logger logger;


    // this inits the logger
    static{
        logger = Logger.getLogger(Interpreter.class.getName());
        //logger.setLevel(Level.FINE);
        logger.setLevel(Level.SEVERE);
        Handler handler;
        Formatter formatter = new Formatter(){

            @Override
            public String getHead(Handler h) {
                return super.getHead(h);
            }

            @Override
            public String format(LogRecord record) {
                return String.format("[%s]: %s\n", record.getLevel(), record.getMessage());
            }
        };

        try {
            handler = new FileHandler("C:/Users/Louis/IdeaProjects/BrainFuck/logs/log%g.txt");
            handler.setFormatter(formatter);
            logger.addHandler(handler);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }




    public void clearMemory(){
        logger.info("Clearing internal memory");
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
            logger.severe("Illegal index into progam");
            throw new IndexOutOfBoundsException("Illegal index into progam");
        }
        if(program[start] != LOOP_START && program[start] != LOOP_END) {
            logger.severe("Character at index start is neither [ nor ] but is " + program[start]);
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
            logger.severe("Brackets in Program do not match up");
            throw new IllegalArgumentException();
        }
        return len;
    }

    public byte interpret(Instruction[] program) throws NullPointerException, IllegalArgumentException{
        logger.info("Started interpreting a program");
        if(program == null) {
            logger.severe("No program to interpret was given");
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
                        logger.fine(String.format("Jumping forward %d", instructionPointer + delta));
                    }
                    break;
                case LOOP_END:
                    // always jump back to the closing [
                    delta = jumpLength(program, instructionPointer);
                    logger.fine(String.format("Jumping backward %d", instructionPointer + delta));
                    break;
                default:
                    logger.severe("Encountered Invalid Instruction: " + instruction);
                    throw new IllegalArgumentException("The Instruction: " + instruction + " is invalid");
            }

            if(pointer == -1) {
                pointer = MAX_MEMORY - 1;
                logger.info("Memory Underflow");
            }else if(pointer == MAX_MEMORY + 1) {
                pointer = 0;
                logger.info("Memory Overflow");
            }
            instructionPointer += delta;

            logger.fine(String.format("Interpreter State:\n\t\tPointer: %d\n\t\tMemory: %d", pointer, memory[pointer]));
        }

        return memory[pointer];
    }

}
