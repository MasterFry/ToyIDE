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
public class Register extends Param {

    private int value;

    public Register(byte position) {
        super(TYPE_REGISTER, position);
    }

    @Override
    public void read(String param) throws SyntaxException {
        if(param.length() != 2 || param.charAt(0) != 'R')
            throw new SyntaxException("Param is not a Register!");

        char c = param.charAt(1);

        if(c >= '0' && c <= '9')
            value = c - '0';

        else if(c >= 'A' && c <= 'F')
            value = c - 'A' + 10;

        else if(c >= 'a' && c <= 'f')
            value = c - 'a' + 10;

        else
            throw new SyntaxException("Param is not a Register!");
    }

    @Override
    public int getInt() {
        return value;
    }

    @Override
    public String getString() {
        throw new IllegalArgumentException("Register does not support String values");
    }

}
