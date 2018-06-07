package main.java;

public class Main {

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter(">>>+<");
        byte ret = interpreter.interpret();
        //System.out.println(ret);
        System.out.println(interpreter);


    }
}
