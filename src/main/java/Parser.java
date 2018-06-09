package main.java;

import static main.java.Instruction.NOP;
import static main.java.Instruction.LOOP_START;
import static main.java.Instruction.LOOP_END;

public class Parser {


    private static boolean validateBrackets(char[] prog){
        int i = 0;
        for(char progStat:prog){
            if(progStat == LOOP_START.getIdentifier())
                i++;
            else if(progStat == LOOP_END.getIdentifier())
                i--;
        }
        return i == 0;
    }


    public static Instruction[] translate(char[] programString){

        Instruction instruction;
        Instruction[] program = new Instruction[programString.length];
        char instructionId;

        for(int i = 0; i < programString.length; i++){

            instruction = NOP;
            instructionId = programString[i];
            for(Instruction candidate:Instruction.values()){
                if(candidate.getIdentifier() == instructionId){
                    instruction = candidate;
                    break;
                }
            }
            program[i] = instruction;
        }
        return program;
    }

    public static Instruction[] parse(String programString){
        char[] identifiers = programString.toCharArray();
        if(!validateBrackets(identifiers)){
            throw new IllegalArgumentException("The Brackets in the given program do not match up !");
        }
        return translate(identifiers);
    }


}
