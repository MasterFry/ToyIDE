package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Sub extends Instruction {

    public Sub(int instruction) {
        super(instruction);
    }

    public Sub(int Rd, int Rs, int Rt) {
        this((SUB << 12) | (Rd << 8) | (Rs << 4) | Rt);
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_SUB;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
        register.write(getRd(),register.read(getRs()) - register.read(getRt()));
        pc.increment();
    }

}
