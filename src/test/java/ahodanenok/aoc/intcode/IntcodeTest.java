package ahodanenok.aoc.intcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @Test
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

    @Test
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
    public void jze_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 5, 0, 4, 99, 4, 0, 99});
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

    @Test
    public void jze_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 5, 1, 4, 99, 4, 0, 99});
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
    public void lt_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1107, 1, 2, 5, 99, 0 });
        pc.run();

        assertEquals(1107, pc.memread(0));
        assertEquals(1, pc.memread(1));
        assertEquals(2, pc.memread(2));
        assertEquals(5, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(1, pc.memread(5));
        assertEquals(0, pc.memread(6));
    }

    @Test
    public void lt_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1107, 2, 1, 5, 99, 1 });
        pc.run();

        assertEquals(1107, pc.memread(0));
        assertEquals(2, pc.memread(1));
        assertEquals(1, pc.memread(2));
        assertEquals(5, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(0, pc.memread(5));
        assertEquals(0, pc.memread(6));
    }

    @Test
    public void eq_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1108, 1, 1, 5, 99, 0 });
        pc.run();

        assertEquals(1108, pc.memread(0));
        assertEquals(1, pc.memread(1));
        assertEquals(1, pc.memread(2));
        assertEquals(5, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(1, pc.memread(5));
        assertEquals(0, pc.memread(6));
    }

    @Test
    public void eq_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1108, 2, 1, 5, 99, 1 });
        pc.run();

        assertEquals(1108, pc.memread(0));
        assertEquals(2, pc.memread(1));
        assertEquals(1, pc.memread(2));
        assertEquals(5, pc.memread(3));
        assertEquals(99, pc.memread(4));
        assertEquals(0, pc.memread(5));
        assertEquals(0, pc.memread(6));
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

    @Test
    public void testPrg_1() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50 });
        pc.run();

        assertEquals(3500, pc.memread(0));
        assertEquals(9, pc.memread(1));
        assertEquals(10, pc.memread(2));
        assertEquals(70, pc.memread(3));
        assertEquals(2, pc.memread(4));
        assertEquals(3, pc.memread(5));
        assertEquals(11, pc.memread(6));
        assertEquals(0, pc.memread(7));
        assertEquals(99, pc.memread(8));
        assertEquals(30, pc.memread(9));
        assertEquals(40, pc.memread(10));
        assertEquals(50, pc.memread(11));
        assertEquals(0, pc.memread(12));
    }

    @Test
    public void testPrg_2() {
        IntcodeComputer pc = new IntcodeComputer(new long[] { 1, 1, 1, 4, 99, 5, 6, 0, 99 });
        pc.run();

        assertEquals(30, pc.memread(0));
        assertEquals(1, pc.memread(1));
        assertEquals(1, pc.memread(2));
        assertEquals(4, pc.memread(3));
        assertEquals(2, pc.memread(4));
        assertEquals(5, pc.memread(5));
        assertEquals(6, pc.memread(6));
        assertEquals(0, pc.memread(7));
        assertEquals(99, pc.memread(8));
        assertEquals(0, pc.memread(9));
    }

    @Test
    public void testPrg_3() {
        // take an input, then output 0 if the input was zero or 1 if the input was non-zero
        IntcodeComputer pc = new IntcodeComputer(new long[] { 3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9 });
        pc.setIn(new WIn(10));
        pc.setOut(new Out() {
            private boolean written = false;
            @Override
            public void write(long n) {
                assertEquals(1, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
        pc.run();
    }

    @Test
    public void testPrg_4() {
        // take an input, then output 0 if the input was zero or 1 if the input was non-zero
        IntcodeComputer pc = new IntcodeComputer(new long[] { 3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9 });
        pc.setIn(new WIn(0));
        pc.setOut(new Out() {
            private boolean written = false;
            @Override
            public void write(long n) {
                assertEquals(0, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
        pc.run();
    }

    @Test
    public void testPrg_5() {
        // take an input, then output 0 if the input was zero or 1 if the input was non-zero
        IntcodeComputer pc = new IntcodeComputer(new long[] { 3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1 });
        pc.setIn(new WIn(10));
        pc.setOut(new Out() {
            private boolean written = false;
            @Override
            public void write(long n) {
                assertEquals(1, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
        pc.run();
    }

    @Test
    public void testPrg_6() {
        // take an input, then output 0 if the input was zero or 1 if the input was non-zero
        IntcodeComputer pc = new IntcodeComputer(new long[] { 3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1 });
        pc.setIn(new WIn(0));
        pc.setOut(new Out() {
            private boolean written = false;
            @Override
            public void write(long n) {
                assertEquals(0, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
        pc.run();
    }

    @Test
    public void testPrg_7() {
        // The program will then output 999 if the input value is below 8,
        // output 1000 if the input value is equal to 8,
        // or output 1001 if the input value is greater than 8.
        IntcodeComputer pc = new IntcodeComputer(new long[] {
                3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99 });
        pc.setIn(new WIn(7));
        pc.setOut(new Out() {
            private boolean written = false;
            @Override
            public void write(long n) {
                assertEquals(999, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
        pc.run();
    }

    @Test
    public void testPrg_8() {
        // The program will then output 999 if the input value is below 8,
        // output 1000 if the input value is equal to 8,
        // or output 1001 if the input value is greater than 8.
        IntcodeComputer pc = new IntcodeComputer(new long[] {
                3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99 });
        pc.setIn(new WIn(8));
        pc.setOut(new Out() {
            private boolean written = false;
            @Override
            public void write(long n) {
                assertEquals(1000, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
        pc.run();
    }

    @Test
    public void testPrg_9() {
        // The program will then output 999 if the input value is below 8,
        // output 1000 if the input value is equal to 8,
        // or output 1001 if the input value is greater than 8.
        IntcodeComputer pc = new IntcodeComputer(new long[] {
                3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99 });
        pc.setIn(new WIn(9));
        pc.setOut(new Out() {
            private boolean written = false;
            @Override
            public void write(long n) {
                assertEquals(1001, n);
                written = true;
            }

            @Override
            public void close() {
                assertTrue(written);
            }
        });
        pc.run();
    }

    @Test
    public void testPrg_10() {
        // takes no input and produces a copy of itself as output.
        IntcodeComputer pc = new IntcodeComputer(new long[] {
                109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99});
        pc.setOut(new Out() {
            List<Long> result = new ArrayList<>();
            @Override
            public void write(long n) {
                result.add(n);
            }

            @Override
            public void close() {
                long[] resultArray = new long[result.size()];
                for (int i = 0; i < result.size(); i++) resultArray[i] = result.get(i);

                assertArrayEquals(new long[] {
                        109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99}, resultArray);
            }
        });
        pc.run();
    }
}
