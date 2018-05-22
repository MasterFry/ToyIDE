package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Ldi extends Instruction {

    public Ldi(int instruction) {
        super(instruction);
    }

    public Ldi(int Rd, int imm) {
        this((LDI << 12) | (Rd << 8) | imm);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_LDI;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        register.write(getRd(), memory.read(register.read(getRt())));
        pc.increment();
    }

}
