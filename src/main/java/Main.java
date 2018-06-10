package main.java;

import java.io.File;

public class Main{

    final static String USAGE = "mbr [program] \n"+
                                "\tInterprets brainfuck programs\n" +
                                "\t\t-Invalid Characters are ignored\n" +
                                "\t\t-Error on unbalanced brackets\n" +
                                "\t\t-4kb memory that wraps around both ways\n";

    final static String INPUT_FLAG = "-i";

    public static void main(String[] args) {
        Engine engine = new Engine();
        if(args.length == 0){
            System.out.println(USAGE);
            System.exit(-1);
        }

        if(args.length == 2 && args[0].equals(INPUT_FLAG)) {
            engine.run(args[1]);
            System.exit(0);
        }


        if(args.length == 1){
           File sourceFile = new File(args[0]);
           if(!sourceFile.exists()) {
               sourceFile = new File(System.getProperty("user.dir") + args[0]);
           }
           engine.run(sourceFile);
        }

    }
}
