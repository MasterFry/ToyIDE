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
public class Link extends Param {

    private String value;

    public Link() {
        super(TYPE_LINK, POS_LINK);
    }

    @Override
    public void read(String param) throws SyntaxException {
        value = getLink(param);
    }

    @Override
    public int getInt() {
        throw new IllegalArgumentException("Link does not support Integer values");
    }

    @Override
    public String getString() {
        return value;
    }

}
