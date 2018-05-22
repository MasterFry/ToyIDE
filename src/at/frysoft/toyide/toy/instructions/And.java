package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class And extends Instruction {

    public And(int instruction) {
        super(instruction);
    }

    public And(int Rd, int Rs, int Rt) {
        this((AND << 12) | (Rd << 8) | (Rs << 4) | Rt);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_AND;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        register.write(getRd(),register.read(getRs()) & register.read(getRt()));
        pc.increment();
    }

}
