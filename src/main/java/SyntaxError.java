package main.java;

public class SyntaxError extends Exception{


    SyntaxError(String msg){
        super(msg);
    }


    SyntaxError(String msg, int index){
        super("[Syntax Error] at index " + Integer.toString(index) + " - " + msg);
    }

    void printMessage(){
        System.err.println(getMessage());
    }

}
