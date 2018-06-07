package at.frysoft.toyide.computer.cpu;

import at.frysoft.toyide.computer.Bus;
import at.frysoft.toyide.computer.memory.Memory;

import java.util.Vector;

public abstract class CPU {

    protected PC pc;
    protected Memory register;
    protected Bus bus;

    private boolean halted;
    private Instruction currentInstruction;
    private int executedInstructions;

    private Vector<CpuListener> cpuListeners;

    public CPU(Memory register, Bus bus, int pcStart) {
        this.register = register;
        this.bus = bus;
        pc = new PC(pcStart);

        currentInstruction = new Instruction();
        cpuListeners = new Vector<>();

        reset();
    }

    protected abstract void execute(Instruction instr);

    public Memory getRegister() {
        return register;
    }

    public void reset() {
        register.reset();
        pc.reset();
        executedInstructions = 0;
        halted = false;
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
        currentInstruction.set(bus.read(pc.get()));

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

    public void stop() {
        halted = true;
    }

    public boolean isHalted() {
        return halted;
    }

    public int getExecutedInstructions() {
        return executedInstructions;
    }

    public void addCpuListener(CpuListener cpuListener) {
        cpuListeners.add(cpuListener);
    }

    public void removeCpuListener(CpuListener cpuListener) {
        cpuListeners.remove(cpuListener);
    }

}
