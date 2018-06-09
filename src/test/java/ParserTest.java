package test.java;

import main.java.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static main.java.Instruction.*;
import main.java.Instruction;

public class ParserTest {

    @Test
    public void testValidate(){
        Instruction[] expected = {RSHIFT, RSHIFT, INCR, LOOP_START, DECR, LOOP_END};
        String program = ">>+[-]";
        Instruction[] actual = Parser.parse(program);

        for(int i = 0; i < expected.length;i++){
            assertEquals(actual[i], expected[i]);
        }
        Instruction[] expected1 = {RSHIFT, RSHIFT, INCR, LOOP_START, DECR, LOOP_END, NOP};
        program = ">>+[-]a";
        actual = Parser.parse(program);
        for(int i = 0; i < expected.length;i++){
            assertEquals(actual[i], expected1[i]);
        }
    }
}
