package at.frysoft.toyide.compiler.statement;

import at.frysoft.toyide.computer.cpu.Instruction;
import at.frysoft.toyide.ressources.R;

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

    public static final OPC HLT = new OPC(Instruction.HLT, R.strings.toy.instruction.HLT);
    public static final OPC ADD = new OPC(Instruction.ADD, R.strings.toy.instruction.ADD);
    public static final OPC SUB = new OPC(Instruction.SUB, R.strings.toy.instruction.SUB);
    public static final OPC AND = new OPC(Instruction.AND, R.strings.toy.instruction.AND);
    public static final OPC XOR = new OPC(Instruction.XOR, R.strings.toy.instruction.XOR);
    public static final OPC SHL = new OPC(Instruction.SHL, R.strings.toy.instruction.SHL);
    public static final OPC SHR = new OPC(Instruction.SHR, R.strings.toy.instruction.SHR);
    public static final OPC LDA = new OPC(Instruction.LDA, R.strings.toy.instruction.LDA);
    public static final OPC LD  = new OPC(Instruction.LD , R.strings.toy.instruction.LD );
    public static final OPC ST  = new OPC(Instruction.ST , R.strings.toy.instruction.ST );
    public static final OPC LDI = new OPC(Instruction.LDI, R.strings.toy.instruction.LDI);
    public static final OPC STI = new OPC(Instruction.STI, R.strings.toy.instruction.STI);
    public static final OPC BZ  = new OPC(Instruction.BZ , R.strings.toy.instruction.BZ );
    public static final OPC BP  = new OPC(Instruction.BP , R.strings.toy.instruction.BP );
    public static final OPC JR  = new OPC(Instruction.JR , R.strings.toy.instruction.JR );
    public static final OPC JL  = new OPC(Instruction.JL , R.strings.toy.instruction.JL );

    public static final OPC PUSH = new OPC(Instruction.PUSH, R.strings.toy.instruction.PUSH);
    public static final OPC POP  = new OPC(Instruction.POP , R.strings.toy.instruction.POP );
    public static final OPC CALL = new OPC(Instruction.CALL, R.strings.toy.instruction.CALL);
    public static final OPC RET  = new OPC(Instruction.RET , R.strings.toy.instruction.RET );

    public static final OPC ORG = new OPC(0, R.strings.compiler.instruction.ORG);
    public static final OPC DW  = new OPC(0, R.strings.compiler.instruction.DW );
    public static final OPC DUP = new OPC(0, R.strings.compiler.instruction.DUP);

}
