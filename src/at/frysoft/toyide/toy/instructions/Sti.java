package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Sti extends Instruction {

    public Sti(int instruction) {
        super(instruction);
    }

    public Sti(int Rd, int imm) {
        this((STI << 12) | (Rd << 8) | imm);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_STI;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        memory.write(register.read(getRt()), getRd());
        pc.increment();
    }

}
