package main.java;

import java.io.File;

public class Main{

    private final static String USAGE = "java -jar BrainFuck.jar [source file] \n"+
                                        "java -jar BrainFuck.jar -i [programString]\n" +
                                        "\nInterprets/Evaluates brainfuck programs\n" +
                                        "\t-Invalid Characters are ignored\n" +
                                        "\t-Error on unbalanced square brackets\n" +
                                        "\t-4kb memory that wraps around both ways\n";

    private final static String INPUT_FLAG = "-i";

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
               // interpret the path as relative to the directory of the running program
               sourceFile = new File(System.getProperty("user.dir") + args[0]);
           }
           engine.run(sourceFile);
        }

    }
}
