package ahodanenok.aoc.intcode;

public class IntcodeComputer {

    private static final long OPCODE_SUM = 1;
    private static final long OPCODE_MUL = 2;

    private ExecutionContext context;
    private In in;
    private Out out;

    public IntcodeComputer(long[] program) {
        Memory memory = new Memory(program);
        context = new ExecutionContext(memory);
        in = In.NONE;
        out = Out.NONE;
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
        while (memread(context.pc) != 99) {
            long opcode = memread(context.pc);
            if (opcode == OPCODE_SUM) {
                long a = memread(context.pc + 1);
                long b = memread(context.pc + 2);
                memset((int) memread(context.pc + 3), a + b);
                context.pc += 4;
            } else if (opcode == OPCODE_MUL) {
                long a = memread(context.pc + 1);
                long b = memread(context.pc + 2);
                memset((int) memread(context.pc + 3), a * b);
                context.pc += 4;
            } else {
                throw new IllegalStateException(String.format("Unknown opcode %d at position %d", opcode, context.pc));
            }
        }
    }
}
