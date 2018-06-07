package at.frysoft.toyide.computer.cpu;

import at.frysoft.toyide.computer.Bus;
import at.frysoft.toyide.computer.memory.Memory;

public class Toy extends CPU {

    public Toy(Memory register, Bus bus, int pcStart) {
        super(register, bus, pcStart);
    }

    @Override
    protected void execute(Instruction instr) {
        switch(instr.getOPC()) {

            case Instruction.HLT:
                return;

            case Instruction.ADD:
                register.write(instr.getRd(),register.read(instr.getRs()) + register.read(instr.getRt()));
                return;

            case Instruction.SUB:
                register.write(instr.getRd(),register.read(instr.getRs()) - register.read(instr.getRt()));
                return;

            case Instruction.AND:
                register.write(instr.getRd(),register.read(instr.getRs()) & register.read(instr.getRt()));
                return;

            case Instruction.XOR:
                register.write(instr.getRd(),register.read(instr.getRs()) ^ register.read(instr.getRt()));
                return;

            case Instruction.SHL:
                register.write(instr.getRd(),register.read(instr.getRs()) << register.read(instr.getRt()));
                return;

            case Instruction.SHR:
                register.write(instr.getRd(),register.read(instr.getRs()) >> register.read(instr.getRt()));
                return;

            case Instruction.LDA:
                register.write(instr.getRd(), instr.getImm());
                return;

            case Instruction.LD:
                register.write(instr.getRd(), bus.read(instr.getImm()));
                return;

            case Instruction.ST:
                bus.write(instr.getImm(), register.read(instr.getRd()));
                return;

            case Instruction.LDI:
                register.write(instr.getRd(), bus.read(instr.getRs() + register.read(instr.getRt())));
                return;

            case Instruction.STI:
                bus.write(instr.getRs() + register.read(instr.getRt()), register.read(instr.getRd()));
                return;

            case Instruction.BZ:
                if(register.read(instr.getRd()) == 0)
                    pc.set(instr.getImm());
                return;

            case Instruction.BP:
                if(register.read(instr.getRd()) > 0)
                    pc.set(instr.getImm());
                return;

            case Instruction.JR:
                pc.set(register.read(instr.getRd()));
                return;

            case Instruction.JL:
                register.write(instr.getRd(), pc.get());
                pc.set(instr.getImm());
                return;

            default:
                throw new IllegalArgumentException("Invalid Instruction for Toy");

        }
    }

}
