package main.java;

public class UnbalancedBracketsException extends SyntaxError {


    UnbalancedBracketsException(Instruction instruction, int index){
        super(String.format("The Instruction %s has no closing bracket", instruction.getIdentifier()), index);
    }

}
