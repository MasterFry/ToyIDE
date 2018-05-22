package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Jr extends Instruction {

    public Jr(int instruction) {
        super(instruction);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_JR;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        pc.set(register.read(getRd()));
    }

}
