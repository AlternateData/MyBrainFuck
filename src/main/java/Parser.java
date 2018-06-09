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

    private static void matchBrackets(Instruction[] prog) throws UnbalancedBracketsException{
        int skips = 0;
        boolean matched;
        for(int i = 0; i < prog.length;i++){
            matched = false;
            if(prog[i] == LOOP_START){
                for(int j=i+1;j < prog.length;j++){
                    if(prog[j] == LOOP_START){
                        skips++;
                    }else if(prog[j] == LOOP_END && skips > 0){
                        skips--;
                    }else if(prog[j] == LOOP_END){
                        matched = true;
                        break;
                    }
                }
                if(skips != 0 || !matched){
                    throw new UnbalancedBracketsException(LOOP_START, i);
                }
            }else if(prog[i] ==  LOOP_END){
                for(int j=i-1;j > 0;j--){
                    if(prog[j] == LOOP_END){
                        skips++;
                    }else if(prog[j] == LOOP_START && skips > 0){
                        skips--;
                    }else{
                        break;
                    }
                }
                if(skips != 0){
                    throw new UnbalancedBracketsException(LOOP_START, i);
                }

            }
        }
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

    public static Instruction[] parse(String programString) throws UnbalancedBracketsException{
        char[] identifiers = programString.toCharArray();
        Instruction[] program = translate(identifiers);
        matchBrackets(program);
        return program;
    }


}
