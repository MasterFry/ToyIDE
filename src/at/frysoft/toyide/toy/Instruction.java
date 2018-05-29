package at.frysoft.toyide.toy;

import at.frysoft.toyide.Strings;

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
                    case PUSH: return Strings.INSTRUCTION_PUSH;
                    case POP : return Strings.INSTRUCTION_POP ;
                    case CALL: return Strings.INSTRUCTION_CALL;
                    case RET : return Strings.INSTRUCTION_RET ;
                }
                // Standard Toy Instructions
                return Strings.INSTRUCTION_HLT;

            case ADD: return Strings.INSTRUCTION_ADD;
            case SUB: return Strings.INSTRUCTION_SUB;
            case AND: return Strings.INSTRUCTION_AND;
            case XOR: return Strings.INSTRUCTION_XOR;
            case SHL: return Strings.INSTRUCTION_SHL;
            case SHR: return Strings.INSTRUCTION_SHR;
            case LDA: return Strings.INSTRUCTION_LDA;
            case LD : return Strings.INSTRUCTION_LD ;
            case ST : return Strings.INSTRUCTION_ST ;
            case LDI: return Strings.INSTRUCTION_LDI;
            case STI: return Strings.INSTRUCTION_STI;
            case BZ : return Strings.INSTRUCTION_BZ ;
            case BP : return Strings.INSTRUCTION_BP ;
            case JR : return Strings.INSTRUCTION_JR ;
            case JL : return Strings.INSTRUCTION_JL ;
            default:
                throw new IllegalArgumentException("Invalid Instruction");
        }
    }

}
