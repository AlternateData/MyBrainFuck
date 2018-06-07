package main.java;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class Interpreter {

    // TODO: retire the programm array and instead load the program directly into memory/tape array
    // TODO: replace VALID_INSTRUCTIONS with an enum, this would make things more flexible
    // TODO: move out the validate methods into a Parser class and create an additional engine class

    final static char[] VALID_INSTRUCTIONS = {'>', '<', '+', '-', '.', ',', '[', ']'};

    final static int MAX_MEMORY = 4096; // 4 kb memory
    final static int MAX_PROGRAM_MEMORY = 1024;

    private char[] program;
    private byte[] memory = new byte[MAX_MEMORY]; // this is the tape

    private final  Scanner scanner = new Scanner(System.in);


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

    public int jumpLength(char[] program, int start){
        int skip = 0;
        int len = 0;

        if(program.length < start)
            throw new IndexOutOfBoundsException("Illegal index into progam");
        if(program[start] != '[' && program[start] != ']')
            throw new IllegalArgumentException("Character at index start is not [ or ]");

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
            }else if(ithInstruction == search && skip != 0){
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
        if(program == null)
            throw new NullPointerException("The Program-String may not be null");
        if(!validateStatements(this.program))
            throw new IllegalArgumentException("The Program-String contains invalid characters");
        if(!validateBrackets(this.program))
            throw new IllegalArgumentException("Brackets do not match up");

        int instructionPointer = 0;
        int pointer = 0;
        int delta;

        while(instructionPointer < program.length){

            char instruction = program[instructionPointer];

            // this handles wrap around
            if(pointer == -1)
                pointer = MAX_MEMORY;
            else if(pointer == MAX_MEMORY + 1)
                pointer = 0;


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
                    if(memory[pointer] == 0)
                        delta = jumpLength(program, instructionPointer) + 1;
                    break;
                case ']':
                    // always jump back to the last [
                    delta = jumpLength(program, instructionPointer);
                    break;
                default:
                    throw new IllegalArgumentException("The Instruction: " + instruction + " is invalid");
            }
            instructionPointer += delta;
        }

        return memory[pointer];
    }

}
