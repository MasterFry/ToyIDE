package at.frysoft.toyide.toy;

import at.frysoft.toyide.ressources.R;

public class Instruction {

    public static final byte HLT = 0x0;
    public static final byte ADD = 0x1;
    public static final byte SUB = 0x2;
    public static final byte AND = 0x3;
    public static final byte XOR = 0x4;
    public static final byte SHL = 0x5;
    public static final byte SHR = 0x6;
    public static final byte LDA = 0x7;
    public static final byte LD  = 0x8;
    public static final byte ST  = 0x9;
    public static final byte LDI = 0xA;
    public static final byte STI = 0xB;
    public static final byte BZ  = 0xC;
    public static final byte BP  = 0xD;
    public static final byte JR  = 0xE;
    public static final byte JL  = 0xF;

    public static final byte PUSH = 0x1;
    public static final byte POP  = 0x2;
    public static final byte CALL = 0x3;
    public static final byte RET  = 0x4;

    private int instruction;

    public Instruction() {
        instruction = HLT;
    }

    public Instruction copy() {
        Instruction i = new Instruction();
        i.instruction = instruction;
        return i;
    }

    public void set(int instruction) {
        this.instruction = instruction;
    }

    public int get() {
        return instruction;
    }

    public int getOPC() {
        return ((instruction >> 12) & 0xF);
    }

    public int getRd() {
        return ((instruction >> 8) & 0xF);
    }

    public int getRs() {
        return ((instruction >> 4) & 0xF);
    }

    public int getRt() {
        return (instruction & 0xF);
    }

    public int getImm() {
        return (instruction & 0xFF);
    }

    public boolean isHalt() {
        return (instruction == 0);
    }

    public String getName() {
        switch(getOPC()) {
            case HLT:
                // Switch for S-Toy Instructions
                switch(getRd()) {
                    case PUSH: return R.strings.toy.instruction.PUSH;
                    case POP : return R.strings.toy.instruction.POP ;
                    case CALL: return R.strings.toy.instruction.CALL;
                    case RET : return R.strings.toy.instruction.RET ;
                }
                // Standard Toy Instructions
                return R.strings.toy.instruction.HLT;

            case ADD: return R.strings.toy.instruction.ADD;
            case SUB: return R.strings.toy.instruction.SUB;
            case AND: return R.strings.toy.instruction.AND;
            case XOR: return R.strings.toy.instruction.XOR;
            case SHL: return R.strings.toy.instruction.SHL;
            case SHR: return R.strings.toy.instruction.SHR;
            case LDA: return R.strings.toy.instruction.LDA;
            case LD : return R.strings.toy.instruction.LD ;
            case ST : return R.strings.toy.instruction.ST ;
            case LDI: return R.strings.toy.instruction.LDI;
            case STI: return R.strings.toy.instruction.STI;
            case BZ : return R.strings.toy.instruction.BZ ;
            case BP : return R.strings.toy.instruction.BP ;
            case JR : return R.strings.toy.instruction.JR ;
            case JL : return R.strings.toy.instruction.JL ;
            default:
                throw new IllegalArgumentException("Invalid Instruction");
        }
    }

    public String getDescription() {
        switch(getOPC()) {

            case Instruction.HLT:
                // Switch for S-Toy Instructions
                switch(getRd()) {
                    case PUSH: return "PUSH: ++SP and Rd => Mem[SP]";
                    case POP : return "POP: Rd <= Mem[SP] and ++SP";
                    case CALL: return "CALL: Push PC, BP = PC and imm => PC";
                    case RET : return "RET: Pop => PC";
                }
                // Standard Toy Instructions
                return "CPU is Halted!";

            case ADD: return String.format("ADD: R%1X <= R%1X + R%1X", getRd(), getRs(), getRt());
            case SUB: return String.format("SUB: R%1X <= R%1X - R%1X", getRd(), getRs(), getRt());
            case AND: return String.format("AND: R%1X <= R%1X & R%1X", getRd(), getRs(), getRt());
            case XOR: return String.format("XOR: R%1X <= R%1X ^ R%1X", getRd(), getRs(), getRt());
            case SHL: return String.format("SHL: R%1X <= R%1X << R%1X", getRd(), getRs(), getRt());
            case SHR: return String.format("SHR: R%1X <= R%1X >> R%1X", getRd(), getRs(), getRt());

            case LDA: return String.format("LDA: R%1X <= 0x%02X", getRd(), getImm());
            case LD : return String.format("LD: R%1X <= Mem[0x%02X]", getRd(), getImm());
            case ST : return String.format("ST: R%1X => Mem[0x%02X]", getRd(), getImm());
            case LDI: return String.format("LDI: R%1X <= Mem[0x%1X + R%1X]", getRd(), getRs(), getRt());
            case STI: return String.format("STI: R%1X => Mem[0x%1X + R%1X]", getRd(), getRs(), getRt());

            case BZ : return "BZ: if Rd == 0 then imm => PC";
            case BP : return "BP: if Rd > 0 then imm => PC";
            case JR : return "JR: Rd => PC";
            case JL : return "JL: Rd <= PC and imm => PC";

            default:
                throw new IllegalArgumentException("Invalid Instruction");
        }
    }

}
