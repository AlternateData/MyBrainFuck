package test.java;

import main.java.Interpreter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.List;
import javafx.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class InterpreterTest {

    Interpreter testbox =  new Interpreter();





    String[] programSet_JumpLength = {"012[4567]9",
                                             "012[4[6]8]",
                                             "[1234]6[8]",
                                             "[]"        ,
                                             "[[[]]]1234",};



    public final static int MAX_TEST_RUNS =  10;



    byte[] desiredResults_JumpLength = {4,6, 5, 1,3};
    int[]  starts = {3,3,0,0,1};

    Map<String, List<Map.Entry>>  testCases;

    @BeforeAll
    public void initTestCases() {

    }


    @RepeatedTest(value=MAX_TEST_RUNS, name="Case {currentRepetition} out of {totalRepetitions}")
    @DisplayName("jumpLength")
    public void testJumpLength(RepetitionInfo repinfo){
        int i, start, len0, len1;
        char[] program;
        if((i = repinfo.getCurrentRepetition()) < programSet_JumpLength.length){
            program = programSet_JumpLength[i].toCharArray();
            start = starts[i];
            len0 = testbox.jumpLength(program, start);
            assertEquals(len0, desiredResults_JumpLength[i], "Failed to compute the length of a jump towards ]");
            len1 = testbox.jumpLength(program, start + len0);
            assertEquals(len0, -len1,"Failed to compute the length of a jump towards [");
        }
    }

}
