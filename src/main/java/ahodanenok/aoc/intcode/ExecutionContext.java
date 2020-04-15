package ahodanenok.aoc.intcode;

public final class ExecutionContext {

    Memory memory;
    int pc;
    int relativeBase;

    public ExecutionContext(Memory memory) {
        this.memory = memory;
    }
}
