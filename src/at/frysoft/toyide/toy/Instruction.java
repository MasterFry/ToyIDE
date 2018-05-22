package at.frysoft.toyide.toy;

import at.frysoft.toyide.toy.instructions.*;

public abstract class Instruction {

    public static final int HLT = 0x0;
    public static final int ADD = 0x1;
    public static final int SUB = 0x2;
    public static final int AND = 0x3;
    public static final int XOR = 0x4;
    public static final int SHL = 0x5;
    public static final int SHR = 0x6;
    public static final int LDA = 0x7;
    public static final int LD  = 0x8;
    public static final int ST  = 0x9;
    public static final int LDI = 0xA;
    public static final int STI = 0xB;
    public static final int BZ  = 0xC;
    public static final int BP  = 0xD;
    public static final int JR  = 0xE;
    public static final int JL  = 0xF;

    public static Instruction getInstruction(PC pc, Memory memory) {
        int instructionCode = memory.read(pc.get());

        switch((instructionCode >> 12) & 0xF) {
            case HLT: return new Hlt(instructionCode);
            case ADD: return new Add(instructionCode);
            case SUB: return new Sub(instructionCode);
            case AND: return new And(instructionCode);
            case XOR: return new Xor(instructionCode);
            case SHL: return new Shl(instructionCode);
            case SHR: return new Shr(instructionCode);
            case LDA: return new Lda(instructionCode);
            case LD : return new Ld (instructionCode);
            case ST : return new St (instructionCode);
            case LDI: return new Ldi(instructionCode);
            case STI: return new Sti(instructionCode);
            case BZ : return new Bz (instructionCode);
            case BP : return new Bp (instructionCode);
            case JR : return new Jr (instructionCode);
            case JL : return new Jl (instructionCode);
            default: return null;
        }
    }

    protected int instruction;

    protected Instruction(int instruction) {
        this.instruction = instruction;
    }

    public void setImm(int imm, boolean areYouSure) {
        if(!areYouSure)
            return;
        instruction = (instruction & 0xFF00) | imm;
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
        return false;
    }

    public abstract String getName();

    public abstract void execute(PC pc, Memory register, Memory memory);

}
