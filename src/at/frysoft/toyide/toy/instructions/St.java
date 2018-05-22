package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class St extends Instruction {

    public St(int instruction) {
        super(instruction);
    }

    public St(int Rd, int imm) {
        this((ST << 12) | (Rd << 8) | imm);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_ST;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        memory.write(getImm(), register.read(getRd()));
        pc.increment();
    }

}
