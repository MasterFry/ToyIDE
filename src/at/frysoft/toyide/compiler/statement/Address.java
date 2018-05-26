package at.frysoft.toyide.compiler.statement;

import at.frysoft.toyide.compiler.SyntaxException;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class Address extends Param {

    private Object address;

    protected Address() {
        super(TYPE_ADDRESS, POS_IMM);
    }

    @Override
    public void read(String param) throws SyntaxException {
        if(param.length() > 2 && param.charAt(0) == '0' && param.charAt(1) == 'x') {
            try {
                address = Integer.parseInt(param.substring(2), 16);
            }catch(NumberFormatException ex) {
                address = getLink(param);
            }
        }else
            address = getLink(param);
    }

    @Override
    public int getInt() {
        if(address instanceof Integer)
            return (int) address;
        return -1;
    }

    @Override
    public String getString() {
        if(address instanceof String)
            return (String) address;
        return null;
    }

    public void setAddress(int address) {
        this.address = address;
    }

}
