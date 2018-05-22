package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Ld extends Instruction {

    public Ld(int instruction) {
        super(instruction);
    }

    public Ld(int Rd, int imm) {
        this((LD << 12) | (Rd << 8) | imm);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_LD;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        register.write(getRd(), memory.read(getImm()));
        pc.increment();
    }

}
