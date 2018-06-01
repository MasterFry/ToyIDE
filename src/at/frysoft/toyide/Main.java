package at.frysoft.toyide;

import at.frysoft.toyide.ressources.R;
import at.frysoft.toyide.ui.ToyIdeWindow;

import javax.swing.*;
import java.io.File;

public class Main {

    public static final boolean DEBUG = true;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignore) {
        }

        {
            File settingsFile = new File("settings.json");
            if(settingsFile.exists() && !settingsFile.isDirectory())
                R.settings.loadSettings();
        }
        //R.settings.set(R.settings.WORKSPACE, "F:\\Programmieren\\JAVA\\ToyIDE\\testfiles\\");

        ToyIdeWindow wnd = new ToyIdeWindow();
        Log.setConsole(wnd.getConsole());
    }

}
