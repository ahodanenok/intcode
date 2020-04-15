package ahodanenok.aoc.intcode;

import java.util.Arrays;

public class WIn implements In {

    private static final int MAX_DATA_COUNT = 10000;

    private long[] data;
    private int readIdx;
    private int dataCount;

    public WIn() {
        this(new long[0]);
    }

    public WIn(long[] initialData) {
        this.data = new long[initialData.length];
        System.arraycopy(initialData, 0, data, 0, initialData.length);
        readIdx = 0;
        dataCount = initialData.length;
    }

    @Override
    public long read() {
        if (readIdx == dataCount) {
            throw new IllegalStateException("no input");
        }

        long n = data[readIdx++];

        // input exhausted, drop read items
        if (readIdx == dataCount) {
            data = Arrays.copyOfRange(data, readIdx, data.length);
            dataCount = 0;
            readIdx = 0;
        }

        return n;
    }

    public void add(long n) {
        if (dataCount == MAX_DATA_COUNT) {
            throw new IllegalStateException("WIn can contain maximum " + MAX_DATA_COUNT + " items");
        }

        if (dataCount == data.length) {
            data = Arrays.copyOf(data, Math.max(data.length, 1) * 2);
        }

        data[dataCount++] = n;
    }
}
