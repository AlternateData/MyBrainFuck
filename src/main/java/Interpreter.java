package main.java;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import java.util.logging.*;

public final class Interpreter {

    // TODO: retire the programm array and instead load the program directly into memory/tape array
    // TODO: replace VALID_INSTRUCTIONS with an enum, this would make things more flexible
    // TODO: move out the validate methods into a Parser class and create an additional engine class
    // TODO: change output and input in our giant switch statement

    final static char[] VALID_INSTRUCTIONS = {'>', '<', '+', '-', '.', ',', '[', ']'};

    final static int MAX_MEMORY = 4096; // 4 kb memory
    final static int MAX_PROGRAM_MEMORY = 1024;

    private char[] program;
    private byte[] memory = new byte[MAX_MEMORY]; // this is the tape

    private final  Scanner scanner = new Scanner(System.in);

    static Logger logger;


    // this inits the logger
    static{
        logger = Logger.getLogger(Interpreter.class.getName());
        logger.setLevel(Level.FINE);
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

    public Interpreter(){

    }

    Interpreter(String program){
        loadProgram(program);
    }


    private static boolean validateStatements(char[] prog){
        boolean valid = false;
        for(char progStat : prog){
            valid = false;
            for(char validStat:VALID_INSTRUCTIONS){
                if(progStat == validStat){
                    valid = true;
                    break;
                }
            }
            if(!valid){
                break;
            }
        }
        return valid;
    }


    private static boolean validateBrackets(char[] prog){
        int i = 0;
        for(char progStat:prog){
            if(progStat == '[')
                i++;
            else if(progStat == ']')
                i--;
        }
        return i == 0;
    }

    public static boolean validate(char[] prog){
        assert(prog != null);
        return validateStatements(prog) && validateBrackets(prog);
    }

    public void loadProgram(String program){
        // this will later on contain code to load the program directly into this.memory
        // fow now the program is stored in a seperate array
        this.program = program.toCharArray();
    }

    // compute the distance from the bracket at program[start] to its closing bracket
    // if there is no bracket at program[start] an IllegalArgumentException is raised
    public int jumpLength(char[] program, int start){
        int skip = 0;
        int len = 0;

        if(program.length < start) {
            logger.severe("Illegal index into progam");
            throw new IndexOutOfBoundsException("Illegal index into progam");
        }
        if(program[start] != '[' && program[start] != ']') {
            logger.severe("Character at index start is neither [ nor ] but is " + program[start]);
            throw new IllegalArgumentException("Character at index start is not [ or ]");
        }

        int i = start + 1;
        int di = 1;
        char seen = program[start];
        char search = ']';


        if(seen == ']') {
            search = '[';
            di = -1;
            i = start - 1;
        }
        //System.out.println("\tSeen: " + seen + "\tdi: " + Integer.toString(di));

        char ithInstruction;
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
        assert(skip != 0);
        return len;
    }




    byte interpret() throws NullPointerException, IllegalArgumentException {
        return interpret(program);
    }

    byte interpret(String program) throws NullPointerException, IllegalArgumentException {
        return interpret(program.toCharArray());
    }

    public byte interpret(char[] program) throws NullPointerException, IllegalArgumentException{
        //load_program(program);
        if(program == null) {
            logger.severe("No program to interpret was given");
            throw new NullPointerException("The Program-String may not be null");
        }

        logger.info("------------------");
        logger.info("Working on program " + new String(program));
        /*
        // validation happens during parsing now
        if(!validateStatements(this.program))
            throw new IllegalArgumentException("The Program-String contains invalid characters");
        if(!validateBrackets(this.program))
            throw new IllegalArgumentException("Brackets do not match up");
        */

        int instructionPointer = 0;
        int pointer = 0;
        int delta;
        char instruction;

        while(instructionPointer < program.length){

            instruction = program[instructionPointer];
            logger.info("Working on Instruction: " + instruction);



            delta = 1;
            switch(instruction){
                case '>':
                    pointer++;
                    break;
                case '<':
                    pointer--;
                    break;
                case '+':
                    memory[pointer]++;
                    break;
                case '-':
                    memory[pointer]--;
                    break;
                case '.':
                    byte[] outputByte = {memory[pointer]};
                    String out = new String(outputByte, StandardCharsets.US_ASCII);
                    System.out.print(out);
                    break;
                case ',':
                    String input = scanner.next();
                    byte[] inputByte = input.getBytes(StandardCharsets.US_ASCII);
                    memory[pointer] = inputByte[0];
                    break;
                case '[':
                    if(memory[pointer] == 0) {
                        delta = jumpLength(program, instructionPointer) + 1;
                        logger.fine(String.format("Jumping forward %d", instructionPointer + delta));
                    }
                    break;
                case ']':
                    // always jump back to the closing [
                    delta = jumpLength(program, instructionPointer);
                    logger.fine(String.format("Jumping backwards %d", instructionPointer + delta));
                    break;
                default:
                    logger.severe("Encountered Invalid Instruction: " + instruction);
                    throw new IllegalArgumentException("The Instruction: " + instruction + " is invalid");
            }

            if(pointer == -1) {
                pointer = MAX_MEMORY - 1;
                logger.info("Memory Underflow");
            }else if(pointer == MAX_MEMORY + 1) {
                logger.info("Memory Overflow");
                pointer = 0;
            }

            logger.info(String.format("Pointer: %d\tMemory: %d", pointer, memory[pointer]));
            instructionPointer += delta;
        }

        return memory[pointer];
    }

}
