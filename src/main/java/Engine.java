package main.java;

import java.io.*;
import java.util.NoSuchElementException;

public class Engine {
    private Interpreter interpreter;

    public Engine(){
        interpreter = new Interpreter();
    }


    private String loadProgramFromFile(File file){
        String programLine = "";
        StringBuilder program = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))){
            while(programLine != null){
                program.append(programLine);
                programLine = br.readLine();
            }
        }catch(FileNotFoundException e){
            System.err.println("The given File:"+ file.getAbsolutePath() + " does not exist");
        }catch(IOException e){
            e.printStackTrace();
        }
        return program.toString();
    }

    public void run(File file){
        String programString = loadProgramFromFile(file);
        run(programString);
    }

    public void run(String programString){

        Instruction[] program;
        try {
            program = Parser.parse(programString);
            interpreter.interpret(program);
        }catch(SyntaxError se){
            se.printMessage();
        }catch(NoSuchElementException e){
            System.out.println("\nProgram ended prematurely. Scanner has run out of elements.");
        }
    }
}
