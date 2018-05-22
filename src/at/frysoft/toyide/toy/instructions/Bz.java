package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Bz extends Instruction {

    public Bz(int instruction) {
        super(instruction);
    }

    public Bz(int Rd, int imm) {
        this((BZ << 12) | (Rd << 8) | imm);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_BZ;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        if(register.read(getRd()) == 0)
            pc.set(getImm());
    }

}
