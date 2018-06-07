package test.java;

import main.java.Interpreter;
import org.junit.jupiter.api.*;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class InterpreterTest {

    Interpreter testbox =  new Interpreter();





    String[] programSet_JumpLength = {"012[4567]9",
                                             "012[4[6]8]",
                                             "[1234]6[8]",
                                             "[]"        ,
                                             "[[[]]]1234",};


    byte[] desiredResults_JumpLength = {5, 6, 5, 1, 3};
    int[]  starts = {3,3,0,0,1};

    public void executeTestCases(List<String> testCases, List<String> desiredResults){


    }



    @DisplayName("jumpLength")
    @Test
    public void testJumpLengthCase(){
        int start, len0, len1;
        char[] program;
        for(int i=0; i<programSet_JumpLength.length;i++){
            program = programSet_JumpLength[i].toCharArray();
            start = starts[i];
            len0 = testbox.jumpLength(program, start);
            System.out.println(Integer.toString(len0) + " " + Integer.toString(desiredResults_JumpLength[i]));
            assertEquals(len0, desiredResults_JumpLength[i], String.format("Failed to compute the length of a jump towards ] (%d)", i));
            len1 = testbox.jumpLength(program, start + len0);
            assertEquals(len0, -len1,"Failed to compute the length of a jump towards [");
        }
    }



}
