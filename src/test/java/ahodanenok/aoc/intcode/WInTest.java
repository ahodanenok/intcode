package ahodanenok.aoc.intcode;

import org.junit.Test;
import static org.junit.Assert.*;

public class WInTest {

    @Test
    public void testRead_1() {
        WIn in = new WIn(new long[] { 1 });
        assertEquals(1, in.read());
    }

    @Test
    public void testRead_2() {
        WIn in = new WIn(new long[] { 1, 2, 3 });
        assertEquals(1, in.read());
        assertEquals(2, in.read());
        assertEquals(3, in.read());
    }

    @Test
    public void testAdd_1() {
        WIn in = new WIn();
        in.add(2);

        assertEquals(2, in.read());
    }

    @Test
    public void testAdd_2() {
        WIn in = new WIn(new long[] { 1 });
        in.add(2);
        in.add(3);

        assertEquals(1, in.read());
        assertEquals(2, in.read());
        assertEquals(3, in.read());
    }

    @Test
    public void testAdd_3() {
        WIn in = new WIn();
        in.add(1);
        in.add(2);
        in.add(3);

        assertEquals(1, in.read());
        assertEquals(2, in.read());
        assertEquals(3, in.read());

        in.add(4);
        in.add(5);
        in.add(6);

        assertEquals(4, in.read());
        assertEquals(5, in.read());
        assertEquals(6, in.read());
    }

    @Test
    public void testAdd_4() {
        WIn in = new WIn(new long[9999]);
        in.add(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testRead_Err_1() {
        WIn in = new WIn();
        in.read();
    }

    @Test(expected = IllegalStateException.class)
    public void testRead_Err_2() {
        WIn in = new WIn(new long[] { 1, 2 });
        in.read();
        in.read();
        in.read();
    }

    @Test(expected = IllegalStateException.class)
    public void testRead_Err_3() {
        WIn in = new WIn(new long[10000]);
        in.add(1);
    }
}
