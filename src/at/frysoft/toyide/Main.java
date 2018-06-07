package at.frysoft.toyide;

import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.ui.ToyIdeWindow;

import javax.swing.*;
import java.io.File;

public class Main {

    public static final boolean DEBUG = true;

    // TODO STI only working like: STI RX X RX
    // TODO add undo and undo undo

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignore) { }

        // Load settings file if existent
        {
            File settingsFile = new File("settings.json");
            if(settingsFile.exists() && !settingsFile.isDirectory())
                R.settings.loadSettings();
        }

        // Create the window and start the program
        ToyIdeWindow wnd = new ToyIdeWindow();
        Log.setConsole(wnd.getConsole());
    }

}
