package ahodanenok.aoc.intcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryTest {

    @Test
    public void memread_1() {
        Memory memory = new Memory(new long[] { 1, 2, 3});
        assertEquals(1, memory.read(0));
        assertEquals(2, memory.read(1));
        assertEquals(3, memory.read(2));
        assertEquals(0, memory.read(3));
        assertEquals(0, memory.read(4));
    }

    @Test(expected = IllegalStateException.class)
    public void memread_Err_1() {
        Memory memory = new Memory(new long[] { 2, 3});
        memory.read(-1);
    }

    @Test(expected = IllegalStateException.class)
    public void memread_Err_2() {
        Memory memory = new Memory(new long[] { 2, 3});
        memory.read(10000);
    }

    @Test
    public void memset_1() {
        Memory memory = new Memory(new long[] { 1, 2, 3});
        memory.write(1, 10);
        memory.write(3, 20);

        assertEquals(1, memory.read(0));
        assertEquals(10, memory.read(1));
        assertEquals(3, memory.read(2));
        assertEquals(20, memory.read(3));
        assertEquals(0, memory.read(4));
    }

    @Test(expected = IllegalStateException.class)
    public void memset_Err_1() {
        Memory memory = new Memory(new long[] { 2, 3});
        memory.write(-1, 10);
    }

    @Test(expected = IllegalStateException.class)
    public void memset_Err_2() {
        Memory memory = new Memory(new long[] { 2, 3});
        memory.write(10000, 10);
    }
}
