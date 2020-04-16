package ahodanenok.aoc.intcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IntcodeComputer {

    public static long[] load(String fileName) throws IOException  {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            String[] parts = line.split(",");

            long[] program = new long[parts.length];
            for (int i = 0; i < parts.length; i++) {
                program[i] = Long.parseLong(parts[i].trim());
            }

            return program;
        }
    }

    private static final long OPCODE_SUM = 1;
    private static final long OPCODE_MUL = 2;
    private static final long OPCODE_IN = 3;
    private static final long OPCODE_OUT = 4;
    private static final long OPCODE_JNZ = 5;
    private static final long OPCODE_JZE = 6;
    private static final long OPCODE_LT = 7;
    private static final long OPCODE_EQ = 8;
    private static final long OPCODE_REL = 9;

    private static final long MODE_POSITION = 0;
    private static final long MODE_IMMEDIATE = 1;
    private static final long MODE_RELATIVE = 2;

    private Memory memory;
    private int pc;
    private int relativeBase;

    private In in;
    private Out out;

    private boolean stopOnOut;

    public IntcodeComputer(long[] program) {
        memory = new Memory(program);
        in = In.NONE;
        out = Out.NONE;
    }

    public void setIn(In in) {
        this.in = in;
    }

    public In getIn() {
        return in;
    }

    public void setOut(Out out) {
        this.out = out;
    }

    public Out getOut() {
        return out;
    }

    public void setStopOnOut(boolean stopOnOut) {
        this.stopOnOut = stopOnOut;
    }

    public void memset(int address, long value) {
        memory.write(address, value);
    }

    public long memread(int address) {
        return memory.read(address);
    }

    public void run() {
        while (memread(pc) != 99) {
            long instruction = memread(pc);
            long opcode = instruction % 100;
            if (opcode == OPCODE_SUM) {
                long a = _memread(instruction, 1);
                long b = _memread(instruction, 2);
                _memset(instruction, 3, a + b);
                pc += 4;
            } else if (opcode == OPCODE_MUL) {
                long a = _memread(instruction, 1);
                long b = _memread(instruction, 2);
                _memset(instruction, 3, a * b);
                pc += 4;
            } else if (opcode == OPCODE_IN) {
                long n = in.read();
                _memset(instruction, 1, n);
                pc += 2;
            } else if (opcode == OPCODE_OUT) {
                long n = _memread(instruction, 1);
                out.write(n);
                pc += 2;
                if (stopOnOut) {
                    return;
                }
            } else if (opcode == OPCODE_REL) {
                long n = _memread(instruction, 1);
                relativeBase += n;
                pc += 2;
            } else if (opcode == OPCODE_JNZ) {
                long n = _memread(instruction, 1);
                if (n != 0) {
                    pc = (int) _memread(instruction, 2);
                } else {
                    pc += 3;
                }
            } else if (opcode == OPCODE_JZE) {
                long n = _memread(instruction, 1);
                if (n == 0) {
                    pc = (int) _memread(instruction, 2);
                } else {
                    pc += 3;
                }
            } else if (opcode == OPCODE_LT) {
                long a = _memread(instruction, 1);
                long b = _memread(instruction, 2);
                _memset(instruction, 3, a < b ? 1 : 0);
                pc += 4;
            } else if (opcode == OPCODE_EQ) {
                long a = _memread(instruction, 1);
                long b = _memread(instruction, 2);
                _memset(instruction, 3, a == b ? 1 : 0);
                pc += 4;
            } else {
                throw new IllegalStateException(String.format("Unknown opcode %d at position %d", opcode, pc));
            }
        }

        out.close();
        in.close();
    }

    private long _memread(long instruction, int offset) {
        assert offset > 0;
        return memread(_address(instruction, offset));
    }

    private void _memset(long instruction, int offset, long value) {
        assert offset > 0;
        memset(_address(instruction, offset), value);
    }

    private int _address(long instruction, int offset) {
        assert offset > 0;

        int n = 10; // parameters mode read from right-to-left after two-digits opcode
        for (int i = 0; i < offset; i++) n *= 10;

        long mode = (instruction / n) % 10;
        if (mode == MODE_POSITION) {
            return (int) memread(pc + offset);
        } else if (mode == MODE_IMMEDIATE) {
            return pc + offset;
        } else if (mode == MODE_RELATIVE) {
            return (int) memread(pc + offset) + relativeBase;
        } else {
            throw new IllegalStateException("Unknown parameter mode " + mode + " in opcode " + instruction);
        }
    }
}
