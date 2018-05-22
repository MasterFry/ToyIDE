package at.frysoft.toyide.toy.instructions;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Hlt extends Instruction {

    public Hlt(int instruction) {
        super(instruction);
    }

    public Hlt() {
        this(HLT);
    }

    @Override
    public boolean isHalt() {
        return true;
    }

    @Override
    public String getName() {
        return Strings.INSTRUCTION_HLT;
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
    }

}
