package ahodanenok.aoc.intcode;

public interface In {

    In NONE = new In() {
        @Override
        public long read() {
            throw new IllegalStateException("no in");
        }

        @Override
        public void close() { }
    };

    long read();

    void close();
}
