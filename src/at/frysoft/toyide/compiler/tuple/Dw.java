package at.frysoft.toyide.compiler.tuple;

import at.frysoft.toyide.Strings;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Dw extends CompilerInstruction {

    public Dw(int param) {
        super(param);
    }

    @Override
    public String getName() {
        return Strings.COMPILER_INSTRUCTION_DW;
    }

}
