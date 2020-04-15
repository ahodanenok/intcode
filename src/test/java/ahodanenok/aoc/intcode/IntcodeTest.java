package ahodanenok.aoc.intcode;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class IntcodeTest {

    @Test
    public void sum_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1, 2, 4, 0, 99 });
        pc.run();

        assertEquals(103, pc.memread(0));
        assertEquals(2, pc.memread(1));
        assertEquals(4, pc.memread(2));
        assertEquals(0, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(0, pc.memread(5));
    }

    @Test
    public void sum_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1, 1, 3, 5, 99 });
        pc.run();

        assertEquals(1, pc.memread(0));
        assertEquals(1, pc.memread(1));
        assertEquals(3, pc.memread(2));
        assertEquals(5, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(6, pc.memread(5));
        assertEquals(0, pc.memread(6));
    }

    @Test
    public void in_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 3, 5, 99 });
        pc.setIn(new In() {
            @Override
            public long read() {
                return 10;
            }

            @Override
            public void close() { }
        });

        pc.run();

        assertEquals(3, pc.memread(0));
        assertEquals(5, pc.memread(1));
        assertEquals(99, pc.memread(2));
        assertEquals(0, pc.memread(3));
        assertEquals(0, pc.memread(4));
        assertEquals(10, pc.memread(5));
        assertEquals(0, pc.memread(6));
    }

    @Test
    public void in_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 3, 5, 3, 0, 99 });
        pc.setIn(new In() {

            LinkedList<Long> input = new LinkedList<>();
            {
                input.add(10L);
                input.add(20L);
            }

            @Override
            public long read() {
                return input.removeFirst();
            }

            @Override
            public void close() { }
        });

        pc.run();

        assertEquals(20, pc.memread(0));
        assertEquals(5, pc.memread(1));
        assertEquals(3, pc.memread(2));
        assertEquals(0, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(10, pc.memread(5));
        assertEquals(0, pc.memread(6));
    }

    @Test
    public void out_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 4, 3, 99, 8 });
        pc.setOut(new Out() {
            @Override
            public void write(long n) {
                assertEquals(8, n);
            }

            @Override
            public void close() { }
        });

        pc.run();

        assertEquals(4, pc.memread(0));
        assertEquals(3, pc.memread(1));
        assertEquals(99, pc.memread(2));
        assertEquals(8, pc.memread(3));
        assertEquals(0, pc.memread(4));
    }
}
