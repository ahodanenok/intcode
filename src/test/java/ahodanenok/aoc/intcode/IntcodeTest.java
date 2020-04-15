package ahodanenok.aoc.intcode;

import org.junit.Test;
import static org.junit.Assert.*;

public class IntcodeTest {

    @Test
    public void sum_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] {1, 2, 3, 2, 99});
        pc.run();

        assertEquals(1, pc.memread(0));
        assertEquals(2, pc.memread(1));
        assertEquals(5, pc.memread(2));
        assertEquals(2, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(0, pc.memread(5));
    }

    @Test
    public void sum_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] {1, 7, 3, 5, 99});
        pc.run();

        assertEquals(1, pc.memread(0));
        assertEquals(7, pc.memread(1));
        assertEquals(3, pc.memread(2));
        assertEquals(5, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(10, pc.memread(5));
        assertEquals(0, pc.memread(6));
    }
}
