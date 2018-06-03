package at.frysoft.toyide.toy;

import at.frysoft.toyide.Log;

import java.io.*;
import java.util.Vector;

public abstract class CPU {

    protected PC pc;
    protected Memory memory;
    protected Memory register;

    private Instruction currentInstruction;

    private int executedInstructions;

    private boolean halted;

    private Vector<CpuListener> cpuListeners;

    public CPU(Memory register, Memory memory) {
        if(register == null) {
            this.register = new Memory(0x10);
            this.register.lockAddress(0x0);
        }else
            this.register = register;

        if(register == null) {
            this.register = new Memory(0x100);
            this.register.lockAddress(0xFF);
        }else
            this.register = memory;

        pc = new PC(0x10);

        currentInstruction = new Instruction();
        cpuListeners = new Vector<>();

        reset();
    }

    protected abstract void execute(Instruction instr);

    public Memory getMemory() {
        return memory;
    }

    public Memory getRegister() {
        return register;
    }

    public void reset() {
        memory.reset();
        register.reset();
        pc.reset();
        executedInstructions = 0;
        halted = false;
    }

    public void load(String fileName) {
        load(new File(fileName));
    }

    public boolean load(File file) {
        return memory.load(file);
    }

    public void start() {
        loadInstruction();
    }

    public void step() {
        if(halted)
            return;

        execute(currentInstruction);
        ++executedInstructions;

        if(cpuListeners.size() != 0) {
            CpuEvent e = new CpuEvent(pc.get(), currentInstruction, this);
            for(CpuListener l : cpuListeners)
                l.onInstructionExecuted(e);
        }

        if(currentInstruction.isHalt())
            halted = true;
        else
            loadInstruction();
    }

    private void loadInstruction() {
        currentInstruction.set(memory.read(pc.get()));

        if(cpuListeners.size() != 0) {
            CpuEvent e = new CpuEvent(pc.get(), currentInstruction, this);
            for(CpuListener l : cpuListeners)
                l.onInstructionChanged(e);
        }

        pc.increment();
    }

    public void run() {
        start();
        while(!halted)
            step();
    }

    public boolean isHalted() {
        return halted;
    }

    public int getExecutedInstructions() {
        return executedInstructions;
    }

    private void printMemoryLine(Memory mem, int start, int end) {
        Log.out.print(String.format("%02x", start) + ": ");
        for(int i = start; i < end; ++i)
            Log.out.print(String.format("%04x", mem.read(i)) + " ");
        Log.out.println();
    }

    public void print(int memCount) {
        Log.out.println();
        Log.out.println("***********************************************************************************");
        Log.out.print("PC: " + String.format("%02x", pc.getPrev()) + " => ");
        Log.out.println("Instruction: " + currentInstruction.getName() + " = " +
                        String.format("%04x", currentInstruction.get()));

        Log.out.println("-----------------------------------------------------------------------------------");
        Log.out.println("Register:");
        printMemoryLine(register, 0, 16);

        Log.out.println("-----------------------------------------------------------------------------------");
        Log.out.println("Memory:");
        for(int i = 0; i < memCount; i += 16)
            printMemoryLine(memory, i, i + 16);
        Log.out.println("***********************************************************************************");
    }

    public void print() {
        print(0xFF);
    }

    public void addCpuListener(CpuListener cpuListener) {
        cpuListeners.add(cpuListener);
    }

    public void removeCpuListener(CpuListener cpuListener) {
        cpuListeners.remove(cpuListener);
    }

}
