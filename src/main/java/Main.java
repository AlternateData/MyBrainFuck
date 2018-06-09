package main.java;

import java.nio.FloatBuffer;

public class Main{

    final static String USAGE = "mbr [program] \n"+
                                "\tInterprets brainfuck programs" +
                                "\t\t-Invalid Characters are ignored" +
                                "\t\t-Error on unbalanced brackets" +
                                "\t\t-4kb memory that wraps around both ways";
    final static String FIBONACCI  = "+++++++++++\n" +
                ">+>>>>++++++++++++++++++++++++++++++++++++++++++++\n" +
                ">++++++++++++++++++++++++++++++++<<<<<<[>[>>>>>>+>\n" +
                "+<<<<<<<-]>>>>>>>[<<<<<<<+>>>>>>>-]<[>++++++++++[-\n" +
                "<-[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]>[<<[>>>+<<<\n" +
                "-]>>[-]]<<]>>>[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]\n" +
                ">[<<+>>[-]]<<<<<<<]>>>>>[+++++++++++++++++++++++++\n" +
                "+++++++++++++++++++++++.[-]]++++++++++<[->-<]>++++\n" +
                "++++++++++++++++++++++++++++++++++++++++++++.[-]<<\n" +
                "<<<<<<<<<<[>>>+>+<<<<-]>>>>[<<<<+>>>>-]<-[>>.>.<<<\n" +
                "[-]]<<[>>+>+<<<-]>>>[<<<+>>>-]<<[<+>-]>[<+>-]<<<-]";

    public static void main(String[] args) {
        Engine engine = new Engine();
        System.out.println("Executing:");
        System.out.println(FIBONACCI);
        System.out.println();
        engine.run(FIBONACCI);
        System.exit(0);
        if(args.length == 0){
            System.out.println(String.format(USAGE));
            System.exit(-1);
        }
        try{
            engine.run(args[1]);
        }catch(IllegalArgumentException e){
            System.err.println("Program contains syntax errors");
            System.exit(-1);
        }
        System.exit(0);
        /*
        engine.run("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.\n+" +
                ">.+++.------.--------.>+.>.");
        engine.run("+++++++++++\n" +
                ">+>>>>++++++++++++++++++++++++++++++++++++++++++++\n" +
                ">++++++++++++++++++++++++++++++++<<<<<<[>[>>>>>>+>\n" +
                "+<<<<<<<-]>>>>>>>[<<<<<<<+>>>>>>>-]<[>++++++++++[-\n" +
                "<-[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]>[<<[>>>+<<<\n" +
                "-]>>[-]]<<]>>>[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]\n" +
                ">[<<+>>[-]]<<<<<<<]>>>>>[+++++++++++++++++++++++++\n" +
                "+++++++++++++++++++++++.[-]]++++++++++<[->-<]>++++\n" +
                "++++++++++++++++++++++++++++++++++++++++++++.[-]<<\n" +
                "<<<<<<<<<<[>>>+>+<<<<-]>>>>[<<<<+>>>>-]<-[>>.>.<<<\n" +
                "[-]]<<[>>+>+<<<-]>>>[<<<+>>>-]<<[<+>-]>[<+>-]<<<-]");
        */
    }
}
