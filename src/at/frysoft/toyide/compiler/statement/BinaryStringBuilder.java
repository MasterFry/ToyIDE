package at.frysoft.toyide.compiler.statement;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class BinaryStringBuilder {

    private int opc;
    private int d;
    private int s;
    private int t;
    private int imm;

    public BinaryStringBuilder() {
        opc = 0;
        d = -1;
        s = -1;
        t = -1;
        imm = -1;
    }

    public void set(byte pos, int value) {
        switch(pos) {
            case Param.POS_D  : d   = value; break;
            case Param.POS_S  : s   = value; break;
            case Param.POS_T  : t   = value; break;
            case Param.POS_IMM: imm = value; break;
        }
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }

    public String getString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%X", opc));

        if(d != -1)
            sb.append(String.format("%X", d));
        else
            sb.append(0);

        if(s != -1 && t != -1)
            sb.append(String.format("%X%X", s, t));

        else if(t != -1)
            sb.append(String.format("0%X", t));

        else if(imm != -1)
            sb.append(String.format("%02X", imm));

        else
            sb.append("00");

        return sb.toString();
    }

}
