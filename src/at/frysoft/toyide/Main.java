package at.frysoft.toyide;

import at.frysoft.toyide.ui.ToyIdeWindow;

import javax.swing.*;

public class Main {

    public static final boolean DEBUG = true;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignore) {
        }

        ToyIdeWindow wnd = new ToyIdeWindow();
        Log.setConsole(wnd.getConsole());
    }

}
