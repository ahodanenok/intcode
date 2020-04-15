package ahodanenok.aoc.intcode;

public class IntcodeComputer {

    private ExecutionContext context;
    private In in;
    private Out out;

    public IntcodeComputer(long[] program) {
        Memory memory = new Memory(program);
        context = new ExecutionContext(memory);
    }

    public void setIn(In in) {
        this.in = in;
    }

    public void setOut(Out out) {
        this.out = out;
    }

    public void memset(int address, long value) {
        context.memory.write(address, value);
    }

    public long memread(int address) {
        return context.memory.read(address);
    }

    public void run() {

    }
}
