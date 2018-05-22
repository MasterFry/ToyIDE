package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Bp extends Instruction {

    public Bp(int instruction) {
        super(instruction);
    }

    public Bp(int Rd, int imm) {
        this((BP << 12) | (Rd << 8) | imm);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_BP;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        int val = register.read(getRd());

        if(val != 0 && (val & 0x8000) == 0)
            pc.set(getImm());
    }

}
