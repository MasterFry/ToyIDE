package at.frysoft.toyide.compiler.statement;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.toy.Instruction;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class OPC {

    public final int opc;

    public final String name;

    private OPC(int opc, String name) {
        this.opc = opc;
        this.name = name;
    }

    public static final OPC HLT = new OPC(Instruction.HLT, Strings.INSTRUCTION_HLT);
    public static final OPC ADD = new OPC(Instruction.ADD, Strings.INSTRUCTION_ADD);
    public static final OPC SUB = new OPC(Instruction.SUB, Strings.INSTRUCTION_SUB);
    public static final OPC AND = new OPC(Instruction.AND, Strings.INSTRUCTION_AND);
    public static final OPC XOR = new OPC(Instruction.XOR, Strings.INSTRUCTION_XOR);
    public static final OPC SHL = new OPC(Instruction.SHL, Strings.INSTRUCTION_SHL);
    public static final OPC SHR = new OPC(Instruction.SHR, Strings.INSTRUCTION_SHR);
    public static final OPC LDA = new OPC(Instruction.LDA, Strings.INSTRUCTION_LDA);
    public static final OPC LD  = new OPC(Instruction.LD , Strings.INSTRUCTION_LD );
    public static final OPC ST  = new OPC(Instruction.ST , Strings.INSTRUCTION_ST );
    public static final OPC LDI = new OPC(Instruction.LDI, Strings.INSTRUCTION_LDI);
    public static final OPC STI = new OPC(Instruction.STI, Strings.INSTRUCTION_STI);
    public static final OPC BZ  = new OPC(Instruction.BZ , Strings.INSTRUCTION_BZ );
    public static final OPC BP  = new OPC(Instruction.BP , Strings.INSTRUCTION_BP );
    public static final OPC JR  = new OPC(Instruction.JR , Strings.INSTRUCTION_JR );
    public static final OPC JL  = new OPC(Instruction.JL , Strings.INSTRUCTION_JL );

    public static final OPC PUSH = new OPC(Instruction.PUSH, Strings.INSTRUCTION_PUSH);
    public static final OPC POP  = new OPC(Instruction.POP , Strings.INSTRUCTION_POP );
    public static final OPC CALL = new OPC(Instruction.CALL, Strings.INSTRUCTION_CALL);
    public static final OPC RET  = new OPC(Instruction.RET , Strings.INSTRUCTION_RET );

    public static final OPC ORG = new OPC(0, Strings.COMPILER_INSTRUCTION_ORG);
    public static final OPC DW  = new OPC(0, Strings.COMPILER_INSTRUCTION_DW );
    public static final OPC DUP = new OPC(0, Strings.COMPILER_INSTRUCTION_DUP);

}
