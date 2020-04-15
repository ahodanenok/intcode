package ahodanenok.aoc.intcode;

import java.util.Arrays;

class Memory {

    private static final int MAX_ADDRESS = 10000;

    private long[] data;

    Memory(long[] initialData) {
        this.data = new long[initialData.length];
        System.arraycopy(initialData, 0, this.data, 0, initialData.length);
    }

    void write(int address, long value) {
        expandIfNeeded(address);
        data[address] = value;
    }

    long read(int address) {
        expandIfNeeded(address);
        return data[address];
    }

    private void expandIfNeeded(int address) {
        if (address >= MAX_ADDRESS || address < 0) {
            throw new IllegalStateException("Invalid address: " + address + ", must be between [0, " + MAX_ADDRESS + "]");
        }

        if (address >= data.length) {
            data = Arrays.copyOf(data, address + 1);
        }
    }
}
