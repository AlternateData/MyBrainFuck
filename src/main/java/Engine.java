package main.java;

public class Engine {
    private Interpreter interpreter;

    public Engine(){
        interpreter = new Interpreter();
    }

    public void run(String programString){

        Instruction[] program;
        try {
            program = Parser.parse(programString);
            interpreter.interpret(program);
        }catch(SyntaxError se){
            se.printMessage();
        }
    }
}
