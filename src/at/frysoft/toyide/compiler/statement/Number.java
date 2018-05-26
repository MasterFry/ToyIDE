package at.frysoft.toyide.compiler.statement;

import at.frysoft.toyide.compiler.SyntaxException;

/**
 * ${FILE_NAME}
 * <p>
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class Number extends Param {

    private int value;

    public Number(byte position) {
        super(TYPE_NUMBER, position);
    }

    @Override
    public void read(String param) throws SyntaxException {
        if(param.length() > 2 && param.charAt(0) == '0' && param.charAt(1) == 'x') {
            try {
                value = Integer.parseInt(param.substring(2), 16);
            }catch(NumberFormatException ex) {
                throw new SyntaxException("Param is not a Number!");
            }

        }else {
            try {
                value = Integer.parseInt(param);
            }catch(NumberFormatException ex) {
                throw new SyntaxException("Param is not a Number!");
            }
        }
    }

    @Override
    public int getInt() {
        return value;
    }

    @Override
    public String getString() {
        throw new IllegalArgumentException("Number does not support String values");
    }

}
