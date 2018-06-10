package main.java;


public class Main{

    final static String USAGE = "mbr [program] \n"+
                                "\tInterprets brainfuck programs\n" +
                                "\t\t-Invalid Characters are ignored\n" +
                                "\t\t-Error on unbalanced brackets\n" +
                                "\t\t-4kb memory that wraps around both ways\n";

    public static void main(String[] args) {
        Engine engine = new Engine();
        if(args.length == 0){
            System.out.println(USAGE);
            System.exit(-1);
        }
        engine.run(args[0]);
    }
}
