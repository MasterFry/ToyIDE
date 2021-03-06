package at.frysoft.toyide.compiler;

/**
 * Created by Stefan on 19.05.2018.
 */
public class LinkerException extends Exception {

    public static final String MSG = "Symbolic link was not found: ";

    public final String link;

    public LinkerException(String link) {
        this.link = link;
    }

    @Override
    public String getMessage() {
        return (MSG + link);
    }

}
