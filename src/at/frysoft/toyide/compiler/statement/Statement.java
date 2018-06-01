package at.frysoft.toyide.compiler.statement;

import at.frysoft.toyide.compiler.SyntaxException;
import at.frysoft.toyide.ressources.R;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class Statement {

    private int address;
    private int lineIndex;

    private Param[] params;

    private final OPC opc;

    private Statement(OPC opc, boolean link, Param... params) {
        this.opc = opc;

        if(link) {
            this.params = new Param[params.length + 1];
            this.params[0] = new Link();
            System.arraycopy(params, 0, this.params, 1, params.length);

        }else {
            this.params = params;
        }
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public int getAddress() {
        return address;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public OPC getOPC() {
        return opc;
    }

    public int getParameterCount() {
        return params.length;
    }

    public Param getParameter(int index) {
        return params[index];
    }

    public String getLink() {
        if(params.length == 0 || !params[0].is(Param.TYPE_LINK))
            return null;
        return params[0].getString();
    }

    public boolean isCompilerInstruction() {
        return (opc == OPC.ORG || opc == OPC.DW || opc == OPC.DUP);
    }

    public boolean hasAddressParam() {
        if(params.length == 0)
            return false;

        Param last = params[params.length - 1];
        return (last.is(Param.TYPE_ADDRESS) && last.getString() != null);
    }

    public Param getAddressParam() {
        Param last = params[params.length - 1];
        if(last.is(Param.TYPE_ADDRESS) && last.getString() != null)
            return last;

        return null;
    }

    public void readParams(String... params) throws SyntaxException {
        if(this.params.length != params.length)
            throw new SyntaxException("Invalid number of parameters!");

        for(int i = 0; i < params.length; ++i) {
            this.params[i].read(params[i]);
        }
    }

    public String getBinaryString() {
        BinaryStringBuilder bsb = new BinaryStringBuilder();

        if(opc == OPC.PUSH || opc == OPC.POP || opc == OPC.CALL || opc == OPC.RET)
            bsb.set(Param.POS_D, opc.opc);
        else
            bsb.setOpc(opc.opc);

        for(Param param : params) {
            if(param instanceof Link)
                continue;

            bsb.set(param.getPosition(), param.getInt());
        }

        return bsb.getString();
    }

    public static Statement create(String name, boolean link) throws SyntaxException {
        switch(name) {

            case R.strings.toy.instruction.HLT:
                return new Statement(
                        OPC.HLT,
                        link
                );

            case R.strings.toy.instruction.ADD:
                return new Statement(
                        OPC.ADD,
                        link,
                        new Register(Param.POS_D),
                        new Register(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.SUB:
                return new Statement(
                        OPC.SUB,
                        link,
                        new Register(Param.POS_D),
                        new Register(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.AND:
                return new Statement(
                        OPC.AND,
                        link,
                        new Register(Param.POS_D),
                        new Register(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.XOR:
                return new Statement(
                        OPC.XOR,
                        link,
                        new Register(Param.POS_D),
                        new Register(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.SHL:
                return new Statement(
                        OPC.SHL,
                        link,
                        new Register(Param.POS_D),
                        new Register(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.SHR:
                return new Statement(
                        OPC.SHR,
                        link,
                        new Register(Param.POS_D),
                        new Register(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.LDA:
                return new Statement(
                        OPC.LDA,
                        link,
                        new Register(Param.POS_D),
                        new Address()
                );

            case R.strings.toy.instruction.LD :
                return new Statement(
                        OPC.LD,
                        link,
                        new Register(Param.POS_D),
                        new Address()
                );

            case R.strings.toy.instruction.ST :
                return new Statement(
                        OPC.ST,
                        link,
                        new Register(Param.POS_D),
                        new Address()
                );

            case R.strings.toy.instruction.LDI:
                return new Statement(
                        OPC.LDI,
                        link,
                        new Register(Param.POS_D),
                        new Number(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.STI:
                return new Statement(
                        OPC.STI,
                        link,
                        new Register(Param.POS_D),
                        new Number(Param.POS_S),
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.BZ:
                return new Statement(
                        OPC.BZ,
                        link,
                        new Register(Param.POS_D),
                        new Address()
                );

            case R.strings.toy.instruction.BP:
                return new Statement(
                        OPC.BP,
                        link,
                        new Register(Param.POS_D),
                        new Address()
                );

            case R.strings.toy.instruction.JR:
                return new Statement(
                        OPC.JR,
                        link,
                        new Register(Param.POS_D)
                );

            case R.strings.toy.instruction.JL:
                return new Statement(
                        OPC.JL,
                        link,
                        new Register(Param.POS_D),
                        new Address()
                );

            case R.strings.toy.instruction.PUSH:
                return new Statement(
                        OPC.PUSH,
                        link,
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.POP :
                return new Statement(
                        OPC.POP,
                        link,
                        new Register(Param.POS_T)
                );

            case R.strings.toy.instruction.CALL:
                return new Statement(
                        OPC.CALL,
                        link,
                        new Address()
                );

            case R.strings.toy.instruction.RET:
                return new Statement(
                        OPC.RET,
                        link
                );

            case R.strings.compiler.instruction.ORG:
                if(link)
                    throw new SyntaxException(R.strings.compiler.ORG_MUST_NOT_HAVE_LINK);
                return new Statement(
                        OPC.ORG,
                        link,
                        new Number(Param.POS_IMM)
                );

            case R.strings.compiler.instruction.DW :
                if(!link)
                    throw new SyntaxException(R.strings.compiler.DW_MUST_HAVE_LINK);
                return new Statement(
                        OPC.DW,
                        true,
                        new Number(Param.POS_IMM)
                );

            case R.strings.compiler.instruction.DUP:
                if(!link)
                    throw new SyntaxException(R.strings.compiler.DUP_MUST_HAVE_LINK);
                return new Statement(
                        OPC.DUP,
                        true,
                        new Number(Param.POS_IMM)
                );

            default:
                return null;
        }
    }

}
