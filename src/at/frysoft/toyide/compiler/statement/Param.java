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
public abstract class Param {

    public static final byte TYPE_REGISTER = 1;
    public static final byte TYPE_NUMBER   = 2;
    public static final byte TYPE_LINK     = 3;
    public static final byte TYPE_ADDRESS  = 4;

    public static final byte POS_LINK = -1;
    public static final byte POS_D    =  1;
    public static final byte POS_S    =  2;
    public static final byte POS_T    =  3;
    public static final byte POS_IMM  =  4;


    private final byte type;
    private final byte position;

    protected Param(byte type, byte position) {
        this.type = type;
        this.position = position;
    }

    public byte getPosition() {
        return position;
    }

    public boolean is(byte type) {
        return this.type == type;
    }

    public abstract void read(String param) throws SyntaxException;

    public abstract int getInt();
    public abstract String getString();

    protected String getLink(String param) throws SyntaxException {
        if(param.isEmpty())
            throw new SyntaxException("Symbolic link must not be empty!");

        {
            char c = param.charAt(0);
            if(c < 'A' ||
                    (c > 'Z' && c < 'a' && c != '_') ||
                    c > 'z')
                throw new SyntaxException("Symbolic link must start with a Letter or an Underscore");
        }

        for(char c : param.toCharArray()) {
            if(c < '0' ||
                    (c > '9' && c < 'A') ||
                    (c > 'Z' && c < 'a' && c != '_') ||
                    c > 'z')
                throw new SyntaxException("Symbolic link must only contain Numbers, Letters and Underscores!");
        }

        return param;
    }

}
