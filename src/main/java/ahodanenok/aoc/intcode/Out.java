package ahodanenok.aoc.intcode;

public interface Out {

    Out NONE = new Out() {
        @Override
        public void write(long n) {
            throw new IllegalStateException("no out");
        }
    };

    void write(long n);

    default void close() { };
}
