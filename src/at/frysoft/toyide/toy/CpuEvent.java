package at.frysoft.toyide.toy;

/**
 * Created on : 31.05.2018
 * Last update: 31.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class CpuEvent {

    private final int pc;

    private final Instruction instruction;

    private final CPU cpu;

    public CpuEvent(int pc, Instruction instruction, CPU cpu) {
        this.pc = pc;
        this.instruction = instruction.copy();
        this.cpu = cpu;
    }

    public int getPc() {
        return pc;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public CPU getCpu() {
        return cpu;
    }

}
