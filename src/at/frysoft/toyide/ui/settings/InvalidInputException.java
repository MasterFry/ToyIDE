package at.frysoft.toyide.ui.settings;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class InvalidInputException extends Exception {

    public final String msg;

    public InvalidInputException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
