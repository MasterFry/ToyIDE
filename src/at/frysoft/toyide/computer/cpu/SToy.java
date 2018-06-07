package at.frysoft.toyide.computer.cpu;

import at.frysoft.toyide.computer.Bus;
import at.frysoft.toyide.computer.memory.Memory;

public class SToy extends Toy {

    public static final int SP = 0xF;
    public static final int BP = 0xE;

    public SToy(Memory register, Bus bus, int pcStart) {
        super(register, bus, pcStart);
    }

    @Override
    protected void execute(Instruction instr) {
        if(instr.getOPC() != Instruction.HLT) {
            super.execute(instr);
            return;
        }

        switch(instr.getRd()) {

            case Instruction.PUSH:
                register.write(SP, register.read(SP) - 1);
                bus.write(register.read(SP), register.read(instr.getRt()));
                return;

            case Instruction.POP:
                register.write(instr.getRt(), bus.read(register.read(SP)));
                register.write(SP, register.read(SP) + 1);
                return;

            case Instruction.CALL:
                register.write(SP, register.read(SP) - 1);
                bus.write(register.read(SP), pc.get());
                pc.set(instr.getImm());
                return;

            case Instruction.RET:
                pc.set(bus.read(register.read(SP)));
                register.write(SP, register.read(SP) + 1);
                return;

            default:
                super.execute(instr);

        }
    }

    @Override
    public void reset() {
        super.reset();

        // Base Pointer
        register.write(0xE, 0xFE);

        // Stack Pointer
        register.write(0xF, 0xFE);
    }

}
