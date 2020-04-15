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
    public void mul_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 2, 2, 4, 0, 99 });
        pc.run();

        assertEquals(4 * 99, pc.memread(0));
        assertEquals(2, pc.memread(1));
        assertEquals(4, pc.memread(2));
        assertEquals(0, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(0, pc.memread(5));
    }

    @Test
    public void mul_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 2, 1, 3, 5, 99 });
        pc.run();

        assertEquals(2, pc.memread(0));
        assertEquals(1, pc.memread(1));
        assertEquals(3, pc.memread(2));
        assertEquals(5, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(5, pc.memread(5));
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
            boolean written = false;

            @Override
            public void write(long n) {
                assertEquals(8, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });

        pc.run();

        assertEquals(4, pc.memread(0));
        assertEquals(3, pc.memread(1));
        assertEquals(99, pc.memread(2));
        assertEquals(8, pc.memread(3));
        assertEquals(0, pc.memread(4));
    }

    @Test
    public void out_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 104, 1125899906842624L, 99 });
        pc.setOut(new Out() {
            boolean written = false;

            @Override
            public void write(long n) {
                assertEquals(1125899906842624L, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });

        pc.run();

        assertEquals(104, pc.memread(0));
        assertEquals(1125899906842624L, pc.memread(1));
        assertEquals(99, pc.memread(2));
        assertEquals(0, pc.memread(3));
    }

    public void jnz_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 5, 1, 4, 99, 4, 0, 99});
        pc.setOut(new Out() {
            boolean written = false;

            @Override
            public void write(long n) {
                assertEquals(5, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
    }

    public void jnz_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 5, 0, 4, 99, 4, 0, 99});
        pc.setOut(new Out() {
            boolean written = false;

            @Override
            public void write(long n) {
                written = true;
            }

            @Override
            public void close() {
                assertFalse(written);
            }
        });
    }

    @Test
    public void modeIm_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1002, 4, 3, 4, 33 });
        pc.run();

        assertEquals(1002, pc.memread(0));
        assertEquals(4, pc.memread(1));
        assertEquals(3, pc.memread(2));
        assertEquals(4, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(0, pc.memread(5));
    }

    @Test
    public void modeIm_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1101, 100, -1, 4, 0 });
        pc.run();

        assertEquals(1101, pc.memread(0));
        assertEquals(100, pc.memread(1));
        assertEquals(-1, pc.memread(2));
        assertEquals(4, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(0, pc.memread(5));
    }

    @Test
    public void modeRel_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 109, 1, 109, 2, 204, 3, 100, 99 });
        pc.setOut(new Out() {
            boolean written = false;

            @Override
            public void write(long n) {
                assertEquals(100, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
    }
}
