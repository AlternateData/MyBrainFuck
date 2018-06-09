package main.java;

import java.nio.FloatBuffer;

public class Main{

    final static String USAGE = "mbr [program] \n"+
                                "\tInterprets brainfuck programs\n" +
                                "\t\t-Invalid Characters are ignored\n" +
                                "\t\t-Error on unbalanced brackets\n" +
                                "\t\t-4kb memory that wraps around both ways\n";

    public static void main(String[] args) {
        Engine engine = new Engine();
        if(args.length == 0){
            System.out.println(String.format(USAGE));
            System.exit(-1);
        }
        try{
            engine.run(args[0]);
        }catch(IllegalArgumentException e){
            System.err.println("Program contains syntax errors");
            System.exit(-1);
        }
        System.exit(0);
    }
}
