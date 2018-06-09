package main.java;

public class UnbalancedBracketsException extends Exception {
    UnbalancedBracketsException(){
        super("The Program has unbalaced brackets");
    }

    UnbalancedBracketsException(Instruction instruction, int index){
        super(String.format("The Instruction(%s) at index %d has no closing bracket", instruction.getIdentifier(), index));
    }

    void printMessage(){
        System.out.println(getMessage());
    }
}
