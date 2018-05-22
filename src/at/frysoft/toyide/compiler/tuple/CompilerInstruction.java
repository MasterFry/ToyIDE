package at.frysoft.toyide.compiler.tuple;

import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.Memory;
import at.frysoft.toyide.toy.PC;

/**
 * Created by Stefan on 20.05.2018.
 */
public abstract class CompilerInstruction extends Instruction {

    public static final int ORG = 0x10;
    public static final int DW  = 0x11;
    public static final int DUP = 0x12;

    protected CompilerInstruction(int instruction) {
        super(instruction);
    }

    @Override
    public void execute(PC pc, Memory register, Memory memory) {
    }

}
