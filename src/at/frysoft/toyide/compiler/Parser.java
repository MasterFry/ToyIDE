package at.frysoft.toyide.compiler;

import at.frysoft.toyide.Strings;
import at.frysoft.toyide.Utils;
import at.frysoft.toyide.compiler.SyntaxException;
import at.frysoft.toyide.compiler.tuple.*;
import at.frysoft.toyide.toy.Instruction;
import at.frysoft.toyide.toy.instructions.*;

import java.util.Vector;

/**
 * Created by Stefan on 20.05.2018.
 */
public class Parser {

    private static String[] getArgs(String line) {
        line = line.replace('\t', ' ');

        int commentStart = line.indexOf(';');
        if(commentStart != -1)
            line = line.substring(0, commentStart);

        String[] r = line.split(" ");
        Vector<String> args = new Vector<>(r.length);

        for(String s : r) {
            if(!s.isEmpty())
                args.add(s);
        }

        return args.toArray(new String[0]);
    }

    private static int getInstructionCode(String arg) {
        switch(arg.toUpperCase()) {

            case Strings.INSTRUCTION_HLT: return Instruction.HLT;
            case Strings.INSTRUCTION_ADD: return Instruction.ADD;
            case Strings.INSTRUCTION_SUB: return Instruction.SUB;
            case Strings.INSTRUCTION_AND: return Instruction.AND;
            case Strings.INSTRUCTION_XOR: return Instruction.XOR;
            case Strings.INSTRUCTION_SHL: return Instruction.SHL;
            case Strings.INSTRUCTION_SHR: return Instruction.SHR;
            case Strings.INSTRUCTION_LDA: return Instruction.LDA;
            case Strings.INSTRUCTION_LD : return Instruction.LD;
            case Strings.INSTRUCTION_ST : return Instruction.ST;
            case Strings.INSTRUCTION_LDI: return Instruction.LDI;
            case Strings.INSTRUCTION_STI: return Instruction.STI;
            case Strings.INSTRUCTION_BZ : return Instruction.BZ;
            case Strings.INSTRUCTION_BP : return Instruction.BP;
            case Strings.INSTRUCTION_JR : return Instruction.JR;
            case Strings.INSTRUCTION_JL : return Instruction.JL;

            case Strings.COMPILER_INSTRUCTION_ORG: return CompilerInstruction.ORG;
            case Strings.COMPILER_INSTRUCTION_DW : return CompilerInstruction.DW;
            case Strings.COMPILER_INSTRUCTION_DUP: return CompilerInstruction.DUP;

            default: return -1;
        }
    }

    private static void checkParameterInteger(Param... params) throws SyntaxException {
        if(params.length != 1)
            throw new SyntaxException("Wrong parameter count: " + params.length + " expected: 1");
        if(!(params[0] instanceof Param.Integer))
            throw new SyntaxException("Invalid parameter: " + params[0].getString());
    }

    private static void checkParameterRd(Param... params) throws SyntaxException {
        if(params.length != 1)
            throw new SyntaxException("Wrong parameter count: " + params.length + " expected: 1");
        if(!(params[0] instanceof Param.Register))
            throw new SyntaxException("Invalid parameter: " + params[0].getString());
    }

    private static void checkParameterRds(Param... params) throws SyntaxException {
        if(params.length != 2)
            throw new SyntaxException("Wrong parameter count: " + params.length + " expected: 2");
        if(!(params[0] instanceof Param.Register))
            throw new SyntaxException("Invalid parameter: " + params[0].getString());
        if(!(params[1] instanceof Param.Register))
            throw new SyntaxException("Invalid parameter: " + params[1].getString());
    }

    private static void checkParameterRdst(Param... params) throws SyntaxException {
        if(params.length != 3)
            throw new SyntaxException("Wrong parameter count: " + params.length + " expected: 3");
        if(!(params[0] instanceof Param.Register))
            throw new SyntaxException("Invalid parameter: " + params[0].getString());
        if(!(params[1] instanceof Param.Register))
            throw new SyntaxException("Invalid parameter: " + params[1].getString());
        if(!(params[2] instanceof Param.Register))
            throw new SyntaxException("Invalid parameter: " + params[2].getString());
    }

    private static void checkParameterRdImm(Param... params) throws SyntaxException {
        if(params.length != 2)
            throw new SyntaxException("Wrong parameter count: " + params.length + " expected: 2");
        if(!(params[0] instanceof Param.Register))
            throw new SyntaxException("Invalid parameter: " + params[0].getString());
        if(params[1] instanceof Param.Register)
            throw new SyntaxException("Invalid parameter: " + params[1].getString());
    }

    private static Object[] getInstruction(int instructionCode, Param... params) throws SyntaxException {
        Object[] ret = {null, null};
        int imm = 0;

        switch(instructionCode) {

            case Instruction.HLT:
                if(params.length != 0)
                    throw new SyntaxException("Wrong parameter count: " + params.length + " expected: 0");
                break;

            case Instruction.ADD:
            case Instruction.SUB:
            case Instruction.AND:
            case Instruction.XOR:
            case Instruction.SHL:
            case Instruction.SHR:
                checkParameterRdst(params);
                break;

            case Instruction.LDA:
            case Instruction.LD:
            case Instruction.ST:
            case Instruction.BZ:
            case Instruction.BP:
            case Instruction.JL:
                checkParameterRdImm(params);
                if (params[1] instanceof Param.Integer)
                    imm = ((Param.Integer) params[1]).value;
                else
                    ret[1] = params[1];
                break;

            case Instruction.LDI:
            case Instruction.STI:
                checkParameterRds(params);
                break;

            case Instruction.JR:
                checkParameterRd(params);
                break;

            case CompilerInstruction.ORG:
            case CompilerInstruction.DW:
            case CompilerInstruction.DUP:
                checkParameterInteger(params);
                break;

        }

        switch(instructionCode) {

            case Instruction.HLT:
                ret[0] = new Hlt();
                return ret;

            case Instruction.ADD:
                ret[0] = new Add(((Param.Register) params[0]).value,
                                 ((Param.Register) params[1]).value,
                                 ((Param.Register) params[2]).value);
                return ret;

            case Instruction.SUB:
                ret[0] = new Sub(((Param.Register) params[0]).value,
                                 ((Param.Register) params[1]).value,
                                 ((Param.Register) params[2]).value);
                return ret;

            case Instruction.AND:
                ret[0] = new And(((Param.Register) params[0]).value,
                                 ((Param.Register) params[1]).value,
                                 ((Param.Register) params[2]).value);
                return ret;

            case Instruction.XOR:
                ret[0] = new Xor(((Param.Register) params[0]).value,
                                 ((Param.Register) params[1]).value,
                                 ((Param.Register) params[2]).value);
                return ret;

            case Instruction.SHL:
                ret[0] = new Shl(((Param.Register) params[0]).value,
                                 ((Param.Register) params[1]).value,
                                 ((Param.Register) params[2]).value);
                return ret;

            case Instruction.SHR:
                ret[0] = new Shr(((Param.Register) params[0]).value,
                                 ((Param.Register) params[1]).value,
                                 ((Param.Register) params[2]).value);
                return ret;

            case Instruction.LDA:
                ret[0] = new Lda(((Param.Register) params[0]).value, imm);
                return ret;

            case Instruction.LD:
                ret[0] = new Ld(((Param.Register) params[0]).value, imm);
                return ret;

            case Instruction.ST:
                ret[0] = new St(((Param.Register) params[0]).value, imm);
                return ret;

            case Instruction.LDI:
                ret[0] = new Ldi(((Param.Register) params[0]).value, ((Param.Register) params[1]).value);
                return ret;

            case Instruction.STI:
                ret[0] = new Sti(((Param.Register) params[0]).value, ((Param.Register) params[1]).value);
                return ret;

            case Instruction.BZ:
                ret[0] = new Bz(((Param.Register) params[0]).value, imm);
                return ret;

            case Instruction.BP:
                ret[0] = new Bp(((Param.Register) params[0]).value, imm);
                return ret;

            case Instruction.JR:
                ret[0] = new Jr((Instruction.JR << 12) | (((Param.Register) params[0]).value << 8));
                return ret;

            case Instruction.JL:
                ret[0] = new Jl(((Param.Register) params[0]).value, imm);
                return ret;

            case CompilerInstruction.ORG:
                ret[0] = new Org(((Param.Integer) params[0]).value);
                return ret;

            case CompilerInstruction.DW:
                ret[0] = new Dw(((Param.Integer) params[0]).value);
                return ret;

            case CompilerInstruction.DUP:
                ret[0] = new Dup(((Param.Integer) params[0]).value);
                return ret;

            default:
                return ret;

        }
    }

    private static Param parseParam(String param) {
        int value;

        if(param.length() == 2 && param.charAt(0) == 'R') {
            value = Utils.hexCharToInt(param.charAt(1));
            if(value != -1)
                return new Param.Register(value);
        }

        try {
            if (param.startsWith("0x"))
                value = Integer.parseInt(param.substring(2), 16);
            else
                value = Integer.parseInt(param);
            return new Param.Integer(value);

        }catch(NumberFormatException ignored) {
        }

        return new Param.SymbolicLink(param);
    }

    public static Tuple parseLine(String line) throws SyntaxException {

        if(line.isEmpty())
            return null;

        String[] args = getArgs(line);

        if(args.length == 0)
            return null;

        int index = 1;
        String link = null;
        int instructionCode = getInstructionCode(args[0]);

        if(instructionCode == -1) {
            if(args.length < 2)
                throw new SyntaxException("Invalid instruction: " + args[0]);

            link = args[0];

            index = 2;
            instructionCode = getInstructionCode(args[1]);

            if(instructionCode == -1)
                throw new SyntaxException("Invalid instruction: " + args[1]);
        }

        Param[] params = new Param[args.length - index];
        for(int i = 0; i < params.length; ++i) {
            params[i] = parseParam(args[index + i]);
        }

        Object[] instrAndLinkTo = getInstruction(instructionCode, params);

        if(instrAndLinkTo[0] == null)
            throw new SyntaxException("Invalid instruction!");

        if(instructionCode == CompilerInstruction.ORG && link != null)
            throw new SyntaxException("ORG must not have a symbolic link!");

        if((instructionCode == CompilerInstruction.DW || instructionCode == CompilerInstruction.DUP) && link == null)
            throw new SyntaxException("DW and DUP must have a symbolic link!");

        String linkTo = null;
        if(instrAndLinkTo[1] != null)
            linkTo = ((Param.SymbolicLink) instrAndLinkTo[1]).link;

        return new Tuple((Instruction) instrAndLinkTo[0], link, linkTo);
    }

}
