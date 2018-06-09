package main.java;

public class BrainFuckEngine {
    private Parser parser;
    private Interpreter interpreter;

    public BrainFuckEngine(){
        parser = new Parser();
        interpreter = new Interpreter();
    }

    public void run(String programString){
        Instruction[] program = parser.parse(programString);
        interpreter.interpret(program);
    }
}
