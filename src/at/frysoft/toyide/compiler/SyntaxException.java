package at.frysoft.toyide.compiler;

/**
 * Created by Stefan on 19.05.2018.
 */
public class SyntaxException extends Exception {

    public final String msg;

    public SyntaxException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
