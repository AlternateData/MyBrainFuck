package main.java;

public class UnbalancedBracketsException extends SyntaxError {

    public UnbalancedBracketsException(){
        super("The Program has unbalanced brackets");
    }

    UnbalancedBracketsException(Instruction instruction, int index){
        super(String.format("The Instruction %s has no closing bracket", instruction.getIdentifier()), index);
    }

}
