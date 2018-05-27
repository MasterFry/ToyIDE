package at.frysoft.toyide.settings;

/**
 * Created on : 26.05.2018
 * Last update: 26.05.2018
 * <p>
 * Contributors:
 * Stefan
 */
public class SettingsException extends Exception {

    public final String msg;

    public SettingsException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
