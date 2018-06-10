package main.java;

import static main.java.Instruction.NOP;
import static main.java.Instruction.LOOP_START;
import static main.java.Instruction.LOOP_END;

public class Parser {


    private static void matchBrackets(Instruction[] program) throws UnbalancedBracketsException{
        int skips;
        boolean matched;
        Instruction searched;
        int j;
        int dj;

        for(int i = 0; i < program.length;i++){
            if(program[i] == LOOP_START){
                searched = LOOP_END;
                j = i + 1;
                dj = 1;
            }else if(program[i] == LOOP_END){
                searched = LOOP_START;
                j = i - 1;
                dj = -1;
            }else{
                continue;
            }

            skips = 0;
            matched = false;

            for(;0 <= j && j < program.length;j+=dj){
                if(program[j] == program[i]){
                    skips++;
                }else if(program[j] == searched && skips > 0){
                    skips--;
                }else if(program[j] == searched){
                    matched = true;
                    break;
                }
            }
            if(skips != 0 || !matched){
                throw new UnbalancedBracketsException(program[i], i);
            }
        }
    }

    private static Instruction[] translate(char[] programString){

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
